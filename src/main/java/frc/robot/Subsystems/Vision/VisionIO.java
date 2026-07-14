package frc.robot.Subsystems.Vision;

import java.util.ArrayList;

import org.photonvision.PhotonCamera;

 public interface VisionIO {
    public default boolean cameraConnected(int cameraNum){
         return false;
    }

    public default boolean camerasConnected(PhotonCamera[] cameras){
         return false;
     }

     public default ArrayList<ArrayList<FilteredCameraResults>> chooseResults(ArrayList<ArrayList<FilteredCameraResults>> FilteredResults){
            return FilteredResults;
     }

     public default void sendResults(ArrayList<ArrayList<FilteredCameraResults>> FilteredResults){
     }
 }
