/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
/* FRC Team 7890 SeQuEnCe                                                     */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.kCANIds;

public class Feeder extends SubsystemBase {

  CANSparkMax objFeedNeo550 = new CANSparkMax(kCANIds.iFeed, MotorType.kBrushless);
  VictorSPX objIndexerVictor = new VictorSPX(kCANIds.iIndexer);

  public Feeder() {
  }
  
  public void setFeedSpeed(double dSpeed) {
    objFeedNeo550.set(dSpeed);
  }

  public void setIndexerSpeed(double dSpeed) {
    objIndexerVictor.set(ControlMode.PercentOutput, dSpeed);
  }

  public void stop() {
    objFeedNeo550.stopMotor();
    objIndexerVictor.set(ControlMode.PercentOutput, 0.0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
