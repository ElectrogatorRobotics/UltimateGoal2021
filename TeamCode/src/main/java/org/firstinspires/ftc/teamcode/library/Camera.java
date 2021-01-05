package org.firstinspires.ftc.teamcode.library;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by Hal on 11/14/2017.
 */

public interface Camera {

     void init(HardwareMap hardwareMap);
     boolean scan(int timeout);
    void setTelemetry(Telemetry telemetry);

     Float getX();
     Float getY();
     Float getAngle();
}
