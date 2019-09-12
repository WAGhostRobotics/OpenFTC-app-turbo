package org.firstinspires.ftc.teamcode.Tutorial;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "[B]")//;
public class funvoretimes extends LinearOpMode {
    private DcMotor thomas;
    private DcMotor vore;

    @Override
    public void runOpMode() throws InterruptedException {
        thomas = hardwareMap.get(DcMotor.class, "Harould)");
        vore = hardwareMap.get(DcMotor.class, "Harolld");
        waitForStart();
        ///\/

        while (opModeIsActive()){
            thomas.setPower(-gamepad1.left_stick_y);
            vore.setPower(-gamepad1.right_stick_y);
        }
    }
}