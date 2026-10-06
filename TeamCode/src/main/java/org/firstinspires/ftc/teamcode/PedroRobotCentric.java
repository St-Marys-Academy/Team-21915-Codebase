package org.firstinspires.ftc.teamcode;

import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.pedro.Constants;

@TeleOp
public class PedroRobotCentric extends OpMode {

    private Follower follower;
    private DcMotor intake;

    @Override
    public void init() {
        follower = Constants.create(hardwareMap);
        intake = hardwareMap.get(DcMotor.class, "intake");
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
            intake.setPower(1.0);
        }
        else {
            intake.setPower(0);
        }
    }
}