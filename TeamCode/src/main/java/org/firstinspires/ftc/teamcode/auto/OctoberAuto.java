package org.firstinspires.ftc.teamcode.auto;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.acmerobotics.roadrunner.trajectory.TrajectoryBuilder;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.util.MecanumDrive;
import org.firstinspires.ftc.teamcode.utilities.computervision.ComputerVision;
import org.firstinspires.ftc.teamcode.utilities.computervision.pipelines.ComputerVisionPipeline;

import java.util.Objects;

@Autonomous(name = "CV_AUTO")
public class OctoberAuto extends LinearOpMode {
    public void runOpMode() throws InterruptedException {
        waitForStart();

        MecanumDrive robot = new MecanumDrive(hardwareMap);

        ComputerVision cv = new ComputerVision();
        cv.runOpMode();
//        cv.webcam.
        String color = ComputerVisionPipeline.color_detected;

        // move to spot 1
        if (color.equals("green")) {
            SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

            Trajectory  northTrajectory = drive.trajectoryBuilder(new Pose2d(35, 62, Math.toRadians(-90)))
                    .strafeLeft(20)
                    .forward(30)
                    .build();

            waitForStart();

            if(isStopRequested()) return;

            drive.followTrajectory(northTrajectory);
        }
        // move to spot 2
        else if (color.equals("yellow")) {
            SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

            Trajectory middleTrajectory = drive.trajectoryBuilder(new Pose2d(37, 63, Math.toRadians(-90)))
                    .splineTo(new Vector2d(35, 35), Math.toRadians(-90))
                    .build();

            waitForStart();

            if(isStopRequested()) return;

            drive.followTrajectory(middleTrajectory);
        }

        // move to spot 3
        else if (color.equals("pink")) {
            SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

            Trajectory southTrajectory = drive.trajectoryBuilder(new Pose2d(35, 62, Math.toRadians(-90)))
                    .strafeRight(15)
                    .forward(30)
                    .build();

            waitForStart();

            if(isStopRequested()) return;

            drive.followTrajectory(southTrajectory);
        }
    }
}
