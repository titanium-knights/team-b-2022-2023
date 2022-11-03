package org.firstinspires.ftc.teamcode.auto;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.acmerobotics.roadrunner.trajectory.TrajectoryBuilder;
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
        claw.close();

//        robot.forward();
//        ComputerVision cv = new ComputerVision();
//        cv.runOpMode();
//        cv.webcam.
//        String color = ComputerVisionPipeline.color_detected;

        // time-auton written by Gabe
        // move to spot 1

        String color = "green";
        if (color.equals("green")) {
//            SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
//
//            Trajectory  northTrajectory = drive.trajectoryBuilder(new Pose2d(35, 62, Math.toRadians(-90)))
//                    .strafeLeft(20)
//                    .forward(30)
//                    .build();
//
//            waitForStart();
//
//            if(isStopRequested()) return;
//
//            drive.followTrajectory(northTrajectory);

            robot.move(-1,0,0);
            sleep(800);
            robot.move(0,0,0);
            robot.move(0,2,0);
            sleep(800);
            robot.move(0,0,0);
        }
        // move to spot 2
        else if (color.equals("yellow")) {
//            SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
//
//            Trajectory middleTrajectory = drive.trajectoryBuilder(new Pose2d(37, 63, Math.toRadians(-90)))
//                    .splineTo(new Vector2d(35, 35), Math.toRadians(-90))
//                    .build();
//
//            waitForStart();
//
//            if(isStopRequested()) return;
//
//            drive.followTrajectory(middleTrajectory);

            robot.move(0,2,0);
            sleep(800);
            robot.move(0,0,0);
        }

        // move to spot 3
        else if (color.equals("pink")) {
//            SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
//
//            Trajectory southTrajectory = drive.trajectoryBuilder(new Pose2d(35, 62, Math.toRadians(-90)))
//                    .strafeRight(15)
//                    .forward(30)
//                    .build();
//
//            waitForStart();
//
//            if(isStopRequested()) return;
//
//            drive.followTrajectory(southTrajectory);

            robot.move(1,0,0);
            sleep(800);
            robot.move(0,0,0);
            robot.move(0,2,0);
            sleep(800);
            robot.move(0,0,0);
        }
    }
}
