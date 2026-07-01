import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// public class VisionIOSystem extends VisionIO{

    /*
    uses the FilteredCameraResults class
    FilteredCameraResults objects contains all the information from one camera
    including type(multitag, singletag)(enum), PhotonTrackedTargets, and EstimatedRobotPose 
    */
    
    //filteredCameraResults should always be in an optional and be null if the camera has no reading

   // public void periodic(){
        //create an empty array list named filteredResults to hold optional FilteredCameraResults objects
        
        //filterResults(cameraResults)
        //fill the arraylist with filteredCameraResults by converting raw results into
        //filtered results(setting the optional to empty if there is no reading)

        //chooseResults(filteredResults)
        //look at the filteredCameraResults arraylist and decide which ones were going to use and remove the others
        //(always use multitag, use single tag if there is no multitag)

        //sendResults(FilteredResults)
        //send all remaining FilteredCameraResults (the contents of FilteredResults) to pose esimator

        //log if it was single tag or multitag, if the cameras were connected, and the confidence
    //}

//     //public static boolean cameraConnected(), takes a camera returns true if connected

//     //public static boolean camerasConnected(), returns true if all of the cameras are connected

    //public static void filterResults(cameraResults), takes in raw results, fills the arraylist with filteredCameraResults

    //public static void chooseResults(FilteredResults), removes the results were not going to use from the arraylist

    //public static void sendResults(FilteredResults), send the arraylist(filteredResults) to the pose estimator, and any other relative information
//}

