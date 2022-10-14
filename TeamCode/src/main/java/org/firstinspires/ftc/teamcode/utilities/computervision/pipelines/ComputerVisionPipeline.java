package org.firstinspires.ftc.teamcode.utilities.computervision.pipelines;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

import java.util.ArrayList;

public class ComputerVisionPipeline extends OpenCvPipeline {
    public boolean colorDetected(Mat input, ArrayList<Scalar> boundary, Mat res) {
        Core.inRange(input, boundary.get(0), boundary.get(1), res);

        double sum = 0;
        for(int i = 0; i < res.rows(); i++) {
            for(int j = 0; j < res.cols(); j++) {
                sum+=res.get(i, j)[0];
            }
        }

        return sum > 0;
    }

    @Override
    public Mat processFrame(Mat input) {
        Mat mat = new Mat();
        Imgproc.cvtColor(input, mat, Imgproc.COLOR_RGB2HSV);

        ArrayList<Scalar> yellow_boundary = new ArrayList<>();
        yellow_boundary.add(new Scalar(0, 133, 0));
        yellow_boundary.add(new Scalar(179, 255, 255));
        ArrayList<Scalar> green_boundary = new ArrayList<>();
        green_boundary.add(new Scalar(0, 16, 96));
        green_boundary.add(new Scalar(179, 255, 255));
        ArrayList<Scalar> pink_boundary = new ArrayList<>();
        pink_boundary.add(new Scalar(0, 161, 0));
        pink_boundary.add(new Scalar(179, 255, 255));

        for(int i =0; i < 3; i++) {
            String color_detected = "";
            Mat res = new Mat();
            if(i==0) {
                if(colorDetected(mat, yellow_boundary, res)) {
                     color_detected = "yellow";
                    return res;
                }
            } else if(i==1) {
                if(colorDetected(mat, green_boundary, res)) {
                    color_detected = "green";
                    return res;
                }
            } else if(i==2) {
                if(colorDetected(mat, pink_boundary, res)) {
                    color_detected = "pink";
                    return res;
                }
            }
        }
        return input;
    }
}
