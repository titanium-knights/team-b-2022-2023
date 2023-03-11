package org.firstinspires.ftc.teamcode.auto;
//PUSHED 1/6/23 5:35
//ALIGN ROBOT WITH INNER BOTTOM CORNER
//Auton starting on right side of the field
import android.graphics.Color;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;

import org.firstinspires.ftc.teamcode.utilities.MecanumDrive;
import org.firstinspires.ftc.teamcode.utilities.Claw;
import org.firstinspires.ftc.teamcode.utilities.Slides;

@Config
@Autonomous(name="ColorDetectionStackAutonRightSymmetrical")
public class ColorDetec_Stack_RightSymmetrical extends LinearOpMode {
    // Define a variable for our color sensor
    ColorSensor colorSensor;
    String color;
    public static int junctionleft = 490;
    public static int junctionforward = 270;
    public static int junctionslowforward = 250;
    public static int junctionslowback = 250;
    public static int junctionback = 100;
    public static int junctionright = 430;

    public static int initright = 530;
    public static int initforward = 620;
    public static int centeringright = 450;
    public static int returningleft = 485;
    public static int returningback = 450;
    public static int returninginit = 530;

    public static int blueleft = 1700;
    public static int blueforward = 1100;

    public static int yellowright = 250;
    public static int yellowforward = 1100;

    public static int pinkright = 1530;
    public static int pinkforward = 1200;

    @Override
    public void runOpMode() {
        // Get the color sensor from hardwareMap
        //This mode uses the left color sensor
        colorSensor = hardwareMap.get(ColorSensor.class, "colorL");

        // Wait for the Play button to be pressed
        waitForStart();

        MecanumDrive robot = new MecanumDrive(hardwareMap);
        Claw claw = new Claw(hardwareMap);
        Slides slides = new Slides(hardwareMap);

        claw.close();
        sleep(800);
        slides.low();
        while (slides.isBusy()) {
            continue;
        }
        slides.stop();

        robot.move(-0.5,0,0);
        sleep(junctionleft);
        robot.move(0,0,0);
        sleep(200);
        robot.move(0,0.5,0);
        sleep(junctionforward);
        robot.move(0, 0.3, 0);
        sleep(junctionslowforward);
        robot.move(0,0,0);
        sleep(200);


        slides.toscan();
        while (slides.isBusy()) {
            continue;
        }
        slides.stop();
        claw.open();
        sleep(200);
        slides.tozero();
        while (slides.isBusy()) {
            continue;
        }
        slides.stop();


        sleep(200);

        robot.move(0,-0.3,0);
        sleep(junctionslowback);
        robot.move(0,-0.5,0);
        sleep(junctionback);
        robot.move(0,0,0);
        sleep(200);
        robot.move(0.5,0,0);
        sleep(junctionright);
        robot.move(0,0,0);
        sleep(200);

        robot.move (0.3, 0,0); //changed from 0.5 to 0.3
        sleep(initright);
        robot.move(0,0,0);
        sleep(200);
        robot.move (0, 0.5, 0);
        sleep(initforward);
        robot.move(0, 0, 0);
        sleep(150);
        robot.move(0.35, 0, 0); // new addition
        sleep(centeringright);
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
        sleep(returningleft);
        robot.move(0,0,0);
        sleep(150);
        robot.move (0, -0.46, 0);
        sleep(returningback);
        robot.move(0, 0, 0);
        sleep(200);
        robot.move (-0.3, 0,0);
        sleep(returninginit);
        robot.move(0,0,0);
        sleep(200);

        //move to spot 1
        if (color.equals("blue")) {
            robot.move(-0.41,0,0);
            sleep(blueleft);
            robot.move(0,0,0);
            robot.move(0,0.5,0);
            sleep(blueforward);
            robot.move(0,0,0);
        }

        // move to spot 2
        else if (color.equals("yellow")) {
            robot.move(0.5,0,0);
            sleep(yellowright);
            robot.move(0,0,0);
            sleep(100);
            robot.move(0,0.5,0);
            sleep(yellowforward);
            robot.move(0,0,0);
        }

        // move to spot 3
        else if (color.equals("pink")) {
            robot.move(0.5,0, 0);
            sleep(pinkright); //changed from 1450
            robot.move(0,0,0);
            robot.move(0,0.5,0);
            sleep(pinkforward);
            robot.move(0,0,0);
        }
    }
}
