package frc.robot.Subsystems.Vision;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.interpolation.InterpolatingDoubleTreeMap;
import edu.wpi.first.math.interpolation.InterpolatingTreeMap;
import edu.wpi.first.math.interpolation.InverseInterpolator;

//calculates where the robot needs to turn to and what angle and speed the hood needs
public class ShooterCalculator{
    
    private double rotation;
    private double hoodAngle;
    private double flywheelSpeed;
    //log this
    public boolean noSolutionFound;

    public ShooterCalculator(double rotation, double hoodAngle, double flywheelSpeed){
        this.rotation = rotation;
        this.hoodAngle = hoodAngle;
        this.flywheelSpeed = flywheelSpeed;
    }

    //lut!!

    //takes in distance, returns time of flight
    public static final InterpolatingDoubleTreeMap timeOfFlightMapFerry =
        new InterpolatingDoubleTreeMap();

    //takes in time of flight, returns flywheel speed
    public static final InterpolatingDoubleTreeMap flywheelSpeedMapFerry =
        new InterpolatingDoubleTreeMap();

    //takes in time of flight, returns hood angle 
    public static final InterpolatingTreeMap<Double, Rotation2d> hoodAngleMapFerry =
        new InterpolatingTreeMap<>(InverseInterpolator.forDouble(), Rotation2d::interpolate);

    //takes in distance returns time of flight
    public static final InterpolatingDoubleTreeMap timeOfFlightMapHub =
        new InterpolatingDoubleTreeMap();

    //takes in time of flight, returns flywheel speed
    public static final InterpolatingDoubleTreeMap flywheelSpeedMapHub =
        new InterpolatingDoubleTreeMap();

    //takes in distance returns time of flight
    public static final InterpolatingTreeMap<Double, Rotation2d> hoodAngleMapHub =
        new InterpolatingTreeMap<>(InverseInterpolator.forDouble(), Rotation2d::interpolate);


    //actual lut values
     static {
        //takes in distance, returns time of flight
        timeOfFlightMapFerry.put(1.47,0.772);

        //takes in time of flight, returns flywheel speed
        flywheelSpeedMapFerry.put(1.47,14.0);

        //takes in time of flight, returns hood angle 
        hoodAngleMapFerry.put(1.47,new Rotation2d(58.0*Math.PI/180.0));

        //takes tof, returns hood angle
        hoodAngleMapHub.put(1.442809759,new Rotation2d(75.0*Math.PI/180.0));

        //takes in time of flight, returns flywheel speed
        flywheelSpeedMapHub.put(1.442809759,26.0);

        //takes in distance, returns time of flight
        timeOfFlightMapHub.put(1.442809759,1.103235);
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
            //projectileSpeed = Lut.getShooterSpeed(timeOfFlight);
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
            //think about what to do if true
            noSolutionFound = true;
        }else{
            noSolutionFound = false;
        }

        rotationRadians = Math.atan2(yDistanceToTarget + virtualTargetYAdjustment, xDistanceToTarget + virtualTargetXAdjustment);
        //double hoodAngle = lut.get(shortestDistanceToTarget).getHoodAngle();
        //double flywheelSpeed = lut.get(shortestDistanceToTarget).getflywheelSpeed();
        ShooterCalculator shot = new ShooterCalculator(rotationRadians, hoodAngle, flywheelSpeed);
        return shot;
    }
}