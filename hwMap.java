package org.firstinspires.ftc.teamcode;




import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;



public class hwMap {
    public DcMotor LeftFMotor;
    public DcMotor LeftBMotor;
    public DcMotor RightFMotor;
    public DcMotor RightBMotor;
    public DcMotor SlideMotor;
    public Servo ClipServo;
    public ElapsedTime elapsedTime = new ElapsedTime();
    public int ticks = 1440;
    com.qualcomm.robotcore.hardware.HardwareMap hwMap;

    public void init(com.qualcomm.robotcore.hardware.HardwareMap robot) {
        hwMap = robot;

        LeftFMotor = hwMap.dcMotor.get("LF");
        RightFMotor = hwMap.dcMotor.get("RF");
        RightBMotor = hwMap.dcMotor.get("RB");
        LeftBMotor = hwMap.dcMotor.get("LB");
        SlideMotor = hwMap.dcMotor.get("SM");
        ClipServo = hwMap.servo.get("CS");
    }

    public void stop(){
        LeftBMotor.setPower(0);
        RightFMotor.setPower(0);
        LeftFMotor.setPower(0);
        RightBMotor.setPower(0);
        SlideMotor.setPower(0);
    }

    public void wait(double time_s){
        elapsedTime.reset();
        while(elapsedTime.seconds()<time_s){}
    }

    public void ClipOpen(){
        ClipServo.setPosition(0.3);
    }

    public void ClipClose(){
        ClipServo.setPosition(0.8);
    }

    public void SlideUp(double speed, double time_s){
        SlideMotor.setPower(speed);
        wait(time_s);
        SlideMotor.setPower(0);
    }

    public void forward_power(double speed){
        LeftFMotor.setPower(-speed);
        RightFMotor.setPower(speed);
        RightBMotor.setPower(speed);
        LeftBMotor.setPower(-speed);
    }
    public void EncoderReset(){
        LeftFMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RightFMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RightBMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        LeftBMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public void use_power(){
        LeftFMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        RightFMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        RightBMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        LeftBMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void EncoderForward(double Rotations, double speed){
        EncoderReset();

        LeftFMotor.setTargetPosition((int)(-Rotations * ticks));
        RightFMotor.setTargetPosition((int)(Rotations * ticks));
        RightBMotor.setTargetPosition((int)(Rotations * ticks));
        LeftBMotor.setTargetPosition((int)(-Rotations* ticks));
        LeftFMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        RightFMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        RightBMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        LeftBMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        LeftFMotor.setPower(speed);
        RightFMotor.setPower(speed);
        RightBMotor.setPower(speed);
        LeftBMotor.setPower(speed);
        while(RightFMotor.isBusy()&&LeftBMotor.isBusy()){}
    }
    public void EncoderForward_CM(double cm, double speed){
        EncoderReset();

        double wheel_circumference=10* Math.PI;
        double rotation= cm/wheel_circumference;
        LeftFMotor.setTargetPosition((int)(-rotation * ticks));
        RightFMotor.setTargetPosition((int)(rotation * ticks));
        RightBMotor.setTargetPosition((int)(rotation * ticks));
        LeftBMotor.setTargetPosition((int)(-rotation * ticks));
        LeftFMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        RightFMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        RightBMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        LeftBMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        LeftFMotor.setPower(speed);
        RightFMotor.setPower(speed);
        RightBMotor.setPower(speed);
        LeftBMotor.setPower(speed);
        while(RightFMotor.isBusy()&&LeftBMotor.isBusy()){}


    }

    public void EncoderRight(double Rotations, double speed){
        EncoderReset();

        LeftFMotor.setTargetPosition((int)(-Rotations * ticks));
        RightFMotor.setTargetPosition((int)(-Rotations * ticks));
        RightBMotor.setTargetPosition((int)(Rotations * ticks));
        LeftBMotor.setTargetPosition((int)(Rotations* ticks));
        LeftFMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        RightFMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        RightBMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        LeftBMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        LeftFMotor.setPower(speed);
        RightFMotor.setPower(speed);
        RightBMotor.setPower(speed);
        LeftBMotor.setPower(speed);
        while(RightFMotor.isBusy()&&LeftBMotor.isBusy()){}
    }
}

