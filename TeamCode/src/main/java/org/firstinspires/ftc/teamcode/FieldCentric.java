package org.firstinspires.ftc.teamcode;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.pedro.Constants;
import com.pedropathing.drivetrain.DrivePowers;
import com.pedropathing.follower.ManualDrive;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class FieldCentric extends OpMode {

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
        shooter.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    @Override
    public void loop() {
        DrivePowers powers = ManualDrive.fieldCentric(
                -gamepad1.left_stick_y,
                -gamepad1.left_stick_x,
                -gamepad1.right_stick_x,
                follower.pose().heading()
        );
        follower.manual(powers);
        follower.update();

        // relocalise button
        if (gamepad1.start) {
            Pose cornerPose = new Pose(0, 0, Math.toRadians(0));
            // On the fly Pose creation, we dont recommend this for Autonomous. Only accepts radians for heading
            follower.setPose(cornerPose); // overrides our pose
        }

        Pose robotPose = follower.pose(); // returns a Pose object
        telemetry.addData("Robot X", robotPose.x());
        telemetry.addData("Robot Y", robotPose.y());
        telemetry.addData("Robot Heading", Math.toDegrees(robotPose.heading())); // Math.toDegrees() is a built-in java method
        telemetry.update();
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
        if (gamepad1.dpad_up) {
            adjusterLeft.setPosition(0.5);
            adjusterRight.setPosition(0.5);
        }
        else if (gamepad1.dpad_down) {
            adjusterLeft.setPosition(0.0);
            adjusterRight.setPosition(0.0);
        }
        if (gamepad1.dpad_right) {
            aim.setPosition(0.5);
        }
        else if (gamepad1.dpad_left){
            aim.setPosition(0);
        }
    }
}