/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
/* FRC Team 7890 SeQuEnCe                                                     */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Solenoid;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.kCANIds;
import frc.robot.Constants.kPneumatics;
import frc.robot.Constants.kFixedSpeeds;

public class Intake extends SubsystemBase {

  VictorSPX objIntakeMotor = new VictorSPX(kCANIds.iIntake);
  Solenoid objSolenoidIntake = new Solenoid(kCANIds.iPCM, kPneumatics.iIntake);

  public Intake() {
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
  
  public void rollIn() {
    objIntakeMotor.set(ControlMode.PercentOutput, -kFixedSpeeds.dIntake); // TODO: May want to change the speed !!!
  }

  public void rollOut() {
    objIntakeMotor.set(ControlMode.PercentOutput, kFixedSpeeds.dIntake); // TODO: May want to change the speed !!!
  }

  public void stop() {
    objIntakeMotor.set(ControlMode.PercentOutput, 0.0);
  }

  public void raise() {
    objSolenoidIntake.set(true);
  }

  public void lower() {
    objSolenoidIntake.set(false);
  }
}
