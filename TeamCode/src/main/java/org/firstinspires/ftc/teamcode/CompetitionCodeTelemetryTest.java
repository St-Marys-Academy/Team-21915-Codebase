package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;


@TeleOp(name = "CompetitonCodeTelemetryTest")
public class CompetitionCodeTelemetryTest extends LinearOpMode {



    private DcMotor frontLeft;
    private DcMotor backLeft;
    private DcMotor frontRight;
    private DcMotor backRight;
    /**
     * Basic mecanum driving
     */
    private void mecanum_drive() {

        // Y and X are combined to make a fraction that creates the power values for the motors. RX is for rotating the robot and only applies to the right stick
        double y = gamepad2.left_stick_y;
        // Factor to counteract imperfect strafing
        double x = -(gamepad2.left_stick_x * 1.1);
        double rx = -gamepad2.right_stick_x;
        // Denominator is the largest motor power (absolute value) or 1.
        // This ensures all powers maintain the same ratio, but only if one is outside of the range [-1, 1].
        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1.3);
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
        frontRight.setDirection(DcMotor.Direction.REVERSE);
        backRight.setDirection(DcMotor.Direction.REVERSE);
        while (opModeIsActive()) {
            mecanum_drive();
            moterData(frontLeft);
            moterData(frontRight);
            moterData(backLeft);
            moterData(backRight);
            telemetry.update();
        }
    }

/**
 * takes in a moter and returns all the info about it.
 * will return the motor's name, type, current power, port number, and direction.
  */
public void moterData(DcMotor returnMotor){
        telemetry.addData( "Motor",returnMotor.getDeviceName()); // doesn't return as intended
        telemetry.addData( "Type",returnMotor.getMotorType());
        telemetry.addData( "Current Power",returnMotor.getPower());
        telemetry.addData( "Port Number",returnMotor.getPortNumber());
        telemetry.addData( "Direction",returnMotor.getDirection());
    }


}
