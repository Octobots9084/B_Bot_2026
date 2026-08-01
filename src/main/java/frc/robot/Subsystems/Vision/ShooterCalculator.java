package frc.robot.Subsystems.Vision;

import edu.wpi.first.math.geometry.Pose2d;

//calculates where the robot needs to turn to and what angle and speed the hood needs
public class ShooterCalculator{
    double degreeToTarget;
    double distanceToTarget;
    double degreesClockwiseToTarget;
    double degreesCounterClockwiseToTarget;
    double wantedDegree;
    double currentDegree;
    
    /*
    use yToTarget and xToTarget to create a triangle than uses the pythagorian theorom to find the shortest distance
    */
    public double calculateDistance(double yToTarget, double xToTarget){
        distanceToTarget = Math.sqrt((yToTarget * yToTarget) + (xToTarget*xToTarget));
        return distanceToTarget;
    }

    /*
    uses yToTarget to create the leg of a triangle then uses sin to find the angle the robot
    needs to be at to face the target
    */
    public double calculateDegree(double yToTarget, double distanceToTarget){

        degreeToTarget = Math.sin(yToTarget/distanceToTarget); 
        return degreeToTarget;
    }

    /*
    starts with the x and y distances and uses them to get the shortest distance then uses that to get the wanted
    degree than uses that to figure out how much to rotate the robot to get to a certain degree, and whether to go
    clockwise or counterclockwise(based on which ones faster), clockwise is positive, counter clockwise is negative
    */
    public double getRotation(double yToTarget, double xToTarget){
        distanceToTarget = calculateDistance(yToTarget, xToTarget);
        wantedDegree = calculateDegree(yToTarget, distanceToTarget);
        //currentDegree = Pose2d.direction();

        if(currentDegree < wantedDegree){
            degreesClockwiseToTarget = wantedDegree - currentDegree;
            //go back to zero then go around until you hit wanted
            degreesCounterClockwiseToTarget = (-1 *currentDegree) + (wantedDegree - 360);

        //current degree is greater than wantedDegree
        }else{
            //go foward to zero than keep going until you hit wanted
            degreesClockwiseToTarget = (360 - currentDegree) + wantedDegree;
            degreesCounterClockwiseToTarget = wantedDegree -currentDegree;
        }

        //finds out which direction takes less rotation
        if(degreesClockwiseToTarget < Math.abs(degreesCounterClockwiseToTarget)){
            return degreesClockwiseToTarget;
        }else{
            return degreesCounterClockwiseToTarget;
        }
    }
}