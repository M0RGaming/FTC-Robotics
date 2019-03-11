

package org.firstinspires.ftc.teamcode.team510nm;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

//import com.qualcomm.robotcore.eventloop.opmode.Disabled;


@Autonomous(name="Auto Test 77D", group="Team 510nm")
//@Disabled
public class AutoTest77D extends LinearOpMode {

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

        //Unlatch

        //Turn left 45˚
        //Drive 47.6 inches back (Double Check measurement tomorrow)
        //Turn left 90˚
        //Drive Forward about 4 inches
        //Detect if crater
            // IF crater, backup 72 Inches
            // Turn 180
            // Drop Marker
            // Backup 75 Inches
            // STOP
        // IF not crater
            //Turn 90˚ Right
            //Drive 4 inches
            //Test if mineral
                // IF mineral
                // drive 4 inches
                // backup 4 inches
                // Turn 90˚ left
                // Drive Forward 24 Inches
                // Drop Marker
                // Backup 75 Inches
                // STOP
            //IF not mineral
                //Turn 90˚ left
                //Drive 20 inches
                //Turn 90˚ right
                //Drive 12 inches forward
                //Test if mineral
                    // IF mineral
                    // Drive 4 inches forward
                    // Drive 16 inches back
                    // Turn 90˚ left
                    // Drive Forward 12 Inches
                    // Drop Marker
                    // Backup 75 Inches
                    // STOP
                //IF not mineral
                    //Turn 90˚ left
                    //Drive 20 inches
                    //Turn 90˚ right
                    //Drive 16 inches forward (Knock off mineral)
                    //Back up 28 inches
                    // Turn 90˚ left
                    // Drive Forward 4 Inches
                    // Drop Marker
                    // Backup 75 Inches
                    // STOP

        unlatch();
        turn(0.8,45,2);
        encoderDrive(0.8,-47.6,-47.6,5);
        turn(0.8,90,2);
        encoderDrive(0.8,4,4,2);

        boolean crater = false;
        if (crater) {
            encoderDrive(1,72,72,7);
            turn(0.8,180,3);
            dropMarker();
            encoderDrive(1,-75,-75,10);
        } else {
            turn(0.8,-90,2);
            encoderDrive(0.8,4,4,2);


            boolean mineral = detectMineral();
            sleep(250);
            if (mineral) {
                encoderDrive(0.8,4,4,2);
                encoderDrive(0.8,-4,-4,2);
                turn(0.8,90,2);
                encoderDrive(1,24,24,5);
                dropMarker();
                encoderDrive(1,-75,-75,10);
            } else {
                turn(0.8,90,2);
                encoderDrive(1,20,20,5);
                turn(0.8,-90,2);
                encoderDrive(1,12,12,5);


                mineral = detectMineral();
                sleep(250);
                if (mineral) {
                    encoderDrive(0.8,4,4,2);
                    encoderDrive(0.8,-16,-16,5);
                    turn(0.8,90,2);
                    encoderDrive(1,12,12,5);
                    dropMarker();
                    encoderDrive(1,-75,-75,10);
                } else {
                    turn(0.8,90,2);
                    encoderDrive(1,20,20,5);
                    turn(0.8,-90,2);
                    encoderDrive(1,16,16,5);
                    encoderDrive(1,-28,-28,5);
                    turn(0.8,90,2);
                    encoderDrive(0.8,4,4,3);
                    dropMarker();
                    encoderDrive(1,-75,-75,10);
                }
            }

        }








        telemetry.addData("Path", "Complete");
        telemetry.update();
    }



    private boolean detectMineral() {
        // bLedOn represents the state of the LED.
        boolean bLedOn = true;

        // Set the LED in the beginning
        robot.sensors.color.enableLed(bLedOn);

        sleep(250);

        if (robot.sensors.color.red() > 150 && robot.sensors.color.green() > 150 && robot.sensors.color.blue() < 200) {
            return true;
        } else {
            return false;
        }

    }

    private boolean detectCrater() {
        sleep(250);
        if (robot.sensors.ods.getLightDetected() > 0.5) {
            return true;
        } else {
            return false;
        }
    }

    private void dropMarker() {
        ElapsedTime NewRuntime = new ElapsedTime();
        robot.marker.setPower(1);
        NewRuntime.reset();
        while (opModeIsActive() && (NewRuntime.seconds() < 2.0)) {
            telemetry.addData("Path", "Leg 1: %2.5f S Elapsed", NewRuntime.seconds());
            telemetry.update();
        }
        robot.marker.setPower(0);
    }
    private void unlatch() {
        ElapsedTime NewRuntime = new ElapsedTime();
        robot.hook.setPower(1);
        NewRuntime.reset();
        while (opModeIsActive() && (NewRuntime.seconds() < 2.5)) {
            telemetry.addData("Path", "Leg 1: %2.5f S Elapsed", NewRuntime.seconds());
            telemetry.update();
        }
        robot.hook.setPower(0);
    }

    private void turn(double speed, double angle, double timeoutS) {
        double distance = (robot.ANGLE/angle)*robot.INCHES_PER_90_DEGREES;
        encoderDrive(speed, -distance, distance, timeoutS);
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

            //sleep(250);   // optional pause after each move
        }
    }
}