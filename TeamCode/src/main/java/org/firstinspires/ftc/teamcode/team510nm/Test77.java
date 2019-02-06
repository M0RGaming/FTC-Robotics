
package org.firstinspires.ftc.teamcode.team510nm;

//import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//import com.qualcomm.robotcore.hardware.DcMotor;
//import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;



@TeleOp(name="Test 77", group="Team 510nm")
//@Disabled
public class Test77 extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private Robot510nm robot = new Robot510nm();


    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();


        robot.init(hardwareMap);


        int currentSel;
        double shoulderpower;
        double elbowpower;
        double wristpos = 0;
        double clawpos = 0;
        double selpos;


        waitForStart();
        runtime.reset();

        while (opModeIsActive()) {


            double leftPower;
            double rightPower;



            // POV Mode uses left stick to go forward, and right stick to turn.
            // - This uses basic math to combine motions and is easier to drive straight.
            double drive = -gamepad1.left_stick_y;
            double turn  =  gamepad1.right_stick_x;
            leftPower    = Range.clip(drive + turn, -1.0, 1.0) ;
            rightPower   = Range.clip(drive - turn, -1.0, 1.0) ;

            // Tank Mode uses one stick to control each wheel.
            // - This requires no math, but it is hard to drive forward slowly and keep straight.
            // leftPower  = -gamepad1.left_stick_y ;
            // rightPower = -gamepad1.right_stick_y ;




            // Get Arm options

            if (gamepad2.dpad_left) {
                currentSel = 1;
            } else if (gamepad2.dpad_right) {
                currentSel = 2;
            } else if (gamepad2.dpad_down) {
                currentSel = 3;
            } else if (gamepad2.dpad_up) {
                currentSel = 4;
            } else {
                currentSel = 0;
            }


            selpos = 0;
            shoulderpower = 0;
            elbowpower = 0;

            if (currentSel == 3) {


                if (gamepad2.left_bumper) {
                    shoulderpower = -0.5;
                } else if (gamepad2.right_bumper) {
                    shoulderpower = 0.5;
                }


            } else if (currentSel == 1) {

                if (gamepad2.left_bumper) {
                    elbowpower = -0.5;
                } else if (gamepad2.right_bumper) {
                    elbowpower = 0.5;
                }

            } else {


                if (currentSel == 4) {
                    selpos = clawpos;
                } else if (currentSel == 2) {
                    selpos = wristpos;
                }



                if (gamepad2.left_bumper && selpos > 0.0) {
                    selpos -= 0.02;
                } else if (gamepad2.right_bumper && selpos < 1.0) {
                    selpos += 0.02;
                }



                if (currentSel == 4) {
                    clawpos = selpos;
                } else if (currentSel == 2) {
                    wristpos = selpos;
                }


            }



            //send all powers/positions
            robot.shoulder.setPower(shoulderpower);
            robot.elbow.setPower(elbowpower);
            robot.claw.setPosition(clawpos);
            robot.wrist.setPosition(wristpos);

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
