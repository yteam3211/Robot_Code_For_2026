// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.limeLight;

import org.photonvision.estimation.CameraTargetRelation;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.VideoSource;
import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lib.util.LimelightHelpers;
import frc.lib.util.LimelightHelpers.LimelightTarget_Barcode;
import frc.lib.util.LimelightHelpers.PoseEstimate;
import frc.robot.subsystems.drive.Drive;

public class limeLightTemp extends SubsystemBase {
  /** Creates a new limeLightVision. */
  private final double linearStdDevBaseline = 0.2;; // Meters
  private final double angularStdDevBaseline = 0.06; // Radians
  // Multipliers to apply for MegaTag 2 observations
  private final double linearStdDevMegatag2Factor = 0.5; // More stable than full 3D solve
  private final double angularStdDevMegatag2Factor = Double.POSITIVE_INFINITY; // No rotation data available
  private String[] LimeLightName; 
  public limeLightTemp(String... LimelightName) {
    this.LimeLightName = LimelightName;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    for (String name : LimeLightName) {
      LimelightHelpers.SetRobotOrientation(name, Drive.getInsatnce().getRotation().getDegrees(),0.0 , 0.0, 0.0, 0.0, 0.0);
      PoseEstimate poseEstimate_megaTag_1 = LimelightHelpers.getBotPoseEstimate_wpiBlue(name);
      PoseEstimate poseEstimate_MegaTag_2 = LimelightHelpers.getBotPoseEstimate_wpiBlue_MegaTag2(name);
      double stdDevFactor = Double.POSITIVE_INFINITY;
      double linearStdDev = Double.POSITIVE_INFINITY;
      double angularStdDev = Double.POSITIVE_INFINITY;
      if (LimelightHelpers.validPoseEstimate(poseEstimate_MegaTag_2)) {
        stdDevFactor = Math.pow(poseEstimate_MegaTag_2.avgTagDist, 2.0) / poseEstimate_MegaTag_2.tagCount;
        linearStdDev = linearStdDevBaseline * stdDevFactor * linearStdDevMegatag2Factor; 
      }
      if (LimelightHelpers.validPoseEstimate(poseEstimate_megaTag_1)) {
        stdDevFactor = Math.pow(poseEstimate_megaTag_1.avgTagDist, 2.0) / poseEstimate_megaTag_1.tagCount;
        angularStdDev = angularStdDevBaseline * stdDevFactor;
      }
      Drive.getInsatnce().addVisionMeasurement(
        new Pose2d(poseEstimate_MegaTag_2.pose.getX(),poseEstimate_MegaTag_2.pose.getY(),poseEstimate_megaTag_1.pose.getRotation()), 
        poseEstimate_MegaTag_2.timestampSeconds, VecBuilder.fill(linearStdDev,linearStdDev,angularStdDev));

    }
  }
}
