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


    //velocity is relative to hub towards hub is potive away is negative left is negative right is positve
    public void calculateShot(double xDistanceToTarget, double yDistanceToTarget, double robotVelocityY, double robotVelocityX){
        double timeOfFlight = -1;
        double timeOfFlightDifference = 1;
        double virtualTargetXAdjustment;
        double virtualTargetYAdjustment ;                               
        double rotationRadians;
        double shortestDistanceToTarget;
        //the shot will be occilating from over shooting to under shooting because of the way time of flight changes, this conteracts that

        //start with a time of flight 
        //

        while(timeOfFlightDifference > 0.01){
            //we keep track of was time of flight last time, unless this is the first time the loop is running
            double oldTimeOfFlight;
            //what we thought the time of flight should be
            if(timeOfFlight != -1){
                oldTimeOfFlight = timeOfFlight;
            }else{
                oldTimeOfFlight = ;
            }
            virtualTargetXAdjustment = -1 * oldTimeOfFlight * robotVelocityX;
            virtualTargetYAdjustment = -1* oldTimeOfFlight * robotVelocityY;
            shortestDistanceToTarget = Math.hypot(xDistanceToTarget + virtualTargetXAdjustment, yDistanceToTarget+ virtualTargetYAdjustment);
            //acutal time of flight acording to lut
            //timeOfFlight = Lut.getTimeOfFlight(shortestDistanceToTarget);
            //timeOfFlightDifference = timeOFFLight - oldTimeOfFlight

            //get the old time of flight 
           //generate a virtual target and get its time of flight
           //subtract the time of flights and do some calculaus 
           //once the time of flights stop changing end the loop
           //check the latest time of flight in the lut
           //get the values            
          

            timeOfFlightDifference = Math.abs(oldTimeOfFlight - timeOfFlight);
        }

        rotationRadians = Math.atan2(yDistanceToTarget + virtualTargetYAdjustment, xDistanceToTarget + virtualTargetXAdjustment);

        //Rotation = rotationRradians
        //hoodAngle = lut.get(shortestDistanceToTarget).getHoodAngle();
        //flywheelSpeed = lut.get(shortestDistanceToTarget).getflywheelSpeed();
    }







    
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