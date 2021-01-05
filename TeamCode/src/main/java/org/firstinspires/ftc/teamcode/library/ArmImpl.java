package org.firstinspires.ftc.teamcode.library;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.hardware.motors.RevRobotics20HdHexMotor;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;
import com.qualcomm.robotcore.util.ElapsedTime;

public class ArmImpl implements Arm {
     public DcMotor rotate;
     public DcMotor extend;
     public Servo grip;
     public Servo FD1;
     public Servo FD2;

     public static final double rotateTicksPerDegree = 1478.4 *     //the encoder ticks per revolution. not sure if includes gearing.
                                                        5 /        // 5:1 gear ratio reduction. WE GOTTA FIGURE THIS OUT!
                                                        360;       //ticks per angle!?!

     public ArmImpl(HardwareMap hwm) {
         rotate = (DcMotorEx) hwm.dcMotor.get("rotate arm");
         rotate.setMotorType(MotorConfigurationType.getMotorType(RevRobotics20HdHexMotor.class));
         rotate.setDirection(DcMotorSimple.Direction.FORWARD);
         rotate.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
         rotate.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
         rotate.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


         extend = (DcMotorEx) hwm.dcMotor.get("extend arm");
         extend.setMotorType(MotorConfigurationType.getMotorType(RevRobotics20HdHexMotor.class));
         extend.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
         extend.setDirection(DcMotorSimple.Direction.FORWARD);
         extend.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

         grip = hwm.servo.get("grip arm");
         grip.scaleRange(0.2, 0.8);
         FD1 = hwm.servo.get("grab FD");
         FD1.scaleRange(0.24,0.74);
         FD2 = hwm. servo.get("grab FD 2");
         FD2. scaleRange(0.31,0.61);

     }

    @Override
    public void armAngle(double armAngle, LinearOpMode lom) {
        rotate.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        long position = Math.round(rotate.getCurrentPosition() + (armAngle * rotateTicksPerDegree));
        rotate.setTargetPosition((int)position);
        setAngleSpeed(1);
        do {
            Thread.yield(); //effectively what the LinearOpMode idle call does
        } while (rotate.isBusy() && lom.opModeIsActive());
        rotate.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void armAngle_bytime(double seconds, LinearOpMode lom) {
        ElapsedTime et = new ElapsedTime();
        et.reset();
        int power = (seconds<0?-1:1);
        long millisecs = Math.round(Math.abs(seconds) * 1000);
        rotate.setPower(power);
        while (et.milliseconds() < millisecs && lom.opModeIsActive()){
            //waiting and waiting
            Thread.yield();
        }
        rotate.setPower(0); //STOP! In the name of love.....
                                    // Can still clean it up. But i like it so I will leave it.
    }

    @Override
    public void setAngleSpeed(double Power) {
        rotate.setPower(Power);
    }

    @Override
    public void setExtendSpeed(double Power) {
        extend.setPower(Power);
    }

    @Override
    public void setExtendPosition(int targetPosition) {

    }

    @Override
    public void grip() {
        grip.setPosition(1);
    }

    @Override
    public void release() {
         grip.setPosition(0); //fling it wide open.
    }

    @Override
    public void setGripPosition(double targetPosition) {
        grip.setPosition(targetPosition);
    }

    @Override
    public void grabFD(){
         FD1.setPosition(1);
         FD2.setPosition(1);
         //servo1 set for maybe .7
         //servo2 set for maybe .6
    }

     @Override
    public void releaseFD(){
         FD1.setPosition(0);
         FD2.setPosition(0);
         //set both servos to probably .25
     }



// function to set an x and y coordinate

    /* have servo 3 work on gripping
     * set servo to power (x)
     * set servo to power (y)
     * have the grip init
     * have the ribo let go
     */

}
