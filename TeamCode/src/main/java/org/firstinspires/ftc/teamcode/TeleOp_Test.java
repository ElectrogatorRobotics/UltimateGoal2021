
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.library.Drive;
import org.firstinspires.ftc.teamcode.library.DriveImpl;
import org.firstinspires.ftc.teamcode.library.ElectorgatorHardware;

/**
 * Created by Luke on 10/1/2017.
 */

@TeleOp(name = "TeleOp-Move Only", group ="Diagnostics")
@Disabled
public class TeleOp_Test extends LinearOpMode {
    ElectorgatorHardware hardware = new ElectorgatorHardware();
	Drive drive;

    double frontLeftDrive, frontRightDrive, backRightDrive, backLeftDrive;
    boolean startRelic = false;
    double maxDrive = 0;

    @Override
    public void runOpMode() throws InterruptedException {
        // initialise the motors
        telemetry.addLine("Initialising... please wait.");
        telemetry.update();

        double throtle;

        drive = new DriveImpl();

        drive.initDrive(hardwareMap);
        drive.setTelemetry(telemetry);
        drive.passLinearOp(this);

        drive.setMotorMode(Drive.MotorMode.POWER);

        telemetry.addLine("Ready to start... thank you for waiting!");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            // calculate the motor speeds
//            frontRightDrive = gamepad1.left_stick_y + gamepad1.right_stick_x + gamepad1.left_stick_x;
//            frontLeftDrive  = gamepad1.left_stick_y - gamepad1.right_stick_x - gamepad1.left_stick_x;
//            backRightDrive  = gamepad1.left_stick_y + gamepad1.right_stick_x - gamepad1.left_stick_x;
//            backLeftDrive   = gamepad1.left_stick_y - gamepad1.right_stick_x + gamepad1.left_stick_x;

            // calculate the throttle position that will be used when calculating the motor powers
            throtle = Math.max(gamepad1.left_trigger,.4); //throtle = drive.throttleControl(gamepad1.left_trigger, .4);

            /**
             * Calculate the power of each motor by multiplying the left Y-axes and the left X-axes that are
             * used for driving normal by the throttle value that we calculated above. The right X-axes is
             * not multiplied by the throttle, because it is used for sliding sideways and can not be controlled
             * efficiently with the throttle due to the high power requirements of sliding.
             */
            frontRightDrive = ((gamepad1.left_stick_y * throtle) + (gamepad1.left_stick_x * throtle) + gamepad1.right_stick_x);
            frontLeftDrive  = ((gamepad1.left_stick_y * throtle) - (gamepad1.left_stick_x * throtle) - gamepad1.right_stick_x);
            backRightDrive  = ((gamepad1.left_stick_y * throtle) + (gamepad1.left_stick_x * throtle) - gamepad1.right_stick_x);
            backLeftDrive   = ((gamepad1.left_stick_y * throtle) - (gamepad1.left_stick_x * throtle) + gamepad1.right_stick_x);

            drive.setDriveSpeed(frontRightDrive,backRightDrive,frontLeftDrive,backLeftDrive);

            telemetry.addData("Front right drive speed = ", "%1.2f", frontRightDrive);
            telemetry.addData("Front left drive speed  = ", "%1.2f", frontLeftDrive);
            telemetry.addData("Back right drive speed  = ", "%1.2f", backRightDrive);
            telemetry.addData("Back left drive speed   = ", "%1.2f", backLeftDrive);
//            telemetry.addData("Front right drive speed = ", "%1.2f", hardware.frontRightDrive.getPower());
//            telemetry.addData("Front left drive speed  = ", "%1.2f", hardware.frontLeftDrive.getPower());
//            telemetry.addData("Back right drive speed  = ", "%1.2f", hardware.backRightDrive.getPower());
//            telemetry.addData("Back left drive speed   = ", "%1.2f", hardware.backLeftDrive.getPower());
	        telemetry.addData("Throttle                = ", "%1.2f", throtle);
            telemetry.update();
        }
    }

}
