package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@Disabled
@TeleOp(name = "MecanumTeleOpTest", group = "e")
public class MecanumTeleOpTest extends OpMode {

    DcMotor fl;
    DcMotor fr;
    DcMotor bl;
    DcMotor br;
    Servo FoundationServo1;
    Servo FoundationServo2;

    double flPower, frPower, blPower, brPower, FoundationServo1Power, FoundationServo2Power;

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
        //following is my code

        //end of my code
        fl.setPower(flPower);
        fr.setPower(frPower);
        bl.setPower(blPower);
        br.setPower(brPower);

        //Left bumper to turn left, right bumper to turn right
        while(gamepad1.left_bumper = true) {
            fl.setPower(-1);
            fr.setPower(1);
            bl.setPower(-1);
            br.setPower(1);
            break;
        }
        while(gamepad1.right_bumper = true) {
            fl.setPower(1);
            fr.setPower(-1);
            bl.setPower(1);
            br.setPower(-1);
            break;
        }
        // run until the end of the match (driver presses STOP)
        double tgtPower = 0.5;
            while(gamepad1.y) {
                // move to 0 degrees.
                FoundationServo1.setPosition(0);
                FoundationServo2.setPosition(0);
                break;
            }
            while(gamepad1.x || gamepad1.b) {
                // move to 90 degrees.
                FoundationServo1.setPosition(0.5);
                FoundationServo2.setPosition(0.5);
                break;
            }
            while(gamepad1.a) {
                // move to 180 degrees.
                FoundationServo1.setPosition(1);
                FoundationServo2.setPosition(1);
                break;
            }
            telemetry.addData("Servo 1 Position", FoundationServo1.getPosition());
            telemetry.addData("Servo 2 Position", FoundationServo2.getPosition());
            telemetry.addData("Target Power", tgtPower);
            telemetry.addData("Status", "Running");
            telemetry.update();
            }
    }