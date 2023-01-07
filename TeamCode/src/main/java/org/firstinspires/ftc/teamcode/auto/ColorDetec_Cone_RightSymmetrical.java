package org.firstinspires.ftc.teamcode.auto;
//PUSHED 1/6/23 5:35
//ALIGN ROBOT WITH INNER BOTTOM CORNER
//Auton starting on right side of the field
import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;

import org.firstinspires.ftc.teamcode.utilities.MecanumDrive;

@Autonomous(name="ColorDetectionConeAutonRightSymmetrical")
public class ColorDetec_Cone_RightSymmetrical extends LinearOpMode {
    // Define a variable for our color sensor
    ColorSensor colorSensor;
    String color;

    @Override
    public void runOpMode() {
        // Get the color sensor from hardwareMap
        //This mode uses the left color sensor
        colorSensor = hardwareMap.get(ColorSensor.class, "colorL");

        // Wait for the Play button to be pressed
        waitForStart();

        MecanumDrive robot = new MecanumDrive(hardwareMap);

        // MOVING CONE TO TERMINAL
        robot.move(0.5, 0, 0);
        sleep(1400);
        robot.move(0, 0, 0);
        sleep(400);
        robot.move(-0.5, 0, 0);
        sleep(1400);
        robot.move(0, 0, 0);
        sleep(400);

        robot.move (0.3, 0,0); //changed from 0.5 to 0.3
        sleep(550);
        robot.move(0,0,0);
        sleep(1000);
        robot.move (0, 0.44, 0);
        sleep(600);
        robot.move(0, 0, 0);
        sleep(550);
        robot.move(0.35, 0, 0); // new addition
        sleep(550);
        robot.move(0, 0, 0);
        sleep(550);

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
        if(hue_avg<=137) {
            color="yellow";
        } else if(hue_avg<=160) {
            color="pink";
        } else {
            color="blue";
        }

        telemetry.addData("color LEFT SENSOR", color);
        telemetry.update();

        sleep(5000);

        // reset

        robot.move(-0.35, 0, 0);
        sleep(550);
        robot.move(0,0,0);
        sleep(550);
        robot.move (0, -0.44, 0);
        sleep(500);
        robot.move(0, 0, 0);
        sleep(1000);
        robot.move (-0.3, 0,0);
        sleep(550);
        robot.move(0,0,0);
        sleep(1000);

        //move to spot 1
        if (color.equals("blue")) {
            robot.move(-0.41,0,0);
            sleep(1600);
            robot.move(0,0,0);
            robot.move(0,0.5,0);
            sleep(1100);
            robot.move(0,0,0);
        }

        // move to spot 2
        else if (color.equals("yellow")) {
            robot.move(0.5,0,0);
            sleep(250);
            robot.move(0,0,0);
            sleep(100);
            robot.move(0,0.5,0);
            sleep(1000);
            robot.move(0,0,0);
        }

        // move to spot 3
        else if (color.equals("pink")) {
            robot.move(0.5,0, 0);
            sleep(1600); //changed from 1450
            robot.move(0,0,0);
            robot.move(0,0.5,0);
            sleep(1000);
            robot.move(0,0,0);
        }
    }
}
