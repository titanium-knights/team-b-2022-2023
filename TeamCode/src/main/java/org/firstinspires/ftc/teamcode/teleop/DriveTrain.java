package org.firstinspires.ftc.teamcode.teleop;
import static java.lang.Thread.sleep;
import android.annotation.SuppressLint;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.utilities.Claw;
import org.firstinspires.ftc.teamcode.utilities.MecanumDrive;
import org.firstinspires.ftc.teamcode.utilities.Slides;

@TeleOp(name="DriveTrain Teleop")
public class DriveTrain extends LinearOpMode {
    int maxheight = -8000;
    int midheight = -6847;
    int lowheight = -3800;


    @Override
    public void runOpMode(){

        MecanumDrive drive = new MecanumDrive(hardwareMap);
        Slides slide = new Slides(hardwareMap);
        Claw claw = new Claw(hardwareMap);
        telemetry.setAutoClear(false);

        //mecanum
        boolean slowMode = true;
        //telemetry resetter
        int counter = 0;
        //If disconnects during match - checks if encoder reset to 0
        boolean encoderset = false;


        //If preset is active: Reset by manual movement, or when position reached.
        boolean presetactive = false;
        int mod = 1;

        waitForStart();
        slide.reset();
        while (opModeIsActive()) {
            //   slowMode
            //if (gamepad1.b) slowMode = !slowMode;
            drive.move(gamepad1.left_stick_x * (slowMode ? 0.3 : .7), -gamepad1.left_stick_y * (slowMode ? 0.3 : .7), gamepad1.right_stick_x * (slowMode ? 0.3 : .7));
            if (gamepad1.a && !encoderset) {slide.reset(); encoderset = true;}

            /*
            if (gamepad1.b){
                telemetry.addData("Request L", slide.getEncoders()[0]);
                telemetry.addData("Request R", slide.getEncoders()[1]);
                telemetry.update();
                ++counter;
            }
            */


            // Slides
            //Manual control, up
            if (gamepad1.left_trigger > 0) {
                encoderset = true;
            presetactive = false;
                slide.upHold();
                ++counter;
            }
            //Manual control, down
            else if (gamepad1.right_trigger > 0) {
                presetactive = false;
                slide.downHold(encoderset);
                ++counter;
            } else if (!presetactive) {slide.stop();}






            if (gamepad1.dpad_down) {slide.tozero(); presetactive = true; encoderset = true;}
            if (gamepad1.dpad_left) {slide.low(); presetactive = true; encoderset = true;}
            if(gamepad1.dpad_up) {slide.middle(); presetactive = true; encoderset = true;}
            if (!slide.isBusy() && presetactive) {slide.stop(); presetactive = false;}


            //Claw
            if (gamepad1.left_bumper){
                claw.open();
            }
            if (gamepad1.right_bumper) {
                claw.close();
            }



            if (counter % 100 == 0) telemetry.clear();

        }
    }
}