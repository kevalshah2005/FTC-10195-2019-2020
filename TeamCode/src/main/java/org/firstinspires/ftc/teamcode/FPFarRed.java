//This program seems to work

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name="FPFarRed", group="Autonomous")
//@Disabled
public class FPFarRed extends LinearOpMode {

    //Declare motors
    DcMotor fl; //Front left wheel
    DcMotor fr; //Front right wheel
    DcMotor bl; //Back left wheel
    DcMotor br; //Back right wheel
    DcMotor ExtendSlide;

    //Declare servos
    Servo FoundationServo1;
    Servo FoundationServo2;
    Servo GrabLeft;
    Servo GrabRight;

    Functions movement = new Functions(null, null, null, null, null, null);


    public void runOpMode() {

        fl = hardwareMap.dcMotor.get("fl");
        fr = hardwareMap.dcMotor.get("fr");
        bl = hardwareMap.dcMotor.get("bl");
        br = hardwareMap.dcMotor.get("br");
        FoundationServo1 = hardwareMap.servo.get("servo1");
        FoundationServo2 = hardwareMap.servo.get("servo2");
        ExtendSlide = hardwareMap.dcMotor.get("RotateSlide");
        GrabLeft = hardwareMap.servo.get("grableft");
        GrabRight = hardwareMap.servo.get("grabright");

        //Reverse motors
        fl.setDirection(DcMotor.Direction.REVERSE);
        bl.setDirection(DcMotor.Direction.REVERSE);

        //Run motors using encoders
        fl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        fr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        bl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        br.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //Initializing servos
        FoundationServo1 = hardwareMap.servo.get("servo1");
        FoundationServo2 = hardwareMap.servo.get("servo2");

        movement.resetFunctions(fl, fr, bl, br, FoundationServo1, FoundationServo2);

        //Reset servos
        movement.FoundationRelease();

        //Miscellaneous

        //Wait for driver to press start
        waitForStart();

        //Reset encoders
        fl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        br.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //Telemetry
        movement.Telemetry();

        //Steps go here
        while (opModeIsActive()) {
            movement.DriveForward(0.7, 16);
            movement.TurnLeft(0.7, 1 * Math.PI);
            movement.DriveRight(0.7, 14);
            movement.DriveForward(0.7, 13);
            sleep(500);
            movement.FoundationGrab();
            movement.DriveBackward(0.7, 48);
            movement.TurnRight(0.7, 1.5 * Math.PI);
            sleep(500);
            movement.FoundationRelease();
            movement.DriveLeft(0.7, 48);
            movement.DriveForward(0.7, 24);
            break;
        }

    }
}
