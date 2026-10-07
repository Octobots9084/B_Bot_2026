package frc.robot.Subsystems.Lights;

import com.ctre.phoenix6.signals.RGBWColor;
import com.ctre.phoenix6.controls.SolidColor;
import com.ctre.phoenix6.controls.RainbowAnimation; // These were in the 2026 comp bot code, so I figured they might be useful.
import com.ctre.phoenix6.controls.StrobeAnimation;

public enum LightAnimations {

    // BUMP YELLOW,
    // HUB GREEN,
    // FERRY BLUE,
    // TRENCH RED,
    // SAFE WHITE,

    //(new SolidColor(0, 65).withColor(new RGBWColor(120, 0, 160))),// Purple
    TRENCH(new SolidColor(0, 65).withColor(new RGBWColor(255, 0, 0))), // Red
    DEFAULT(new SolidColor(0, 65).withColor(new RGBWColor(255, 255, 255))), // White
    HUB(new SolidColor(0, 65).withColor(new RGBWColor(0, 160, 75))),     // Green
    FERRY(new SolidColor(0, 65).withColor(new RGBWColor(0, 0, 255))),    // Blue
    SAFE(new SolidColor(0, 65).withColor(new RGBWColor(0, 255, 255))), // Teal
    BUMP(new SolidColor(0, 65).withColor(new RGBWColor(255, 255,0)));  // Yellow



    private LightAnimations(SolidColor color) {
        this.color = color;
    }
    public SolidColor color;
}
