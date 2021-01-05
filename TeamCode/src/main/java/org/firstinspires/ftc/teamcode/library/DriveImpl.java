package org.firstinspires.ftc.teamcode.library;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.motors.Matrix12vMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

public class DriveImpl implements Drive {
    public DcMotorEx frontRightDrive = null;
    public DcMotorEx frontLeftDrive = null;
    public DcMotorEx backRightDrive = null;
    public DcMotorEx backLeftDrive = null;

    public BNO055IMU bno055IMU = null;

    private ElapsedTime runtime = new ElapsedTime();

    public Orientation angle = null;
    
    private static final double DRIVE_POWER = .3;
    private static final double TURN_POWER = .35;
    private static final double SLIDE_POWER = .5;
    private static final double TURN_THRESHOLD = .5;

    Telemetry LOG;

    private LinearOpMode lom;

    /**
     * This is the minimum power that the drive train can move
     */

    private static final int MAX_SPEED = (6000 / 360);
    public static final double MIN_SPEED = 0.4;

    /**
     * Calculate the number of ticks per inch of the wheel
     * <p>
     * (Wheel diameter * PI) *  ticks per wheel regulation
     * wheel diameter = 4 inches
     * ticks per revolution of wheel = 7 counts per motor revolution * 20 gearbox reduction (20:1)
     */
    public static final double ENCODER_COUNTS_PER_INCH = (28 * 28) / (Math.PI * 4);

    public enum MoveMethod {STRAIGHT, TURN, SLIDE}

    public DriveImpl() { }

    public DriveImpl(Telemetry telem, LinearOpMode lop) {
        setTelemetry(telem);
        lom = lop;
    }




    public void setTelemetry(Telemetry telem) {
        LOG = telem;
    }

    public void passLinearOp(LinearOpMode lop) {
        lom = lop;
    }

    public void initDrive(HardwareMap hardwareMap) {
        frontRightDrive = (DcMotorEx) hardwareMap.dcMotor.get("front right drive");
        frontRightDrive.setMotorType(MotorConfigurationType.getMotorType(Matrix12vMotor.class));
        frontRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        frontRightDrive.setDirection(DcMotorSimple.Direction.FORWARD);
        frontRightDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        frontRightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        frontLeftDrive = (DcMotorEx) hardwareMap.dcMotor.get("front left drive");
        frontLeftDrive.setMotorType(MotorConfigurationType.getMotorType(Matrix12vMotor.class));
        frontLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        frontLeftDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        frontLeftDrive.setDirection(DcMotorSimple.Direction.FORWARD);
        frontLeftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        backRightDrive = (DcMotorEx) hardwareMap.dcMotor.get("back right drive");
        backRightDrive.setMotorType(MotorConfigurationType.getMotorType(Matrix12vMotor.class));
//        backRightDrive.setDirection(DcMotorSimple.Direction.FORWARD);
        backRightDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        backRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        backLeftDrive = (DcMotorEx) hardwareMap.dcMotor.get("back left drive");
        backLeftDrive.setMotorType(MotorConfigurationType.getMotorType(Matrix12vMotor.class));
        backRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeftDrive.setDirection(DcMotorSimple.Direction.REVERSE);
//        backLeftDrive.setDirection(DcMotorSimple.Direction.FORWARD);        //somehow running backwards.
        backLeftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        setMotorMode(MotorMode.POWER);
    }

    public void init_bno055IMU (HardwareMap hardwareMap) {
        bno055IMU = hardwareMap.get( BNO055IMU.class, "imu");
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.gyroPowerMode = BNO055IMU.GyroPowerMode.NORMAL;
        parameters.gyroBandwidth = BNO055IMU.GyroBandwidth.HZ32;
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.mode = BNO055IMU.SensorMode.COMPASS;
        bno055IMU.initialize(parameters);
    }

    // forward can go backwards????
    //what the actual fuck
    public void forward(double inches) {
        forward(inches, DRIVE_POWER);
    }
    public void forward(double inches, double power) {
        int ticks = (int) Math.round(inches * ENCODER_COUNTS_PER_INCH);
        setTargetTolerance(50);
        setTargetPosition(ticks);
        driveByPosition(power, lom);
    }

    public void turn(double angle) {
        turnToAngle(angle, lom);
    }

    public void slide(double inches){
        power_slide(inches, SLIDE_POWER);
        stop();
        setMotorMode(MotorMode.POWER);
    }

