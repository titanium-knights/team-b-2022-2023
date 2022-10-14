package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.util.MecanumDrive;
import org.firstinspires.ftc.teamcode.utilities.computervision.ComputerVision;
import org.firstinspires.ftc.teamcode.utilities.computervision.pipelines.ComputerVisionPipeline;

@Autonomous(name = "CV_AUTO")
public class OctoberAuto extends LinearOpMode {
    public void runOpMode() throws InterruptedException {
        waitForStart();

        MecanumDrive robot = new MecanumDrive(hardwareMap);

        ComputerVision cv = new ComputerVision();
        cv.runOpMode();
//        cv.webcam.

    }
}
