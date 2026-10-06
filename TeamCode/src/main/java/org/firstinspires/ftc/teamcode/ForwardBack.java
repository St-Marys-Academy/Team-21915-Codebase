package org.firstinspires.ftc.teamcode;

import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp
public class ForwardBack extends OpMode{
    private DcMotor leftFront;
    private DcMotor leftBack;
    private DcMotor rightFront;
    private DcMotor rightBack;
    @Override
    public void init() {
        leftFront = hardwareMap.get(DcMotor.class, "leftFront");
        leftBack = hardwareMap.get(DcMotor.class, "leftBack");
        rightFront = hardwareMap.get(DcMotor.class, "rightFront");
        rightBack = hardwareMap.get(DcMotor.class, "rightBack");
        leftFront.setDirection(DcMotor.Direction.REVERSE);
        leftBack.setDirection(DcMotor.Direction.REVERSE);
    }
    @Override
    public void loop() {
        if (gamepad1.a) {
            leftFront.setPower(1.0);
            leftBack.setPower(1.0);
            rightFront.setPower(1.0);
            rightBack.setPower(1.0);
        }
        else if (gamepad1.b) {
            leftFront.setPower(-1.0);
            leftBack.setPower(-1.0);
            rightFront.setPower(-1.0);
            rightBack.setPower(-1.0);
        }
        else {
            leftFront.setPower(0);
            leftBack.setPower(0);
            rightFront.setPower(0);
            rightBack.setPower(0);
        }
    }
}
