package org.firstinspires.ftc.teamcode.teleop;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.util.MecanumDrive;

@TeleOp(name="DriveTrain Teleop")
public class DriveTrain extends LinearOpMode {
    public static DcMotor fl, fr, bl, br;

    public void initialize(){
    }

    @Override
    public void runOpMode() throws InterruptedException {
        MecanumDrive drive = new MecanumDrive(hardwareMap);
        boolean slowMode = false;

        waitForStart();

        while (opModeIsActive()) {
            //   slowMode
            if (gamepad1.dpad_up) slowMode = !slowMode;

            drive.move(gamepad1.left_stick_x * (slowMode ? 0.3 : .7), -gamepad1.left_stick_y * (slowMode ? 0.3 : .7), gamepad1.right_stick_x * (slowMode ? 0.3 : .7));

            // Slides

            // Turntable

            //Claw


        }
    }
}