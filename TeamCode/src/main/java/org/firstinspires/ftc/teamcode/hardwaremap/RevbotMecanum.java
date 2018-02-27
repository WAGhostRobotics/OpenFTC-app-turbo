package org.firstinspires.ftc.teamcode.hardwaremap;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import me.joshlin.a3565lib.RobotMap;
import me.joshlin.a3565lib.component.drivetrain.Mecanum;
import me.joshlin.a3565lib.component.hardware.MultiDcMotor;
import me.joshlin.a3565lib.component.interfaces.Intake;
import me.joshlin.a3565lib.component.interfaces.Lift;
import me.joshlin.a3565lib.component.interfaces.Pivot;
import me.joshlin.a3565lib.component.motor.MotorIntake;
import me.joshlin.a3565lib.component.motor.MotorLift;
import me.joshlin.a3565lib.component.sensor.GhostIMU;
import me.joshlin.a3565lib.component.servo.ServoPivot;

/**
 * Created by 3565 on 2/15/2018.
 */

public class RevbotMecanum extends RobotMap {
    // Declare drive motors
    public DcMotor frontL;
    public DcMotor frontR;
    public DcMotor backL;
    public DcMotor backR;

    // Declare other motors
    public DcMotor lift;
    public DcMotor leftSpinner;
    public DcMotor rightSpinner;

    // Declare servos
    public Servo flipperServo;
    public Servo verticalServo;

    // Declare other sensors
    public BNO055IMU imu;

    // Declare robot components.
    // Holds the glyph flipper.
    public Pivot flipper;
    // Holds the ball knocker arm.
    public Pivot vertical;
    // Holds the REV IMU.
    public GhostIMU ghostIMU;
    // Holds the glyph lift.
    public Lift glyphLift;
    // Holds the intake.
    public Intake intake;

    @Override
    public void init(HardwareMap aHwMap) {
        this.hardwareMap = aHwMap;

        // Assign motors to DcMotor variables
        frontL = hardwareMap.get(DcMotor.class, "frontL");
        frontR = hardwareMap.get(DcMotor.class, "frontR");
        backL = hardwareMap.get(DcMotor.class, "backL");
        backR = hardwareMap.get(DcMotor.class, "backR");

        lift = hardwareMap.get(DcMotor.class, "lift");
        leftSpinner = hardwareMap.get(DcMotor.class, "leftSpinner");
        rightSpinner = hardwareMap.get(DcMotor.class, "rightSpinner");

        // Assign servos to Servo variables
        flipperServo = hardwareMap.get(Servo.class, "flipper");
        verticalServo = hardwareMap.get(Servo.class, "vertical");

        // Assign sensors to sensor variables
        imu = hardwareMap.get(BNO055IMU.class, "imu");

        // Set motors to run with encoders
        setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER, frontL, frontR);
        setMode(DcMotor.RunMode.RUN_USING_ENCODER, backL, backR);

        // Set all motors to brake
        setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE, frontL, frontR, backL, backR);

        // Reverse right motor
        rightSpinner.setDirection(DcMotorSimple.Direction.REVERSE);

        // Initialize the components of the robot.
        drivetrain = new Mecanum(frontL, frontR, backL, backR);
        flipper = new ServoPivot(flipperServo, 0.63, 0.24, 0.04);
        vertical = new ServoPivot(verticalServo, .6, 0);
        ghostIMU = new GhostIMU(imu);
        glyphLift = new MotorLift(lift);
        intake = new MotorIntake(new MultiDcMotor(leftSpinner, rightSpinner));
    }
}