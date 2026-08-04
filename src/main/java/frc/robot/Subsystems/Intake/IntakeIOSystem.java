package frc.robot.Subsystems.Intake;

import java.lang.reflect.Field;

import org.littletonrobotics.junction.Logger;

import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.configs.TalonFXConfigurator;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.MotionMagicVelocityVoltage;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.MotorAlignmentValue;

import frc.robot.Constants;

public class IntakeIOSystem extends IntakeIO {


    public TalonFX rollerMover;
    public TalonFX rollerSpinner;
    public TalonFX rollerSpinnerF;

    MotionMagicVoltage moverMagic; 
    MotionMagicVelocityVoltage spinnerMagic;

    Follower rollerMoverFollower;
    Follower rollerSpinnerFollower;

    public IntakeIOSystem() {
        //todo get actual device ids that probably arent taken
        rollerMover = new TalonFX(0, Constants.krakenBus);
        rollerSpinner = new TalonFX(1, Constants.krakenBus);
        rollerSpinnerF = new TalonFX(3, Constants.krakenBus);

        rollerMoverFollower = new Follower(0, MotorAlignmentValue.Opposed); //todo this is my guess but idk
        rollerSpinnerFollower = new Follower(1, MotorAlignmentValue.Opposed); //again

        moverMagic = new MotionMagicVoltage(0);
        spinnerMagic = new MotionMagicVelocityVoltage(0);

        config();

        rollerMover.setControl(moverMagic);
        rollerSpinner.setControl(spinnerMagic);

        rollerSpinnerF.setControl(rollerSpinnerFollower);
    }

    //excuse the weird setup - this is to create slots to fill 
    public void config() {
        TalonFXConfiguration config = new TalonFXConfiguration();

        config.MotorOutput.Inverted = InvertedValue.Clockwise_Positive;
        config.CurrentLimits.StatorCurrentLimit = 40;
        config.CurrentLimits.StatorCurrentLimitEnable = true;
        
        rollerMover.getConfigurator().apply(config);


        config = new TalonFXConfiguration(); 

        config.MotorOutput.Inverted = InvertedValue.Clockwise_Positive;
        config.CurrentLimits.StatorCurrentLimit = 40;
        config.CurrentLimits.StatorCurrentLimitEnable = true;
        
        rollerSpinner.getConfigurator().apply(config);

    }


    @Override
    public void spinRollers(double b) {
        spinnerMagic.Velocity = b;
        rollerSpinner.setControl(spinnerMagic);
    }

    
    /**TODO figure out actual positions */
    @Override
    public void moveRollerToPos(Double pos) {
        moverMagic.Position = pos;
        rollerSpinner.setControl(moverMagic);
    }

    public void log() {
        Logger.recordOutput("mover/", rollerMover.getDeviceTemp().getValueAsDouble());
        Logger.recordOutput("mover/", rollerMover.getPosition().getValueAsDouble());
        Logger.recordOutput("mover/", rollerMover.getMotorVoltage().getValueAsDouble());

        Logger.recordOutput("spinner/", rollerSpinner.getDeviceTemp().getValueAsDouble());
        Logger.recordOutput("spinner/", rollerSpinner.getPosition().getValueAsDouble());
        Logger.recordOutput("spinner/", rollerSpinner.getMotorVoltage().getValueAsDouble());

        Logger.recordOutput("spinnerF/", rollerSpinnerF.getDeviceTemp().getValueAsDouble());
        Logger.recordOutput("spinnerF/", rollerSpinnerF.getPosition().getValueAsDouble());
        Logger.recordOutput("spinnerF/", rollerSpinnerF.getMotorVoltage().getValueAsDouble());
    }
}
