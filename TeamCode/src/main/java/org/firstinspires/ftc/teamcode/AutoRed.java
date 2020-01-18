package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.teamcode.robot.BNO055;

import java.util.List;

@Autonomous(name = "AutoRedFoundation", group = "A")
public class AutoRed extends LinearOpMode {

    public DcMotor fl;//names the parts
    public DcMotor fr;
    public DcMotor bl;
    public DcMotor br;
    public Servo servoleft;
    public Servo servoright;
    public DcMotor slide;
    public Servo armleft;
    public Servo armright;
    public BNO055IMU imu;
    private BNO055 bno055;

    private static final String TFOD_MODEL_ASSET = "Skystone.tflite";
    private static final String LABEL_FIRST_ELEMENT = "Stone";
    private static final String LABEL_SECOND_ELEMENT = "Skystone";
    private VuforiaLocalizer vuforia;
    private TFObjectDetector tfod;
    private static final String VUFORIA_KEY = "AU3C7xn/////AAABmfJcoDxTPkyRiG+foWZ1ZMODauw4ioLkzt6U76lAxLZ/Llgvsbb4vQHqNn3mB2UHzmX9XJhiwK2DkausDRYx7vhOV8eZ91MLa6gnKWgiZykSrUar6P4SUiyOzGDdhGlr+Er/rI8Oe73HaWdkcVi/6jHG4qWGuNr6jA6PWhZEad46Q6tPRXC2SHngbc1Ur+/T1FYEx6LcXZ0EKjOiqGVdlwOy0Oi7rk9L45Tg8DaAANz5sFtCODlY5hyhGElXTsw5HnXMaxNad20sGDf9ZuVK0j7yCymcUhEVVFnLnIkyVuTwstKZg2KLXpC+uoSajaDbadg67F53WinFzMLfmxOz60BKyYg6HSVOxRp3qzneqIrR";

    @Override
    public void runOpMode() throws InterruptedException {

        fl = hardwareMap.dcMotor.get("fl"); //names motors in phones
        fr = hardwareMap.dcMotor.get("fr");
        bl = hardwareMap.dcMotor.get("bl");
        br = hardwareMap.dcMotor.get("br");
        servoleft = hardwareMap.servo.get("servo1");
        servoright = hardwareMap.servo.get("servo2");
        armleft = hardwareMap.servo.get("grableft");
        armright = hardwareMap.servo.get("grabright");
        slide = hardwareMap.dcMotor.get("slide");
        bno055 = new BNO055(imu);

        //gyro calibration
        while (!isStopRequested() && !bno055.isReady()) {
            sleep(50);
            idle();
        }

//        fl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        fr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        bl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        servoleft.setPosition(.2);
        servoright.setPosition(.6);

        fl.setDirection(DcMotor.Direction.REVERSE);
        bl.setDirection(DcMotor.Direction.REVERSE);

        fl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        fr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        br.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        bl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        initVuforia();
        if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
            initTfod();
        }

        slide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        if (tfod != null) {
            tfod.activate();
        }

        waitForStart();
        bl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        driveDistance(.5, 1000);
        sleep(100);
        strafeDistance(.5, -200);
        servoleft.setPosition(.1);
        servoright.setPosition(.7);
        driveDistance(.5, -1000);
        servoleft.setPosition(.6);
        servoright.setPosition(.2);
        strafeDistance(.5, 2000);

        while (opModeIsActive()) {
            List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
            if (updatedRecognitions != null) {
                int i = 0;
                for (Recognition recognition : updatedRecognitions) {
                    telemetry.addData(String.format("label (%d)", i), recognition.getLabel());
                    telemetry.addData(String.format("  left,top (%d)", i), "%.03f , %.03f",
                            recognition.getLeft(), recognition.getTop());
                    telemetry.addData(String.format("  right,bottom (%d)", i), "%.03f , %.03f",
                            recognition.getRight(), recognition.getBottom());
                }
                telemetry.update();

            }
        }
        if (tfod != null) {
            tfod.deactivate();
            tfod.shutdown();
        }


