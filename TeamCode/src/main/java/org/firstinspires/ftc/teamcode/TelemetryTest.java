package org.firstinspires.ftc.teamcode;
import androidx.annotation.NonNull;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IMU;



@Autonomous(name = "TelemetryTest")
public class TelemetryTest extends LinearOpMode {



    private DcMotor frontLeft;
    private DcMotor backLeft;
    private DcMotor frontRight;
    private DcMotor backRight;
    private IMU imu;

    /**
     * This function is executed when this Op Mode is selected from the Driver Station.
     */
    @Override
    public void runOpMode() {
        frontLeft = hardwareMap.get(DcMotor.class, "front_left");
        backLeft = hardwareMap.get(DcMotor.class, "back_left");
        frontRight = hardwareMap.get(DcMotor.class, "front_right");
        backRight = hardwareMap.get(DcMotor.class, "back_right");
        // Create a RevHubOrientationOnRobot object for use with an IMU in a REV Robotics Control
        // Hub or Expansion Hub, specifying the hub's orientation on the robot via the direction
        // that the REV Robotics logo is facing and the direction that the USB ports are facing.
        IMU.Parameters imuparameters = new IMU.Parameters(new RevHubOrientationOnRobot(RevHubOrientationOnRobot.LogoFacingDirection.UP, RevHubOrientationOnRobot.UsbFacingDirection.BACKWARD));
        imu.initialize(imuparameters);
        imu.resetYaw();
        waitForStart();
        // Reverses motors so they all go in the same direction
        frontRight.setDirection(DcMotor.Direction.REVERSE);
        backRight.setDirection(DcMotor.Direction.REVERSE);
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        if (opModeIsActive()) {
            convertToRotations(4);
        }
        /*while (opModeIsActive()) {
            motorData(frontLeft);
            telemetry.update();
        }*/
    }

/**
 * @param returnMotor the motor that will be printed to the telemetry
 * takes in a motor and returns all the info about it.
 * will return the motor's name, type, current power, port number, and direction.
 */
    public void motorData(@NonNull DcMotor returnMotor){
        String motorNames[] = {"backLeft","frontLeft", "backRight", "frontRight" };
        telemetry.addData("Motor Name", motorNames[returnMotor.getPortNumber()] );

        // telemetry.addData( "Motor",returnMotor.getDeviceName().toString()); // doesn't return as intended
        telemetry.addData( "Type",returnMotor.getMotorType().toString());
        telemetry.addData( "Current Power",returnMotor.getPower());
        telemetry.addData( "Port Number",returnMotor.getPortNumber());
        telemetry.addData( "Direction",returnMotor.getDirection());
        if(returnMotor.getDirection() == DcMotorSimple.Direction.REVERSE){
            telemetry.addData("Position",  returnMotor.getCurrentPosition());
        }
        else {
            telemetry.addData("Position",  -returnMotor.getCurrentPosition());
        }
        telemetry.addData("Target Position", returnMotor.getTargetPosition());
    }
    public void resetDriveMotors() {
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public void setRunMode() {
        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
    public void convertToRotations(double distance) {
        int distanceToRotations = -(int)(((distance * 12) / 12.86319)  * 530);
        driveRotations(distanceToRotations);
    }

    public void driveRotations(int rotations) {
        resetDriveMotors();
        frontLeft.setTargetPosition(rotations);
        frontRight.setTargetPosition(rotations);
        backLeft.setTargetPosition(rotations);
        backRight.setTargetPosition(rotations);
        setRunMode();
        frontLeft.setPower(0.5);
        frontRight.setPower(0.5);
        backLeft.setPower(0.5);
        backRight.setPower(0.5);
        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);
    }
}