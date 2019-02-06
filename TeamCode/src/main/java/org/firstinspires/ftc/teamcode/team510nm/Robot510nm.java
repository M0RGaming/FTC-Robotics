/* Copyright (c) 2017 CHINMAY. All rights reserved.
 *
 */

package org.firstinspires.ftc.teamcode.team510nm;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
//import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * This is NOT an opmode.
 *
 */
public class Robot510nm {
    /* Public OpMode members. */
    DcMotor leftDriveB = null;
    DcMotor rightDriveB = null;
    DcMotor leftDriveF = null;
    DcMotor rightDriveF = null;
    DcMotor shoulder = null;
    DcMotor elbow = null;
    Servo wrist = null;
    Servo claw = null;

    private static final double COUNTS_PER_MOTOR_REV = 1440 ;
    private static final double DRIVE_GEAR_REDUCTION = 2.0 ; // This is < 1.0 if geared UP
    private static final double WHEEL_DIAMETER_INCHES = 4.0 ;


    double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * 3.1415);

    //public static final double MID_SERVO = 0.5;


    //private ElapsedTime period  = new ElapsedTime();

    //init
    void init(HardwareMap hwMap) {


        leftDriveF  = hwMap.get(DcMotor.class, "ldf");
        rightDriveF = hwMap.get(DcMotor.class, "rdf");
        leftDriveB  = hwMap.get(DcMotor.class, "ldb");
        rightDriveB = hwMap.get(DcMotor.class, "rdb");
        shoulder    = hwMap.get(DcMotor.class, "shoulder");
        elbow       = hwMap.get(DcMotor.class, "elbow");
        wrist       = hwMap.get(Servo.class, "wrist");
        claw        = hwMap.get(Servo.class, "claw");


        leftDriveB.setDirection(DcMotor.Direction.REVERSE);
        leftDriveF.setDirection(DcMotor.Direction.REVERSE);
        rightDriveB.setDirection(DcMotor.Direction.FORWARD);
        rightDriveF.setDirection(DcMotor.Direction.FORWARD);


        setAllWheelPower(0);
        shoulder.setPower(0);
        elbow.setPower(0);


        setWheelMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //elbow.setDirection(Servo.Direction.REVERSE);
        wrist.setDirection(Servo.Direction.REVERSE);

        claw.setPosition(0);
        wrist.setPosition(0);
    }

    void resetEncoders() {
        leftDriveB.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftDriveF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightDriveB.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightDriveF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftDriveB.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightDriveF.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftDriveB.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightDriveF.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    void setWheelMode(DcMotor.RunMode runmode) {
        leftDriveB.setMode(runmode);
        rightDriveF.setMode(runmode);
        leftDriveB.setMode(runmode);
        rightDriveF.setMode(runmode);
    }

    void setAllWheelPower(double powerin) {
        leftDriveB.setPower(powerin);
        leftDriveF.setPower(powerin);
        rightDriveB.setPower(powerin);
        rightDriveF.setPower(powerin);
    }

    void stopWheels() {
        setAllWheelPower(0);
    }

}

