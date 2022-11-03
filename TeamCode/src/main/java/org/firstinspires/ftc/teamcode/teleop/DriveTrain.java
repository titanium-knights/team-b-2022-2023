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
    int maxheight = -8000;
    int midheight = -6847;
    int lowheight = -3800;


    @Override
    public void runOpMode() throws InterruptedException {

        MecanumDrive drive = new MecanumDrive(hardwareMap);
        Slides slide = new Slides(hardwareMap);
        Claw claw = new Claw(hardwareMap);
        telemetry.setAutoClear(false);

        //mecanum
        boolean slowMode = false;
        //slide preset: 0 - orig, 1 - low, 2 - med, 3 - high
        int counter = 0;
        boolean encoderset = false;

        waitForStart();

        while (opModeIsActive()) {
            //   slowMode
            if (gamepad1.b) slowMode = !slowMode;
            drive.move(gamepad1.left_stick_x * (slowMode ? 0.3 : .7), -gamepad1.left_stick_y * (slowMode ? 0.3 : .7), gamepad1.right_stick_x * (slowMode ? 0.3 : .7));

            // Slides
            //Manual control, up
            if (gamepad1.left_trigger > 0) {
                slide.upHold();
                telemetry.addData("encoder postup", slide.getEncoder());
                telemetry.update();
                ++counter;
            }
            //Manual control, down
            else if (gamepad1.right_trigger > 0) {
                slide.downHold(encoderset);
                telemetry.addData("encoder postdown", slide.getEncoder());
                telemetry.update();
                ++counter;

            } else{
                slide.stop();
            }

            if (gamepad1.dpad_down) slide.tozero();
            if (gamepad1.dpad_left) slide.low();
            if(gamepad1.dpad_up) slide.middle();
            if (!slide.isBusy()) slide.stop();

            if (gamepad1.a) {
                slide.reset();
                encoderset = true;
            }

            //Claw
            if (gamepad1.left_bumper) claw.close();

            if (gamepad1.right_bumper) claw.open();

            if (counter % 100 == 0) telemetry.clear();

        }
    }
}