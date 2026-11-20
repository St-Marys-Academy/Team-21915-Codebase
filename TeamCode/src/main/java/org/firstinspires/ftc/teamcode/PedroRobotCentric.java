package org.firstinspires.ftc.teamcode;

import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.pedro.Constants;

@TeleOp
public class PedroRobotCentric extends OpMode {

    private Follower follower;
    private DcMotor intake;
    private DcMotor shooter;
    private Servo adjusterLeft;
    private Servo adjusterRight;
    private Servo aim;

    @Override
    public void init() {
        follower = Constants.create(hardwareMap);
        intake = hardwareMap.get(DcMotor.class, "intake");
        shooter = hardwareMap.get(DcMotor.class, "shooter");
        adjusterLeft = hardwareMap.get(Servo.class, "adjusterLeft");
        adjusterRight = hardwareMap.get(Servo.class, "adjusterRight");
        aim = hardwareMap.get(Servo.class, "aim");
        adjusterLeft.setDirection(Servo.Direction.REVERSE);
    }

    @Override
    public void loop() {
        follower.manual(
                -gamepad1.left_stick_y,
                -gamepad1.left_stick_x,
                -gamepad1.right_stick_x
        );
        follower.update();
        if (gamepad1.a) {
            intake.setPower(1);
        }
        else if (gamepad1.b) {
            intake.setPower(-1);
        }
        else {
            intake.setPower(0);
        }
        if (gamepad1.right_bumper) {
            shooter.setPower(1);
        }
        else {
            shooter.setPower(0);
        }
        if (gamepad1.dpad_up) {
            adjusterLeft.setPosition(0.5);
            adjusterRight.setPosition(0.5);
        }
        else if (gamepad1.dpad_down) {
            adjusterLeft.setPosition(0);
            adjusterRight.setPosition(0);
        }
        if (gamepad1.dpad_right) {
            aim.setPosition(1);
        }
        else if (gamepad1.dpad_left){
            aim.setPosition(0);
        }
    }
}