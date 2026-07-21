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
    ArrayList<PhotonTrackedTarget> targets = new ArrayList<>();
    Optional<EstimatedRobotPose> visionEst;
    PhotonPipelineResult result;

    //constructor takes in a raw result
    public FilteredCameraResults(PhotonPipelineResult result, PhotonPoseEstimator photonEstimator){
        this.result = result;
        timeStamp = result.getTimestampSeconds();

        //the list of targets from the result gets filled with targets that are not to ambiguis
        for(int i = 0; i< result.getTargets().size(); i++){
            if(result.getTargets().get(i).poseAmbiguity<0.2){
                targets.add((result.getTargets()).get(i));
            }
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
}
