/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
/* FRC Team 7890 SeQuEnCe                                                     */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Launcher;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.Constants.kFixedSpeeds;

public class LauncherSpeed extends CommandBase {

  Launcher objLauncher;
  DoubleSupplier dsSpeed;
  boolean bShootLow;
  boolean bShootLowHmi;
  boolean bShootLowOld;
  boolean bShootInitLine;
  boolean bShootInitLineHmi;
  boolean bShootInitLineOld;
  double dSpeedHmi;

  public LauncherSpeed(Launcher objLauncherIn, DoubleSupplier dsSpeedIn) {
    objLauncher = objLauncherIn;
    dsSpeed = dsSpeedIn;
    addRequirements(objLauncher);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    SmartDashboard.putBoolean("Low Shot", bShootLow);
    SmartDashboard.putBoolean("Init Line Shot", bShootInitLine);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    bShootLowHmi = SmartDashboard.getBoolean("Low Shot", false);
    bShootInitLineHmi = SmartDashboard.getBoolean("Init Line Shot", false);
    if (bShootLow != bShootLowHmi) {bShootLow = bShootLowHmi;}
    if (bShootInitLine != bShootInitLineHmi) {bShootInitLine = bShootInitLineHmi;}
    if (bShootLow & !bShootLowOld) {bShootInitLine = false; SmartDashboard.putBoolean("Init Line Shot", false);}
    if (bShootInitLine & !bShootInitLineOld) {bShootLow = false; SmartDashboard.putBoolean("Low Shot", false);}
    if (bShootLow) {
      objLauncher.setSpeed(kFixedSpeeds.dLauncherLowRPM / kFixedSpeeds.dLauncherMaxRPM, false);
      dSpeedHmi = kFixedSpeeds.dLauncherLowRPM;
    }
    else {
      if (bShootInitLine) {
        objLauncher.setSpeed(kFixedSpeeds.dLauncherInitLineRPM / kFixedSpeeds.dLauncherMaxRPM, false);
        dSpeedHmi = kFixedSpeeds.dLauncherInitLineRPM;
      }
      else {
        objLauncher.setSpeed(dsSpeed.getAsDouble(), false);
        dSpeedHmi = kFixedSpeeds.dLauncherMaxRPM * dsSpeed.getAsDouble();
      }
    }
    SmartDashboard.putNumber("Launcher Command", dSpeedHmi);
    SmartDashboard.putNumber("Launcher Velocity", objLauncher.getVelocity9());
    bShootLowOld = bShootLow;
    bShootInitLineOld = bShootInitLine;
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    objLauncher.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
