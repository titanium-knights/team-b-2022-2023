package org.firstinspires.ftc.teamcode.utilities;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import java.util.HashMap;
import java.util.Objects;

public class MecanumDrive {

    public MecanumDrive(HardwareMap hmap) {
        fl = hmap.get(DcMotor.class, CONFIG.FRONTLEFT);
        fr = hmap.get(DcMotor.class, CONFIG.FRONTRIGHT);
        bl = hmap.get(DcMotor.class, CONFIG.BACKLEFT);
        br = hmap.get(DcMotor.class, CONFIG.BACKRIGHT);

        fl.setDirection(DcMotorSimple.Direction.REVERSE);
        bl.setDirection(DcMotorSimple.Direction.FORWARD);
        fr.setDirection(DcMotorSimple.Direction.REVERSE);
        br.setDirection(DcMotorSimple.Direction.REVERSE);

        directions.put(fl, new double[]{1, 1});
        directions.put(fr, new double[]{1, -1});
        directions.put(bl, new double[]{1, -1});
        directions.put(br, new double[]{1, 1});
    }

    public static DcMotor fl, fr, bl, br;

    public static HashMap<DcMotor, double[]> directions = new HashMap<>();

    public void move(double x, double y, double turn) {

        // dot of fl and br
        double dot_fl = dot(Objects.requireNonNull(directions.get(fl)), new double[]{x, y}) + turn;
        double dot_fr = (dot(Objects.requireNonNull(directions.get(fr)), new double[]{x, y}) + turn);
        double dot_bl = dot(Objects.requireNonNull(directions.get(fr)), new double[]{x, y}) - turn;
        double dot_br = dot(Objects.requireNonNull(directions.get(fl)), new double[]{x, y}) - turn;

        double max = Math.max(1, Math.max(Math.max(Math.abs(dot_fl), Math.abs(dot_fr)), Math.max(Math.abs(dot_bl), Math.abs(dot_br))));
        fl.setPower(dot_fl / max);
        br.setPower(dot_br / max);

        fr.setPower(dot_fr / max);
        bl.setPower(dot_bl / max);
    }

    // Each double[] will be a direction vector of length two
    public double dot(double[] a, double[] b) {
        return a[0] * b[0] + a[1] * b[1];
    }

    public void rotate(double r) {
        double[] arr = new double[]{fl.getPower() + r, fr.getPower() - r, bl.getPower() + r, br.getPower() - r};
        double max1 = Math.max(Math.abs(arr[0]), Math.abs(arr[1]));
        double max2 = Math.max(Math.abs(arr[2]), Math.abs(arr[3]));
        double max = Math.max(max1, max2);

        for (int i = 0; i < 4; i++) {
            arr[i] /= max;
        }

        fl.setPower(arr[0]);
        fr.setPower(arr[1]);
        bl.setPower(arr[2]*.5);
        br.setPower(arr[3]);
    }
}