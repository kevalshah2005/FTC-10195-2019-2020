package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "TeleOp", group = "A")
public class MecanumTeleOp extends OpMode {

    public DcMotor fl;//names the parts
    public DcMotor fr;
    public DcMotor bl;
    public DcMotor br;
    public Servo servoleft;
    public Servo servoright;
//    public DcMotor lift;
//    public Servo grab;

    @Override
    public void init() {

        fl = hardwareMap.dcMotor.get("fl"); //names motors in phones
        fr = hardwareMap.dcMotor.get("fr");
        bl = hardwareMap.dcMotor.get("bl");
        br = hardwareMap.dcMotor.get("br");
        servoleft = hardwareMap.servo.get("servo1");
        servoright = hardwareMap.servo.get("servo2");
//        lift = hardwareMap.dcMotor.get("lift");
//        grab = hardwareMap.servo.get("grab");

        fl.setPower(0);
        fr.setPower(0);
        bl.setPower(0);
        br.setPower(0);
        servoleft.setPosition(.2);
        servoright.setPosition(.6);
        //grab.setPosition
        //lift.setPower(0);

        fl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        br.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        fr.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        bl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        br.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
    @Override
    public void loop() {


        double lefty = -gamepad1.left_stick_y; //add negative because y axis is flipped on gamepad
        double rightx = gamepad1.right_stick_x;
        double leftx = gamepad1.left_stick_x;

        fl.setPower(((lefty + rightx + leftx)*-1)*.5); //sets power (in a joystick its between 1 to -1)
        fr.setPower((lefty - rightx - leftx)*.5);
        bl.setPower((lefty +  rightx - leftx)*.5);
        br.setPower((lefty - rightx + leftx)*.5);

        //sonic
        if (gamepad1.left_trigger == 1) {
            fl.setPower((lefty + rightx + leftx)*-1); //sets power (in a joystick its between 1 to -1)
            fr.setPower(lefty - rightx - leftx);
            bl.setPower(lefty +  rightx - leftx);
            br.setPower(lefty - rightx + leftx);
        }

        //servos
        if(gamepad1.y) {
            // grab foundation
            servoleft.setPosition(.1);
            servoright.setPosition(.7);
        } else if (gamepad1.b) {
            // move to 90 degrees.
            servoleft.setPosition(0.2);
            servoright.setPosition(0.6);
        }

//        if(gamepad1.left_bumper) {
//            lift.setPower(.5);
//        }
//        if(gamepad1.right_bumper) {
//            lift.setPower(-.5);
//        }
//        if(gamepad1.dpad_up) {
//            grab.setPosition(.2);
//        }
//        if(gamepad1.dpad_down) {
//            grab.setPosition(.8);
//        }


        telemetry.addData("Servo 1 Position", servoleft.getPosition());
        telemetry.addData("Servo 2 Position", servoright.getPosition());
        //telemetry.addData("Grab servo", grab.getPosition());
        telemetry.addData("Status", "Running");
        telemetry.addData("Front left", fl.getCurrentPosition());
        telemetry.addData("Front left", fr.getCurrentPosition());
        telemetry.addData("Front left", bl.getCurrentPosition());
        telemetry.addData("Front left", br.getCurrentPosition());
        telemetry.update();
    }
}