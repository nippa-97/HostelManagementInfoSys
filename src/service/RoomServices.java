/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.util.ArrayList;
import javax.swing.JTable;
import model.Room;

/**
 *
 * @author Semasinghe L.S. IT19051130
 */
public interface RoomServices {
    public void AddRoom(Room room);
    public void DeleteSelectedRow(JTable table);

    public ArrayList<Room> getRooms ();
}
