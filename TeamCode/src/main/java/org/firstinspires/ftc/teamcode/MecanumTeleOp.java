package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "MecanumTeleOp", group = "e")
public class MecanumTeleOp extends OpMode {

    DcMotor fl;
    DcMotor fr;
    DcMotor bl;
    DcMotor br;
    Servo FoundationServo1;
    Servo FoundationServo2;

    @Override
    public void init() {
        fl = hardwareMap.dcMotor.get("fl");
        fr = hardwareMap.dcMotor.get("fr");
        bl = hardwareMap.dcMotor.get("bl");
        br = hardwareMap.dcMotor.get("br");
        FoundationServo1 = hardwareMap.servo.get("servo1");
        FoundationServo2 = hardwareMap.servo.get("servo2");
    }

    @Override
    public void loop() {
        // The left joystick to move forward/backward, right to move left/right
        fl.setPower(-gamepad1.left_stick_y + gamepad1.right_stick_x);
        fr.setPower(gamepad1.left_stick_y + gamepad1.right_stick_x);
        bl.setPower(-gamepad1.left_stick_y - gamepad1.right_stick_x);
        br.setPower(gamepad1.left_stick_y - gamepad1.right_stick_x);

        //Left bumper to turn left, right bumper to turn right
        if(gamepad1.left_bumper = true) {
            fl.setPower(-1);
            fr.setPower(1);
            bl.setPower(-1);
            br.setPower(1);
        } else if(gamepad1.right_bumper = true) {
            fl.setPower(1);
            fr.setPower(-1);
            bl.setPower(1);
            br.setPower(-1);
        }
        // run until the end of the match (driver presses STOP)
        double tgtPower = 0.5;
            if(gamepad1.y) {
                // move to 0 degrees.
                FoundationServo1.setPosition(0);
                FoundationServo2.setPosition(0);
            } else if (gamepad1.x || gamepad1.b) {
                // move to 90 degrees.
                FoundationServo1.setPosition(0.5);
                FoundationServo2.setPosition(0.5);
            } else if (gamepad1.a) {
                // move to 180 degrees.
                FoundationServo1.setPosition(1);
                FoundationServo2.setPosition(1);
            }
            telemetry.addData("Servo 1 Position", FoundationServo1.getPosition());
            telemetry.addData("Servo 2 Position", FoundationServo2.getPosition());
            telemetry.addData("Target Power", tgtPower);
            telemetry.addData("Status", "Running");
            telemetry.update();
            }
    }