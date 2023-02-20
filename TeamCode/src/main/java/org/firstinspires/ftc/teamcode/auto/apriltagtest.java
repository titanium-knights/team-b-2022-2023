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

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.pipeline.AprilTagDetectionPipeline;
import org.openftc.apriltag.AprilTagDetection;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

import java.util.ArrayList;

public class apriltagtest extends LinearOpMode {

    OpenCvCamera camera;
    AprilTagDetectionPipeline aprilTagDetectionPipeline;

    static final double FEET_PER_METER = 3.28084;

    // Lens intrinsics
    // UNITS ARE PIXELS
    // NOTE: this calibration is for the C920 webcam at 800x448.
    // You will need to do your own calibration for other configurations!
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

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        camera = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);
        aprilTagDetectionPipeline = new AprilTagDetectionPipeline(tagsize, fx, fy, cx, cy);

        camera.setPipeline(aprilTagDetectionPipeline);
        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
        {
            @Override
            public void onOpened()
            {
                camera.startStreaming(1280,720, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode)
            {

            }
        });

        if (opModeIsActive()) {//change to 'if' statement
            /*telemetry.addData("1 imu heading", lastAngles.firstAngle);
            telemetry.addData("2 global heading", globalAngle);
            telemetry.addData("3 correction", correction);*/
            //sleep(260);
            //detectTag();
            //telemetry.update();
            //motorsRun(-195, -195, -195, -195);
            //sleep(3000);
            while(tagID == 0){
                detectTag();
                telemetry.addData("tag", tagID);
                telemetry.update();
            }
            //motorsRun(-1000, -1000, -1000, -1000);//drive forward to the next tile
            //sleep(2000);
            //putConeRight();

            /*if(tagID == 1){
                motorsRun(1300, -1300, -1300, 1300);
            }
            else if(tagID == 3){
                motorsRun(-1150, 1150, 1150, -1150);
            }
            else if(tagID == 2){
                motorsRun(0, 0, 0, 0);
            }*/

        }

        sleep(100000000);
    }

    void detectTag() {
        // Calling getDetectionsUpdate() will only return an object if there was a new frame
        // processed since the last time we called it. Otherwise, it will return null. This
        // enables us to only run logic when there has been a new frame, as opposed to the
        // getLatestDetections() method which will always return an object.
        ArrayList<AprilTagDetection> detections = aprilTagDetectionPipeline.getDetectionsUpdate();
        // fx = aprilTagDetectionPipeline.fx;

        // If there's been a new frame...
        if(detections != null)
        {
            /*telemetry.addData("FPS", camera.getFps());
            telemetry.addData("Overhead ms", camera.getOverheadTimeMs());
            telemetry.addData("Pipeline ms", camera.getPipelineTimeMs());
            */
            // If we don't see any tags
            if(detections.size() == 0)
            {
                numFramesWithoutDetection++;

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
                    /*telemetry.addLine(String.format("\nDetected tag ID=%d", tagID));
                    telemetry.addLine(String.format("Translation X: %.2f meters", x));
                    telemetry.addLine(String.format("Translation Y: %.2f meters", y));
                    telemetry.addLine(String.format("Translation Z: %.2f meters", z-0.3));
                    //telemetry.addLine(String.format("Rotation Yaw: %.2f degrees", yaw));
                    //telemetry.addLine(String.format("Rotation Pitch: %.2f degrees", pitch));
                    //telemetry.addLine(String.format("Rotation Roll: %.2f degrees", roll));
                    telemetry.addLine(String.format("fx: %.2f", fx));
                    telemetry.addLine(String.format("fy: %.2f", fy));
                    telemetry.addLine(String.format("cx: %.2f", cx));
                    telemetry.addLine(String.format("cy: %.2f", cy));*/
                }
            }

            telemetry.update();
        }

        sleep(20);
    }
}
