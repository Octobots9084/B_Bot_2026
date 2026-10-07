package frc.robot.Subsystems.RollerFloor;

import static edu.wpi.first.units.Units.KilogramSquareMeters;
import static edu.wpi.first.units.Units.Seconds;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.system.plant.DCMotor;
import frc.robot.Subsystems.RollerFloor.RollerFloorSubsystem;
import yams.gearing.MechanismGearing;
import yams.gearing.GearBox;
import yams.motorcontrollers.SmartMotorController;
import yams.motorcontrollers.SmartMotorControllerConfig;
import yams.motorcontrollers.SmartMotorControllerConfig.ControlMode;
import yams.motorcontrollers.SmartMotorControllerConfig.MotorMode;
import yams.motorcontrollers.SmartMotorControllerConfig.TelemetryVerbosity;
import yams.motorcontrollers.remote.TalonFXWrapper;

public class RollerFloorTalonFX {    
    public SmartMotorController FloorFollowerSmc;
    public SmartMotorControllerConfig FloorSMCConfig;
    public SmartMotorController FloorMotor;
    public SmartMotorControllerConfig FloorFollowerSmcConfig;

    public RollerFloorTalonFX(RollerFloorSubsystem subsystem){
    FloorFollowerSmcConfig = new SmartMotorControllerConfig()
    .withClosedLoopController(0.25,0,0)
    .withSimClosedLoopController(0.25,0,0)
    .withFeedforward(new SimpleMotorFeedforward(0, 0.05, 0))
    .withSimFeedforward(new SimpleMotorFeedforward(0, 0.05, 0))
    .withMomentOfInertia(KilogramSquareMeters.of(0.000056))
    .withControlMode(ControlMode.CLOSED_LOOP)
    .withTelemetry("rollerFloor", TelemetryVerbosity.HIGH)
    .withMotorInverted(false)
    .withGearing(new MechanismGearing(GearBox.fromReductionStages(1,3)))
    .withClosedLoopRampRate(Seconds.of(0.125))
    .withOpenLoopRampRate(Seconds.of(0.125))
    .withIdleMode(MotorMode.BRAKE)  
    .withSubsystem(subsystem);

    }


    public void init() {
        FloorFollowerSmc = new TalonFXWrapper(new TalonFX(10), DCMotor.getKrakenX44(1), FloorFollowerSmcConfig);
        FloorSMCConfig = FloorFollowerSmcConfig.clone()
        .withLooselyCoupledFollowers(FloorFollowerSmc);
        FloorMotor = new TalonFXWrapper(new TalonFX(11), DCMotor.getKrakenX44(1), FloorSMCConfig);
    }
}
