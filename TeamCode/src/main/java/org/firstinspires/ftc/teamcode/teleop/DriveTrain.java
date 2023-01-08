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


    @Override
    public void runOpMode(){

        MecanumDrive drive = new MecanumDrive(hardwareMap);
        Slides slide = new Slides(hardwareMap);
        Claw claw = new Claw(hardwareMap);
        telemetry.setAutoClear(false);

        //mecanum
        boolean slowMode = false;
        //telemetry resetter
        int counter = 0;
        //If disconnects during match - checks if encoder reset to 0


        //If preset is active: Reset by manual movement, or when position reached.
        boolean presetactive = false;
        int mod = 1;

        waitForStart();
        slide.reset();
        while (opModeIsActive()) {
            //   slowMode
            if (gamepad1.b) slowMode = !slowMode;
            drive.move(gamepad1.left_stick_x
                            * (slowMode ? 0.3 : .6),
                    -gamepad1.left_stick_y
                            * (slowMode ? 0.3 : .6),
                    gamepad1.right_stick_x
                            * (slowMode ? 0.3 : .55));


            // Slides
            //Manual control, up
            if (gamepad1.left_trigger > 0) {
                presetactive = false;
                slide.upHold();
                telemetry.addData("Up",
                        slide.getEncoders()[0]);
                telemetry.update();
                ++counter;
            }
            //Manual control, down
            else if (gamepad1.right_trigger > 0) {
                presetactive = false;
                slide.downHold();
                telemetry.addData("Down",
                        slide.getEncoders()[0]);
                telemetry.update();
                ++counter;
            } else if (!presetactive) {slide.stop();}

            if (gamepad1.dpad_down) {slide.tozero(); claw.close();
                presetactive = true;}
            if (gamepad1.dpad_left) {slide.low();
                presetactive = true;}
            if(gamepad1.dpad_up) {slide.middle();
                presetactive = true;}
            if (!slide.isBusy() && presetactive) {slide.stop();
                presetactive = false;}

            //Claw
            if (gamepad1.x){
                telemetry.addData("RIGHT SLIDE TELEMETRY:", slide.getEncoders()[0]);
                telemetry.addData("LEFT SLIDE TELEMETRY:", slide.getEncoders()[1]);
                telemetry.update();
            }
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