package org.firstinspires.ftc.teamcode;
import androidx.annotation.NonNull;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;


@Autonomous(name = "TelemetryTest")
public class AutoTelemetryTestv1 extends LinearOpMode {



    private DcMotor leftFront;
    private DcMotor leftBack;
    private DcMotor rightFront;
    private DcMotor rightBack;
    private IMU imu;

    /**
     * This function is executed when this Op Mode is selected from the Driver Station.
     */
    @Override
    public void runOpMode() {
        imu = hardwareMap.get(IMU.class, "imu");
        leftFront = hardwareMap.get(DcMotor.class, "leftFront");
        leftBack = hardwareMap.get(DcMotor.class, "leftBack");
        rightFront = hardwareMap.get(DcMotor.class, "rightFront");
        rightBack = hardwareMap.get(DcMotor.class, "rightBack");
        // Create a RevHubOrientationOnRobot object for use with an IMU in a REV Robotics Control
        // Hub or Expansion Hub, specifying the hub's orientation on the robot via the direction
        // that the REV Robotics logo is facing and the direction that the USB ports are facing.
        IMU.Parameters imuparameters = new IMU.Parameters(new RevHubOrientationOnRobot(RevHubOrientationOnRobot.LogoFacingDirection.UP, RevHubOrientationOnRobot.UsbFacingDirection.FORWARD));
        imu.initialize(imuparameters);
        imu.resetYaw();
        waitForStart();
        // Reverses motors so they all go in the same direction
        rightFront.setDirection(DcMotor.Direction.REVERSE);
        rightBack.setDirection(DcMotor.Direction.REVERSE);
        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        if (opModeIsActive()) {
            convertToRotations(4);
            rotateRight(90);
            convertToRotations(4);
        }
        /*while (opModeIsActive()) {
            motorData(leftFront);
            telemetry.update();
        }*/
    }

/**
 * @param returnMotor the motor that will be printed to the telemetry
 * takes in a motor and returns all the info about it.
 * will return the motor's name, type, current power, port number, and direction.
 */
    public void motorData(@NonNull DcMotor returnMotor){
        String motorNames[] = {"leftBack","leftFront", "rightBack", "rightFront" };
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
        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
    public void setRunToPosition() {
        leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
    public void setRunWithoutEncoders() {
        leftFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
    public void convertToRotations(double distance) {
        int distanceToRotations = -(int)(((distance * 12) / 12.86319)  * 530);
        driveRotations(distanceToRotations);
    }
    public void driveRotations(int rotations) {
        resetDriveMotors();
        leftFront.setTargetPosition(rotations);
        rightFront.setTargetPosition(rotations);
        leftBack.setTargetPosition(rotations);
        rightBack.setTargetPosition(rotations);
        setRunToPosition();
        while (rightBack.isBusy()) {
            leftFront.setPower(0.5);
            rightFront.setPower(0.5);
            leftBack.setPower(0.5);
            rightBack.setPower(0.5);
        }
        leftFront.setPower(0);
        rightFront.setPower(0);
        leftBack.setPower(0);
        rightBack.setPower(0);
        setRunWithoutEncoders();
        resetDriveMotors();
    }
    private void rotateRight(int degrees) {
        setRunWithoutEncoders();
        imu.resetYaw();
        while (opModeIsActive() && Math.abs(imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES)) < degrees) {
            leftBack.setPower(-0.5);
            leftFront.setPower(-0.5);
            rightBack.setPower(0.5);
            rightFront.setPower(0.5);
            telemetry.addData("Yaw", Math.abs(imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES)));
            telemetry.update();
        }
        leftBack.setPower(0);
        rightBack.setPower(0);
        leftFront.setPower(0);
        rightFront.setPower(0);
    }
}