package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.robotcore.external.JavaUtil;

@TeleOp(name = "CompetitionCodev4Laser")
public class CompetitionCodev1 extends LinearOpMode {

    private DcMotor front_left;
    private DcMotor back_left;
    private DcMotor front_right;
    private DcMotor back_right;

    boolean slow_mode2;

    /**
     * Basic mecanum driving
     */
    private void mecanum_drive() {
        float y;
        double x;
        float rx;
        double denominator;

        // Y and X are combined to make a fraction that creates the power values for the motors. RX is for rotating the robot and only applies to the right stick
        y = gamepad2.left_stick_y;
        // Factor to counteract imperfect strafing
        x = -(gamepad2.left_stick_x * 1.1);
        rx = -gamepad2.right_stick_x;
        // Denominator is the largest motor power (absolute value) or 1.
        // This ensures all powers maintain the same ratio, but only if one is outside of the range [-1, 1].
        denominator = JavaUtil.maxOfList(JavaUtil.createListWith(JavaUtil.sumOfList(JavaUtil.createListWith(Math.abs(y), Math.abs(x), Math.abs(rx))), 1.3));
        // This is the final fraction that calculates the motor powers.
        if (gamepad2.right_trigger >= 0.45) {
            front_left.setPower(((y + x + rx) / denominator) / 2);
            back_left.setPower((((y - x) + rx) / denominator) / 2);
            front_right.setPower((((y - x) - rx) / denominator) / 2);
            back_right.setPower((((y + x) - rx) / denominator) / 2);
        } else {
            front_left.setPower((y + x + rx) / denominator);
            back_left.setPower(((y - x) + rx) / denominator);
            front_right.setPower(((y - x) - rx) / denominator);
            back_right.setPower(((y + x) - rx) / denominator);
        }
    }

    /**
     * This function is executed when this Op Mode is selected from the Driver Station.
     */
    @Override
    public void runOpMode() {
        front_left = hardwareMap.get(DcMotor.class, "front_leftAsDcMotor");
        back_left = hardwareMap.get(DcMotor.class, "back_leftAsDcMotor");
        front_right = hardwareMap.get(DcMotor.class, "front_rightAsDcMotor");
        back_right = hardwareMap.get(DcMotor.class, "back_rightAsDcMotor");

        waitForStart();
        // Reverses motors so they all go in the same direction
        front_right.setDirection(DcMotor.Direction.REVERSE);
        back_right.setDirection(DcMotor.Direction.REVERSE);
        while (opModeIsActive()) {
            mecanum_drive();
        }
    }
}