        //mecanum(0,-.4,0);

    }

    private void initVuforia() {
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();
        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = CameraDirection.BACK;
        vuforia = ClassFactory.getInstance().createVuforia(parameters);
    }

    private void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfodParameters.minimumConfidence = 0.8;
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_FIRST_ELEMENT, LABEL_SECOND_ELEMENT);
    }

    public void driveDistance(double power, int ticks) {
        int initial = bl.getCurrentPosition();
        int lastError = Integer.MAX_VALUE;
        if (ticks > 0) {
            mecanum(0, -0.4, 0);
            int error = Math.abs(bl.getCurrentPosition() - (initial - ticks));
            while (opModeIsActive() && error > 5) {
                idle();
                // error should be strictly decreasing
                if (error > lastError) {
                    break;
                }
                lastError = error;
                error = Math.abs(bl.getCurrentPosition() - (initial - ticks));
            }
        } else {
            mecanum(0, 0.4, 0);
            int error = Math.abs((initial - ticks) - bl.getCurrentPosition());
            while (opModeIsActive() && error > 5) {
                idle();

                if (error > lastError) {
                    break;
                }
                lastError = error;
                error = Math.abs((initial - ticks) - bl.getCurrentPosition());
            }
        }
//        fl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        fr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        bl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        br.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//
//        fl.setTargetPosition(ticks);
//        fr.setTargetPosition(ticks);
//        bl.setTargetPosition(ticks);
//        br.setTargetPosition(ticks);
//
//        fl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        fr.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        bl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        br.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//
//        fr.setPower(power);
//        fl.setPower(power);
//        bl.setPower(power);
//        br.setPower(power);

//        while (opModeIsActive() && fr.isBusy()) {
//            telemetry.addData("encoder-fwd", fr.getCurrentPosition() + "  busy=" + fr.isBusy());
//            telemetry.update();
//            idle();
//        }

        fr.setPower(0);
        fl.setPower(0);
        bl.setPower(0);
        br.setPower(0);
    }

    public void strafeDistance(double power, int ticks) {
        int initial = bl.getCurrentPosition();
        int lastError = Integer.MAX_VALUE;
        if (ticks > 0) {
            mecanum(0.5, 0, 0);
            int error = Math.abs(bl.getCurrentPosition() - (initial + ticks));
            while (opModeIsActive() && error > 5) {
                idle();
                // error should be strictly decreasing
                if (error > lastError) {
                    break;
                }
                lastError = error;
                error = Math.abs(bl.getCurrentPosition() - (initial + ticks));
            }
        } else {
            mecanum(-0.5, 0, 0);
            int error = Math.abs((initial + ticks) - bl.getCurrentPosition());
            while (opModeIsActive() && error > 5) {
                idle();

                if (error > lastError) {
                    break;
                }
                lastError = error;
                error = Math.abs((initial + ticks) - bl.getCurrentPosition());
            }
        }
//        fl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        fr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        bl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        br.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//
//        fl.setTargetPosition(ticks);
//        fr.setTargetPosition(-ticks);
//        bl.setTargetPosition(-ticks);
//        br.setTargetPosition(ticks);
//
//        fl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        fr.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        bl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        br.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//
//        fr.setPower(power);
//        fl.setPower(power);
//        bl.setPower(power);
//        br.setPower(power);

//        while (opModeIsActive() && fr.isBusy()) {
//            telemetry.addData("encoder-fwd", fr.getCurrentPosition() + "  busy=" + fr.isBusy());
//            telemetry.update();
//            idle();
//        }

        fr.setPower(0);
        fl.setPower(0);
        bl.setPower(0);
        br.setPower(0);
    }

    public void turnDistance(double power, int ticks) {
        int initial = bl.getCurrentPosition();
        int lastError = Integer.MAX_VALUE;
        if (ticks > 0) {
            mecanum(0, 0, 0.5);
            int error = Math.abs(bl.getCurrentPosition() - (initial - ticks));
            while (opModeIsActive() && error > 5) {
                idle();
                // error should be strictly decreasing
                if (error > lastError) {
                    break;
                }
                lastError = error;
                error = Math.abs(bl.getCurrentPosition() - (initial - ticks));
            }
        } else {
            mecanum(0, 0, -0.5);
            int error = Math.abs((initial - ticks) - bl.getCurrentPosition());
            while (opModeIsActive() && error > 5) {
                idle();

                if (error > lastError) {
                    break;
                }
                lastError = error;
                error = Math.abs((initial - ticks) - bl.getCurrentPosition());
            }
        }
//        fl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        fr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        bl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        br.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//
//        fl.setTargetPosition(ticks);
//        fr.setTargetPosition(-ticks);
//        bl.setTargetPosition(ticks);
//        br.setTargetPosition(-ticks);
//
//        fl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        fr.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        bl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        br.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//
//        fr.setPower(power);
//        fl.setPower(power);
//        bl.setPower(power);
//        br.setPower(power);
//
//        while (opModeIsActive() && fr.isBusy()) {
//            telemetry.addData("encoder-fwd", fr.getCurrentPosition() + "  busy=" + fr.isBusy());
//            telemetry.update();
//            idle();
//        }
        fr.setPower(0);
        fl.setPower(0);
        bl.setPower(0);
        br.setPower(0);
    }

    public void mecanum(double x, double y, double yawPower) {
        fl.setPower(Range.clip(-y + x + yawPower, -1, 1));
        fr.setPower(Range.clip(y + x + yawPower, -1, 1));
        br.setPower(Range.clip(y - x + yawPower, -1, 1));
        bl.setPower(Range.clip(-y - x + yawPower, -1, 1));

    }
}

