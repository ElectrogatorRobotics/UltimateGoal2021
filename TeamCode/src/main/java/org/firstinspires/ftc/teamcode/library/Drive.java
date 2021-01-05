package org.firstinspires.ftc.teamcode.library;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

public interface Drive {

    enum MotorMode {POWER, ENCODER, POSITION}

    /**
     * initialise the drivetrain motors and servos
     * @param hardwareMap
     */
    void initDrive(HardwareMap hardwareMap);
    void init_bno055IMU (HardwareMap hardwareMap);
    void setTelemetry(Telemetry telem);
    void passLinearOp(LinearOpMode lop);
    void setMotorMode(MotorMode MM);

    /**
     * set the power of all drive motors to the same value
     * @param speed
     */
    void setDriveSpeed(double speed);

    void setTargetPosition (int targetPosition);

    /**
     * tolerance is measured in encoder ticks
     * @param tolerance
     */
    void setTargetTolerance(int tolerance);

    void forward(double inches);
    void slide(double inches);

    /**
     * set the power of each drive motor to a unique value
     * @param frontRight
     * @param backRight
     * @param frontLeft
     * @param backLeft
     */
    void setDriveSpeed(double frontRight, double backRight, double frontLeft, double backLeft);

    void stop();

    void driveByPosition(double power, LinearOpMode lom);
    double turnToAngle (double angleToTurn, LinearOpMode lom);
    void slideOver(int targetPosition, LinearOpMode lom);
}
