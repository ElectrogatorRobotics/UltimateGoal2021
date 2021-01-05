package org.firstinspires.ftc.teamcode.library;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.library.Camera;
import org.firstinspires.ftc.teamcode.library.CameraImpl;
import org.firstinspires.ftc.teamcode.library.Drive;
import org.firstinspires.ftc.teamcode.library.DriveImpl;
import org.firstinspires.ftc.teamcode.library.Arm;
import org.firstinspires.ftc.teamcode.library.ArmImpl;
/*@Autonomous(name="Auto Test 2", group="Testing")
public class automode extends LinearOpMode {

        @Override
        public void runOpMode() throws InterruptedException {
                Drive drive = new DriveImpl();
                drive.initDrive(hardwareMap);
                drive.init_bno055IMU(hardwareMap);
                drive.passLinearOp(this);
                drive.setTelemetry(telemetry);

                Camera camera = new CameraImpl();
                camera.init(hardwareMap);
                camera.setTelemetry(telemetry);

                Arm arm = new ArmImpl(hardwareMap);

                waitForStart();

                while(opModeIsActive()){
                        camera.scan(3000);
                        telemetry.addData("Cam X:",camera.getX());
                        telemetry.addData("Cam Y",camera.getY());
                        telemetry.addData("Cam Ang",camera.getAngle());
                        telemetry.update();
                        sleep(2000);
                }
        void Drive () extends
                telemetry.addData("camX",camera.getX());
                telemetry.addData("camY", camera.getY());
                telemetry.addData("camAngle",camera.getAngle());
                telemetry.update();
                drive.go(camera.getX());

        }
}


// scan camera
        // camera.scan()
// get cam values
        //camera.get x.(); or camera.get y();
        //drive.go to XY (x,y, here, now)
// scan?
        //camera.scan
// turn and line up
        //drive.turn
// scan for the sky stone
        //camera.scanSkystone ()
// the above is a special function that looks only for sky stones
//slide over to the block
        //drive.slide(x)
        //arm.grab floor();
// grab the block
// turn and drive to the other side
// drop the block off
// get our ass out of the way
//end the code take a fucking brake you deserve it

//*/