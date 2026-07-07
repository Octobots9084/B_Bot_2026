package frc.robot.Subsystems.Intake;

import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.MotionMagicVelocityVoltage;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.MotorAlignmentValue;

import frc.robot.Constants;

public class IntakeIOSystem extends IntakeIO {
    TalonFX rollerMover;
    TalonFX rollerSpinner;
    TalonFX rollerMoverF;
    TalonFX rollerSpinnerF;

    MotionMagicVoltage moverMagic; 
    MotionMagicVelocityVoltage spinnerMagic;

    Follower rollerMoverFollower;
    Follower rollerSpinnerFollower;

    public IntakeIOSystem() {
        //todo get actual devide ids that probably arent taken
        rollerMover = new TalonFX(0, Constants.krakenBus);
        rollerSpinner = new TalonFX(1, Constants.krakenBus);
        rollerMoverF = new TalonFX(2, Constants.krakenBus);
        rollerSpinnerF = new TalonFX(3, Constants.krakenBus);

        rollerMoverFollower = new Follower(0, MotorAlignmentValue.Opposed); //todo this is my guess but idk
        rollerSpinnerFollower = new Follower(1, MotorAlignmentValue.Opposed); //again

        moverMagic = new MotionMagicVoltage(0);
        spinnerMagic = new MotionMagicVelocityVoltage(0);

        //TODO figure out what to config here

        rollerMover.setControl(moverMagic);
        rollerSpinner.setControl(spinnerMagic);

        rollerMoverF.setControl(rollerMoverFollower);
        rollerSpinnerF.setControl(rollerSpinnerFollower);
    }
    @Override
    public void spinRollers(double b) {
        spinnerMagic.Velocity = b;
        rollerSpinner.setControl(spinnerMagic);
    }

    
    /**TODO figure out actual positions */
    @Override
    public void moveRollerToPos(float pos) {
        moverMagic.Position = pos / 5;
        rollerSpinner.setControl(moverMagic);
    }

    
}
