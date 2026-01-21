// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.Shooter;

import com.ctre.phoenix6.hardware.TalonFX;

/** Add your docs here. */
public class ShooterIOReal {
    private TalonFX m_master = new TalonFX(ShooterConstants.m_masterID, ShooterConstants.m_canbus);
    private TalonFX m_Slvae1 = new TalonFX(ShooterConstants.m_slaveID1, ShooterConstants.m_canbus);
    private TalonFX m_Slvae2 = new TalonFX(ShooterConstants.m_slaveID2, ShooterConstants.m_canbus);
    private TalonFX m_Slvae3 = new TalonFX(ShooterConstants.m_slaveID3, ShooterConstants.m_canbus);
}
