package frc.robot.Subsystems.Lights;

import com.ctre.phoenix6.signals.RGBWColor;
import com.ctre.phoenix6.controls.SolidColor;
import com.ctre.phoenix6.controls.RainbowAnimation; // These were in the 2026 comp bot code, so I figured they might be useful.
import com.ctre.phoenix6.controls.StrobeAnimation;

public enum LightAnimations {
    DEFAULT(new SolidColor(0, 65).withColor(new RGBWColor(120, 0, 160))),// Purple
    DISABLED(new SolidColor(0, 65).withColor(new RGBWColor(255, 0, 0))), // Red
    IDLE(new SolidColor(0, 65).withColor(new RGBWColor(255, 255, 255))), // White
    HUB(new SolidColor(0, 65).withColor(new RGBWColor(0, 160, 75))),     // Green
    FERRY(new SolidColor(0, 65).withColor(new RGBWColor(0, 0, 255))),    // Blue
    SPINUP(new SolidColor(0, 65).withColor(new RGBWColor(0, 255, 255))), // Teal
    INTAKE(new SolidColor(0, 65).withColor(new RGBWColor(255, 255,0)));  // Yellow
  //OTHER_STATE                                                                                                   // Orange

    private LightAnimations(SolidColor color) {
        this.color = color;
    }
    public SolidColor color;
}
