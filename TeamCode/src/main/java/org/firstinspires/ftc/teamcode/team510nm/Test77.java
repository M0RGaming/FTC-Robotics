// Copyright Chinmay Patil 2019
package org.firstinspires.ftc.teamcode.team510nm;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;



@TeleOp(name="Test 77", group="Team 510nm")
public class Test77 extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private Robot510nm robot = new Robot510nm();


    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();


        robot.init(hardwareMap);



        waitForStart();
        runtime.reset();

        while (opModeIsActive()) {


            double leftPower;
            double rightPower;



            // POV Mode uses left stick to go forward, and right stick to turn.
            double drive = -gamepad1.left_stick_y;
            double turn  =  gamepad1.right_stick_x;
            leftPower    = Range.clip(drive + turn, -1.0, 1.0) ;
            rightPower   = Range.clip(drive - turn, -1.0, 1.0) ;



            // Tank Mode uses one stick to control each wheel.
            // leftPower  = -gamepad1.left_stick_y ; // y axis is flipped
            // rightPower = -gamepad1.right_stick_y ; // y axis is flipped

            //double liftPower;
            //liftPower = -gamepad2.left_stick_y;



            if (leftPower < 0.01 && leftPower > -0.01) {
                leftPower = 0;
            } else if (rightPower < 0.01 && rightPower > -0.01) {
                rightPower = 0;
            } //else if (liftPower < 0.01 && liftPower > -0.01) {
               // liftPower = 0;
            //}

            double liftPower = 0;
            if (gamepad1.dpad_up) {
                liftPower = 1;
            } else if (gamepad1.dpad_down) {
                liftPower = -1;
            }



            robot.hook.setPower(liftPower);
            robot.leftDriveF.setPower(leftPower);
            robot.rightDriveF.setPower(rightPower);
            robot.leftDriveB.setPower(leftPower);
            robot.rightDriveB.setPower(rightPower);

            // Show the elapsed game time and wheel power.
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("Motors", "left (%.2f), right (%.2f)", leftPower, rightPower);
            telemetry.update();
        }
    }
}
