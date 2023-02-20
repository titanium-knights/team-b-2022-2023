package org.firstinspires.ftc.teamcode.utilities;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Claw {
    Servo clawServo_right;
    Servo clawServo_left;
    //right + = close, left - = close

    public Claw(HardwareMap hmap){
        this.clawServo_left = hmap.servo.get(CONFIG.CLAW_L);
        this.clawServo_right = hmap.servo.get(CONFIG.CLAW_R);
    }


    public double getLeftPosition() {return clawServo_left.getPosition();}
    public double getRightPosition() {return clawServo_right.getPosition();}

    public void open(){
        clawServo_left.setPosition(0.15);
//        clawServo_right.setPosition(0.95);
    }
    public void close(){
        clawServo_left.setPosition(0.5);
//        clawServo_right.setPosition(0.86);
    }

    public void test1(){
        clawServo_left.setPosition(1);
    }
    public void test0(){
        clawServo_left.setPosition(0);
    }
}
