package org.firstinspires.ftc.teamcode.Tutorial;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Autonomous(name = "BreilasAuto")

public class BreilasAuto extends LinearOpMode {
    DcMotor left; DcMotor right;
    @Override
    public void runOpMode() throws InterruptedException {
        left = hardwareMap.get(DcMotor.class, "left");
        right = hardwareMap.get(DcMotor.class, "right");
        right.setDirection(DcMotorSimple.Direction.REVERSE);
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        waitForStart();
        left.setPower(1);
        right.setPower(1);
        sleep(1000);
        left.setPower(0);
        right.setPower(0);
        sleep(1000);
        left.setPower(-1);
        right.setPower(1);
        sleep(2000);
        left.setPower(0);
        right.setPower(0);

    }
}
