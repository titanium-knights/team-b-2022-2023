package org.firstinspires.ftc.teamcode.auto.ColorDetec;
//PUSHED 1/6/23 5:35
//ALIGN ROBOT WITH INNER BOTTOM CORNER
//Auton starting on left side of the field
import android.graphics.Color;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;

import org.firstinspires.ftc.teamcode.utilities.MecanumDrive;
@Config
@Autonomous(name="ColorDetectionAutonLeft")
public class ColorDetec_Left extends LinearOpMode {
    // Define a variable for our color sensor
    ColorSensor colorSensor;
    ColorSensor colorSensor2;
    String color;
    public static int initleft = 480;
    public static int initforward = 480;
    public static int centeringleft = 455;
    public static int returningright = 485;
    public static int returningback = 470;
    public static int returninginit = 530;

    public static int blueleft = 1700;
    public static int blueforward = 1200;

    public static int yellowleft = 250;
    public static int yellowforward = 1000;

    public static int pinkright = 900;
    public static int pinkforward = 1000;

    @Override
    public void runOpMode() {
        // Get the color sensor from hardwareMap
        //This mode uses the right color sensor
        colorSensor = hardwareMap.get(ColorSensor.class, "colorR");


        // Wait for the Play button to be pressed
        waitForStart();

        MecanumDrive robot = new MecanumDrive(hardwareMap);


        robot.move (-0.3, 0,0);
        sleep(initleft);
        robot.move(0,0,0);
        sleep(150);
        robot.move (0, 0.5, 0);
        sleep(initforward);
        robot.move(0, 0, 0);
        sleep(100);
        robot.move(-0.35, 0, 0); // new addition
        sleep(centeringleft);
        robot.move(0, 0, 0);
        sleep(150);

        int count = 0;
        int hue_sum = 0;
        while (count < 30) {
            telemetry.addData("Alpha", colorSensor.alpha());
            telemetry.addData("Red", colorSensor.red());
            telemetry.addData("Green", colorSensor.green());
            telemetry.addData("Blue", colorSensor.blue());
            float[] hsv = new float[3];
            Color.RGBToHSV(colorSensor.red(), colorSensor.green(), colorSensor.blue(), hsv);
            telemetry.addData("Hue", hsv[0]);
            hue_sum+=hsv[0];
            telemetry.addData("Saturation", hsv[1]);
            telemetry.addData("Value", hsv[2]);
            telemetry.update();
            count++;
        }
        int hue_avg = hue_sum/30;
        telemetry.addData("Hue Avg", hue_avg);
        if(hue_avg<=111) {
            color="yellow";
        } else if(hue_avg<=160) {
            color="pink";
        } else {
            color="blue";
        }

        telemetry.addData("color RIGHT SENSOR", color);
        telemetry.update();

        sleep(500);

        // reset

        robot.move(0.35, 0, 0);
        sleep(returningright);
        robot.move(0,0,0);
        sleep(150);
        robot.move (0, -0.46, 0);
        sleep(returningback);
        robot.move(0, 0, 0);
        sleep(200);
        robot.move (0.3, 0,0);
        sleep(returninginit);
        robot.move(0,0,0);
        sleep(200);

        // Location 1
        if (color.equals("blue")) {
            robot.move(-0.5,0,0);
            sleep(blueleft);
            robot.move(0,0,0);
            sleep(100);
            robot.move(0,0.5,0);
            sleep(blueforward);
            robot.move(0,0,0);
        }

        // Location 3
        else if (color.equals("yellow")) {
            robot.move(-0.5,0,0);
            sleep(yellowleft);
            robot.move(0,0,0);
            sleep(100);
            robot.move(0,0.5,0);
            sleep(yellowforward);
            robot.move(0,0,0);
        }

        // Location 3
        else if (color.equals("pink")) {
            robot.move(0.5,0, 0);
            sleep(pinkright);
            robot.move(0,0,0);
            sleep(100);
            robot.move(0,0.5,0);
            sleep(pinkforward);
            robot.move(0,0,0);
        }
    }
}
