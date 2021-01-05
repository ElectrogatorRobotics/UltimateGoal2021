
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.library.Arm;
import org.firstinspires.ftc.teamcode.library.ArmImpl;
import org.firstinspires.ftc.teamcode.library.Drive;
import org.firstinspires.ftc.teamcode.library.DriveImpl;
import org.firstinspires.ftc.teamcode.library.ElectorgatorHardware;

/**
 * Created by Luke on 10/1/2017.
 */

@TeleOp(name = "TeleOp - HighSpeed")
@Disabled
public class TeleOp_HighSpeed extends LinearOpMode {
    ElectorgatorHardware hardware = new ElectorgatorHardware();
	Drive drive;
	Arm arm;

    double frontLeftDrive, frontRightDrive, backRightDrive, backLeftDrive;
    double rotate, extend;

    @Override
    public void runOpMode() throws InterruptedException {
        // initialise the motors
        telemetry.addLine("Initialising... please wait.");
        telemetry.update();

        double throtle, throttle2;

        drive = new DriveImpl();

        drive.initDrive(hardwareMap);
        drive.setTelemetry(telemetry);
        drive.passLinearOp(this);

        drive.setMotorMode(Drive.MotorMode.POWER);

        arm = new ArmImpl(hardwareMap);

        telemetry.addLine("Ready to start... thank you for waiting!");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {

            ///////////////////////////////   GAMEPAD 1   \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

            // calculate the throttle position that will be used when calculating the motor powers
            throtle = Math.max(1 - gamepad1.left_trigger, .4); //throtle = drive.throttleControl(gamepad1.left_trigger, .4);

            /**
             * Calculate the power of each motor by multiplying the left Y-axes and the left X-axes that are
             * used for driving normal by the throttle value that we calculated above. The right X-axes is
             * not multiplied by the throttle, because it is used for sliding sideways and can not be controlled
             * efficiently with the throttle due to the high power requirements of sliding.
             */
            frontRightDrive = (gamepad1.left_stick_y * throtle) + (gamepad1.left_stick_x * throtle) + (gamepad1.right_stick_x * throtle);
            frontLeftDrive  = (gamepad1.left_stick_y * throtle) - (gamepad1.left_stick_x * throtle) - (gamepad1.right_stick_x * throtle);
            backRightDrive  = (gamepad1.left_stick_y * throtle) + (gamepad1.left_stick_x * throtle) - (gamepad1.right_stick_x * throtle);
            backLeftDrive   = (gamepad1.left_stick_y * throtle) - (gamepad1.left_stick_x * throtle) + (gamepad1.right_stick_x * throtle);

            drive.setDriveSpeed(frontRightDrive,backRightDrive,frontLeftDrive,backLeftDrive);

            if (gamepad1.x) {
                arm.grabFD();
            }
            else if (gamepad1.y){
                arm.releaseFD();
            }

            //////////////////////////////////   GAMEPAD 2   \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

            throttle2 = Math.max(1 - gamepad2.left_trigger,.5);

            rotate = -gamepad2.left_stick_y * throttle2;
            arm.setAngleSpeed(rotate);
            extend = -gamepad2.right_stick_y * throttle2;
            arm.setExtendSpeed(extend);

            if(gamepad2.left_bumper) arm.grip();
            else if (gamepad2.right_bumper) arm.release();


            ////////////////////////////// TELEMETRY \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

            telemetry.addData("Front right drive speed = ", "%1.2f", frontRightDrive);
            telemetry.addData("Front left drive speed  = ", "%1.2f", frontLeftDrive);
            telemetry.addData("Back right drive speed  = ", "%1.2f", backRightDrive);
            telemetry.addData("Back left drive speed   = ", "%1.2f", backLeftDrive);
	        telemetry.addData("Throttle                = ", "%1.2f", throtle);
	        telemetry.addLine();
            telemetry.addData("angle speed   = ", "%1.2f", rotate);
            telemetry.addData("extend speed  = ", "%1.2f", extend);
            telemetry.addData("Throttle 2    = ", "%1.2f", throttle2);
            telemetry.update();
        }
    }

}
