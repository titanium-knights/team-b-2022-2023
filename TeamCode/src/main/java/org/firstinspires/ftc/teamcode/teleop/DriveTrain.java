package org.firstinspires.ftc.teamcode.teleop;


import static java.lang.Thread.sleep;

import android.annotation.SuppressLint;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;


import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.utilities.Claw;
import org.firstinspires.ftc.teamcode.utilities.MecanumDrive;
import org.firstinspires.ftc.teamcode.utilities.Slides;

@TeleOp(name="DriveTrain Teleop")
public class DriveTrain extends LinearOpMode {
    public static DcMotor fl, fr, bl, br;



    @SuppressLint("DefaultLocale")
    @Override
    public void runOpMode() throws InterruptedException {
        MecanumDrive drive = new MecanumDrive(hardwareMap);
        Slides slide = new Slides(hardwareMap);
        Claw claw = new Claw(hardwareMap);
        telemetry.setAutoClear(false);

        //mecanum
        boolean slowMode = false;
        //slide preset: 0 - orig, 1 - low, 2 - med, 3 - high
        int mode = 0;
        int action;

        waitForStart();

        while (opModeIsActive()) {


            //   slowMode
            if (gamepad1.b) slowMode = !slowMode;
            drive.move(gamepad1.left_stick_x * (slowMode ? 0.3 : .7), -gamepad1.left_stick_y * (slowMode ? 0.3 : .7), gamepad1.right_stick_x * (slowMode ? 0.3 : .7));

            /*
            if (gamepad1.dpad_down) drive.flm((slowMode ? 1 : -1));
            if (gamepad1.dpad_left) drive.frm((slowMode ? 1 : -1));
            if (gamepad1.dpad_up){
                drive.blm((slowMode ? 1 : -1));
                telemetry.addData("Back test", 1);
                telemetry.addData("BACK LEFT", drive.getEncoder());
                telemetry.update();
            }
            if (gamepad1.dpad_right) drive.brm((slowMode ? 1 : -1));
             */

            // Slides
            //Manual control, up
            if (gamepad1.left_trigger > 0) {
                //comment one or the other.
                //first - 100ms, second - while held
                slide.pSlideUp();
                slide.upHold();
                telemetry.addData("encoder postman", slide.getEncoder());
                telemetry.update();
            }
            //Manual control, down
            else if (gamepad1.right_trigger > 0) {
                //same as above, reversed
                slide.pSlideDown();
                slide.downHold();
                telemetry.addData("encoder postman", slide.getEncoder());
                telemetry.update();

            } else{
                //if using pSlide, comment out.
                slide.stop();
            }


            /*
            //height control
            action = -1;
            telemetry.addData("encoder prepreset", slide.getEncoder());

            if (gamepad1.dpad_down) mode = 0;
            if (gamepad1.dpad_left) mode = 1;
            if (gamepad1.dpad_up) mode = 2;
            if (gamepad1.dpad_right) mode = 3;
            action = slide.move(mode);

            if (action > -1){
                telemetry.addData("presetval", action);
                telemetry.addData("encoder postpreset", slide.getEncoder());
            }
            if (action == -2){
                telemetry.addData("Slide", "busy");
            }
            telemetry.update();

             */



            if (gamepad1.b) slide.stop();

            //Claw
            if (gamepad1.left_bumper)
                claw.close();

            if (gamepad1.right_bumper)
                claw.open();

        }
    }
}