package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "OwlBot", group = "e")
public class OwlBot extends OpMode {
    private DcMotor left;
    private DcMotor right;
    private Servo servo1;
    private Servo servo2;
    private double leftPower, rightPower;

    @Override
    public void init() {
        left = hardwareMap.dcMotor.get("left");
        right = hardwareMap.dcMotor.get("right");
        servo1 = hardwareMap.servo.get("servo1");
        servo2 = hardwareMap.servo.get("servo2");
    }

    @Override
    public void loop() {
        left.setPower(-gamepad1.left_stick_y + gamepad1.right_stick_x);
        right.setPower(gamepad1.left_stick_y + gamepad1.right_stick_x);
        if (gamepad1.left_bumper == true) {
            servo1.setPosition(0);
            servo2.setPosition(1);
        } else {
            servo1.setPosition(.55);
            servo2.setPosition(.3);
        }
        left.setPower(leftPower);
        right.setPower(rightPower);
    }
}