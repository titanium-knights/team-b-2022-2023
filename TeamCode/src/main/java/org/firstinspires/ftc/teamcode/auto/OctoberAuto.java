package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.utilities.MecanumDrive;
import org.firstinspires.ftc.teamcode.utilities.computervision.ComputerVision;
import org.firstinspires.ftc.teamcode.utilities.computervision.pipelines.ComputerVisionPipeline;

import java.util.Objects;
import org.firstinspires.ftc.teamcode.utilities.Claw;

@Autonomous(name = "CV_AUTO")
public class OctoberAuto extends LinearOpMode {
    public void runOpMode() throws InterruptedException {
        waitForStart();

        MecanumDrive robot = new MecanumDrive(hardwareMap);
        Claw claw = new Claw(hardwareMap);
        claw.open();

        robot.forward();
        /*
        ComputerVision cv = new ComputerVision();
        cv.runOpMode();
//        cv.webcam.
        String color = ComputerVisionPipeline.color_detected;

        // move to spot 1
        if (color.equals("green")) {
            robot.setPower(1,1,0);

        }
        // move to spot 2
        else if (color.equals("yellow")) {
            robot.setPower(1,0,0);
        }

        // move to spot 3
        else if (color.equals("pink")) {
            robot.setPower(1,-1,0);
        }

         */
    }
}
