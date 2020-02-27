/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
/* FRC Team 7890 SeQuEnCe                                                     */
/*----------------------------------------------------------------------------*/

package frc.robot;

// controller imports
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;

// subsystem imports
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Launcher;
import frc.robot.subsystems.Tilter;
import frc.robot.subsystems.Traveler;
import frc.robot.subsystems.Winch;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Floor;

// commands imports
import frc.robot.commands.DriveRobot;
import frc.robot.commands.ElevatorSpeed;
import frc.robot.commands.LauncherSpeed;
import frc.robot.commands.LowerIntake;
import frc.robot.commands.MoveTraveler;
import frc.robot.commands.RaiseIntake;
import frc.robot.commands.RollIn;
import frc.robot.commands.RollOut;
import frc.robot.commands.ShiftDriveSpeed;
import frc.robot.commands.FeedLauncher;
import frc.robot.commands.TiltLauncher;
import frc.robot.commands.WinchUp;
import frc.robot.commands.RaiseFloor;

// import edu.wpi.first.wpilibj2.command.Command;
// import edu.wpi.first.wpilibj2.command.CommandScheduler;
// import edu.wpi.first.wpilibj2.command.ScheduleCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
// import edu.wpi.first.wpilibj2.command.RunCommand;
// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {

  XboxController objXboxDriverStick = new XboxController(0);    // TO Do: Add this to Constants!!!
  XboxController objXboxCoPilotStick = new XboxController(1);
  // Joystick objButtonBox = new Joystick(2);

  // The robot's subsystems and commands are defined here...
  private final DriveTrain objDriveTrain = new DriveTrain();
  private final Intake objIntake = new Intake();
  private final Launcher objLauncher = new Launcher();
  private final Elevator objElevator = new Elevator();
  private final Winch objWinch = new Winch();
  private final Traveler objTraveler = new Traveler();
  private final Tilter objTilter = new Tilter();
  private final Feeder objFeeder = new Feeder();
  private final Floor objFloor = new Floor();

  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
    // driver always on
    objDriveTrain.setDefaultCommand(new DriveRobot(() -> -objXboxDriverStick.getRawAxis(1), () -> objXboxDriverStick.getRawAxis(4), objDriveTrain));
    objLauncher.setDefaultCommand(new LauncherSpeed(objLauncher, () -> objXboxDriverStick.getRawAxis(3)));
    objWinch.setDefaultCommand(new WinchUp(objWinch, () -> objXboxDriverStick.getRawButton(7),  () -> objXboxDriverStick.getRawButton(8)));
    objTraveler.setDefaultCommand(new MoveTraveler(objTraveler, () -> objXboxDriverStick.getRawAxis(0)));
    // copilot always on
    objElevator.setDefaultCommand(new ElevatorSpeed(objElevator, () -> objXboxCoPilotStick.getRawAxis(5)));
    objTilter.setDefaultCommand(new TiltLauncher(objTilter, () -> objXboxCoPilotStick.getRawAxis(1)));
  }

  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {

    // driver stick bindings
    final JoystickButton buttonDriverA = new JoystickButton(objXboxDriverStick, 1);
    final JoystickButton bumperDriverL = new JoystickButton(objXboxDriverStick, 5); // Left bumper shifts into high gear!!!
    final JoystickButton bumperDriverR = new JoystickButton(objXboxDriverStick, 6); // Right bumper shifts into low gear!!!

    // copilot stick bindings
    final JoystickButton buttonCoPilotA = new JoystickButton(objXboxCoPilotStick, 1);
    final JoystickButton buttonCoPilotB = new JoystickButton(objXboxCoPilotStick, 2);
    final JoystickButton buttonCoPilotY = new JoystickButton(objXboxCoPilotStick, 4);
    final JoystickButton buttonCoPilotBack = new JoystickButton(objXboxCoPilotStick, 7);
    final JoystickButton buttonCoPilotStart = new JoystickButton(objXboxCoPilotStick, 8);

    // button box bindings
    // final JoystickButton buttonBox5 = new JoystickButton(objButtonBox, 5);

    // driver button actions
    buttonDriverA.whileHeld(new FeedLauncher(objFeeder));
    bumperDriverL.whenPressed(new ShiftDriveSpeed(objDriveTrain, true));    // shift into high gear on rising edge
    bumperDriverR.whenPressed(new ShiftDriveSpeed(objDriveTrain, false));   // shift into low gear on rising edge

    // copilot button actions
    buttonCoPilotA.whileHeld(new RollOut(objIntake));
    buttonCoPilotB.whileHeld(new RaiseFloor(objFloor));
    buttonCoPilotY.whileHeld(new RollIn(objIntake));
    buttonCoPilotBack.whenPressed(new LowerIntake(objIntake));
    buttonCoPilotStart.whenPressed(new RaiseIntake(objIntake));

    // button box actions
    // buttonBox5.whileHeld(new RollOut(objIntake));

  }


  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  // public Command getAutonomousCommand() {
  //   // An ExampleCommand will run in autonomous
  //   // return m_autoCommand;
  // }
}
