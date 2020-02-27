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
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.ControlType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.kCANIds;
import frc.robot.Constants.kSpeedMults;
import frc.robot.Constants.kLauncherPID;

public class Launcher extends SubsystemBase {

  CANSparkMax objNeo9 = new CANSparkMax(kCANIds.iLauncherOne, MotorType.kBrushless);
  CANSparkMax objNeo16 = new CANSparkMax(kCANIds.iLauncherTwo, MotorType.kBrushless);

  CANEncoder objEncoder9 = new CANEncoder(objNeo9);
  CANEncoder objEncoder16 = new CANEncoder(objNeo16);

  CANPIDController objPIDController9 = objNeo9.getPIDController();
  CANPIDController objPIDController16 = objNeo16.getPIDController();

  public Launcher() {

    objPIDController9.setP(kLauncherPID.dKp);
    objPIDController9.setI(kLauncherPID.dKi);
    objPIDController9.setD(kLauncherPID.dKd);
    objPIDController9.setIZone(kLauncherPID.dKiz);
    objPIDController9.setFF(kLauncherPID.dKff);
    objPIDController9.setOutputRange(kLauncherPID.dMinOutput, kLauncherPID.dMaxOutput);

    objPIDController16.setP(kLauncherPID.dKp);
    objPIDController16.setI(kLauncherPID.dKi);
    objPIDController16.setD(kLauncherPID.dKd);
    objPIDController16.setIZone(kLauncherPID.dKiz);
    objPIDController16.setFF(kLauncherPID.dKff);
    objPIDController16.setOutputRange(kLauncherPID.dMinOutput, kLauncherPID.dMaxOutput);

  }
  
  public void setSpeed(double dSpeed, boolean bPIDActive) {
    // if bPIDActive then input dSpeed is in RPM, otherwise in range -1 to +1
    if (bPIDActive) {
      objPIDController9.setReference(-kSpeedMults.dLauncherMult * dSpeed, ControlType.kVelocity);
      objPIDController16.setReference(dSpeed, ControlType.kVelocity);
    }
    else {
      objNeo9.set(-kSpeedMults.dLauncherMult * dSpeed);
      objNeo16.set(dSpeed);
    }
  }

  public double getVelocity9() {
    return objEncoder9.getVelocity();
  }

  public void stop() {
    objNeo9.stopMotor();
    objNeo16.stopMotor();
  }

  public void togglePID(boolean bPIDActive) {
    // TODO: So maybe try out a backup system if the PID decides to not work... !!!
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

}
