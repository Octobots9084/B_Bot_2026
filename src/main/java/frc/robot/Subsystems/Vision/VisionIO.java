package frc.robot.Subsystems.Vision;

import java.util.ArrayList;

 public interface VisionIO {
     public default boolean CameraConnected(int camera){
         return false;
    }

     public default boolean CamerasConnected(){
         return false;
     }

     public default void sendResults(ArrayList<FilteredCameraResults> FilteredResults){
     }
 }
