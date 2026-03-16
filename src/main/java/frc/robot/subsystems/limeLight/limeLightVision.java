// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.limeLight;

import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lib.util.LimelightHelpers;
import frc.lib.util.LimelightHelpers.PoseEstimate;
import frc.robot.subsystems.drive.Drive;

public class limeLightVision extends SubsystemBase {
  /** Creates a new limeLightVision. */
  private final double linearStdDevBaseline = 0.2;; // Meters
  private final double angularStdDevBaseline = 0.06; // Radians
  // Multipliers to apply for MegaTag 2 observations
  private final double linearStdDevMegatag2Factor = 0.5; // More stable than full 3D solve
  private final double angularStdDevMegatag2Factor = Double.POSITIVE_INFINITY; // No rotation data available
  private String[] LimeLightName;
  public limeLightVision(String... LimelightName) {
    this.LimeLightName = LimelightName;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    for (String name : LimeLightName) {
      LimelightHelpers.SetRobotOrientation(name, Drive.getInsatnce().getRotation().getDegrees(),0.0 , 0.0, 0.0, 0.0, 0.0);
      PoseEstimate poseEstimate_megaTag_1 = LimelightHelpers.getBotPoseEstimate_wpiBlue(name);
      if (LimelightHelpers.validPoseEstimate(poseEstimate_megaTag_1)) {
            double stdDevFactor = Math.pow(poseEstimate_megaTag_1.avgTagDist, 2.0) / poseEstimate_megaTag_1.tagCount;
            double angularStdDev = angularStdDevBaseline * stdDevFactor;
          Drive.getInsatnce().addVisionMeasurement(poseEstimate_megaTag_1.pose, poseEstimate_megaTag_1.timestampSeconds,
           VecBuilder.fill(Double.POSITIVE_INFINITY,Double.POSITIVE_INFINITY,angularStdDev));
      } 
      PoseEstimate poseEstimate_MegaTag_2 = LimelightHelpers.getBotPoseEstimate_wpiBlue_MegaTag2(name);
      if (LimelightHelpers.validPoseEstimate(poseEstimate_MegaTag_2)) {
        double stdDevFactor = Math.pow(poseEstimate_MegaTag_2.avgTagDist, 2.0) / poseEstimate_MegaTag_2.tagCount;
        double linearStdDev = linearStdDevBaseline * stdDevFactor * linearStdDevMegatag2Factor;
        double angularStdDev = angularStdDevBaseline * stdDevFactor * angularStdDevMegatag2Factor;
        Drive.getInsatnce().addVisionMeasurement(poseEstimate_MegaTag_2.pose, poseEstimate_MegaTag_2.timestampSeconds, VecBuilder.fill(linearStdDev,linearStdDev,angularStdDev));
      }
    }
  }
}
