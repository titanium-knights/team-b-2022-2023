package org.firstinspires.ftc.teamcode.utilities.computervision;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.utilities.computervision.pipelines.ComputerVisionPipeline;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvWebcam;
import static org.firstinspires.ftc.teamcode.utilities.CONFIG.WEBCAM;

public class ComputerVision extends LinearOpMode {
    public OpenCvWebcam webcam;
    public ComputerVisionPipeline cvp = new ComputerVisionPipeline();
    public Telemetry telemetry;

    public ComputerVision(HardwareMap hmap, Telemetry telemetry) {
        this.telemetry = telemetry;
        telemetry.addData("constructor", "constructor");
        webcam = OpenCvCameraFactory.getInstance().createWebcam(hmap.get(WebcamName.class, WEBCAM));
        this.telemetry.addData("init", "camera initialized");
        this.telemetry.update();
    }

    @Override
    public void runOpMode() {
        webcam.setPipeline(cvp);

        webcam.setMillisecondsPermissionTimeout(5000);
        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {

            @Override
            public void onOpened() {
                telemetry.addLine("camera has opened");
                telemetry.update();

                webcam.startStreaming(320, 240, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {
                telemetry.addLine("camera is not opening");
                telemetry.update();
            }
        });
    }
}
