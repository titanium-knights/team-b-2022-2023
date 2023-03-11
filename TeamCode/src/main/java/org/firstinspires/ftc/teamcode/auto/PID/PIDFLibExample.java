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
import org.firstinspires.ftc.teamcode.utilities.Claw;
import org.firstinspires.ftc.teamcode.utilities.MecanumDrive;
import org.firstinspires.ftc.teamcode.utilities.Slides;

import com.acmerobotics.dashboard.config.Config;

@Config
@Autonomous(name="PIDFLibExample")
public class PIDFLibExample extends LinearOpMode {
    public static int forwardPos = 2300;
    public static int turnLeft = 600;
    public static int forward2Pos = 500; // 500 dif
    public static int strafeRight = 400;

    public static int forwardmulti = 4;
    public static int rotationmulti = 1;


    public static MotorEx br, bl, fr, fl;
    public static PController pController;

    @Override
    public void runOpMode() {
        waitForStart();
        MotorEx br = new MotorEx(hardwareMap, CONFIG.BACKRIGHT);
        MotorEx bl = new MotorEx(hardwareMap, CONFIG.BACKLEFT);
        MotorEx fr = new MotorEx(hardwareMap, CONFIG.FRONTRIGHT);
        MotorEx fl = new MotorEx(hardwareMap, CONFIG.FRONTLEFT);
        Slides slides = new Slides(hardwareMap);
        Claw claw = new Claw(hardwareMap);
        telemetry.setAutoClear(false);

        br.setInverted(false);
        fr.setInverted(false);
        bl.setInverted(true);
        fl.setInverted(true);
        br.resetEncoder();
        fr.resetEncoder();
        bl.resetEncoder();
        fl.resetEncoder();

        pController = new PController(10);
        pController.setTolerance(10);

        waitForStart();

        slides.reset();
        claw.close();


        moveForward(forwardPos);

        strafeRight(strafeRight);

        strafeLeft(strafeRight);

        turnLeft(turnLeft);

        moveForward(forward2Pos);

        stop();
    }

    public void set(int dist){
        pController.reset();
        sleep(10);
        pController.setSetPoint(dist);
    }

    public void moveForward(int dist) {
        // perform the control loop
        /*
         * The loop checks to see if the controller has reached
         * the desired setpoint within a specified tolerance
         * range
         */
        set(dist);
        while (!pController.atSetPoint()) {
            double output = pController.calculate(
                    br.getCurrentPosition()  // the measured value
            );
            output += pController.calculate(
                    fl.getCurrentPosition()
            );
            output += pController.calculate(
                    bl.getCurrentPosition()
            );
            output += pController.calculate(
                    fr.getCurrentPosition()
            );
            output /= 4;
            telemetry.addData("moveForward speed", output);
            telemetry.update();

            output/=forwardmulti;
            br.setVelocity(output);
            fr.setVelocity(output);
            fl.setVelocity(output);
            bl.setVelocity(output);
        }
        brake();
    }

    public void turnLeft(int dist) {
        set(dist);
        while (!pController.atSetPoint()) {
            double output = pController.calculate(
                    fr.getCurrentPosition()
            );
            output += pController.calculate(
                    br.getCurrentPosition()
            );
            output /= 2;
            output /= rotationmulti;
            telemetry.addData("turnLeft speed", output);
            telemetry.update();
            br.setVelocity(output);
            fr.setVelocity(output);
            fl.setVelocity(-output);
            bl.setVelocity(-output);
        }
        brake();
    }

    public void strafeRight(int dist) {
        set(dist);
        while (!pController.atSetPoint()) {
            double output = pController.calculate(
                    fl.getCurrentPosition()
            );
            output += pController.calculate(
                    br.getCurrentPosition()
            );
            output /= 2;

            telemetry.addData("strafeLeft speed", output);
            telemetry.update();
            br.setVelocity(output);
            fr.setVelocity(-output);
            fl.setVelocity(output);
            bl.setVelocity(-output);
        }
        brake();
    }

    public void strafeLeft(int dist) {
        set(dist);
        while (!pController.atSetPoint()) {
            double output = pController.calculate(
                    fr.getCurrentPosition()
            );
            output += pController.calculate(
                    bl.getCurrentPosition()
            );
            output /= 2;

            telemetry.addData("strafeRight speed", output);
            telemetry.update();
            br.setVelocity(-output);
            fr.setVelocity(output);
            fl.setVelocity(-output);
            bl.setVelocity(output);
        }
        brake();
    }

    public void brake() {
        br.stopMotor();
        fl.stopMotor();
        bl.stopMotor();
        fr.stopMotor();
        sleep(300);
    }


}
