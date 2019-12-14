package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "MecanumTele", group = "e")
public class MecanumTele extends OpMode {

    private DcMotor fl;
    private DcMotor fr;
    private DcMotor bl;
    private DcMotor br;
    private Servo FoundationServo1;
    private Servo FoundationServo2;

    private double flPower, blPower, frPower, brPower;
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
        // triggers are to turn
        // bumpers control servos
        fl.setPower(flPower);
        fr.setPower(frPower);
        bl.setPower(blPower);
        br.setPower(brPower);
        if (gamepad1.left_stick_y >= 0.5) { // forward
            flPower = .5;
            frPower = -.5;
            blPower = .5;
            brPower = -.5;
        }
        if (gamepad1.left_stick_y <= -0.5){ // backward
            flPower = -.5;
            frPower = .5;
            blPower = -.5;
            brPower = .5;
        }
        if(gamepad1.left_stick_y <= 0.5 & gamepad1.left_stick_y >= -0.5){
            flPower = 0;
            frPower = 0;
            blPower = 0;
            brPower = 0;
        }
        if (gamepad1.right_stick_x >= 0.5){ // right
            flPower = .5;
            frPower = -.5;
            blPower = -.5;
            brPower = .5;
        }
        if (gamepad1.right_stick_x <= -0.5){ // left
            flPower = -.5;
            frPower = .5;
            blPower = .5;
            brPower = -.5;
        }
        if(gamepad1.right_stick_x <= 0.5 & gamepad1.right_stick_x >= -0.5){
            flPower = 0;
            frPower = 0;
            blPower = 0;
            brPower = 0;
        }
        double tgtPower = 0.5;
        if (gamepad1.y) {
            // move to 0 degrees.
            FoundationServo1.setPosition(0);
            FoundationServo2.setPosition(0);
        }
        if (gamepad1.x || gamepad1.b) {
            // move to 90 degrees.
            FoundationServo1.setPosition(0.5);
            FoundationServo2.setPosition(0.5);
        }
        if (gamepad1.a) {
            // move to 180 degrees.
            FoundationServo1.setPosition(1);
            FoundationServo2.setPosition(1);
        }

        //telemetry.addData("Servo 1 Position", FoundationServo1.getPosition());
        //telemetry.addData("Servo 2 Position", FoundationServo2.getPosition());
        //telemetry.addData("Target Power", tgtPower);
        //telemetry.addData("Status", "Running");
        //telemetry.update();
    }
}