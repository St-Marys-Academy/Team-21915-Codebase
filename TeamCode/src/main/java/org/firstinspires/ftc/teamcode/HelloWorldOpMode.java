package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

/*
 * This is a minimal sample OpMode. It does not control any hardware - it simply
 * prints a message to the Driver Station's telemetry console (and to logcat) so
 * you can confirm that your code is being deployed and run correctly.
 *
 * This OpMode will appear on the Driver Station's OpMode list under the name
 * "Hello World" in the "Examples" group.
 */
@TeleOp(name = "Hello World", group = "Examples")
@SuppressWarnings("unused")
public class HelloWorldOpMode extends LinearOpMode {

    private final ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() {
        // Print to logcat as well, viewable via "adb logcat" or Android Studio's Logcat tab.
        android.util.Log.i("HelloWorldOpMode", "Initialized");

        // Show a message on the Driver Station before the match starts.
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Wait for the driver to press START on the Driver Station.
        waitForStart();
        runtime.reset();

        // Run until the driver presses STOP.
        while (opModeIsActive()) {
            telemetry.addData("Status", "Running");
            telemetry.addData("Hello", "World! Elapsed time: " + runtime);
            telemetry.update();

            android.util.Log.i("HelloWorldOpMode", "Hello, World! Elapsed time: " + runtime);
        }
    }
}
