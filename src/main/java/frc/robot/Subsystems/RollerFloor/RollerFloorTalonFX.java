package frc.robot.Subsystems.RollerFloor;

import static edu.wpi.first.units.Units.Seconds;

import com.ctre.phoenix6.hardware.TalonFX;

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
    private TalonFX MainMotor = new TalonFX(8);
    private TalonFX FollowerMotor = new TalonFX(9);
    public SmartMotorControllerConfig FloorFollowerSMCConfig = new SmartMotorControllerConfig(RollerFloorSubsystem.getInstance())
    .withClosedLoopController(0.1,0,0)
    .withSimClosedLoopController(0.1,0,0)
    .withControlMode(ControlMode.CLOSED_LOOP)
    .withTelemetry("rollerFloor", TelemetryVerbosity.HIGH)
    .withMotorInverted(false)
    .withGearing(new MechanismGearing(GearBox.fromReductionStages(22,18)))
    .withClosedLoopRampRate(Seconds.of(0.25))
    .withOpenLoopRampRate(Seconds.of(0.25))
    .withIdleMode(MotorMode.BRAKE);
    public SmartMotorController FloorFollowerSmc = new TalonFXWrapper(FollowerMotor, DCMotor.getKrakenX60(1), FloorFollowerSMCConfig);
    public SmartMotorControllerConfig FloorSMCConfig = FloorFollowerSMCConfig.clone()
    .withLooselyCoupledFollowers(FloorFollowerSmc);
    public SmartMotorController FloorMotor = new TalonFXWrapper(MainMotor, DCMotor.getKrakenX60(1), FloorSMCConfig);
}
