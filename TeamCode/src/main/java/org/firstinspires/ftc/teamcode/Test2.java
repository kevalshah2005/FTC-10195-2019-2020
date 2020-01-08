package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;


@Autonomous(name="Test2", group="Autonomous")
//@Disabled
public class Test2 extends LinearOpMode {

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

        fl.setDirection(DcMotor.Direction.REVERSE);
        bl.setDirection(DcMotor.Direction.REVERSE);

        fl.setPower(1);
        fr.setPower(1);
        bl.setPower(1);
        br.setPower(1);

        sleep(3000);

        fl.setPower(0);
        fr.setPower(0);
        bl.setPower(0);
        br.setPower(0);
    }
}