    public void power_slide(double inches, double power){
        int ticks = (int) Math.round(inches * ENCODER_COUNTS_PER_INCH);
        slideOver(ticks, lom, power);
    }



    public void setMotorMode(MotorMode mode){
        switch(mode){
            case POWER:
                frontLeftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                frontRightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                backLeftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                backRightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                break;
            case ENCODER:
                frontLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                frontRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                backLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                backRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                break;
            case POSITION:
                frontLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                frontRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                backLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                backRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                break;
        }
    }

    public void setDriveSpeed (double power){
        frontRightDrive.setPower(power);
        frontLeftDrive.setPower(power);
        backRightDrive.setPower(power);
        backLeftDrive.setPower(power);
    }

    public void setDriveSpeed (double frontRight, double backRight, double frontLeft, double backLeft) {
        frontRightDrive.setPower(frontRight);
        frontLeftDrive.setPower(frontLeft);
        backRightDrive.setPower(backRight);
        backLeftDrive.setPower(backLeft);
    }



    public void setTargetPosition (int targetPosition) {
        frontLeftDrive.setTargetPosition(frontLeftDrive.getCurrentPosition() + targetPosition);
        frontRightDrive.setTargetPosition(frontRightDrive.getCurrentPosition() + targetPosition);
        backLeftDrive.setTargetPosition(backLeftDrive.getCurrentPosition() + targetPosition);
        backRightDrive.setTargetPosition(backRightDrive.getCurrentPosition() + targetPosition);
    }

    /**
     * tolerance is measured in encoder ticks
     * @param tolerance
     */
    public void setTargetTolerance(int tolerance) {
        frontRightDrive.setTargetPositionTolerance(tolerance);
        frontLeftDrive.setTargetPositionTolerance(tolerance);
        backRightDrive.setTargetPositionTolerance(tolerance);
        backLeftDrive.setTargetPositionTolerance(tolerance);
    }

    public void stop () {
        setDriveSpeed(0.0);
    }



    public void driveByPosition(double power, LinearOpMode lom){
        setMotorMode(MotorMode.POSITION);
        setDriveSpeed(power);
        do {
            Thread.yield(); //effectively what the LinearOpMode idle call does
        } while (frontRightDrive.isBusy() && lom.opModeIsActive());
        stop();
        setMotorMode(MotorMode.POWER);
    }

    public double turnToAngle (double angleToTurn, LinearOpMode lom) {
        angle = bno055IMU.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES);
        /**
         * set {@link angleToTurn} equal to the {@link imu}'s Z axes
         */
        setMotorMode(MotorMode.POWER);
        double power = TURN_POWER;
        if(angleToTurn < 0) power *= -1;
        angleToTurn *= -1;
        double targetAngle = (angle.thirdAngle + angleToTurn + 360)%360;
        if(targetAngle >180){
            targetAngle -= 360;
        }
        runtime.reset();
        while(Math.abs(angle.thirdAngle - targetAngle) > TURN_THRESHOLD && runtime.seconds() < 10 && lom.opModeIsActive()){
            angle = bno055IMU.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES);
            setDriveSpeed(power, power, power*-1, power*-1);
            Thread.yield();
        }
        stop();
        setMotorMode(MotorMode.ENCODER);
        return angleToTurn;
    }

    public void slideOver(int targetPosition, LinearOpMode lom) {
        slideOver(targetPosition, lom, SLIDE_POWER);
        stop();
        setMotorMode(MotorMode.POWER);
    }
    public void slideOver(int targetPosition, LinearOpMode lom, double power){

        setTargetTolerance(50);
        frontLeftDrive.setTargetPosition(frontLeftDrive.getCurrentPosition() + targetPosition);
        frontRightDrive.setTargetPosition(frontRightDrive.getCurrentPosition() - targetPosition);
        backLeftDrive.setTargetPosition(backLeftDrive.getCurrentPosition() - targetPosition);
        backRightDrive.setTargetPosition(backRightDrive.getCurrentPosition() + targetPosition);
        setMotorMode(MotorMode.POSITION);
        setDriveSpeed(power, power*-1, power*-1, power);
        do {
            Thread.yield(); //effectively what the LinearOpMode idle call does
        } while (frontRightDrive.isBusy() && lom.opModeIsActive());
    }
}
