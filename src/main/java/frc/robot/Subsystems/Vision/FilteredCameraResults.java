package frc.robot.Subsystems.Vision;

import java.util.ArrayList;
import java.util.Optional;

// Calculates robot position on the field using detected AprilTags
import org.photonvision.*;
//Contains all vision detections from a single camera frame
import org.photonvision.targeting.PhotonPipelineResult;
// Represents one detected target (AprilTag, reflective tape, etc.)
import org.photonvision.targeting.PhotonTrackedTarget;

import edu.wpi.first.math.Matrix;
import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.numbers.N3;

public class FilteredCameraResults{
    /*
    numTargets: how many targets are in a result
    photonTrackedTargets: all the information about one target/tag
    EstimatedRobotPose: the pose of the robot
    PhotonPipelineResult: all the information from one reading
    */

    int numTargets;
    double timeStamp;
    Matrix<N3, N1> kResultDev = VecBuilder.fill(0.0, 0.0, 0.0);
    ArrayList<PhotonTrackedTarget> targets = new ArrayList<>();
    Optional<EstimatedRobotPose> visionEst;
    PhotonPipelineResult result;

    //constructor takes in a raw result
    public FilteredCameraResults(PhotonPipelineResult result, PhotonPoseEstimator photonEstimator){
        this.result = result;

        timeStamp = result.getTimestampSeconds();

        //the list of targets from the result gets filled with targets
        for(int i = 0; i< result.getTargets().size(); i++){
                targets.add((result.getTargets()).get(i));
        }        

        numTargets = targets.size();

        kResultDev = VecBuilder.fill(numTargets, timeStamp, numTargets);
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

    public Matrix<N3, N1> getStandardDev(){
        return kResultDev;
    }

    public void setVisionSD(Matrix<N3, N1> kRD){
        kResultDev = kRD;
    }
}