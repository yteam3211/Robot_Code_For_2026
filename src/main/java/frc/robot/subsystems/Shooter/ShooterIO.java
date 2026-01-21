// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.Shooter;

import edu.wpi.first.math.geometry.Pose2d;

/** Add your docs here. */
public interface ShooterIO {
    public class ShooterIOInputs {
        public boolean isMasterConnected = false;
        public boolean isSlvae1 = false;
        public boolean isSlvae2 = false;
        public boolean isSlvae3 = false;
        public double velocity = 0;
        public double acceleration = 0;
    }
    public void updateInputs(ShooterIOInputs inputs);
    public void clacShootVelocity(Pose2d robotPose);
    public void setVelocity(double vel);
    public void shoot();
}
