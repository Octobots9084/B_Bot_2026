package frc.robot.Subsystems.Vision;

import java.util.ArrayList;
import java.util.Optional;

import org.photonvision.*;
//Contains all vision detections from a single camera frame
import org.photonvision.targeting.PhotonPipelineResult;
// Represents one detected target (AprilTag, reflective tape, etc.)
import org.photonvision.targeting.PhotonTrackedTarget;
// Calculates robot position on the field using detected AprilTags
import org.photonvision.PhotonPoseEstimator;

public class FilteredCameraResults{
    /*
    numTargets: how many targets are in a result
    photonTrackedTargets: all the information about one target/tag
    EstimatedRobotPose: the pose of the robot
    PhotonPipelineResult: all the information from one reading
    */

    int numTargets;
    double timeStamp;
    double visionSD = 0;
    ArrayList<PhotonTrackedTarget> targets = new ArrayList<>();
    Optional<EstimatedRobotPose> visionEst;
    PhotonPipelineResult result;

    //constructor takes in a raw result
    public FilteredCameraResults(PhotonPipelineResult result, PhotonPoseEstimator photonEstimator, double visionSDX, double visionSDY, double visionSDR){
        this.result = result;
        this.visionSD = visionSD;

        timeStamp = result.getTimestampSeconds();

        //the list of targets from the result gets filled with targets
        for(int i = 0; i< result.getTargets().size(); i++){
                targets.add((result.getTargets()).get(i));
        }        

        numTargets = targets.size();

        //calculate pose based on if your single or multitag
        if(numTargets > 1){
            visionEst = photonEstimator.estimateCoprocMultiTagPose(result);
        }

        if(numTargets == 1){
            visionEst = photonEstimator.estimateLowestAmbiguityPose(result);
        }
    }

    public int getNumTargets(){
        return numTargets;
    }

    public double getTimeStamp(){
        return timeStamp;
    }

    public PhotonPipelineResult getResult(){
        return result;
    }

    public void setVisionSDX(double sD){
        visionSD = sD;
    }
}