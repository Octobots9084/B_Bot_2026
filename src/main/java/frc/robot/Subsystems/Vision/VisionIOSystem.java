package frc.robot.Subsystems.Vision;

// Represents a PhotonVision camera
import org.photonvision.PhotonCamera;
import java.util.ArrayList;
import java.util.List;
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

    private final PhotonCamera[] cameras;
    private final PhotonPoseEstimator[] photonEstimators;

    //constructor for cameras and pose estimator
    public VisionIOSystem(){
    cameras = new PhotonCamera[] {
            new PhotonCamera("Camera 1"), 
            new PhotonCamera("Camera 2"),
            new PhotonCamera("Camera 3"), 
            new PhotonCamera("Camera 4"), 
            new PhotonCamera("Camera 5")
    };

    photonEstimators = new PhotonPoseEstimator[] {
    //         new PhotonPoseEstimator(Constants.kTagLayout, Constants.robotToCamFrontRight), 
    //         new PhotonPoseEstimator(Constants.kTagLayout, Constants.robotToCamFrontLeft),
    //         new PhotonPoseEstimator(Constants.kTagLayout, Constants.robotToCamLeft),
    //         new PhotonPoseEstimator(Constants.kTagLayout, Constants.robotToCamRight),  
    //         new PhotonPoseEstimator(Constants.kTagLayout, Constants.robotToCamBack)
    };

    }

    public void periodic(){
        PhotonPoseEstimator photonEstimator;
        //all the filteredResults from every camera from every unread reading
        ArrayList<ArrayList<FilteredCameraResults>> FilteredResults = new ArrayList<>();
        //an arraylist of all of the unread results from each camera is created 
        List<List<PhotonPipelineResult>> cameraResults = new ArrayList<>();

        //all the unread results for each camera get added to a list
        for(int i= 0; i < cameras.length; i++){
            cameraResults.add(cameras[i].getAllUnreadResults());
        }

        
        //fill FilteredResults with FilteredCameraResultsObjects
        //FilteredResults = filterResults(cameraResults, FilteredResults, photonEstimator);

        //remove FilteredCameraResults objects that see less tags than any of the other cameras
        FilteredResults = chooseResults(FilteredResults);
       

        //send the FilteredCameraResults arraylist to the pose esimator
        sendResults(FilteredResults);

        //log numTags, if the cameras were connected, and the confidence
    }
        // takes a camera returns true if connected
        public boolean cameraConnected(int cameraNum){
            return cameras[cameraNum].isConnected();
        }

        //returns true if all of the cameras are connected
        public boolean camerasConnected(PhotonCamera[] cameras){
            for(int i = 0; i < 5; i++){
                if(!cameras[i].isConnected()){
                    return false;
                }
            }
            return true;
        }

    //turns results into filtered results objects
    public static ArrayList<ArrayList<FilteredCameraResults>> filterResults(ArrayList<ArrayList<PhotonPipelineResult>> results, ArrayList<ArrayList<FilteredCameraResults>> FilteredResults, PhotonPoseEstimator photonEstimator){
        for(int h = 0; h < results.size(); h++){
            ArrayList<FilteredCameraResults> unreadResults = new ArrayList<>();
            for(int i = 0; i < results.get(h).size(); i++){
                PhotonPipelineResult result = results.get(h).get(i);
                FilteredCameraResults filteredResult = new FilteredCameraResults(result, photonEstimator);
                unreadResults.add(filteredResult);
            }
            FilteredResults.add(unreadResults);
        }
        return FilteredResults;
    }

    public ArrayList<ArrayList<FilteredCameraResults>> chooseResults(ArrayList<ArrayList<FilteredCameraResults>> FilteredResults){
        int numOfResultsPerCamera = FilteredResults.get(0).size();
        //loops through once for each unreadResult
        for(int h = 0; h<numOfResultsPerCamera;h++){
            //needs to get logged
            int mostTargets = 0;
            //figures out whats the most amount of targets any camera saw
            for(int i = 0; i < FilteredResults.size(); i++){
                if(FilteredResults.get(i).get(h).getNumTargets() > mostTargets){
                    mostTargets = FilteredResults.get(i).get(h).getNumTargets();
                }
            }
            //none of the cameras saw any targets )=
            if(mostTargets == 0){
                //TODO put something special here or delete it WILL CAUSE ERRORS OTHERWISE!!!!
            }
            else{
                //only keeps results that have the most amount of targets
                for(int i = 0; i < FilteredResults.size(); i++){
                    if(FilteredResults.get(i).get(h).getNumTargets() != mostTargets){
                        FilteredResults.get(i).remove(h);
                    }
                }
            }
        }
        return FilteredResults;
    }
    
    //send the arraylist to the pose estimator, and any other relative information
    public void sendResults(ArrayList<ArrayList<FilteredCameraResults>> FilteredResults){

    }
}

