package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.robotcore.external.JavaUtil;

@TeleOp(name = "CompetitionCodev4Laser")
public class CompetitionCodev4Laser extends LinearOpMode {

    private DcMotor front_left;
    private DcMotor back_left;
    private DcMotor front_right;
    private DcMotor back_right;
    private AnalogInput laserAnalogInput;
    private CRServo elevator1;
    private CRServo elevator2;
    private DcMotor Shoot1;
    private Servo flap;
    private DcMotor intake;

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
        if (slow_mode2) {
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
     * Gets and prints laser telemetry information to console
     */
    private void LaserTelemetry() {
        telemetry.addData("Laser Voltage", laserAnalogInput.getVoltage());
        telemetry.addData("Laser Distance (mm)", (laserAnalogInput.getVoltage() / laserAnalogInput.getMaxVoltage()) * 1000);
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
        laserAnalogInput = hardwareMap.get(AnalogInput.class, "laserAnalogInputAsAnalogInput");
        elevator1 = hardwareMap.get(CRServo.class, "elevator1AsCRServo");
        elevator2 = hardwareMap.get(CRServo.class, "elevator2AsCRServo");
        Shoot1 = hardwareMap.get(DcMotor.class, "Shoot1AsDcMotor");
        flap = hardwareMap.get(Servo.class, "flapAsServo");
        intake = hardwareMap.get(DcMotor.class, "intakeAsDcMotor");

        waitForStart();
        // Reverses motors so they all go in the same direction
        elevator1.setDirection(CRServo.Direction.REVERSE);
        elevator2.setDirection(CRServo.Direction.REVERSE);
        Shoot1.setDirection(DcMotor.Direction.REVERSE);
        front_right.setDirection(DcMotor.Direction.REVERSE);
        back_right.setDirection(DcMotor.Direction.REVERSE);
        flap.setDirection(Servo.Direction.REVERSE);
        Shoot1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        while (opModeIsActive()) {
            slow_mode();
            mecanum_drive();
            flywheel();
            elevator();
            intake2();
            LaserTelemetry();
            flappyer_flap();
            reset_shoot_encoder();
            telemetry.addData("key", ((DcMotorEx) Shoot1).getVelocity() * 28);
            telemetry.update();
        }
    }

    /**
     * Controls intake power
     */
    private void intake2() {
        if (gamepad1.a) {
            intake.setPower(1);
        } else if (gamepad1.b) {
            intake.setPower(-1);
        } else {
            intake.setPower(0);
        }
    }

    /**
     * Controls flywheel power
     */
    private void flywheel() {
        if (gamepad1.dpad_down) {
            Shoot1.setPower(-0.5);
        } else if (gamepad1.dpad_up) {
            ((DcMotorEx) Shoot1).setVelocity((4800.0 / 60.0) * 28.0);
        } else if (gamepad1.dpad_left) {
            ((DcMotorEx) Shoot1).setVelocity((4000.0 / 60.0) * 28.0);
        } else {
            Shoot1.setPower(0);
        }
    }

    /**
     * Sets slow mode for mecanum drive
     */
    private void slow_mode() {
        if (gamepad2.right_trigger >= 0.45) {
            slow_mode2 = true;
        } else {
            slow_mode2 = false;
        }
    }

    /**
     * Resets flywheel encoder
     */
    private void reset_shoot_encoder() {
        if (gamepad1.options) {
            Shoot1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        }
    }

    /**
     * Controls elevator power
     */
    private void elevator() {
        if (gamepad1.x) {
            elevator1.setPower(1);
            elevator2.setPower(1);
        } else if (gamepad1.y) {
            elevator1.setPower(-1);
            elevator2.setPower(-1);
        } else {
            elevator1.setPower(0);
            elevator2.setPower(0);
        }
    }

    /**
     * Controls flap position
     */
    private void flappyer_flap() {
        if (gamepad1.right_bumper) {
            flap.setPosition(0.5);
        } else {
            flap.setPosition(1.85);
        }
    }
}
