package org.firstinspires.ftc.teamcode.utilities;

import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;

//NEGATIVE IS UP
import static java.lang.Thread.sleep;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Slides {

    // to go up
    //Looking from front, Left (acc on the right in this view), must go clockwise
    // Right (Acc on the left in this view), must go counter clockwise
    // Positive power is counter clockwise,

    //position at initial
    int Lpos;
    int Rpos;
    //Current state of slide. 0 - idle, 1 - up, 2 - down
    int state;

    //Preset heights,
    // TO DO: CALIBRATE
    int maxheight = 8000;
    int midheight = 6847;
    int lowheight = 3800;

    public Slides(HardwareMap hmap){
        this.slideMotorL = hmap.dcMotor.get(CONFIG.SLIDEL);
        this.slideMotorR = hmap.dcMotor.get(CONFIG.SLIDER);
        this.state = this.Lpos = this.Rpos = 0;

        slideMotorL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slideMotorR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        slideMotorL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        slideMotorR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        slideMotorL.setZeroPowerBehavior(BRAKE);
        slideMotorR.setZeroPowerBehavior(BRAKE);
    }
    DcMotor slideMotorL;
    DcMotor slideMotorR;

    public int[] getEncoders(){
        return new int[]{slideMotorL.getCurrentPosition(), slideMotorR.getCurrentPosition()};
    }

    public int[] getTargets(){
        return new int[] {slideMotorL.getTargetPosition(), slideMotorR.getTargetPosition()};
    }
    public DcMotor.RunMode getMode(){
        return slideMotorL.getMode();
    }

    public void setPower(double power){
        slideMotorL.setPower(-0.9898*power);
        slideMotorR.setPower(power);
    }

    public void stop(){
        slideMotorL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        slideMotorR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        setPower(0);

        Lpos = getEncoders()[0];
        Rpos = getEncoders()[1];

        state = 0;
    }

    public boolean isBusy() {return slideMotorL.isBusy() || slideMotorR.isBusy();}

    public void setTarget(int target){
        slideMotorL.setTargetPosition(-target);
        slideMotorR.setTargetPosition(target);
    }

    public void reset(){
        slideMotorL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slideMotorR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        Lpos = Rpos = 0;
        state = 0;

        slideMotorL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        slideMotorR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void runToPosition(){
        slideMotorL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        slideMotorR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        setPower(1);

    }

    public void tozero(){
        setTarget(0);
        runToPosition();
        Lpos = getEncoders()[0];
        Rpos = getEncoders()[1];
    }

    public void low(){
        setTarget(lowheight);
        runToPosition();
        Lpos = getEncoders()[0];
        Rpos = getEncoders()[1];
    }

    public void middle(){
        setTarget(midheight);
        runToPosition();
        Lpos = getEncoders()[0];
        Rpos = getEncoders()[1];
    }

    public void upHold(){
        slideMotorL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        slideMotorR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        Lpos = getEncoders()[0];
        Rpos = getEncoders()[1];
        if (Rpos >= maxheight){
            setPower(0);
            return;
        }
        if (state == 1 && Rpos >= midheight + 1500){
            setPower(0.75);
            Lpos = getEncoders()[0];
            Rpos = getEncoders()[1];
            return;
        }

        if (state == 1){
            return;
        }
        state = 1;
        setPower(1);

    }

    public void downHold(boolean encoder){
        slideMotorL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        slideMotorR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        Lpos = getEncoders()[0];
        Rpos = getEncoders()[1];
        if (!encoder){
            setPower(-1.5);
            return;
        }
        if (Rpos <= 100){
            setPower(0);
            return;
        }
        if (state == 2 && Rpos <= 1800){
            setPower(-0.5);
            Lpos = getEncoders()[0];
            Rpos = getEncoders()[1];
            return;
        }

        if (state == 2){
            return;
        }
        state = 2;
        setPower(-1);

    }






}
