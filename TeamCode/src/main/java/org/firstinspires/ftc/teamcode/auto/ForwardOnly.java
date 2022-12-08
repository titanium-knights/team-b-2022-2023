package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.utilities.MecanumDrive;

@Autonomous(name="ForwardOnlyAuton")
public class ForwardOnly extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        // Wait for the Play button to be pressed
        waitForStart();

        MecanumDrive robot = new MecanumDrive(hardwareMap);

        telemetry.addLine("start");

        sleep(5000);
        robot.move (0, 1.6, 0); // change value
        sleep(800); // change value
        robot.move(0,0,0);

        telemetry.addLine("end");
    }
}
