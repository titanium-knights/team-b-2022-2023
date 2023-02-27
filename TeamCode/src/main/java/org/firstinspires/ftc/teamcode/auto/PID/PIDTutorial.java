package org.firstinspires.ftc.teamcode.auto.PID;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.utilities.CONFIG;
//import org.firstinspires.ftc.teamcode.utilities.MecanumDrive;

public class PIDTutorial extends LinearOpMode {

    DcMotor fl, fr, bl, br;
    private BNO055IMU imu;

    double integralSum = 0;
    double Kp = 0;
    double Ki = 0;
    double Kd = 0;
    double Kf = 0;

    ElapsedTime timer = new ElapsedTime();
    private double lastError = 0;

    @Override
    public void runOpMode() throws InterruptedException {
        fl = hardwareMap.get(DcMotor.class, CONFIG.FRONTLEFT);
        fl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        fr = hardwareMap.get(DcMotor.class, CONFIG.FRONTRIGHT);
        fr.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        bl = hardwareMap.get(DcMotor.class, CONFIG.BACKLEFT);
        bl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        br = hardwareMap.get(DcMotor.class, CONFIG.BACKRIGHT);
        br.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        fl.setDirection(DcMotorSimple.Direction.REVERSE);
        bl.setDirection(DcMotorSimple.Direction.FORWARD);
        fr.setDirection(DcMotorSimple.Direction.REVERSE);
        br.setDirection(DcMotorSimple.Direction.FORWARD);

        imu = hardwareMap.get(BNO055IMU.class, "imu");
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.mode = BNO055IMU.SensorMode.IMU;
        parameters.angleUnit = BNO055IMU.AngleUnit.RADIANS;
        imu.initialize(parameters);

        waitForStart();

        double referenceAngle = Math.toRadians(90);
        while (opModeIsActive()) {

            double power = PIDControl(referenceAngle, imu.getAngularOrientation().firstAngle);
            fl.setPower(power);
            bl.setPower(-power);
            fr.setPower(power);
            br.setPower(-power);
        }
    }

    public double PIDControl(double reference, double state) {
        double error = angleWrap(reference - state);
        integralSum += error * timer.seconds();
        double derivative = (error - lastError) / timer.seconds();
        lastError = error;

        timer.reset();

        double output = (error * Kp) + (derivative * Kd) + (integralSum * Ki) + (reference * Kf);
        return output;
    }

    public double angleWrap(double radians) {
        while (radians > Math.PI) {
            radians -= 2 * Math.PI;
        }
        while (radians < Math.PI) {
            radians += 2 * Math.PI;
        }
        return radians;
    }

}
