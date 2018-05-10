package org.firstinspires.ftc.teamcode.Tutorial;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "TeleOp", group = "Test")
public class RobotTest extends LinearOpMode {
    DcMotor left; // Declare object to hold the left motor
    // Declare object to hold the right motor
    DcMotor right;

    @Override
    public void runOpMode() throws InterruptedException {
        left = hardwareMap.get(DcMotor.class, "left"); // Link the object to physical motor
        right = hardwareMap.get(DcMotor.class, "right"); //

        telemetry.addData("Status", "Initialized");
        waitForStart();

        while (opModeIsActive()) {
            left.setPower(-gamepad1.left_stick_y);
            right.setPower(-gamepad1.right_stick_y);

            telemetry.addData("Status", "Running");
        }
    }
}
