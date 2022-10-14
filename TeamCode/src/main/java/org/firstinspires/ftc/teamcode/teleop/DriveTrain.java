package org.firstinspires.ftc.teamcode.teleop;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;


import org.firstinspires.ftc.teamcode.utilities.Claw;
import org.firstinspires.ftc.teamcode.utilities.MecanumDrive;
import org.firstinspires.ftc.teamcode.utilities.Slides;

@TeleOp(name="DriveTrain Teleop")
public class DriveTrain extends LinearOpMode {
    public static DcMotor fl, fr, bl, br;

    public void initialize(){
    }

    @Override
    public void runOpMode() throws InterruptedException {
        MecanumDrive drive = new MecanumDrive(hardwareMap);
        Slides slide = new Slides(hardwareMap);
        Claw claw = new Claw(hardwareMap);
        boolean slowMode = false;

        waitForStart();

        while (opModeIsActive()) {
            //   slowMode
            if (gamepad1.dpad_up) slowMode = !slowMode;

            drive.move(gamepad1.left_stick_x * (slowMode ? 0.3 : .7), -gamepad1.left_stick_y * (slowMode ? 0.3 : .7), gamepad1.right_stick_x * (slowMode ? 0.3 : .7));

            // Slides
            slide.spin(gamepad1.left_trigger/10);
            slide.spin(-gamepad1.right_trigger/10);

            //Claw
            if (gamepad1.left_bumper)
                claw.open();
            if (gamepad1.right_bumper)
                claw.close();


        }
    }
}