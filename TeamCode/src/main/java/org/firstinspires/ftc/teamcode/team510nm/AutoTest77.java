

package org.firstinspires.ftc.teamcode.team510nm;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;



@Autonomous(name="Auto Test 77", group="Team 510nm")
//@Disabled
public class AutoTest77 extends LinearOpMode {

    private Robot510nm robot = new Robot510nm();   // Use Robots hardware
    private ElapsedTime runtime = new ElapsedTime();


    @Override
    public void runOpMode() {

        robot.init(hardwareMap);


        telemetry.addData("Status", "Resetting Encoders");
        telemetry.update();


        robot.resetEncoders();



        telemetry.addData("Path0Front",  "Front at %7d :%7d", robot.leftDriveF.getCurrentPosition(), robot.rightDriveF.getCurrentPosition());
        telemetry.addData("Path0Back",  "Back at %7d :%7d", robot.leftDriveB.getCurrentPosition(), robot.rightDriveB.getCurrentPosition());
        telemetry.update();


        waitForStart();

        /* OLD PSEUDO CODE

        //turn 45 degrees to the left

        encoderDrive(0.6,18,18,5);

        //turn 180 degrees

        //Scan picture

        //turn 180 degrees

        */

        /*
            Stating Position -> Assume edge of line
                17.25 x 17.25 inches out
                24.395 inches diagonally from center
            Target Position
                Distance at tile
                    At middle of the next square
                    12 x 12 inches out
                    16.971 inches diagonally from corner of tile
                Distance to the tile
                    24 - 17.25 = 6.75 inches from edge of line to edge of tile
                    6.75 x 6.75 inches out
                    3.674 inches from edge of line to next tile

            Distance to travel
                16.971 + 3.674 inches to mineral from edge of start line

            Wont have to subtract about 2 inches, because we have extra space in our chassis.
         */


        // Assume starting in correct direction
        encoderDrive(0.5,20.645,20.645,5); // Total of 5s

        /*
            Read color, if gold then push it 2 inches off
            If not gold
                backup so that the center of the robot is at the corer of the tile (0.5*16.971)
                turn 45 degrees right so we are facing another mineral
                Drive up to it (12 inches)


            Worst Case:
                Drive Past mineral (pushing it) until halfway through following tile
                Detect if crater or not (touch or distance sensor)
                Turn 90˚ So that we are either facing depot or in crater (partially)


         */
        boolean gold = true;
        //Detect Gold Here (1s)

        if (gold) { // Best Case
            encoderDrive(0.5,2,2,3); // Total of 9s

            // Detect Distance ahead (1s)
            double distance = 50.91;

            if (distance > 18) {
                encoderDrive(0.8,32,32,3); // Total of 13s

                // Drop Team Marker (1s)

                // Turn 45˚ Right (1s)

                encoderDrive(0.8,84,84,10); // Total of 25


            } else {

            }



        } else {
            encoderDrive(0.5,-8.485,-8.485,3); // Total of 9s

            // Turn Right 45˚ Here (1s)

            encoderDrive(0.5,12,12,3); // Total of 13s

            //Detect Gold Here (1s)

            if (gold) {

                encoderDrive(0.5,2,2,3); // Total of 17s

                // Detect Crater Here
                boolean crater = true;

                // Turn 90 left (1s)

                if (!crater) {
                    encoderDrive(1,24,24,3); // Total of 21s
                    // Drop team marker (1s)
                    encoderDrive(1,84,84,5); // Total of 27s
                }




            } else { // Worst Case Scenario

                encoderDrive(0.5,-12,-12,3); // Total of 17s

                // Turn Left 90˚ Here (1s)

                encoderDrive(1, 14,14,3); // Total of 21s

                // Detect Crater Here
                boolean crater = true;

                // Turn 90 right (1s)

                if (!crater) {
                    encoderDrive(1,24,24,3); // Total of 25s
                    // Drop team marker (1s)
                }

            }

        }




        telemetry.addData("Path", "Complete");
        telemetry.update();
    }


    private void encoderDrive(double speed, double leftInches, double rightInches, double timeoutS) {
        int newLeftTargetF;
        int newRightTargetB;
        int newLeftTargetB;
        int newRightTargetF;

        double CPI = robot.COUNTS_PER_INCH;

        // Ensure that the opmode is still active
        if (opModeIsActive()) {

            //determine target
            newLeftTargetF = robot.leftDriveF.getCurrentPosition() + (int)(leftInches * CPI);
            newRightTargetB = robot.rightDriveB.getCurrentPosition() + (int)(rightInches * CPI);
            newLeftTargetB = robot.leftDriveB.getCurrentPosition() + (int)(leftInches * CPI);
            newRightTargetF = robot.rightDriveF.getCurrentPosition() + (int)(rightInches * CPI);



            robot.leftDriveB.setTargetPosition(newLeftTargetB);
            robot.rightDriveF.setTargetPosition(newRightTargetF);
            robot.leftDriveF.setTargetPosition(newLeftTargetF);
            robot.rightDriveB.setTargetPosition(newRightTargetB);

            // Turn On RUN_TO_POSITION
            robot.setWheelMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            robot.setAllWheelPower(Math.abs(speed));


            while (
                    opModeIsActive() && (
                            runtime.seconds() < timeoutS
                    ) && (
                            robot.leftDriveB.isBusy()
                                    && robot.rightDriveB.isBusy()
                                    && robot.leftDriveF.isBusy()
                                    && robot.rightDriveF.isBusy()
                    )) {

                // Display it for the driver, the path 1 is the target, the path 2 is the current count.
                telemetry.addData("Path1Front",  "Running to %7d :%7d", newLeftTargetF,  newRightTargetF);
                telemetry.addData("Path1Back",  "Running to %7d :%7d", newLeftTargetB,  newRightTargetB);
                telemetry.addData("Path2Front",  "Running at %7d :%7d", robot.leftDriveF.getCurrentPosition(), robot.rightDriveF.getCurrentPosition());
                telemetry.addData("Path2Back",  "Running at %7d :%7d", robot.leftDriveB.getCurrentPosition(), robot.rightDriveB.getCurrentPosition());
                telemetry.update();
            }

            // Stop all motion;
            robot.stopWheels();

            // Turn off RUN_TO_POSITION
            robot.setWheelMode(DcMotor.RunMode.RUN_USING_ENCODER);

            sleep(250);   // optional pause after each move
        }
    }
}