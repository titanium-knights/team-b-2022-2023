package org.firstinspires.ftc.teamcode.auto.PID;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.arcrobotics.ftclib.controller.PController;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.checkerframework.checker.units.qual.Angle;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.internal.opmode.TelemetryImpl;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.utilities.CONFIG;
import com.acmerobotics.dashboard.config.Config;

@Config
@Autonomous(name="PIDFLibExample")
public class PIDFLibExample extends LinearOpMode {
    static final int DISTANCE = 2000;
    public void PIDFVelocityControl(MotorEx m, PIDFController pidfController, String mName, int startPos) {
        telemetry.addData(mName+" start pos", startPos);
        telemetry.addData(mName+" current position", m.getCurrentPosition());
        telemetry.update();
        double output = pidfController.calculate(
                m.getCurrentPosition()  // the measured value
        );
        telemetry.addData(mName + " velocity", output/4);
        telemetry.update();
        m.setVelocity(output/4);
    }

    @Override
    public void runOpMode() {
        waitForStart();
        MotorEx br = new MotorEx(hardwareMap, CONFIG.BACKRIGHT);
        MotorEx bl = new MotorEx(hardwareMap, CONFIG.BACKLEFT);
        MotorEx fr = new MotorEx(hardwareMap, CONFIG.FRONTRIGHT);
        MotorEx fl = new MotorEx(hardwareMap, CONFIG.FRONTLEFT);
        br.setInverted(false);
        fr.setInverted(false);
        /*
         * A sample control loop for a motor
         */
        PController pController = new PController(10);

        // We set the setpoint here.
        // Now we don't have to declare the setpoint
        // in our calculate() method arguments.
        pController.setSetPoint(1200);

        // perform the control loop
        /*
         * The loop checks to see if the controller has reached
         * the desired setpoint within a specified tolerance
         * range
         */
        while (!pController.atSetPoint()) {
            double output = pController.calculate(
                    br.getCurrentPosition()  // the measured value
            );
            br.setVelocity(output);
            output = pController.calculate(
                    fr.getCurrentPosition()
            );
            fr.setVelocity(output);
        }
        br.stopMotor(); // stop the motor

        // NOTE: motors have internal PID control
    }

//    @Override
//    public void runOpMode() {
//        waitForStart();
//        MotorEx br = new MotorEx(hardwareMap, CONFIG.BACKRIGHT);
//        MotorEx bl = new MotorEx(hardwareMap, CONFIG.BACKLEFT);
//        MotorEx fr = new MotorEx(hardwareMap, CONFIG.FRONTRIGHT);
//        MotorEx fl = new MotorEx(hardwareMap, CONFIG.FRONTLEFT);
//        bl.setInverted(true);
//        fl.setInverted(true);
//        while(true) {
//            telemetry.addData("Velocity", 1000);
//            telemetry.update();
//            br.setVelocity(1000);
//            bl.setVelocity(1000);
//            fr.setVelocity(1000);
//            fl.setVelocity(1000);
//        }
//    }
}
