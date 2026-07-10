package frc.robot.Subsystems.Vision;

import java.util.ArrayList;
//Contains all vision detections from a single camera frame
import org.photonvision.targeting.PhotonPipelineResult;
// Represents one detected target (AprilTag, reflective tape, etc.)
import org.photonvision.targeting.PhotonTrackedTarget;
// Calculates robot position on the field using detected AprilTags
import org.photonvision.PhotonPoseEstimator;

public class VisionIOSystem implements VisionIO{

    /*
    uses the FilteredCameraResults class
    FilteredCameraResults objects contains all the information from one camera
    including type(multitag, singletag)(enum), PhotonTrackedTargets, and EstimatedRobotPose 
    */
    
    public void periodic(){
        ArrayList<FilteredCameraResults> FilteredResults = new ArrayList<>();
        //TODO fill with actual results
        ArrayList<PhotonPipelineResult> results = new ArrayList<>();
        //needs to actually get created
        PhotonPoseEstimator photonEstimator;

        //fill FilteredResults with FilteredCameraResultsObjects
        //FilteredResults = filterResults(results, FilteredResults, photonEstimator);

        //remove FilteredCameraResults objects that see less tags than any of the other cameras
        FilteredResults = chooseResults(FilteredResults);
       

        //send the FilteredCameraResults arraylist to the pose esimator
        sendResults(FilteredResults);

        //log if it was single tag or multitag, if the cameras were connected, and the confidence
    }

//     //public static boolean cameraConnected(camera), takes a camera returns true if connected

//     //public static boolean camerasConnected(), returns true if all of the cameras are connected

    public static ArrayList<FilteredCameraResults> filterResults(ArrayList<PhotonPipelineResult> results, ArrayList<FilteredCameraResults> FilteredResults, PhotonPoseEstimator photonEstimator){
        for(int i = 0; i < results.size(); i++){
            PhotonPipelineResult result = results.get(i);
            FilteredCameraResults filteredResult = new FilteredCameraResults(result, photonEstimator);
            FilteredResults.add(filteredResult);
        }
        return FilteredResults;
    }

    public static ArrayList<FilteredCameraResults> chooseResults(ArrayList<FilteredCameraResults> FilteredResults){
        //needs to get logged
        int mostTargets = 0;
        //figures out whats the most amount of targets any camera saw
        for(int i = 0; i < FilteredResults.size(); i++){
            if(FilteredResults.get(i).getNumTargets() > mostTargets){
                mostTargets = FilteredResults.get(i).getNumTargets();
            }
        }
        //none of the cameras saw any targets )=
        if(mostTargets == 0){
            
        }
        else{
            //only keeps results that have the most amount of targets
            for(int i = 0; i < FilteredResults.size(); i++){
                if(FilteredResults.get(i).getNumTargets() != mostTargets){
                    FilteredResults.remove(i);
                    i--;
                }
            }
        }
        return FilteredResults;
    }
    
    //send the arraylist to the pose estimator, and any other relative information
    public void sendResults(ArrayList<FilteredCameraResults> FilteredResults){

    }
}

