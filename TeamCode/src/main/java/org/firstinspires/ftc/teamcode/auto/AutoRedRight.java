package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.teamcode.RevbotMecanum;
import org.firstinspires.ftc.teamcode.teleop.CVLinearOpMode;

import me.joshlin.a3565lib.component.drivetrain.DriveMath;
import me.joshlin.a3565lib.component.drivetrain.Drivetrain;
import me.joshlin.a3565lib.component.drivetrain.Mecanum;
import me.joshlin.a3565lib.component.interfaces.Pivot;
import me.joshlin.a3565lib.component.sensor.IMU;
import me.joshlin.a3565lib.component.servo.ServoPivot;
import me.joshlin.a3565lib.enums.Alliance;

/**
 * Created by josh on 2/19/18.
 */

@Autonomous(name = "Auto Red Right", group = "Auto")
public class AutoRedRight extends CVLinearOpMode {
    static final int LEFT_POSITION = 0;
    static final int CENTER_POSITION = 1;
    static final int RIGHT_POSITION = 2;
    RelicRecoveryVuMark vuMark;
    RevbotMecanum robot = new RevbotMecanum();
    Drivetrain drivetrain;
    int targetPosition = CENTER_POSITION;
    int targetPosLocation;

    int[] currentCryptoboxPositions;

    boolean aligned = false;

    IMU imuObj;

    double currentAngle;

    Pivot flipper;

    /**
     * Override this method and place your code here.
     * <p>
     * Please do not swallow the InterruptedException, as it is used in cases
     * where the op mode needs to be terminated early.
     *
     * @throws InterruptedException
     */
    @Override
    public void runOpMode() throws InterruptedException {
        initVuforia();
        initDogeCV(Alliance.BLUE);

        robot.init(hardwareMap);
        drivetrain = new Mecanum(robot.frontL, robot.frontR, robot.backL, robot.backR);
        flipper = new ServoPivot(robot.flipper, 0.40, 0.38, 0.35);
        flipper.down();

        initBNO055IMU(robot.imu);
        imuObj = new IMU(imu);
        imuObj.resetAngle();

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        relicTrackables.activate();

        do {
            vuMark = RelicRecoveryVuMark.from(relicTemplate);
            telemetry.addData("VuMark", "%s visible", vuMark);
            telemetry.update();
        } while (opModeIsActive() && vuMark == RelicRecoveryVuMark.UNKNOWN);

        vuforia.close();

        sleep(1000);

        drivetrain.move(DriveMath.inputsToMotors(0, -.5, 0), 2100);
        drivetrain.move(DriveMath.inputsToMotors(-.5, 0, 0), 800);

        currentAngle = imuObj.getAngle();

        while (opModeIsActive() && !inRange(currentAngle, -2, 2)) {
            currentAngle = imuObj.getAngle();

            if (currentAngle < -3) {
                drivetrain.move(DriveMath.inputsToMotors(0, 0, -0.06));
            } else if (currentAngle > 3) {
                drivetrain.move(DriveMath.inputsToMotors(0, 0, 0.06));
            }

            telemetry.addData("Current Heading", currentAngle);
            telemetry.update();
        }

        drivetrain.move(DriveMath.inputsToMotors(0, 0, 0));

        cryptoboxDetector.enable();


        switch (vuMark) {
            case LEFT:
                targetPosition = LEFT_POSITION;
                break;
            case CENTER:
                targetPosition = CENTER_POSITION;
                break;
            case RIGHT:
                targetPosition = RIGHT_POSITION;
                break;
        }

        sleep(1000);

        while (opModeIsActive() && !aligned) {
            currentCryptoboxPositions = cryptoboxDetector.getCryptoBoxPositions();
            targetPosLocation = currentCryptoboxPositions[targetPosition];
            if (inRange(targetPosLocation, (cryptoboxDetector.getFrameSize().width) - 2, (cryptoboxDetector.getFrameSize().width) + 2)) {
                aligned = true;
            } else if (targetPosLocation < (cryptoboxDetector.getFrameSize().width) - 2) {
                drivetrain.move(DriveMath.inputsToMotors(0, -.03, 0));
            } else if (targetPosLocation > (cryptoboxDetector.getFrameSize().width) + 2) {
                drivetrain.move(DriveMath.inputsToMotors(0, .03, 0));
            }

            if (!cryptoboxDetector.isCryptoBoxDetected() || !cryptoboxDetector.isColumnDetected()) {
                drivetrain.move(DriveMath.inputsToMotors(0, 0, 0));
            }

            telemetry.addData("isCryptoBoxDetected", cryptoboxDetector.isCryptoBoxDetected());
            telemetry.addData("isColumnDetected ", cryptoboxDetector.isColumnDetected());

            telemetry.addData("Column Left ", cryptoboxDetector.getCryptoBoxLeftPosition());
            telemetry.addData("Column Center ", cryptoboxDetector.getCryptoBoxCenterPosition());
            telemetry.addData("Column Right ", cryptoboxDetector.getCryptoBoxRightPosition());
            telemetry.update();
        }

        drivetrain.move(DriveMath.inputsToMotors(0, 0, 0));


        sleep(2000);


        cryptoboxDetector.disable();


        currentAngle = imuObj.getAngle();
        telemetry.addData("Status", "Aligned");
        telemetry.addData("Angle", currentAngle);
        telemetry.update();


        while (opModeIsActive() && !inRange(currentAngle, -92, -88)) {
            currentAngle = imuObj.getAngle();
            drivetrain.move(DriveMath.inputsToMotors(0, 0, .10));
            telemetry.addData("Turning", "");
            telemetry.addData("Angle", currentAngle);
            telemetry.update();
        }

        drivetrain.move(DriveMath.inputsToMotors(0, 0, 0), 2000);

        drivetrain.move(DriveMath.inputsToMotors(0, -.3, 0), 3000);

        flipper.up();

        sleep(2000);

        drivetrain.move(DriveMath.inputsToMotors(0, -.2, 0), 500);

        robot.beep();
    }

    private boolean inRange(double number, double lower, double upper) {
        return lower < number && number < upper;
    }
}