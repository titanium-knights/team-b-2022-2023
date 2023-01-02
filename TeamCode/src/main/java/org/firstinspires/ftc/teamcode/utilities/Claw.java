package org.firstinspires.ftc.teamcode.utilities;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Claw {
    Servo clawServo_right;
    Servo clawServo_left;
    int posr, posl;
    //right + = close, left - = close

    public Claw(HardwareMap hmap){
        this.clawServo_left = hmap.servo.get(CONFIG.CLAW_L);
        this.clawServo_right = hmap.servo.get(CONFIG.CLAW_R);
        this.posr = 0;
        this.posl = 0;
    }

    public void increase(){
        posr += .1;
        posl += .1;
    }
    public void decrease(){
        posl -= .1;
        posr -= .1;
    }

    public void set(){
        clawServo_right.setPosition(posr);
        clawServo_left.setPosition(posl);
    }
    public double getLeftPosition() {return clawServo_left.getPosition();}
    public double getRightPosition() {return clawServo_right.getPosition();}

    public void close(){clawServo_left.setPosition(0.1); clawServo_right.setPosition(0.8);}
    public void open(){clawServo_left.setPosition(0.2); clawServo_right.setPosition(0.5);}
}
