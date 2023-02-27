package org.firstinspires.ftc.teamcode.auto;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import android.graphics.Color;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
@Config
@Autonomous(name="colortest")
public class colortest  extends LinearOpMode {
    ColorSensor colorSensor;
    String color;

    @Override
    public void runOpMode(){
        colorSensor = hardwareMap.get(ColorSensor.class, "colorR");
        int count = 0;
        int hue_sum = 0;
        while (count < 30) {

            float[] hsv = new float[3];
            Color.RGBToHSV(colorSensor.red(), colorSensor.green(), colorSensor.blue(), hsv);

            hue_sum+=hsv[0];

            count++;
        }
        int hue_avg = hue_sum/30;
        telemetry.addData("Hue Avg", hue_avg);
        if(hue_avg<=127) {
            color="yellow";
        } else if(hue_avg<=160) {
            color="pink";
        } else {
            color="blue";
        }

        telemetry.addData("color RIGHT SENSOR", color);

        colorSensor = hardwareMap.get(ColorSensor.class, "colorL");
        count = 0;
        hue_sum = 0;
        while (count < 30) {
            float[] hsv = new float[3];
            Color.RGBToHSV(colorSensor.red(), colorSensor.green(), colorSensor.blue(), hsv);

            hue_sum+=hsv[0];

            count++;
        }
        hue_avg = hue_sum/30;
        telemetry.addData("Hue Avg", hue_avg);
        if(hue_avg<=127) {
            color="yellow";
        } else if(hue_avg<=160) {
            color="pink";
        } else {
            color="blue";
        }

        telemetry.addData("color LEFT SENSOR", color);
        telemetry.update();
        sleep(100000000);
    }

}
