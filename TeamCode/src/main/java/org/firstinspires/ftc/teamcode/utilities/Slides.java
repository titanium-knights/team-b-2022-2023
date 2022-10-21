package org.firstinspires.ftc.teamcode.utilities;

import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;

//NEGATIVE IS UP
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

//ONCE ENCODERS INSTALLED
public class Slides {
    DcMotor slideMotor;
    //position at initial
    int initpos;
    int pos;

    //unknown
    int maxheight = 0;
    int midheight = 0;
    int lowheight = 0;

    public Slides(HardwareMap hmap){

        this.slideMotor = hmap.dcMotor.get(CONFIG.SLIDE);
        this.initpos = this.pos = 0;

        slideMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slideMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        slideMotor.setZeroPowerBehavior(BRAKE);

    }

    public void stop(){
        slideMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        slideMotor.setPower(0);
    }

    public int getEncoder(){
        return slideMotor.getCurrentPosition();
    }

    public int getTarget(){
        return slideMotor.getTargetPosition();
    }

    public void setTarget(int target){
        slideMotor.setTargetPosition(target);
    }

    public void runToPosition(){
        slideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        slideMotor.setPower(0.3);
        while (slideMotor.isBusy() && pos > initpos && pos < maxheight) pos = getEncoder();
        slideMotor.setPower(0);
        slideMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    // positive target is up, negative is down
    public int move(int mode){
        if (slideMotor.isBusy()) return -1;

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



    // to find min/max, true = up, false = down
    public void manual(boolean direction){
        if (slideMotor.isBusy()) return;

        setTarget(pos + (direction ? -1 : 1) * 90);
        runToPosition();
    }







}
