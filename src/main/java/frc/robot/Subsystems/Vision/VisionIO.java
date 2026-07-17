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

     public default ArrayList<FilteredCameraResults> chooseResults(ArrayList<FilteredCameraResults> filteredResults){
          return filteredResults;
     }

     public default ArrayList<FilteredCameraResults> sendResults(ArrayList<FilteredCameraResults> filteredResults){
          return filteredResults;
     }
 }
