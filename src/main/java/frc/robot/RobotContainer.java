// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.Autos;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.Flywheel;
import frc.robot.subsystems.IntakeClass;
import frc.robot.subsystems.Loader;
import edu.wpi.first.epilogue.Logged;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
@Logged
public class RobotContainer {
    // The robot's subsystems and commands are defined here...
    private final DriveSubsystem m_driveSubsystem = new DriveSubsystem();
    private final Flywheel m_flywheel = new Flywheel();
    private final IntakeClass m_intake = new IntakeClass();
    private final Loader m_loader = new Loader();
  

    // Replace with CommandPS4Controller or CommandJoystick if needed
    private final CommandXboxController m_driverController =
        new CommandXboxController(OperatorConstants.kDriverControllerPort);

    /** The container for the robot. Contains subsystems, OI devices, and commands. */
    public RobotContainer() {
        // Configure the trigger bindings
        configureBindings();
    }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
   * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
    private void configureBindings() {

        // Control the drive with split-stick arcade controls
        m_driveSubsystem.setDefaultCommand(
            m_driveSubsystem.arcadeDriveCommand(
                () -> -m_driverController.getLeftY(), () -> -m_driverController.getRightX()));
                // m_driverController.a().onTrue(m_intake.runIntakeCommand());
                // m_driverController.b().whileTrue(m_intake.runIntakeCommand());
                m_driverController.x().toggleOnTrue(m_intake.runIntakeCommand());
                // m_driverController.y().onTrue(m_intake.stopIntakeCommand());


                // whileTrue starts the command when the button is held.
                // When the button is released, whileTrue cancels the command.
                // Cancelling the command causes finallyDo in the subsystem command to run,/
                // and finallyDo stops the motor. Therefore, no separate stop binding is needed.

                m_driverController.a().toggleOnTrue(m_flywheel.runShooterCommand());
                m_driverController.b().whileTrue(m_loader.runToFlywheelCommand());




        /* TO DO: Add bindings here */
    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        return Autos.driveDistance(m_driveSubsystem);
    }
    
}
