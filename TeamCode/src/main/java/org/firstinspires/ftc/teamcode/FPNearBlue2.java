//This program seems to work

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;


@Autonomous(name="FPNearBlue2", group="Autonomous")
//@Disabled
public class FPNearBlue2 extends LinearOpMode {

    //Declare motors
    DcMotor fl; //Front left wheel
    DcMotor fr; //Front right wheel
    DcMotor bl; //Back left wheel
    DcMotor br; //Back right wheel

    //Declare servos
    Servo FoundationServo1;
    Servo FoundationServo2;


    public void runOpMode() {

        //Initializing motors
        fl = hardwareMap.dcMotor.get("fl");
        fr = hardwareMap.dcMotor.get("fr");
        bl = hardwareMap.dcMotor.get("bl");
        br = hardwareMap.dcMotor.get("br");

        //Reverse motors
        fl.setDirection(DcMotor.Direction.REVERSE);
        bl.setDirection(DcMotor.Direction.REVERSE);

        fl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        fr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        bl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        br.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //Initializing servos
        FoundationServo1 = hardwareMap.servo.get("servo1");
        FoundationServo2 = hardwareMap.servo.get("servo2");

        //Miscellaneous

        //Wait for driver to press start
        waitForStart();

        //Reset encoders
        fl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        br.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //Telemetry
        Telemetry();

        //Steps go here
        while(opModeIsActive()){
            DriveForward(0.5, 24);
            sleep(500);
            FoundationGrab();
            sleep(500);
            DriveBackward(0.5, 22);
            sleep(500);
            FoundationRelease();
            sleep(500);
            DriveRight(0.5, 48);
            break;
        }

    }
    //Methods for moving

