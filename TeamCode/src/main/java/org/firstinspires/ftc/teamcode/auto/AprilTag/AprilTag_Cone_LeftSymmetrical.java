/*
 * Copyright (c) 2021 OpenFTC Team
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.firstinspires.ftc.teamcode.auto;
import org.firstinspires.ftc.teamcode.utilities.Slides;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.pipeline.AprilTagDetectionPipeline;
import org.firstinspires.ftc.teamcode.utilities.MecanumDrive;
import org.openftc.apriltag.AprilTagDetection;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

import java.util.ArrayList;

@Config
@Autonomous(name="AprilTag_Cone_LeftSymmetrical")
public class AprilTag_Cone_LeftSymmetrical extends LinearOpMode {

    public static int initright = 480;
    public static int initforward = 430;
    public static int centeringright = 555;
    public static int returningleft = 485;
    public static int returningback = 410;
    public static int returninginit = 530;

    public static int blueleft = 900;
    public static int blueforward = 1200;

    public static int yellowleft = 250;
    public static int yellowforward = 1000;

    public static int pinkright = 1500;
    public static int pinkforward = 800;

    OpenCvCamera camera;
    AprilTagDetectionPipeline aprilTagDetectionPipeline;

    static final double FEET_PER_METER = 3.28084;

    double fx = 1000; // guess
    double fy = 1000; // guess
    double cx = 640;
    double cy = 360;

    // UNITS ARE METERS
    double tagsize = 0.166;

    int numFramesWithoutDetection = 0;
    int tagID = 0;
    double x, y, z, yaw, pitch, roll;

    final float DECIMATION_HIGH = 3;
    final float DECIMATION_LOW = 2;
    final float THRESHOLD_HIGH_DECIMATION_RANGE_METERS = 1.0f;
    final int THRESHOLD_NUM_FRAMES_NO_DETECTION_BEFORE_LOW_DECIMATION = 4;

    @Override
    public void runOpMode() throws InterruptedException {
        Slides slides = new Slides(hardwareMap);
        slides.todrop();
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        camera = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);
        aprilTagDetectionPipeline = new AprilTagDetectionPipeline(tagsize, fx, fy, cx, cy);

        camera.setPipeline(aprilTagDetectionPipeline);
        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                camera.startStreaming(1280, 720, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {

            }
        });


        while (tagID == 0) {
            telemetry.addData("cum", "cum");
            detectTag();
            telemetry.addData("tag", tagID);
            telemetry.update();
        }

        if (tagID == 4) {
            telemetry.addData("tag", tagID);
            telemetry.addData("NUMBER", "FOUR");
        } else if (tagID == 5) {
            telemetry.addData("tag", tagID);
            telemetry.addData("NUMBER", "FIVE");
        } else if (tagID == 19) {
            telemetry.addData("tag", tagID);
            telemetry.addData("NUMBER", "NINETEEN");
        }
        telemetry.update();
        sleep(100000000); // might have to get rid of this

        waitForStart();
        MecanumDrive robot = new MecanumDrive(hardwareMap);

        // MOVING CONE TO TERMINAL
        robot.move(-0.5, 0, 0);
        sleep(1400);
        robot.move(0, 0, 0);
        robot.move(0, 0, -0.1);
        sleep(10);
        sleep(400);
        robot.move(0.5, 0, 0);
        sleep(1400);
        robot.move(0, 0, 0);
        sleep(400);

        if (tagID == 4) {
            robot.move(-0.5,0,0);
            sleep(blueleft);
            robot.move(0,0,0);
            sleep(100);
            robot.move(0,0.5,0);
            sleep(blueforward);
            robot.move(0,0,0);
        }

        if (tagID == 5) {
            robot.move(0.5,0,0);
            sleep(yellowleft);
            robot.move(0,0,0);
            sleep(100);
            robot.move(0,0.5,0);
            sleep(yellowforward);
            robot.move(0,0,0);
        }

        if (tagID == 19) {
            robot.move(0.5,0, 0);
            sleep(pinkright); //changed from 1450
            robot.move(0,0,0);
            sleep(100);
            robot.move(0,0.5,0);
            sleep(pinkforward);
            robot.move(0,0,0);
        }
    }

    void detectTag() {
        telemetry.addData("detecting", "arf");
        telemetry.update();
        // Calling getDetectionsUpdate() will only return an object if there was a new frame
        // processed since the last time we called it. Otherwise, it will return null. This
        // enables us to only run logic when there has been a new frame, as opposed to the
        // getLatestDetections() method which will always return an object.
        ArrayList<AprilTagDetection> detections = aprilTagDetectionPipeline.getDetectionsUpdate();
        // fx = aprilTagDetectionPipeline.fx;

        // If there's been a new frame...
        if(detections != null)
        {
            // If we don't see any tags
            if(detections.size() == 0)
            {
                numFramesWithoutDetection++;
                telemetry.addData("frames without detection", numFramesWithoutDetection);
                telemetry.update();
                // If we haven't seen a tag for a few frames, lower the decimation
                // so we can hopefully pick one up if we're e.g. far back
                if(numFramesWithoutDetection >= THRESHOLD_NUM_FRAMES_NO_DETECTION_BEFORE_LOW_DECIMATION)
                {
                    aprilTagDetectionPipeline.setDecimation(DECIMATION_LOW);
                }
            }
            // We do see tags!
            else
            {
                numFramesWithoutDetection = 0;

                // If the target is within 1 meter, turn on high decimation to
                // increase the frame rate
                if(detections.get(0).pose.z < THRESHOLD_HIGH_DECIMATION_RANGE_METERS)
                {
                    aprilTagDetectionPipeline.setDecimation(DECIMATION_HIGH);
                }

                for(AprilTagDetection detection : detections)
                {

                    tagID = detection.id;
                    x = detection.pose.x;
                    y = detection.pose.y;
                    z = detection.pose.z;
                    yaw = Math.toDegrees(detection.pose.yaw);
                    pitch = Math.toDegrees(detection.pose.pitch);
                    roll = Math.toDegrees(detection.pose.roll);
                    telemetry.addLine(String.format("\nDetected tag ID=%d", tagID));
                }
            }

            telemetry.update();
        }

        sleep(20);
    }
}
