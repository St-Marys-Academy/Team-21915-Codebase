package org.firstinspires.ftc.teamcode.pedro;

import com.pedropathing.algorithm.ForesightConfig;
import com.pedropathing.controllers.Controller;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Matrix;
import com.pedropathing.math.Vector2D;
import com.pedropathing.revhub.drivetrains.MecanumConfig;
import com.pedropathing.revhub.localizers.PinpointConfig;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class Constants {
    public static Follower create(HardwareMap h) {
        // return new Follower(Drivetrain, Localizer, Foresight);
        return null;
    }
    public static MecanumConfig drivetrainConfig = new MecanumConfig(c -> {
        c.frontLeftName.set("leftFront");
        c.frontRightName.set("rightFront");
        c.backLeftName.set("leftBack");
        c.backRightName.set("rightBack");
        c.frontLeftDirection.set(DcMotorSimple.Direction.REVERSE);
        c.frontRightDirection.set(DcMotorSimple.Direction.FORWARD);
        c.backLeftDirection.set(DcMotorSimple.Direction.REVERSE);
        c.backRightDirection.set(DcMotorSimple.Direction.FORWARD);
    });
    public static PinpointConfig localizerConfig = new PinpointConfig(c -> {
        c.name.set("pinpoint");
        c.podType.set(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        c.xPodOffset.set(4.4922157347671625);
        c.yPodOffset.set(-11.275871457077386);
        c.xPodDirection.set(GoBildaPinpointDriver.EncoderDirection.FORWARD);
        c.yPodDirection.set(GoBildaPinpointDriver.EncoderDirection.FORWARD);
        c.globalDistanceUnit.set(DistanceUnit.INCH);
        c.offsetUnits.set(DistanceUnit.INCH);
    });
    public static ForesightConfig foresightConfig = new ForesightConfig(
            c -> {
                Controller primaryTranslationalForward = Controller.proportional(0.13427157746282858);
                Controller secondaryTranslationalForward = Controller.proportional(0.04960976006988785);
                Controller primaryTranslationalLateral = Controller.proportional(0.20821777114886547);
                Controller secondaryTranslationalLateral = Controller.proportional(0.0769309027581929);

                c.forwardTranslational.set(Controller.piecewise(secondaryTranslationalForward).put(2.5, primaryTranslationalForward));
                c.strafeTranslational.set(Controller.piecewise(secondaryTranslationalLateral).put(2.5, primaryTranslationalLateral));

                c.coast.set(Controller.proportionalFeedforward(0.015577604247465172));
                c.brake.set(Controller.proportionalFeedforward(0.013240963610345395));

                c.headingFeedback.set(Controller.proportional(2.969288381998106));
                c.headingBrakeCoefficients.set(Vector2D.cartesian(0.043646253753756074, 0.005680155257016751));

                c.linearBrakeCoefficients.set(Matrix.diag(0.0708785597556916, 0.03230193214798473));
                c.quadraticBrakeCoefficients.set(Matrix.diag(0.0010200287310259358, 0.0019663964564973274));

                c.maxAchievableForwardVelocity.set(63.11793920382657);
                c.maxAchievableStrafeVelocity.set(53.574084892315454);
                c.naturalForwardDeceleration.set(31.684142177595817);
                c.naturalStrafeDeceleration.set(50.17645980168436);
            }
    );
}