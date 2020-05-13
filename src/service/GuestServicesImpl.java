/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import model.Guest;
import util.Database;





public class GuestServicesImpl implements GuestServices {
    public void newGuest (Guest guest) {
		try {
			
			//Openning DB connection
			Class.forName(Database.dbDriver);
			Connection connection = DriverManager.getConnection(Database.dbURL, Database.dbUsername, Database.dbPassword);
			Statement statement = connection.createStatement();
			
			UserServices userService = new UserServicesImpl();
                        userService.newUser(guest);
			
			//SQL INSERT statements for new Guests
			String queryGuest = "INSERT INTO guest (username, availability, room, emergName, emergPhone) VALUES (?,?,?,?,?)";
			
			//Prepared Statement Queries
			PreparedStatement psGuest = connection.prepareStatement(queryGuest);
			psGuest.setString(1, guest.getUsername());
			psGuest.setBoolean(2, guest.isAvailability());
			psGuest.setString(3, guest.getRoom());
                        psGuest.setString(4, guest.getEmName());
                        psGuest.setString(5, guest.getEmPhone());
			
			//Executing Prepared Statements
			psGuest.execute();
			
			//Closing DB Connection
			connection.close();
		}
		catch (SQLIntegrityConstraintViolationException e) {
			JOptionPane.showMessageDialog(null, "You have entered existing user's information \n" + e, "Invalid Inputs!", JOptionPane.ERROR_MESSAGE);
			System.exit(-1);
		}
                catch (SQLException e){
                    JOptionPane.showMessageDialog(null, "Task Failed! \n" + e, "Database Error", JOptionPane.ERROR_MESSAGE);
			System.exit(-1);
                }
		catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Task Failed! \n" + e, "Error", JOptionPane.ERROR_MESSAGE);
			System.exit(-1);
		}
	}
}
