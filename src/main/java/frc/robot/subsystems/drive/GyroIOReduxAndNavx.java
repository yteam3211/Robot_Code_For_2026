// Copyright (c) 2025 FRC 6328
// http://github.com/Mechanical-Advantage
//
// Use of this source code is governed by an MIT-style
// license that can be found in the LICENSE file at
// the root directory of this project.

package frc.robot.subsystems.drive;

import com.reduxrobotics.sensors.canandgyro.Canandgyro;
import com.reduxrobotics.sensors.canandgyro.CanandgyroSettings;
import com.studica.frc.AHRS;
import com.studica.frc.AHRS.NavXComType;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.util.Units;
import java.util.Queue;

public class GyroIOReduxAndNavx implements GyroIO {
  private final Canandgyro canandgyro = new Canandgyro(47, "rio");
  private final AHRS navX =
      new AHRS(NavXComType.kMXP_SPI, (int) Math.min(200, Drive.ODOMETRY_FREQUENCY));

  private final Queue<Double> yawTimestampQueue;
  private final Queue<Double> yawPositionQueueCanandgyro;
  private final Queue<Double> yawPositionQueueNavx;

  public GyroIOReduxAndNavx() {
    // Configure the gyro
    CanandgyroSettings settings =
        new CanandgyroSettings()
            .setYawFramePeriod(1.0 / Drive.ODOMETRY_FREQUENCY)
            .setAngularPositionFramePeriod(1.0 / Drive.ODOMETRY_FREQUENCY)
            .setAngularVelocityFramePeriod(1.0 / Drive.ODOMETRY_FREQUENCY);
    canandgyro.setSettings(settings, 0.2, 5);
    canandgyro.setPose(new Rotation3d(), 0.5);
    canandgyro.clearStickyFaults();
    // Register the gyro signals
    yawTimestampQueue = PhoenixOdometryThread.getInstance().makeTimestampQueue();
    yawPositionQueueCanandgyro =
        PhoenixOdometryThread.getInstance().registerSignal(canandgyro::getYaw);
    PhoenixOdometryThread.getInstance().registerSignal(canandgyro::getRoll);
    yawPositionQueueNavx = PhoenixOdometryThread.getInstance().registerSignal(() -> -navX.getYaw());
  }

  @Override
  public void updateInputs(GyroIOInputs inputs) {
    if (canandgyro.isConnected()) {
      inputs.connected = canandgyro.isConnected();
      inputs.yawPosition = Rotation2d.fromRotations(canandgyro.getYaw());
      inputs.yawVelocityRadPerSec = Units.rotationsToRadians(canandgyro.getAngularVelocityYaw());
      inputs.yawPitchRollPosition = canandgyro.getRotation3d();
      inputs.odometryYawTimestamps =
          yawTimestampQueue.stream().mapToDouble((Double value) -> value).toArray();
      inputs.odometryYawPositions =
          yawPositionQueueCanandgyro.stream()
              .map(Rotation2d::fromRotations)
              .toArray(Rotation2d[]::new);
      yawTimestampQueue.clear();
      yawPositionQueueCanandgyro.clear();
      yawPositionQueueNavx.clear();
    } else {
      inputs.connected = navX.isConnected();
      inputs.yawPosition = Rotation2d.fromDegrees(-navX.getYaw());
      inputs.yawVelocityRadPerSec = Units.degreesToRadians(-navX.getRawGyroZ());
      inputs.yawPitchRollPosition = navX.getRotation3d();
      inputs.odometryYawTimestamps =
          yawTimestampQueue.stream().mapToDouble((Double value) -> value).toArray();
      inputs.odometryYawPositions =
          yawPositionQueueNavx.stream()
              .map((Double value) -> Rotation2d.fromDegrees(-value))
              .toArray(Rotation2d[]::new);
      yawTimestampQueue.clear();
      yawPositionQueueCanandgyro.clear();
      yawPositionQueueNavx.clear();
    }
  }
}
