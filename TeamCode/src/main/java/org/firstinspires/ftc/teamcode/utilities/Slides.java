package org.firstinspires.ftc.teamcode.utilities;

import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;

//NEGATIVE IS UP
import static java.lang.Thread.sleep;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Slides {
    DcMotor slideMotorL;
    DcMotor slideMotorR;
    //position at initial
    int Lpos;
    int Rpos;
    int state;

    //unknown, values go into negative, 0 > lowheight > midheight > maxheight
    int maxheight = -8000;
    int midheight = -6847;
    int lowheight = -3800;

    public Slides(HardwareMap hmap){
        this.slideMotorL = hmap.dcMotor.get(CONFIG.SLIDEL);
        this.slideMotorR = hmap.dcMotor.get(CONFIG.SLIDER);
        this.state = this.Lpos = this.Rpos = 0;

        slideMotorL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slideMotorR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        slideMotorL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        slideMotorR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        slideMotorL.setZeroPowerBehavior(BRAKE);
        slideMotorR.setZeroPowerBehavior(BRAKE);
    }

    public int getEncoderL(){
        return slideMotorL.getCurrentPosition();
    }
    public int getEncoderR(){
        return slideMotorR.getCurrentPosition();
    }

    public void setPower(double power){
        slideMotorL.setPower(power);
        slideMotorR.setPower(power);
    }

    public void stop(){
        slideMotorL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        slideMotorR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        setPower(0);
        Lpos = getEncoderL();
        Rpos = getEncoderR();
        state = 0;
    }

    public boolean isBusy() {return slideMotorL.isBusy();}

    public void setTarget(int target){
        slideMotorL.setTargetPosition(target);
        slideMotorR.setPower(target);
    }

    public void reset(){
        slideMotorL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slideMotorR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        Lpos = Rpos = 0;

        slideMotorL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        slideMotorR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void runToPosition(){
        slideMotorL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        slideMotorR.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        setPower(1);
    }

    public void tozero(){
        setTarget(-100);
        runToPosition();
        Lpos = getEncoderL();
        Rpos = getEncoderR();
    }

    public void low(){
        setTarget(lowheight);
        runToPosition();
        Lpos = getEncoderL();
        Rpos = getEncoderR();
    }

    public void middle(){
        setTarget(midheight);
        runToPosition();
        Lpos = getEncoderL();
        Rpos = getEncoderR();
    }

    public void upHold(){
        slideMotorL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        slideMotorR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        if (state == 1 && Lpos <= midheight + 1500){
            setPower(-0.75);
            Lpos = getEncoderL();
            Rpos = getEncoderR();
            return;
        }
        if (state == 1 || Lpos <= maxheight) return;
        state = 1;
        setPower(-1);
        Lpos = getEncoderL();
        Rpos = getEncoderR();
    }

    public void downHold(boolean encoder){
        slideMotorL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        slideMotorR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        if (!encoder){
            setPower(1.5);
            return;
        }
        if (state == 2 && Lpos >= -1800){
            setPower(0.5);
            Lpos = getEncoderL();
            Rpos = getEncoderR();
            return;
        }
        if (state == 2 || Lpos >= -200) return;
        state = 2;
        setPower(1);
        Lpos = getEncoderL();
        Rpos = getEncoderR();
    }





    // time-based / jerks
    public void pSlideUp() throws InterruptedException {
        slideMotorL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        slideMotorR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        setPower(-1);
        sleep(100);
        setPower(0);
        Lpos = getEncoderL();
        Rpos = getEncoderR();
    }

    public void pSlideDown() throws InterruptedException {
        slideMotorL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        slideMotorR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        setPower(1);
        sleep(100);
        setPower(0);
        Lpos = getEncoderL();
        Rpos = getEncoderR();
    }
}
