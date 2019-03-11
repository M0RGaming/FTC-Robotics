/* Copyright (c) 2019 CHINMAY. All rights reserved.
 *
 */

package org.firstinspires.ftc.teamcode.team510nm;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * This is NOT an opmode.
 *
 */
class Robot510nm {
    /* Public OpMode members. */
    DcMotor leftDriveB = null;
    DcMotor rightDriveB = null;
    DcMotor leftDriveF = null;
    DcMotor rightDriveF = null;
    DcMotor hook = null;
    DcMotor marker = null;

    Sensors510nm sensors = new Sensors510nm();



    // Use the following as a preliminary measure (in theory it should work, but loss of grip exists)
    private static final double COUNTS_PER_MOTOR_REV = 1440 ;
    private static final double DRIVE_GEAR_REDUCTION = 1.0 ; // This is < 1.0 if geared UP
    private static final double WHEEL_DIAMETER_INCHES = 4.0 ;
    double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * 3.1415);


    // If above isn't calibrated, use the following code instead, by doing some basic calibrations on the field
    //private static final double INCHES_PER_COUNT = 4;
    //double COUNTS_PER_INCH = 1/INCHES_PER_COUNT;

    double ANGLE = 30;
    double INCHES_PER_90_DEGREES = 5;




    void init(HardwareMap hwMap) {


        leftDriveF  = hwMap.get(DcMotor.class, "ldf");
        rightDriveF = hwMap.get(DcMotor.class, "rdf");
        leftDriveB  = hwMap.get(DcMotor.class, "ldb");
        rightDriveB = hwMap.get(DcMotor.class, "rdb");
        hook = hwMap.get(DcMotor.class, "hook");
        marker = hwMap.get(DcMotor.class, "marker");

        sensors.init(hwMap);





        leftDriveB.setDirection(DcMotor.Direction.FORWARD);
        leftDriveF.setDirection(DcMotor.Direction.REVERSE);
        rightDriveB.setDirection(DcMotor.Direction.REVERSE);
        rightDriveF.setDirection(DcMotor.Direction.REVERSE);
        hook.setDirection(DcMotor.Direction.FORWARD);

        hook.setPower(0);
        setAllWheelPower(0);


        setWheelMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }

    void resetEncoders() {
        leftDriveB.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftDriveF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightDriveB.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightDriveF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftDriveB.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightDriveB.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftDriveF.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightDriveF.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    void setWheelMode(DcMotor.RunMode runmode) {
        leftDriveB.setMode(runmode);
        rightDriveB.setMode(runmode);
        leftDriveF.setMode(runmode);
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

