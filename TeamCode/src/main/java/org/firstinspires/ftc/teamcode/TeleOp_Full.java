
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.library.Drive;
import org.firstinspires.ftc.teamcode.library.DriveImpl;
import org.firstinspires.ftc.teamcode.library.Arm;
import org.firstinspires.ftc.teamcode.library.ArmImpl;
import org.firstinspires.ftc.teamcode.library.ElectorgatorHardware;

/**
 * Created by Luke on 10/1/2017.
 */

@TeleOp(name = "TeleOp")
public class TeleOp_Full extends LinearOpMode {
    ElectorgatorHardware hardware = new ElectorgatorHardware();
	Drive drive;
	Arm arm;

    double frontLeftDrive, frontRightDrive, backRightDrive, backLeftDrive;
    double extend;

    @Override
    public void runOpMode() throws InterruptedException {
        // initialise the motors
        telemetry.addLine("Initialising... please wait.");
        telemetry.update();

        double throtle;
        double ringArmPosition = 0;

        drive = new DriveImpl();

        drive.initDrive(hardwareMap);
        drive.setTelemetry(telemetry);
        drive.passLinearOp(this);

        drive.setMotorMode(Drive.MotorMode.POWER);

        arm = new ArmImpl(hardwareMap);

        telemetry.addLine("Ready to start... thank you for waiting!");
        telemetry.update();

        waitForStart();

        arm.ringGrabberPosition(ringArmPosition);

        while (opModeIsActive()) {

            ///GAMEPAD 1

            // calculate the throttle position that will be used when calculating the motor powers
            throtle = Math.max(gamepad1.left_trigger,.4); //throtle = drive.throttleControl(gamepad1.left_trigger, .4);

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



            //GAMEPAD 2
            extend = gamepad2.right_stick_y * 0.5;
            arm.setExtendSpeed(extend);

            double gp2lt = gamepad2.left_trigger;
            double gp2rt = gamepad2.right_trigger;
            if(gp2lt > .5f) arm.grip();
            else if (gp2rt > .5f) arm.release();


            if(gamepad2.y){
                ringArmPosition = 0;
            }
            else if(gamepad2.a){
                ringArmPosition = 1;
            }
            else if(gamepad2.b){
                ringArmPosition = .51;
            }
            else if(gamepad2.x){
                //arm kicker boots it
            }
            else if(gamepad2.left_bumper){
                while(gamepad2.left_bumper);
                ringArmPosition -= .01;
            }
            else if(gamepad2.right_bumper){
                while(gamepad2.right_bumper);
                ringArmPosition += .01;
            }
            arm.ringGrabberPosition(ringArmPosition);

            //TELEMETRY

            telemetry.addData("Front right drive speed = ", "%1.2f", frontRightDrive);
            telemetry.addData("Front left drive speed  = ", "%1.2f", frontLeftDrive);
            telemetry.addData("Back right drive speed  = ", "%1.2f", backRightDrive);
            telemetry.addData("Back left drive speed   = ", "%1.2f", backLeftDrive);
	        telemetry.addData("Throttle                = ", "%1.2f", throtle);
	        telemetry.addLine();
            telemetry.addData("extend speed  = ", "%1.2f", extend);
            telemetry.addData(" GP2 LT = ", "%1.2f", gp2lt);
            telemetry.addData(" GP2 RT = ", "%1.2f", gp2rt);
            telemetry.addData(" Ring Arm = ", "%1.2f", ringArmPosition);
            telemetry.update();
        }
    }

}
