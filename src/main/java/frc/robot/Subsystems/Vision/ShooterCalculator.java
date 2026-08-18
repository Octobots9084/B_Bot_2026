package frc.robot.Subsystems.Vision;

import edu.wpi.first.math.geometry.Pose2d;

//calculates where the robot needs to turn to and what angle and speed the hood needs
public class ShooterCalculator{
    private double rotation;
    private double hoodAngle;
    private double flywheelSpeed;

    double degreeToTarget;
    double distanceToTarget;
    double degreesClockwiseToTarget;
    double degreesCounterClockwiseToTarget;
    double wantedDegree;
    double currentDegree;
    //log this
    int noSolutionCount =0;


    public ShooterCalculator(double rotation, double hoodAngle, double flywheelSpeed){
        this.rotation = rotation;
        this.hoodAngle = hoodAngle;
        this.flywheelSpeed = flywheelSpeed;
    }

    public double getRotation(){
        return rotation;
    }

    public double getHoodAngle(){
        return hoodAngle;
    }

    public double getflywheelSpeed(){
        return flywheelSpeed;
    }


    //velocity is relative to hub towards hub is potive away is negative left is negative right is positve
    public ShooterCalculator calculateShot(double xDistanceToTarget, double yDistanceToTarget, double robotVelocityY, double robotVelocityX){
        double timeOfFlight = 0;
        double timeOfFlightDifference = 67;
        double timeOfFlightDifferenceDerivative=0;
        double virtualTargetXAdjustment = 0;
        double virtualTargetYAdjustment = 0;                               
        double rotationRadians;
        double shortestDistanceToTarget;
        double previousTimeOfFlight = 0;
        double timeOfFlightLut = 0;
        double projectileSpeed = 0;
        int iterations = 0;

        while(timeOfFlightDifference > 0.01 && iterations <= 25){
            virtualTargetXAdjustment = -1 * timeOfFlight * robotVelocityX;
            virtualTargetYAdjustment = -1* timeOfFlight * robotVelocityY;
            shortestDistanceToTarget = Math.hypot(xDistanceToTarget + virtualTargetXAdjustment, yDistanceToTarget+ virtualTargetYAdjustment);
            //timeOfFlightLut = Lut.getTimeOfFlight(shortestDistanceToTarget);
            //projectileSpeed = Lut.getProjectileSpeed(shortestDistanceToTarget);
            timeOfFlightDifference = timeOfFlight - timeOfFlightLut;
            timeOfFlightDifferenceDerivative = 1 + (((xDistanceToTarget + virtualTargetXAdjustment) *robotVelocityX) + ((yDistanceToTarget + virtualTargetYAdjustment) * robotVelocityY))/
            (shortestDistanceToTarget*projectileSpeed); 
            
            previousTimeOfFlight = timeOfFlight;
            timeOfFlight = previousTimeOfFlight - timeOfFlightDifference/timeOfFlightDifferenceDerivative;

            timeOfFlightDifference = Math.abs(previousTimeOfFlight - timeOfFlightDifference);
            iterations++;
        }
        //log this
        if(iterations > 25){
            noSolutionCount++;
        }

        rotationRadians = Math.atan2(yDistanceToTarget + virtualTargetYAdjustment, xDistanceToTarget + virtualTargetXAdjustment);
        //double hoodAngle = lut.get(shortestDistanceToTarget).getHoodAngle();
        //double flywheelSpeed = lut.get(shortestDistanceToTarget).getflywheelSpeed();
        ShooterCalculator shot = new ShooterCalculator(rotationRadians, hoodAngle, flywheelSpeed);
        return shot;
    }
}