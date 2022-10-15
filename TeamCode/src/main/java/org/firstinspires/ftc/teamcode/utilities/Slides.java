package org.firstinspires.ftc.teamcode.utilities;

import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Slides {
    DcMotor slideMotor;
    int curpos;

    public Slides(HardwareMap hmap){
        this.slideMotor = hmap.dcMotor.get(CONFIG.SLIDE);
        this.curpos = 0;
        slideMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        slideMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slideMotor.setZeroPowerBehavior(BRAKE);
    }

    public void spin (double dx) { slideMotor.setPower(dx);}

    public void stop() { slideMotor.setPower(0);}

    public int getEncoderPosition() {return slideMotor.getCurrentPosition();}

    //left trigger
    public void downFromTop() throws InterruptedException {
        //int pos = slideMotor.getCurrentPosition();
        //telemetry.addData("startposDown", "value:");
        slideMotor.setTargetPosition(90);
        slideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        slideMotor.setPower(0.19);
        Thread.sleep(30); // 1.7 seconds
        slideMotor.setPower(0);
    }

    //right trigger
    public void topPosition() throws InterruptedException {
        //int pos = slideMotor.getCurrentPosition();
        //telemetry.addData("startposUp", "value:");
        slideMotor.setTargetPosition(-90);
        slideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        slideMotor.setPower(-.3);
        Thread.sleep(30); // 1.7 seconds
        slideMotor.setPower(0);

    }

    public void setPower(double pwr) { slideMotor.setPower(pwr);}

}
