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
    int maxheight = -8000;
    int midheight = -6847;
    int lowheight = -3800;

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
        pos = getEncoder();
        state = 0;
    }

    public boolean isBusy() {return slideMotor.isBusy(); }

    public int getEncoder(){
        return slideMotor.getCurrentPosition();
    }

    public void setTarget(int target){
        slideMotor.setTargetPosition(target);
    }

    public void reset(){
        slideMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        pos = 0;
        slideMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void runToPosition(){
        slideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        slideMotor.setPower(4);
    }

    public void tozero(){
        setTarget(-200);
        runToPosition();
        pos = getEncoder();
    }

    public void low(){
        setTarget(lowheight);
        runToPosition();
        pos = getEncoder();
    }

    public void middle(){
        setTarget(midheight);
        runToPosition();
        pos = getEncoder();
    }

    public void upHold(){
        slideMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        if (state == 1 && pos <= midheight + 1500){
            slideMotor.setPower(-0.75);
            pos = getEncoder();
        }
        if (state == 1 || pos <= maxheight) return;
        state = 1;
        slideMotor.setPower(-2);
        pos = getEncoder();
    }

    public void downHold(boolean encoder){
        slideMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        if (!encoder){
            slideMotor.setPower(1.5);
            return;
        }
        if (state == 2 && pos >= -1800){
            slideMotor.setPower(0.5);
            pos = getEncoder();
            return;
        }
        if (state == 2 || pos >= -200) return;
        state = 2;
        slideMotor.setPower(3);
        pos = getEncoder();
    }





    // time-based / jerks
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
}
