/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
/* FRC Team 7890 SeQuEnCe                                                     */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import java.util.function.DoubleSupplier;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Traveler extends SubsystemBase {
  private final CANSparkMax TravelerMotor; 
  /**
   * Creates a new Traveler.
   */
  public Traveler() {
    TravelerMotor = new CANSparkMax(Constants.kCANIds.iTraveler, MotorType.kBrushless);
  }

  public void move(DoubleSupplier dSpeed) {
    TravelerMotor.set(dSpeed.getAsDouble());
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
