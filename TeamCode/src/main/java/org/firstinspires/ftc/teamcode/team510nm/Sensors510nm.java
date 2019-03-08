package org.firstinspires.ftc.teamcode.team510nm;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;

/**
 * This is NOT an opmode.
 *
 */

class Sensors510nm {
    OpticalDistanceSensor ods = null; // Has a distance of 7'


    void init(HardwareMap hwMap) {
        ods = hwMap.get(OpticalDistanceSensor.class, "sDist");
    }
}
