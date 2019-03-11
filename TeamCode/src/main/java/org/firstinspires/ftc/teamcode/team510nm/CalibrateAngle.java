

package org.firstinspires.ftc.teamcode.team510nm;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

//import com.qualcomm.robotcore.eventloop.opmode.Disabled;


@Autonomous(name="Calibrate Angle (77)", group="Team 510nm")
public class CalibrateAngle extends LinearOpMode {

    private Robot510nm robot = new Robot510nm();   // Use Robots hardware
    private ElapsedTime runtime = new ElapsedTime();


    @Override
    public void runOpMode() {

        robot.init(hardwareMap);
        robot.resetEncoders();

        encoderDrive(0.8,-5,5,5); // 5 inches

        waitForStart();

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