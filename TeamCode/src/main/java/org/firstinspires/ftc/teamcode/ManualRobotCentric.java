package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp
public class ManualRobotCentric extends OpMode {

    private DcMotor leftFront;
    private DcMotor leftBack;
    private DcMotor rightFront;
    private DcMotor rightBack;
    private DcMotor intake;
    private DcMotor shooter;
    /**
     * Basic mecanum driving
     */
    private void mecanum_drive() {

        // Y and X are combined to make a fraction that creates the power values for the motors. RX is for rotating the robot and only applies to the right stick
        double y = -gamepad1.left_stick_y;
        // Factor to counteract imperfect strafing
        double x = gamepad1.left_stick_x * 1.1;
        double rx = gamepad1.right_stick_x;
        // Denominator is the largest motor power (absolute value) or 1.
        // This ensures all powers maintain the same ratio, but only if one is outside of the range [-1, 1].
        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1.3);
        // This is the final fraction that calculates the motor powers.
        leftFront.setPower((y + x + rx) / denominator);
        leftBack.setPower(((y - x) + rx) / denominator);
        rightFront.setPower(((y - x) - rx) / denominator);
        rightBack.setPower(((y + x) - rx) / denominator);
    }
    @Override
    public void init() {
        leftFront = hardwareMap.get(DcMotor.class, "leftFront");
        leftBack = hardwareMap.get(DcMotor.class, "leftBack");
        rightFront = hardwareMap.get(DcMotor.class, "rightFront");
        rightBack = hardwareMap.get(DcMotor.class, "rightBack");
        intake = hardwareMap.get(DcMotor.class, "intake");
        shooter = hardwareMap.get(DcMotor.class, "shooter");
        leftFront.setDirection(DcMotor.Direction.REVERSE);
        leftBack.setDirection(DcMotor.Direction.REVERSE);
    }
    @Override
    public void loop() {
        mecanum_drive();
        if (gamepad1.a) {
            intake.setPower(1.0);
        }
        else if (gamepad1.b) {
            intake.setPower(-1.0);
        }
        else {
            intake.setPower(0);
        }
        if (gamepad1.right_bumper) {
            shooter.setPower(1.0);
        }
        else {
            shooter.setPower(0);
        }
    }
}
