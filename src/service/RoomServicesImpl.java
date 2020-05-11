/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import util.Database;
import model.Room;


public class RoomServicesImpl implements RoomServices {

    public static String assignRoom (boolean gender, boolean age) {
			
		/*
		 * Adults
		 * 	Male - Block M
		 * 	Female Block W
		 * 
		 * Minors
		 * 	Male - Block B
		 * 	Female - Block G
		 */
		
		if (age == true) {
			if (gender == true) {
				return findVacantRoom('M');
			}
			else {
				return findVacantRoom('W');
			}
		}
		else {
			if (gender == true) {
				return findVacantRoom('B');
			}
			else {
				return findVacantRoom('G');
			}
		}
	}

	private static String findVacantRoom(char block) {
            String roomNumber = null;
		try {
			Class.forName(Database.dbDriver);
			Connection connection = DriverManager.getConnection(Database.dbURL, Database.dbUsername, Database.dbPassword);
			Statement statement = connection.createStatement();
			
			//finds a room in the specified block which is not full
			String queryVacantRooms = "SELECT roomNumber FROM room WHERE roomNumber = '" + block + "???' AND occupied < capasity";
			
			ResultSet rsVacantRooms = statement.executeQuery(queryVacantRooms);
			
			if (rsVacantRooms.next()) {
				roomNumber = rsVacantRooms.getString("roomNumber");				
			}
			
			//Closing DB Connection
			connection.close();
						
		}
		catch (Exception error) {
			JOptionPane.showMessageDialog(null, error, "Database Error", JOptionPane.ERROR_MESSAGE);
			System.exit(-1);
		}
		return roomNumber;
        }
        
        public void AddRoom(Room room){
            try {
			Class.forName(Database.dbDriver);
			Connection connection = DriverManager.getConnection(Database.dbURL, Database.dbUsername, Database.dbPassword);
			
			//SQL INSERT statements for new Guests
                        String queryRoom = "INSERT INTO room (roomNumber, rental, capasity) VALUES (?,?,?)";

                        //Prepared Statement Queries
                        PreparedStatement psRoom = connection.prepareStatement(queryRoom);
                        psRoom.setString(1, room.getRoomNumber());
                        psRoom.setFloat(2, room.getRental());
                        psRoom.setInt(3, room.getCapasity());

                        //Executing Prepared Statements
                        psRoom.execute();
                        
                        JOptionPane.showMessageDialog(null, "Room " + room.getRoomNumber() + " successfully added", "SUCCESS!", JOptionPane.INFORMATION_MESSAGE);
			
			//Closing DB Connection
			connection.close();
						
		}
		catch (ClassNotFoundException | SQLException error) {
			JOptionPane.showMessageDialog(null, error, "Database Error", JOptionPane.ERROR_MESSAGE);
			System.exit(-1);
		}
        }
        
        public ArrayList<Room> getRooms (){
            ArrayList <Room> roomsList = new ArrayList<>();

            try {
                Class.forName(Database.dbDriver);
                Connection connection = DriverManager.getConnection(Database.dbURL, Database.dbUsername, Database.dbPassword);
                Statement statement = connection.createStatement();

                //finds a room in the specified block which is not full
                String queryRooms = "SELECT * FROM room";
                
                ResultSet rsRooms;
                rsRooms = statement.executeQuery(queryRooms);

                String[] row = new String [4];
                
                
                while (rsRooms.next()){
                    Room r = new Room(rsRooms.getString(1), rsRooms.getFloat(2), rsRooms.getInt(3), rsRooms.getInt(4));
                    roomsList.add(r);

                rsRooms.close();

                //Closing DB Connection
                connection.close();
                }
            }
						
            catch (ClassNotFoundException | SQLException error) {
                    JOptionPane.showMessageDialog(null, error, "Database Error", JOptionPane.ERROR_MESSAGE);
                    System.exit(-1);
            }
        return roomsList;
        }

        
        
    
}
