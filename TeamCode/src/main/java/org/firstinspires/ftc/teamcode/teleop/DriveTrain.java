package org.firstinspires.ftc.teamcode.teleop;


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

    public void initialize(){
        MecanumDrive drive = new MecanumDrive(hardwareMap);
        Slides slide = new Slides(hardwareMap);
        Claw claw = new Claw(hardwareMap);
    }

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
            if (gamepad1.dpad_up) slowMode = !slowMode;

            drive.move(gamepad1.left_stick_x * (slowMode ? 0.3 : .7), -gamepad1.left_stick_y * (slowMode ? 0.3 : .7), gamepad1.right_stick_x * (slowMode ? 0.3 : .7));

            // Slides
            //Manual control, up
            if (gamepad1.left_trigger > 0) {
                telemetry.addData("encoder preman", slide.getEncoder());
                telemetry.addData("left trigger", "slides down");
                slide.manual(true);
                telemetry.addData("encoder postman", slide.getEncoder());
                telemetry.update();
            }
            //Manual control, down
            else if (gamepad1.right_trigger > 0) {
                telemetry.addData("encoder preman", slide.getEncoder());
                telemetry.addData("right trigger", "slides down");
                slide.manual(false);
                telemetry.addData("encoder postman", slide.getEncoder());
                telemetry.update();
            }

            //height control
            action = -1;
            telemetry.addData("encoder prepreset", slide.getEncoder());
            if (gamepad1.dpad_down) action = slide.move(0);
            if (gamepad1.dpad_left) action = slide.move(1);
            if (gamepad1.dpad_up) action = slide.move(2);
            if (gamepad1.dpad_right) action = slide.move(3);
            if (action > -1){
                telemetry.addData("presetval", action);
                telemetry.addData("encoder postpreset", slide.getEncoder());
            }

            if (gamepad1.b) slide.stop();


            //Claw
            if (gamepad1.left_bumper)
                claw.close();

            if (gamepad1.right_bumper)
                claw.open();


        }
    }
}