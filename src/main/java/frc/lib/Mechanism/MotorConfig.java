// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.lib.Mechanism;

import com.ctre.phoenix6.signals.GravityTypeValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.ctre.phoenix6.signals.StaticFeedforwardSignValue;

/** Add your docs here. */
public class MotorConfig {
    private final int id;
    private final String CanBusName;
    private final String MotorName;
    private double Kp;
    private double Ki;
    private double Kd;
    private double Kg;
    private double Ks;
    private double Kv;
    private double Ka;
    private NeutralModeValue neutralMode = NeutralModeValue.Brake;
    private double CruiseVelocity;
    private double Acceleration; 
    private double Jerk;
    private double SensorToMechanismRatio;
    private double MotorToSensorRatio;
    private GravityTypeValue GravityType = GravityTypeValue.Arm_Cosine;
    private StaticFeedforwardSignValue StaticFeedforwardSign = StaticFeedforwardSignValue.UseClosedLoopSign;
        public MotorConfig(String MotorName,int id, String canBusName){
            this.id = id;
            this.CanBusName = canBusName;
            this.MotorName = MotorName;
        }
        public MotorConfig WithPid(double kP,double kI,double kD){
            this.Kp = kP;
            this.Ki = kI;
            this.Kd = kD;
            return this;
        }
        public MotorConfig withFeedForward(double kv,double ka,double kg,double ks){
            this.Kv = kv;
            this.Ka = ka;
            this.Kg = kg;
            this.Ks = ks;
            return this;
        }
        public MotorConfig withGravityType(GravityTypeValue gravityTypeValue){
            this.GravityType = gravityTypeValue;
            return this;
        }
        public MotorConfig withStaticFeedforwardSign(StaticFeedforwardSignValue StaticFeedforwardSign){
            this.StaticFeedforwardSign = StaticFeedforwardSign;
            return this;
        }
        public MotorConfig withNeutralMode(NeutralModeValue value){
            this.neutralMode = value;
            return this;
        }
        public MotorConfig withMotionMagic(double CruiseVelocity,double Acceleration,double Jerk){
            this.Acceleration = Acceleration;
            this.CruiseVelocity = CruiseVelocity;
            this.Jerk = Jerk;
            return this;
        }
        public MotorConfig withSensorToMechanismRatio(double ratio){
            this.SensorToMechanismRatio = ratio;
            return this;
        }
        public MotorConfig withMotorToSensorRatio(double ratio){
            this.MotorToSensorRatio = ratio;        
        return this;
    }
    public int getId() {
        return id;
    }

    public String getCanBusName() {
        return CanBusName;
    }

    public String getMotorName() {
        return MotorName;
    }

    public double getKP() {
        return Kp;
    }

    public double getKI() {
        return Ki;
    }

    public double getKD() {
        return Kd;
    }

    public double getKG() {
        return Kg;
    }

    public double getKS() {
        return Ks;
    }

    public double getKV() {
        return Kv;
    }

    public double getKA() {
        return Ka;
    }

    public NeutralModeValue getNeutralMode() {
        return neutralMode;
    }

    public double getCruiseVelocity() {
        return CruiseVelocity;
    }

    public double getAcceleration() {
        return Acceleration;
    }

    public double getJerk() {
        return Jerk;
    }

    public double getSensorToMechanismRatio() {
        return SensorToMechanismRatio;
    }
    public double getMotorToSensorRatio(){
        return MotorToSensorRatio;
    }
    public GravityTypeValue getGravityType() {
        return GravityType;
    }
    public StaticFeedforwardSignValue getStaticFeedforwardSign() {
        return StaticFeedforwardSign;
    }
}
