package org.firstinspires.ftc.teamcode.Tutorial;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name="thesharonthing")
public class Idontreallycarewhatucallit extends LinearOpMode {
    DcMotor left_motor;
    DcMotor right_motor;
    @Override
    public void runOpMode() {
        left_motor= hardwareMap.get(DcMotor.class,"left");
        right_motor= hardwareMap.get(DcMotor.class, "right");

        telemetry.addData("robot", "status");

        waitForStart();
        while(opModeIsActive()) {
            left_motor.setPower(gamepad1.left_stick_y);
            right_motor.setPower(gamepad2.left_stick_y);
        }
    }
}
