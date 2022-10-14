package org.firstinspires.ftc.teamcode.utilities;

import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Slides {
    DcMotor slideMotor;

    public Slides(HardwareMap hmap){
        this.slideMotor = hmap.dcMotor.get(CONFIG.SLIDE);
        slideMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        slideMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slideMotor.setZeroPowerBehavior(BRAKE);
    }

    public void spin (double dx) { slideMotor.setPower(dx);}

    public void stop() { slideMotor.setPower(0);}

    public int getEncoderPosition() {return slideMotor.getCurrentPosition();}

    //right trigger
    public void up(int mode){
        //CHANGE
        slideMotor.setTargetPosition(0);
        slideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        slideMotor.setPower(0);
    }

    //left trigger
    public void down(int mode){
        //CHANGE
        slideMotor.setTargetPosition(0);
        slideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        slideMotor.setPower(0);
    }

    public void setPower(double pwr) { slideMotor.setPower(pwr);}

}
