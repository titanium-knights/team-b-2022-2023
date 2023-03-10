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
import org.firstinspires.ftc.teamcode.utilities.MecanumDrive;

import com.acmerobotics.dashboard.config.Config;

@Config
@Autonomous(name="PIDFLibExample")
public class PIDFLibExample extends LinearOpMode {

    public static int pos1 = 0;
    public static int pos2 = 2100;
    public static int pos3 = 2700;
    public static int pos4 = 2730;
    public static int pos5 = 1530;
    public static int pos6 = 2730;

    public void moveForward(PController pController, MotorEx br, MotorEx fl, MotorEx bl, MotorEx fr) {
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
            telemetry.addData("speed", output);
            telemetry.update();
            if(output<1000) {
                break;
            }

            output/=4;
            br.setVelocity(output);
            fr.setVelocity(output);
            fl.setVelocity(output);
            bl.setVelocity(output);
        }
    }

    public void moveBackward(PController pController, MotorEx br, MotorEx fl, MotorEx bl, MotorEx fr) {
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
            if(output<1000) {
                break;
            }

            output/=4;
            telemetry.addData("speed", output);
            telemetry.update();
            br.setVelocity(output);
            fr.setVelocity(output);
            fl.setVelocity(output);
            bl.setVelocity(output);
        }
    }

    public void turnRight(PController pController, MotorEx br, MotorEx fl, MotorEx bl, MotorEx fr) {
        while (!pController.atSetPoint()) {
            double output = pController.calculate(
                    fl.getCurrentPosition()
            );
            output += pController.calculate(
                    bl.getCurrentPosition()
            );;
            output /= 2;
            telemetry.addData("speed", output);
            telemetry.update();
            if(output<1000) {
                break;
            }

            br.setVelocity(-output);
            fr.setVelocity(-output);
            fl.setVelocity(output);
            bl.setVelocity(output);
        }
    }

    public void turnLeft(PController pController, MotorEx br, MotorEx fl, MotorEx bl, MotorEx fr) {
        while (!pController.atSetPoint()) {
            double output = pController.calculate(
                    fr.getCurrentPosition()
            );
            output += pController.calculate(
                    br.getCurrentPosition()
            );
            output /= 2;
            if (output<1000) {
                break;
            }

            telemetry.addData("turning speed", output);
            telemetry.update();
            br.setVelocity(output);
            fr.setVelocity(output);
            fl.setVelocity(-output);
            bl.setVelocity(-output);
        }
    }

    public void stop(MotorEx br, MotorEx fl, MotorEx bl, MotorEx fr) {
        br.stopMotor();
        fl.stopMotor();
        bl.stopMotor();
        fr.stopMotor();
        sleep(300);
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
        bl.setInverted(true);
        fl.setInverted(true);
        br.resetEncoder();
        fr.resetEncoder();
        bl.resetEncoder();
        fl.resetEncoder();

        PController pController = new PController(10);
//        int pos = 0;

//        pos+=2100;
        pController.setSetPoint(pos2);
        moveForward(pController, br, fl, bl, fr);

        stop(br, fl, bl, fr);

//        pos+=600;
        pController.setSetPoint(pos3);
        turnRight(pController, br, fl, bl, fr);

        stop(br, fl, bl, fr);

//        pos+=30;
        pController.setSetPoint(pos4);
        moveForward(pController, br, fl, bl, fr);

        stop(br, fl, bl, fr);

//        pos-=1200;
        pController.setSetPoint(pos5);
        moveBackward(pController, br, fl, bl, fr);

        stop(br, fl, bl, fr);

//        pos+=1200;
        pController.setSetPoint(pos6);
        turnLeft(pController, br, fl, bl, fr);

        stop(br, fl, bl, fr);
    }
}
