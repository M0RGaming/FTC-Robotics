

package org.firstinspires.ftc.teamcode.team510nm;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

//import com.qualcomm.robotcore.eventloop.opmode.Disabled;


@Autonomous(name="Calibrate Distance (77)", group="Team 510nm")
public class CalibrateDistance extends LinearOpMode {

    private Robot510nm robot = new Robot510nm();   // Use Robots hardware
    private ElapsedTime runtime = new ElapsedTime();


    @Override
    public void runOpMode() {

        robot.init(hardwareMap);
        robot.resetEncoders();

        waitForStart();

        encoderDrive(0.8,2000,5);




    }


    private void encoderDrive(double speed, int counts, double timeoutS) {
        int newLeftTargetF;
        int newRightTargetB;
        int newLeftTargetB;
        int newRightTargetF;


        // Ensure that the opmode is still active
        if (opModeIsActive()) {

            //determine target
            newLeftTargetF = robot.leftDriveF.getCurrentPosition() + counts;
            newRightTargetB = robot.rightDriveB.getCurrentPosition() + counts;
            newLeftTargetB = robot.leftDriveB.getCurrentPosition() + counts;
            newRightTargetF = robot.rightDriveF.getCurrentPosition() + counts;



            robot.leftDriveB.setTargetPosition(newLeftTargetB);
            robot.rightDriveF.setTargetPosition(newRightTargetF);
            robot.leftDriveF.setTargetPosition(newLeftTargetF);
            robot.rightDriveB.setTargetPosition(newRightTargetB);

            telemetry.addData("Path1Front",  "Running to %7d :%7d", newLeftTargetF,  newRightTargetF);
            telemetry.addData("Path1Back",  "Running to %7d :%7d", newLeftTargetB,  newRightTargetB);
            telemetry.addData("Path2Front",  "Running at %7d :%7d", robot.leftDriveF.getCurrentPosition(), robot.rightDriveF.getCurrentPosition());
            telemetry.addData("Path2Back",  "Running at %7d :%7d", robot.leftDriveB.getCurrentPosition(), robot.rightDriveB.getCurrentPosition());
            telemetry.update();

            sleep(250);

            // Turn On RUN_TO_POSITION
            robot.leftDriveB.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.rightDriveF.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.leftDriveB.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.rightDriveF.setMode(DcMotor.RunMode.RUN_TO_POSITION);

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