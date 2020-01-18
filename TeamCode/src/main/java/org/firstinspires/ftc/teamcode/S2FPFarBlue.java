//This program seems to work

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;

import static org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection.BACK;


@Autonomous(name="S2FPFarBlue", group="Autonomous")
//@Disabled
public class S2FPFarBlue extends LinearOpMode {

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

    private static final VuforiaLocalizer.CameraDirection CAMERA_CHOICE = BACK;
    private static final boolean PHONE_IS_PORTRAIT = false;

    /*
     * IMPORTANT: You need to obtain your own license key to use Vuforia. The string below with which
     * 'parameters.vuforiaLicenseKey' is initialized is for illustration only, and will not function.
     * A Vuforia 'Development' license key, can be obtained free of charge from the Vuforia developer
     * web site at https://developer.vuforia.com/license-manager.
     *
     * Vuforia license keys are always 380 characters long, and look as if they contain mostly
     * random data. As an example, here is a example of a fragment of a valid key:
     *      ... yIgIzTqZ4mWjk9wd3cZO9T1axEqzuhxoGlfOOI2dRzKS4T0hQ8kT ...
     * Once you've obtained a license key, copy the string from the Vuforia web site
     * and paste it in to your code on the next line, between the double quotes.
     */
    private static final String VUFORIA_KEY =
            "AW28CYL/////AAABmcy/lmP9/0RvjkXPMqtjC2Bd7fvrqXlCA9ZEGKl7l3njJV4C+T5xtxofjeTvgakTb2irRmv8xVirUF2F91AIIN8ZNHSJfOt4a60bSamCBrorJzniaUBPVGpYkplDIQ6vEU859bpZ1tm1XVtEuWGKr/4v/idQZ/ctMEn8m5Pb6S/qFBolszEHw6MesTAuJoTX9qhcwl6LNQKztlP+UFBwIU+PjOH9N9lLUxO0CTjsu+euNMeS6vTuT6HN5S6iQyxJw+XKyHj4Tvq/by0izZjCvLb3epBbW8qnq0W69Yk/C8qXp2wnh+8SRnkNbrI1HhkPTmq6wkUuW0xuDUFUL7QNNeRxAcMv3k+UjbUSovX71O1I";

    // Since ImageTarget trackables use mm to specifiy their dimensions, we must use mm for all the physical dimension.
    // We will define some constants and conversions here
    private static final float mmPerInch = 25.4f;
    private static final float mmTargetHeight = (6) * mmPerInch;          // the height of the center of the target image above the floor

    // Constant for Stone Target
    private static final float stoneZ = 2.00f * mmPerInch;

    // Constants for the center support targets
    private static final float bridgeZ = 6.42f * mmPerInch;
    private static final float bridgeY = 23 * mmPerInch;
    private static final float bridgeX = 5.18f * mmPerInch;
    private static final float bridgeRotY = 59;                                 // Units are degrees
    private static final float bridgeRotZ = 180;

    // Constants for perimeter targets
    private static final float halfField = 72 * mmPerInch;
    private static final float quadField = 36 * mmPerInch;

    // Class Members
    private OpenGLMatrix lastLocation = null;
    private VuforiaLocalizer vuforia = null;
    private boolean targetVisible = false;
    private float phoneXRotate = 0;
    private float phoneYRotate = 0;
    private float phoneZRotate = 0;

    String skystonePos = "";

    Functions movement = new Functions(null, null, null, null, null, null, null);
    org.firstinspires.ftc.teamcode.Vuforia Vuforia = new Vuforia();

    public void runOpMode() {

        fl = hardwareMap.dcMotor.get("fl");
        fr = hardwareMap.dcMotor.get("fr");
        bl = hardwareMap.dcMotor.get("bl");
        br = hardwareMap.dcMotor.get("br");
        FoundationServo1 = hardwareMap.servo.get("servo1");
        FoundationServo2 = hardwareMap.servo.get("servo2");
        ExtendSlide = hardwareMap.dcMotor.get("slide");
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

        movement.resetFunctions(fl, fr, bl, br, ExtendSlide, FoundationServo1, FoundationServo2);
        Vuforia.resetVuforia();

        //Reset servos
        movement.FoundationRelease();

        final String VUFORIA_KEY =
                "AW28CYL/////AAABmcy/lmP9/0RvjkXPMqtjC2Bd7fvrqXlCA9ZEGKl7l3njJV4C+T5xtxofjeTvgakTb2irRmv8xVirUF2F91AIIN8ZNHSJfOt4a60bSamCBrorJzniaUBPVGpYkplDIQ6vEU859bpZ1tm1XVtEuWGKr/4v/idQZ/ctMEn8m5Pb6S/qFBolszEHw6MesTAuJoTX9qhcwl6LNQKztlP+UFBwIU+PjOH9N9lLUxO0CTjsu+euNMeS6vTuT6HN5S6iQyxJw+XKyHj4Tvq/by0izZjCvLb3epBbW8qnq0W69Yk/C8qXp2wnh+8SRnkNbrI1HhkPTmq6wkUuW0xuDUFUL7QNNeRxAcMv3k+UjbUSovX71O1I";

        final VuforiaLocalizer.CameraDirection CAMERA_CHOICE = BACK;

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = CAMERA_CHOICE;

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
            movement.DriveForward(0.7, 24);
            sleep(1000);
            Vuforia.runOpMode();
            if(Vuforia.skystonePos == "left"){
                movement.DriveLeft(0.7, 10);
            }
            if(Vuforia.skystonePos == "center"){
                movement.DriveBackward(0.7, 10);
            }
            if(Vuforia.skystonePos == "right"){
                movement.DriveRight(0.7, 10);
            }
            break;
        }

    }
}
    