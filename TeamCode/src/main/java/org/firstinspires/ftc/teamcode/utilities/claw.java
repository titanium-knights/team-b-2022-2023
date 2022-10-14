package org.firstinspires.ftc.teamcode.util;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class claw {
    Servo clawServo_right;
    Servo clawServo_left;

    public claw(HardwareMap hmap){
        this.clawServo_left = hmap.servo.get(CONFIG.CLAW_L);
        this.clawServo_right = hmap.servo.get(CONFIG.CLAW_R);
    }

    public void spin(double dx) {
        clawServo_left.setPosition(dx);
        clawServo_right.setPosition(-dx);
    }

    public void close() {
        spin(-.6);
    }

    public void open() {
        spin(.7);
    }

}
