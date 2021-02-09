package org.firstinspires.ftc.teamcode.library;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.hardware.motors.RevRobotics20HdHexMotor;
import com.qualcomm.robotcore.hardware.ServoImplEx;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;
import com.qualcomm.robotcore.util.ElapsedTime;

public class ArmImpl implements Arm {
     public DcMotor wgmotor;
     public Servo wggrip;
     public Servo ringSnagger;

     public static final double rotateTicksPerDegree = 1478.4 *     //the encoder ticks per revolution. not sure if includes gearing.
                                                        5 /        // 5:1 gear ratio reduction. WE GOTTA FIGURE THIS OUT!
                                                        360;       //ticks per angle!?!

     public ArmImpl(HardwareMap hwm) {
         wgmotor = (DcMotorEx) hwm.dcMotor.get("uppy Downy craney thingy");
         wgmotor.setMotorType(MotorConfigurationType.getMotorType(RevRobotics20HdHexMotor.class));
         wgmotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
         wgmotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

         wggrip = hwm.servo.get("wg lock");
         wggrip.scaleRange(0.1, 0.5);

        ringSnagger = hwm.servo.get("Ring Arm");
        ringSnagger.scaleRange(0.32, 0.82);

     }

    @Override
    public void setExtendSpeed(double Power) {
        wgmotor.setPower(Power);
    }

    @Override
    public void setExtendPosition(int targetPosition) {

    }

    @Override
    public void grip() {
        wggrip.setPosition(0);
    }

    @Override
    public void release() {
         wggrip.setPosition(1); //fling it wide open.
    }

    @Override
    public void ringGrabberPosition(double position) {
        ringSnagger.setPosition(position);
    }

    // function to set an x and y coordinate

    /* have servo 3 work on gripping
     * set servo to power (x)
     * set servo to power (y)
     * have the grip init
     * have the ribo let go
     */

}
