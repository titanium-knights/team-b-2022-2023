package org.firstinspires.ftc.teamcode.util.computervision.pipelines;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

public class ComputerVisionPipeline extends OpenCvPipeline {

    @Override
    public Mat processFrame(Mat input) {
        // TODO: add code to detect color in frame
        Imgproc.cvtColor(input, input, Imgproc.COLOR_RGBA2GRAY);
        return input;
    }
}
