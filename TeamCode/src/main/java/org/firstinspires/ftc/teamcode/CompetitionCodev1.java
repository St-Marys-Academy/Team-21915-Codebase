package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.robotcore.external.JavaUtil;

@TeleOp(name = "CompetitionCodev1")
public class CompetitionCodev1 extends LinearOpMode {

    private DcMotor frontLeft;
    private DcMotor backLeft;
    private DcMotor frontRight;
    private DcMotor backRight;
    /**
     * Basic mecanum driving
     */
    private void mecanum_drive() {
        double y;
        double x;
        double rx;
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
        frontLeft.setPower((y + x + rx) / denominator);
        backLeft.setPower(((y - x) + rx) / denominator);
        frontRight.setPower(((y - x) - rx) / denominator);
        backRight.setPower(((y + x) - rx) / denominator);
    }
    /**
     * This function is executed when this Op Mode is selected from the Driver Station.
     */
    @Override
    public void runOpMode() {
        frontLeft = hardwareMap.get(DcMotor.class, "front_left");
        backLeft = hardwareMap.get(DcMotor.class, "back_left");
        frontRight = hardwareMap.get(DcMotor.class, "front_right");
        backRight = hardwareMap.get(DcMotor.class, "back_right");

        waitForStart();
        // Reverses motors so they all go in the same direction
        //frontRight.setDirection(DcMotor.Direction.REVERSE);
        backRight.setDirection(DcMotor.Direction.REVERSE);
        while (opModeIsActive()) {
            mecanum_drive();
        }
    }
}
