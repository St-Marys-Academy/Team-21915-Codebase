package org.firstinspires.ftc.teamcode;
import com.pedropathing.ivy.Command;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.pedropathing.follower.Follower;
import org.firstinspires.ftc.teamcode.pedro.Constants;
import com.pedropathing.api.PoseFactory;
import com.pedropathing.math.Pose;
import static com.pedropathing.api.Paths.*;
import com.pedropathing.paths.Path;
import com.pedropathing.ivy.Scheduler;
import static com.pedropathing.ivy.Scheduler.schedule;
import static com.pedropathing.ivy.groups.Groups.sequential;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;
@Autonomous
public class PedroAutoTest extends OpMode {
    private Follower follower;
    private final PoseFactory poseFactory = PoseFactory.degrees();
    // Poses
    private final Pose start = poseFactory.of(82.5028, 11.9337, 90);
    private final Pose path1 = poseFactory.of(81.8743, 123.5279, 270);
    private final Pose path1Control1 = poseFactory.of(133.2186, 34.4886, 0);
    private final Pose path1Control2 = poseFactory.of(131.8982, 106.9715, 0);
    // Paths
    private Path path1() {
        return curve(start, path1Control1, path1Control2, path1).linear(start, path1);
    }
    public Command autoRoutine() {
        return sequential(
                follow(follower, path1())
        );
    }
    @Override
    public void init() {
        Scheduler.reset();
        follower = Constants.create(hardwareMap);
        follower.setPose(start);
        follower.update();
    }
    @Override
    public void start() {
        schedule(autoRoutine());
    }
    @Override
    public void loop() {
        follower.update();
        Scheduler.execute();
    }
}