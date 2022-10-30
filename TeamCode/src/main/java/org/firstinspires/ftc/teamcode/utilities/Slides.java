package org.firstinspires.ftc.teamcode.utilities;

import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;

//NEGATIVE IS UP
import static java.lang.Thread.sleep;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Slides {
    DcMotor slideMotor;
    //position at initial
    int pos;
    int state;

    //unknown, values go into negative, 0 > lowheight > midheight > maxheight
    int maxheight = -7600;
    int midheight = 0;
    int lowheight = 0;

    public Slides(HardwareMap hmap){

        this.slideMotor = hmap.dcMotor.get(CONFIG.SLIDE);
        ///0 - stat, 1 - up, 2 - down
        this.state = this.pos = 0;


        slideMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slideMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        slideMotor.setZeroPowerBehavior(BRAKE);

    }

    public void stop(){
        slideMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        slideMotor.setPower(0);
        state = 0;

    }

    public double getPower() { return slideMotor.getPower();}

    public int getEncoder(){
        return slideMotor.getCurrentPosition();
    }

    public int getTarget(){
        return slideMotor.getTargetPosition();
    }

    public void setTarget(int target){
        slideMotor.setTargetPosition(target);
    }

    public void pSlideUp() throws InterruptedException {
        slideMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        slideMotor.setPower(-1);
        sleep(100);
        slideMotor.setPower(0);
        pos = getEncoder();
    }

    public void pSlideDown() throws InterruptedException {
        slideMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        slideMotor.setPower(1);
        sleep(100);
        slideMotor.setPower(0);
        pos = getEncoder();
    }

    public void upHold(){
        if (state == 1 || pos <= maxheight) return;
        state = 1;
        slideMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        slideMotor.setPower(-1);
        pos = getEncoder();
    }

    public void downHold(){
        if (state == 2 || pos >= 0) return;
        state = 2;
        slideMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        slideMotor.setPower(1);
        pos = getEncoder();
    }



    public void runToPosition(){
        slideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        slideMotor.setPower(0.8);
        while (slideMotor.isBusy()) pos = getEncoder();
        slideMotor.setPower(0);
        slideMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    // positive target is up, negative is down
    public int move(int mode){
        if (slideMotor.isBusy()) return -2;

        int target;

        switch(mode){
            case 0:
                target = 0;
                break;
            case 1:
                target = lowheight;
                break;
            case 2:
                target = midheight;
                break;
            case 3:
                target = maxheight;
                break;
            default:
                target = 0;
        }

        setTarget(target);
        runToPosition();

        return mode;
    }











}
