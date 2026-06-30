// // Resizable list that can grow and shrink dynamically
// import java.util.ArrayList;
// // General List interface (often used instead of ArrayList directly)
// import java.util.List;
// // Represents a value that may or may not exist (avoids null)
// import java.util.Optional;

// public class VisionIOSystem extends VisionIO{

//     //new data type
//     //FilteredResults[] is an array containing all the information from one camera
//     //including type(multitag, singletag), pose, and confidence

//     public void periodic(){
//         //get the raw results from the cameras
        
//         //filterResults()
//         //convert the raw results to filteredResults[]

//         //chooseResults()
//         //look at the filteredResults and decide which ones were going to use
//         //(always use multitag, use single tag if there is no multitag)

//         //sendResults()
//         //send all chosen FilteredResults to pose esimator

//         //log relevant information
//     }

//     //public static boolean cameraConnected(), takes a camera returns true if connected

//     //public static boolean camerasConnected(), returns true if all of the cameras are connected

//     //public static FilteredResults[] filterResults(cameraResults), takes in raw results, return organized sorted information

//     //public static list of FilteredResults were going to use chooseResults(list of all FlteredResults), takes in sorted results, returns the ones were going to use

//     //public static void sendResults(list of filtered results were going to use), takes in a list of the filtered results were going to use, send it to pose calculator
// }

