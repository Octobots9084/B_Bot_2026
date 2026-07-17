package frc.robot.Subsystems.Vision;


// Represents a PhotonVision camera
import org.photonvision.PhotonCamera;
//i lowkey forgot why this is here
import java.nio.file.DirectoryStream.Filter;
//holds data types, more specific then list
import java.util.ArrayList;
//holds data types, needed for unread results
import java.util.List;
//Contains all vision detections from a single camera frame
import org.photonvision.targeting.PhotonPipelineResult;
// Represents one detected target (AprilTag, reflective tape, etc.)
import org.photonvision.targeting.PhotonTrackedTarget;
//timer that starts when the match starts
import edu.wpi.first.wpilibj.Timer;
// Calculates robot position on the field using detected AprilTags
import org.photonvision.PhotonPoseEstimator;
//the constants we made
import frc.robot.Constants;



public class VisionIOSystem implements VisionIO{

    /*
    uses the FilteredCameraResults class
    FilteredCameraResults objects contains all the information from one camera
    including type(multitag, singletag)(enum), PhotonTrackedTargets, and EstimatedRobotPose 
    */

    private final PhotonCamera[] cameras;
    private final PhotonPoseEstimator[] photonEstimators;
    public static double lastMultitag = -1;

    //constructor for cameras and pose estimator
    public VisionIOSystem(){
    cameras = new PhotonCamera[] {
            new PhotonCamera("Camera 1"), 
            new PhotonCamera("Camera 2"),
            new PhotonCamera("Camera 3"), 
            new PhotonCamera("Camera 4"), 
            new PhotonCamera("Camera 5")
    };
    //need the real values in constants
    photonEstimators = new PhotonPoseEstimator[] {
            // new PhotonPoseEstimator(Constants.kTagLayout, Constants.robotToCamFrontRight), 
            // new PhotonPoseEstimator(Constants.kTagLayout, Constants.robotToCamFrontLeft),
            // new PhotonPoseEstimator(Constants.kTagLayout, Constants.robotToCamLeft),
            // new PhotonPoseEstimator(Constants.kTagLayout, Constants.robotToCamRight),  
            // new PhotonPoseEstimator(Constants.kTagLayout, Constants.robotToCamBack)
    };

    }

    public void periodic(){
        PhotonPoseEstimator photonEstimator;
        //all the filteredResults from every camera from every unread reading
        ArrayList<FilteredCameraResults> filteredResults = new ArrayList<>();
        //an arraylist of all of the unread results from each camera is created 
        List<PhotonPipelineResult> cameraResults = new ArrayList<>();

        //all the unread results for each camera get added to a list
        for(int h= 0; h < cameras.length; h++){
            List<PhotonPipelineResult> unreadResults = new ArrayList<>();
            unreadResults = cameras[h].getAllUnreadResults();
            for(int i = 0; i < unreadResults.size(); i++){
                cameraResults.add(unreadResults.get(i));
            }
        }

        
        //fill FilteredResults with FilteredCameraResultsObjects
        //FilteredResults = filterResults(cameraResults, FilteredResults, photonEstimator);

        filteredResults = chooseResults(filteredResults);
       

        //send the FilteredCameraResults arraylist to the pose esimator
        sendResults(filteredResults);

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
    public static ArrayList<FilteredCameraResults> filterResults(ArrayList<PhotonPipelineResult> results, ArrayList<FilteredCameraResults> filteredResults, PhotonPoseEstimator photonEstimator){
            for(int i = 0; i < results.size(); i++){
                PhotonPipelineResult result = results.get(i);
                FilteredCameraResults filteredResult = new FilteredCameraResults(result, photonEstimator);
                filteredResults.add(filteredResult);
            }
    
        return filteredResults;
    }

    //keeps all multitag targets and removes single tag targets unless we dont have a multitag target from within .1
    public ArrayList<FilteredCameraResults> chooseResults(ArrayList<FilteredCameraResults> filteredResults){
        for(int i =0; i< filteredResults.size(); i++){
            FilteredCameraResults result = filteredResults.get(i);
            if(result.getNumTargets() == 0){
                filteredResults.remove(i);
                i--;
            }else if(result.getNumTargets() == 1){
                if(Timer.getFPGATimestamp() -0.1 > lastMultitag && result.getTimeStamp() > lastMultitag){
                    //there hasn't been a multitag seen in at least .1 seconds so we can do singletag
                }else{
                    filteredResults.remove(i);
                    i--;
                }
            }else{
                if(result.getTimeStamp() > lastMultitag){
                lastMultitag = result.getTimeStamp();
                }
            }
        }
        return filteredResults;
    }

    //send the arraylist to the pose estimator, and any other relative information
    public ArrayList<FilteredCameraResults> sendResults(ArrayList<FilteredCameraResults> filteredResults){
        return filteredResults;
    }
}

