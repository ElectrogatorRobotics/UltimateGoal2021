package org.firstinspires.ftc.teamcode.library;
import org.firstinspires.ftc.teamcode.library.ElectorgatorHardware;
import org. firstinspires.ftc.teamcode.library.Drive;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.hardware.motors.RevRobotics20HdHexMotor;

public interface Arm{


    //void Arm(HardwareMap HardwareMap);
    void setExtendSpeed (double Power);
    void setExtendPosition (int targetPosition );
    void grip();
    void release();

    void ringGrabberPosition(double position);




//  function to bend servo2 with remote?

// function to set an x and y coordinate

    /* have servo 3 work on gripping
     * set servo to power (x)
     * set servo to power (y)
     * have the grip init
     * have the ribo let go
     */
}


