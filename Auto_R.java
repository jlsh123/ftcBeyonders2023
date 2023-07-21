package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import org.openftc.apriltag.AprilTagDetection;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;

import java.util.ArrayList;

@Autonomous(name = "Auto_R", group = "final")

public class Auto_R extends LinearOpMode {
    OpenCvCamera camera;
    AprilTagDetectionPipeline aprilTagDetectionPipeline;
    hwMap robot = new hwMap();

    static final double FEET_PER_METER = 3.28084;

    double fx = 578.272;
    double fy = 578.272;
    double cx = 402.145;
    double cy = 221.506;
    double forward = 13;
    double backward = 47;
    int location = 0;
    double tagsize = 0.166;

    AprilTagDetection tagOfInterest = null;

    @Override
    public void runOpMode() {
        robot.init(hardwareMap);

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        camera = OpenCvCameraFactory.getInstance().createInternalCamera(OpenCvInternalCamera.CameraDirection.BACK, cameraMonitorViewId);
        aprilTagDetectionPipeline = new AprilTagDetectionPipeline(tagsize, fx, fy, cx, cy);

        camera.setPipeline(aprilTagDetectionPipeline);
        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                camera.startStreaming(1280, 720, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {

            }
        });

        telemetry.setMsTransmissionInterval(50);

        waitForStart();
        robot.SlideMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        robot.elapsedTime.reset();
        boolean tagNotFound = true;
        telemetry.addLine("nothing detected :(");
        telemetry.update();
        while (tagNotFound &&robot.elapsedTime.seconds()<5) {
            ArrayList<AprilTagDetection> currentDetections = aprilTagDetectionPipeline.getLatestDetections();

            for (AprilTagDetection tag : currentDetections) {
                if (tag.id == 1 || tag.id == 2 || tag.id == 3) {
                    tagOfInterest = tag;
                    location = tag.id;
                    tagNotFound = false;
                    break;
                }
            }
            sleep(20);
        }
        //show results
        if (location != 0){
            telemetry.addData("Target found :) , location", location);
        }else{
            telemetry.addLine("nothing detected after 5 seconds :(");
        }
        telemetry.update();
        //auto code here

        //place cone
        robot.ClipClose();
        robot.wait(0.6);
        robot.SlideUp(1, 1.25);
        robot.EncoderRight(-1.1,0.5);
        robot.wait(0.3);
        robot.EncoderForward_CM(forward,0.5);
        robot.wait(0.3);
        robot.ClipOpen();
        robot.wait(0.4);
        //return to starting position
        robot.EncoderForward_CM(-forward,0.5);
        robot.wait(0.3);
        robot.SlideUp(-1, 0.9);
        robot.wait(0.1);
        robot.EncoderRight(1.1,0.5);
        robot.wait(0.3);
        //go forward
        robot.EncoderForward_CM(110,0.5);
        robot.wait(0.3);
        robot.EncoderForward_CM(-backward,0.5);
        robot.wait(0.3);
        //parking
        if (location == 1 || location == 0){
            robot.EncoderRight(-2.2,0.5);
        }else if (location == 3){
            robot.EncoderRight(2.2,0.5);
        }

    }

}
