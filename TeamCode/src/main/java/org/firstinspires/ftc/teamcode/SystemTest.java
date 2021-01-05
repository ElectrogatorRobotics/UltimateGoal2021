
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.library.ElectorgatorHardware;

import com.qualcomm.robotcore.util.ElapsedTime;


@TeleOp(name = "SysFunc Test", group ="Diagnostics")
@Disabled
public class SystemTest extends LinearOpMode {
    ElectorgatorHardware hardware = new ElectorgatorHardware();

    @Override
    public void runOpMode() throws InterruptedException {
        // initialise the motors
        telemetry.addLine("Initialising... please wait.");
        telemetry.update();

        hardware.initMotors(hardwareMap);

        telemetry.addLine("Ready to start... thank you for waiting!");
        telemetry.addLine("Press A for motors or B for servos (C1)");
        telemetry.update();

        waitForStart();

        ElapsedTime et = new ElapsedTime();

        while(!gamepad1.a && !gamepad1.b);
        if(gamepad1.a) {
            while(gamepad1.a);
            //Front Left Drive
            telemetry.addLine("Running Front Left Drive");
            telemetry.update();
            hardware.frontLeftDrive.setPower(.6);
            while (!gamepad1.a)
                et.reset();
            hardware.frontLeftDrive.setPower(0);
            while (et.milliseconds() < 1000) ;

            //Front Right Drive
            telemetry.addLine("Running Front Right Drive");
            telemetry.update();
            hardware.frontRightDrive.setPower(.6);
            while (!gamepad1.a)
                et.reset();
            hardware.frontRightDrive.setPower(0);
            while (et.milliseconds() < 1000) ;

            //Back Left Drive
            telemetry.addLine("Running Back Left Drive");
            telemetry.update();
            hardware.backLeftDrive.setPower(.6);
            while (!gamepad1.a)
                et.reset();
            hardware.backLeftDrive.setPower(0);
            while (et.milliseconds() < 1000) ;

            //Back Right Drive
            telemetry.addLine("Running Back Right Drive");
            telemetry.update();
            hardware.backRightDrive.setPower(.6);
            while (!gamepad1.a)
                et.reset();
            hardware.backRightDrive.setPower(0);
            while (et.milliseconds() < 1000) ;

        }
        else if(gamepad1.b) {
            while(gamepad1.b);
            float servo = 0.5f;
            while (!gamepad1.right_bumper) {
                if (gamepad1.a) {
                    servo -= .2;
                } else if (gamepad1.b) {
                    servo += .2;
                } else if (gamepad1.x) {
                    servo -= .1;
                } else if (gamepad1.y) {
                    servo += .1;
                }
                servo = Math.min(.8f, Math.max(.2f, servo));
                hardware.wobbleCap.setPosition(servo);

                telemetry.addData("Grip Servo = ", servo);
                telemetry.update();
            }
            while (gamepad1.right_bumper) ;

        }
    }
}
