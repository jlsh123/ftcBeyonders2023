package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;





@TeleOp(name = "teleOp", group = "final")
public class teleOp extends LinearOpMode {

    double y_coor;
    double x_coor;
    double rotate;
    double LFSpeed;
    double LBSpeed;
    double RFSpeed;
    double RBSpeed;
    int servoState = 0;
    boolean notPressed = true;
    hwMap robot = new hwMap();

    @Override
    public void runOpMode() {
        robot.init(hardwareMap);
        telemetry.addData("calibrating", "initialized");
        telemetry.update();

        waitForStart();// runs after pressing start

        //brake motors when power is 0
        robot.SlideMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        while (opModeIsActive()) {
            x_coor = gamepad1.right_stick_x;
            y_coor = -gamepad1.right_stick_y;
            rotate = gamepad1.left_stick_x;
            if (gamepad1.left_bumper){
                //shifting in a lower speed
                LFSpeed = (-y_coor - x_coor - rotate) * 0.3;
                LBSpeed = (-y_coor + x_coor - rotate) * 0.3;
                RFSpeed = (y_coor - x_coor - rotate) * 0.3;
                RBSpeed = (y_coor + x_coor - rotate) * 0.3;
            }else {
                //shifting in normal speed
                LFSpeed = Range.clip((-y_coor - x_coor - rotate),-1.0,1.0);
                LBSpeed = Range.clip((-y_coor + x_coor - rotate),-1.0,1.0);
                RFSpeed = Range.clip((y_coor - x_coor - rotate),-1.0,1.0);
                RBSpeed = Range.clip((y_coor + x_coor - rotate),-1.0,1.0);
            }
            robot.LeftFMotor.setPower(LFSpeed);
            robot.LeftBMotor.setPower(LBSpeed);
            robot.RightFMotor.setPower(RFSpeed);
            robot.RightBMotor.setPower(RBSpeed);

            //slide
            if (!gamepad2.left_bumper){
                robot.SlideMotor.setPower(-gamepad2.right_stick_y);
            }else{
                robot.SlideMotor.setPower(-gamepad2.right_stick_y * 0.3);
            }

            //servo open&close
            if(servoState==0&&gamepad2.right_bumper&&notPressed){
                robot.ClipClose();
                servoState = 1;
                notPressed = false;
            }else if(servoState==1&&gamepad2.right_bumper&&notPressed){
                robot.ClipOpen();
                servoState = 0;
                notPressed = false;
            }else if(!gamepad2.right_bumper){
                notPressed = true;
            }

            telemetry.addData("power (LF)",robot.LeftFMotor.getPower());
            telemetry.addData("power (RF)",robot.RightFMotor.getPower());
            telemetry.addData("power (RB)",robot.RightBMotor.getPower());
            telemetry.addData("power (LB)",robot.LeftBMotor.getPower());

            telemetry.addData("pos (LF)",robot.LeftFMotor.getCurrentPosition());
            telemetry.addData("pos (RF)",robot.RightFMotor.getCurrentPosition());
            telemetry.addData("pos (RB)",robot.RightBMotor.getCurrentPosition());
            telemetry.addData("pos (LB)",robot.LeftBMotor.getCurrentPosition());

            telemetry.addData("Robot Status","updated");
            telemetry.update();
        }
    }
}
