package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.utilities.MecanumDrive;
import org.firstinspires.ftc.teamcode.utilities.computervision.ComputerVision;
import org.firstinspires.ftc.teamcode.utilities.computervision.pipelines.ComputerVisionPipeline;

@Autonomous(name = "CVParkAuton")
public class CVParkAuton extends LinearOpMode {
    public void runOpMode() throws InterruptedException {
        waitForStart();

        MecanumDrive robot = new MecanumDrive(hardwareMap);
        ComputerVision cv = new ComputerVision(hardwareMap, telemetry);
        cv.runOpMode();

        String color = ComputerVisionPipeline.color_detected;
        sleep(7500);
        telemetry.addData("color", color);
        telemetry.update();
        // time-auton written by Gabe
        // move to spot 1
//
//        if (color.equals("green")) {
//            robot.move(-1,0,0);
//            sleep(800);
//            robot.move(0,0,0);
//            robot.move(0,2,0);
//            sleep(800);
//            robot.move(0,0,0);
//        }
//        // move to spot 2
//        else if (color.equals("yellow")) {
//            robot.move(0,2,0);
//            sleep(800);
//            robot.move(0,0,0);
//        }
//
//        // move to spot 3
//        else if (color.equals("pink")) {
//            robot.move(1,0,0);
//            sleep(800);
//            robot.move(0,0,0);
//            robot.move(0,2,0);
//            sleep(800);
//            robot.move(0,0,0);
//        }
    }
}
