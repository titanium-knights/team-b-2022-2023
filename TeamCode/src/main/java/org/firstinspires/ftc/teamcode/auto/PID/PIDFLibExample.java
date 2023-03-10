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
    public void runForwardBackward(PController pController, MotorEx br, MotorEx fl, MotorEx bl, MotorEx fr) {
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
            if(output<0) {
                break;
            }
            br.setVelocity(output);
            fr.setVelocity(output);
            fl.setVelocity(output);
            bl.setVelocity(output);
        }
    }

    @Override
    public void runOpMode() {
        waitForStart();
        MotorEx br = new MotorEx(hardwareMap, CONFIG.BACKRIGHT);
        MotorEx bl = new MotorEx(hardwareMap, CONFIG.BACKLEFT);
        MotorEx fr = new MotorEx(hardwareMap, CONFIG.FRONTRIGHT);
        MotorEx fl = new MotorEx(hardwareMap, CONFIG.FRONTLEFT);
        MecanumDrive drive = new MecanumDrive(hardwareMap);
        br.setInverted(false);
        fr.setInverted(false);
        bl.setInverted(true);
        fl.setInverted(true);

        /*
         * A sample control loop for a motor
         */
        PController pController = new PController(10);

        int pos = 0;
        // We set the setpoint here.
        // Now we don't have to declare the setpoint
        // in our calculate() method arguments.
        pos+=1800;
        pController.setSetPoint(pos);
        runForwardBackward(pController, br, fl, bl, fr);

        pos+=600;
        pController.setSetPoint(pos);

        // perform the control loop
        /*
         * The loop checks to see if the controller has reached
         * the desired setpoint within a specified tolerance
         * range
         */
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
            if(output<0) {
                break;
            }
            br.setVelocity(-output);
            fr.setVelocity(-output);
            fl.setVelocity(output);
            bl.setVelocity(output);
        }

//        telemetry.addLine("starting turning");
//        telemetry.update();
//        fl.setVelocity(7000);
//        bl.setVelocity(7000);
//        fr.setVelocity(-7000);
//        br.setVelocity(-7000);
//        sleep(250);
//        fl.setVelocity(0);
//        bl.setVelocity(0);
//        fr.setVelocity(0);
//        br.setVelocity(0);

        // We set the setpoint here.
        // Now we don't have to declare the setpoint
        // in our calculate() method arguments.
        pos+=30;
        pController.setSetPoint(pos);
        runForwardBackward(pController, br, fl, bl, fr);
        telemetry.addLine("done going forward");

        sleep(3000);

        telemetry.addLine("going backwards");
        pos-=600;
        pController.setSetPoint(pos);
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
            if(output>0) {
                break;
            }
            telemetry.addData("speed", output);
            telemetry.update();
            br.setVelocity(output);
            fr.setVelocity(output);
            fl.setVelocity(output);
            bl.setVelocity(output);
        }

        pos+=1200;
        pController.setSetPoint(pos);

        // perform the control loop
        /*
         * The loop checks to see if the controller has reached
         * the desired setpoint within a specified tolerance
         * range
         */
        while (!pController.atSetPoint()) {
            double output = pController.calculate(
                    fl.getCurrentPosition()
            );
            output += pController.calculate(
                    bl.getCurrentPosition()
            );
            output /= 2;
            if(output < 0) { // TODO: set threshold
                break;
            }
            telemetry.addData("turning speed", output);
            telemetry.update();
            br.setVelocity(output);
            fr.setVelocity(output);
            fl.setVelocity(-output);
            bl.setVelocity(-output);
        }

//        pos+=600;
//        pController.setSetPoint(pos);
//
//        // perform the control loop
//        /*
//         * The loop checks to see if the controller has reached
//         * the desired setpoint within a specified tolerance
//         * range
//         */
//        while (!pController.atSetPoint()) {
//            double output = pController.calculate(
//                    fl.getCurrentPosition()
//            );
//            output += pController.calculate(
//                    bl.getCurrentPosition()
//            );;
//            output /= 2;
//            telemetry.addData("speed", output);
//            telemetry.update();
//            if(output<0) {
//                break;
//            }
//            br.setVelocity(output);
//            fr.setVelocity(output);
//            fl.setVelocity(-output);
//            bl.setVelocity(-output);
//        }
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
