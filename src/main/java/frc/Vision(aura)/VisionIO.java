import java.util.ArrayList;

public interface VisionIO {
    public default boolean CameraConnected(int camera){
        return false;
    }

     public default boolean CamerasConnected(){
        return false;
    }

    public default void sendResults(ArrayList<FilteredResults[]> selectedResults){
    }
}