    public void DriveForward(double power, int distance)
    {
        //Reset encoders
        fl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        br.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //Convert distance to ticks
        int ticks = (int)((1120)/(3*3.14159))*(distance);

        //Set target position
        fl.setTargetPosition(ticks);
        fr.setTargetPosition(ticks);
        bl.setTargetPosition(ticks);
        br.setTargetPosition(ticks);

        //Get current position
        int flPos = fl.getCurrentPosition();
        int frPos = fr.getCurrentPosition();
        int blPos = bl.getCurrentPosition();
        int brPos = br.getCurrentPosition();

        //Set mode to RUN_TO_POSITION
        fl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        fr.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        bl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        br.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        while(flPos < ticks && frPos < ticks && blPos < ticks && brPos < ticks && opModeIsActive()) {
            //Telemetry to show where the wheels are
            telemetry.addData("flPos", flPos);
            telemetry.addData("frPos", frPos);
            telemetry.addData("blPos", blPos);
            telemetry.addData("brPos", brPos);

            //While all encoder counts are less than the amount given
            fl.setPower(power);
            fr.setPower(power);
            bl.setPower(power);
            br.setPower(power);

            //Get current position to update the position values
            flPos = fl.getCurrentPosition();
            frPos = fr.getCurrentPosition();
            blPos = bl.getCurrentPosition();
            brPos = br.getCurrentPosition();
        }

        fl.setPower(0);
        fr.setPower(0);
        bl.setPower(0);
        br.setPower(0);

        fl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        fr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        bl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        br.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void DriveBackward(double power, int distance)
    {
        //Reset encoders
        fl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        br.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //Convert distance to ticks
        int ticks = (int)((1120)/(3*3.14159))*(distance);

        //Set target position
        fl.setTargetPosition(-ticks);
        fr.setTargetPosition(-ticks);
        bl.setTargetPosition(-ticks);
        br.setTargetPosition(-ticks);

        //Get current position
        int flPos = fl.getCurrentPosition();
        int frPos = fr.getCurrentPosition();
        int blPos = bl.getCurrentPosition();
        int brPos = br.getCurrentPosition();

        //Set mode to RUN_TO_POSITION
        fl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        fr.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        bl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        br.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        while(flPos > -ticks && frPos > -ticks && blPos > -ticks && brPos > -ticks && opModeIsActive()) {
            //Telemetry to show where the wheels are
            telemetry.addData("flPos", flPos);
            telemetry.addData("frPos", frPos);
            telemetry.addData("blPos", blPos);
            telemetry.addData("brPos", brPos);

            //While all encoder counts are less than the amount given
            fl.setPower(-power);
            fr.setPower(-power);
            bl.setPower(-power);
            br.setPower(-power);

            //Get current position to update the position values
            flPos = fl.getCurrentPosition();
            frPos = fr.getCurrentPosition();
            blPos = bl.getCurrentPosition();
            brPos = br.getCurrentPosition();
        }

        fl.setPower(0);
        fr.setPower(0);
        bl.setPower(0);
        br.setPower(0);

        fl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        fr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        bl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        br.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void DriveLeft(double power, int distance)
    {
        //Reset encoders
        fl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        br.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //Convert distance to ticks
        int ticks = (int)((1120)/(3*3.14159))*(distance);

        //Set target position
        fl.setTargetPosition(-ticks);
        fr.setTargetPosition(ticks);
        bl.setTargetPosition(ticks);
        br.setTargetPosition(-ticks);

        //Get current position
        int flPos = fl.getCurrentPosition();
        int frPos = fr.getCurrentPosition();
        int blPos = bl.getCurrentPosition();
        int brPos = br.getCurrentPosition();

        //Set mode to RUN_TO_POSITION
        fl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        fr.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        bl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        br.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        while(flPos > -ticks && frPos < ticks && blPos < ticks && brPos > -ticks && opModeIsActive()) {
            //Telemetry to show where the wheels are
            telemetry.addData("flPos", flPos);
            telemetry.addData("frPos", frPos);
            telemetry.addData("blPos", blPos);
            telemetry.addData("brPos", brPos);

            //While all encoder counts are less than the amount given
            fl.setPower(-power);
            fr.setPower(power);
            bl.setPower(power);
            br.setPower(-power);

            //Get current position to update the position values
            flPos = fl.getCurrentPosition();
            frPos = fr.getCurrentPosition();
            blPos = bl.getCurrentPosition();
            brPos = br.getCurrentPosition();
        }

        fl.setPower(0);
        fr.setPower(0);
        bl.setPower(0);
        br.setPower(0);

        fl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        fr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        bl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        br.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void DriveRight(double power, int distance)
    {
        //Reset encoders
        fl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        br.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //Convert distance to ticks
        int ticks = (int)((1120)/(3*3.14159))*(distance);

        //Set target position
        fl.setTargetPosition(ticks);
        fr.setTargetPosition(-ticks);
        bl.setTargetPosition(-ticks);
        br.setTargetPosition(ticks);

        //Get current position
        int flPos = fl.getCurrentPosition();
        int frPos = fr.getCurrentPosition();
        int blPos = bl.getCurrentPosition();
        int brPos = br.getCurrentPosition();

        //Set mode to RUN_TO_POSITION
        fl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        fr.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        bl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        br.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        while(flPos < ticks && frPos > -ticks && blPos > -ticks && brPos < ticks && opModeIsActive()) {
            //Telemetry to show where the wheels are
            telemetry.addData("flPos", flPos);
            telemetry.addData("frPos", frPos);
            telemetry.addData("blPos", blPos);
            telemetry.addData("brPos", brPos);

            //While all encoder counts are less than the amount given
            fl.setPower(power);
            fr.setPower(-power);
            bl.setPower(-power);
            br.setPower(power);

            //Get current position
            flPos = fl.getCurrentPosition();
            frPos = fr.getCurrentPosition();
            blPos = bl.getCurrentPosition();
            brPos = br.getCurrentPosition();
        }

        fl.setPower(0);
        fr.setPower(0);
        bl.setPower(0);
        br.setPower(0);

        fl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        fr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        bl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        br.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void TurnLeft(double power, int distance)
    {
        //Reset encoders
        fl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        br.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //Convert distance to ticks
        int ticks = (int)((1120)/(3*3.14159))*(distance);

        //Set target position
        fl.setTargetPosition(-ticks);
        fr.setTargetPosition(ticks);
        bl.setTargetPosition(-ticks);
        br.setTargetPosition(ticks);

        //Get current position
        int flPos = fl.getCurrentPosition();
        int frPos = fr.getCurrentPosition();
        int blPos = bl.getCurrentPosition();
        int brPos = br.getCurrentPosition();

        //Set mode to RUN_TO_POSITION
        fl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        fr.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        bl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        br.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        while(flPos > -ticks && frPos < ticks && blPos > -ticks && brPos < ticks && opModeIsActive()) {
            //Telemetry to show where the wheels are
            telemetry.addData("flPos", flPos);
            telemetry.addData("frPos", frPos);
            telemetry.addData("blPos", blPos);
            telemetry.addData("brPos", brPos);

            //While all encoder counts are less than the amount given
            fl.setPower(-power);
            fr.setPower(power);
            bl.setPower(-power);
            br.setPower(power);

            //Get current position
            flPos = fl.getCurrentPosition();
            frPos = fr.getCurrentPosition();
            blPos = bl.getCurrentPosition();
            brPos = br.getCurrentPosition();
        }

        fl.setPower(0);
        fr.setPower(0);
        bl.setPower(0);
        br.setPower(0);

        fl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        fr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        bl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        br.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void TurnRight(double power, int distance) {
        //Reset encoders
        fl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        br.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //Convert distance to ticks
        int ticks = (int)((1120)/(3*3.14159))*(distance);

        //Set target position
        fl.setTargetPosition(ticks);
        fr.setTargetPosition(-ticks);
        bl.setTargetPosition(ticks);
        br.setTargetPosition(-ticks);

        //Get current position
        int flPos = fl.getCurrentPosition();
        int frPos = fr.getCurrentPosition();
        int blPos = bl.getCurrentPosition();
        int brPos = br.getCurrentPosition();

        //Set mode to RUN_TO_POSITION
        fl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        fr.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        bl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        br.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        while(flPos < ticks && frPos > -ticks && blPos < ticks && brPos > -ticks && opModeIsActive()) {
            //Telemetry to show where the wheels are
            telemetry.addData("flPos", flPos);
            telemetry.addData("frPos", frPos);
            telemetry.addData("blPos", blPos);
            telemetry.addData("brPos", brPos);

            //While all encoder counts are less than the amount given
            fl.setPower(power);
            fr.setPower(-power);
            bl.setPower(power);
            br.setPower(-power);

            //Get current position
            flPos = fl.getCurrentPosition();
            frPos = fr.getCurrentPosition();
            blPos = bl.getCurrentPosition();
            brPos = br.getCurrentPosition();
        }

        fl.setPower(0);
        fr.setPower(0);
        bl.setPower(0);
        br.setPower(0);

        fl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        fr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        bl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        br.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void FoundationGrab()
    {
        //Grab foundation
        FoundationServo1.setPosition(1); //Works
        FoundationServo2.setPosition(-0.8);
        sleep(3000);
    }

    public void FoundationRelease()
    {
        //Release foundation
        FoundationServo1.setPosition(0.2);
        FoundationServo2.setPosition(0.6);
        sleep(1000);
    }

    public void Telemetry()
    {
        telemetry.addData("Status", "Initialized");
        telemetry.update();
    }
}