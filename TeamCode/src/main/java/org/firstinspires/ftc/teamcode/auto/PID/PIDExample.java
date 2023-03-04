package org.firstinspires.ftc.teamcode.auto.PID;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AngularVelocity;
//import org.firstinspires.ftc.robotcore.external.navigation.AngularOrientation;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;
import org.firstinspires.ftc.teamcode.utilities.CONFIG;

public class PIDExample extends LinearOpMode {

    private BNO055IMU imu;
    private DcMotor leftFront, leftBack, rightFront, rightBack;
    private ElapsedTime runtime;

    // Constants for PID control
    private static final double KP = 0.01;
    private static final double KI = 0.01;
    private static final double KD = 0.01;
    private static final double TOLERANCE = 0.3;
    private static final double KP_ROTATE = 0.01;
    private static final double KI_ROTATE = 0.01;
    private static final double KD_ROTATE = 0.01;
    private static final double TOLERANCE_ROTATE = 0.3;

    // Constants for robot movement, NOTE: NEEDS CHANGING!!!
    private static final double WHEEL_DIAMETER = 4.0;
    private static final double WHEEL_CIRCUMFERENCE = WHEEL_DIAMETER * Math.PI;
    private static final double COUNTS_PER_REV = 1440.0;
    private static final double COUNTS_PER_INCH = COUNTS_PER_REV / WHEEL_CIRCUMFERENCE;
    private static final double DRIVE_SPEED = 0.5;

    @Override
    public void runOpMode() {

        // Initialize hardware and IMU
        initializeHardware();
        initializeIMU();

        // Wait for start button to be pressed
        waitForStart();
        runtime.reset();

        // Move robot to desired location using PID control
        run(90.0, 10.0); // Move 10 inches to the right
        run(-90.0, 10.0); // Move 10 inches to the left
        run(0.0, 10.0); // Move 10 inches forward
        run(180.0, 10.0); // Move 10 inches backward

        // Rotate robot 90 degrees using IMU
        rotate(90.0);
        rotate(-90.0);

        // Stop all motors
        stopAllMotors();
    }

    private void initializeHardware() {
        // Map hardware devices
        leftFront = hardwareMap.get(DcMotor.class, CONFIG.FRONTLEFT);
        leftBack = hardwareMap.get(DcMotor.class, CONFIG.BACKLEFT);
        rightFront = hardwareMap.get(DcMotor.class, CONFIG.FRONTRIGHT);
        rightBack = hardwareMap.get(DcMotor.class, CONFIG.BACKRIGHT);

        // Set motor directions, needs changing???
        leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
        leftBack.setDirection(DcMotorSimple.Direction.REVERSE);
        rightFront.setDirection(DcMotorSimple.Direction.FORWARD);
        rightBack.setDirection(DcMotorSimple.Direction.FORWARD);
//        fl.setDirection(DcMotorSimple.Direction.REVERSE);
//        bl.setDirection(DcMotorSimple.Direction.FORWARD);
//        fr.setDirection(DcMotorSimple.Direction.REVERSE);
//        br.setDirection(DcMotorSimple.Direction.FORWARD);

        // Set motor modes
        leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Set motor power to 0
        leftFront.setPower(0);
        leftBack.setPower(0);
        rightFront.setPower(0);
        rightBack.setPower(0);

        // Set up encoder reset
        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    private void initializeIMU() {
        // Set up IMU parameters
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
        parameters.loggingEnabled = true;
        parameters.loggingTag = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();

        // Map IMU device
        imu = hardwareMap.get(BNO055IMU.class, "imu");

        // Initialize IMU
        imu.initialize(parameters);
    }

    private void run(double angle, double distance) {
        // Calculate target position
        double targetX = distance * Math.cos(Math.toRadians(angle));
        double targetY = distance * Math.sin(Math.toRadians(angle));

        // Initialize PID variables
        double integral = 0;
        double derivative = 0;
        double lastError = 0;

        // Loop until target is reached
        while (opModeIsActive()) {
            // Calculate error
            double errorX = targetX - (getEncoderDistance(leftFront) + getEncoderDistance(leftBack) - getEncoderDistance(rightFront) - getEncoderDistance(rightBack)) / 4.0;
            double errorY = targetY - (getEncoderDistance(leftFront) - getEncoderDistance(leftBack) + getEncoderDistance(rightFront) - getEncoderDistance(rightBack)) / 4.0;

            // Calculate PID correction
            integral += errorX;
            derivative = errorX - lastError;
            lastError = errorX;
            double correctionX = errorX * KP + integral * KI + derivative * KD;

            integral += errorY;
            derivative = errorY - lastError;
            lastError = errorY;
            double correctionY = errorY * KP + integral * KI + derivative * KD;

            // Calculate motor powers
            double leftPower = DRIVE_SPEED * (correctionY - correctionX);
            double rightPower = DRIVE_SPEED * (correctionY + correctionX);

            // Set motor powers
            leftFront.setPower(leftPower);
            leftBack.setPower(leftPower);
            rightFront.setPower(rightPower);
            rightBack.setPower(rightPower);

            // Check if target is reached
            if (Math.abs(errorX) < TOLERANCE && Math.abs(errorY) < TOLERANCE) {
                // Stop motors
                stopAllMotors();
                break;
            }

            // Wait for motors to update
            sleep(50);
        }
    }

    private void rotate(double targetAngle) {
        // Initialize PID variables
        double integral = 0;
        double derivative = 0;
        double lastError = 0;
        // Get initial angle
        double startAngle = getAngle();

        // Loop until target angle is reached
        while (opModeIsActive()) {
            // Calculate error
            double error = targetAngle - (getAngle() - startAngle);

            // Calculate PID correction
            integral += error;
            derivative = error - lastError;
            lastError = error;
            double correction = error * KP_ROTATE + integral * KI_ROTATE + derivative * KD_ROTATE;

            // Calculate motor powers
            double leftPower = -correction;
            double rightPower = correction;

            // Set motor powers
            leftFront.setPower(leftPower);
            leftBack.setPower(leftPower);
            rightFront.setPower(rightPower);
            rightBack.setPower(rightPower);

            // Check if target angle is reached
            if (Math.abs(error) < TOLERANCE_ROTATE) {
                // Stop motors
                stopAllMotors();
                break;
            }

            // Wait for motors to update
            sleep(50);
        }
    }

    private void stopAllMotors() {
        leftFront.setPower(0);
        leftBack.setPower(0);
        rightFront.setPower(0);
        rightBack.setPower(0);
    }

    private double getEncoderDistance(DcMotor motor) {
        return motor.getCurrentPosition() / COUNTS_PER_INCH;
    }

    private double getAngle() {
        // Get angles from IMU
        Orientation angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        // Convert angles to range -180 to 180
        double angle = angles.firstAngle;
        if (angle > 180) {
            angle -= 360;
        } else if (angle < -180) {
            angle += 360;
        }

        return angle;
    }

}
