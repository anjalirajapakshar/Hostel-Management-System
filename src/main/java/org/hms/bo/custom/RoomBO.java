package org.hms.bo.custom;

import org.hms.bo.SuperBO;
import org.hms.dto.RoomDTO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface RoomBO extends SuperBO {
    public List<RoomDTO> getAllRooms() throws SQLException, ClassNotFoundException, IOException;
    public boolean addRoom(RoomDTO dto) throws SQLException, ClassNotFoundException, IOException;

    public boolean updateRoom(RoomDTO dto) throws SQLException, ClassNotFoundException, IOException;

    public boolean deleteRoom(String id) throws SQLException, ClassNotFoundException, IOException;

    public String generateNewRoomID() throws SQLException, ClassNotFoundException, IOException;
    public RoomDTO searchRoom(String id) throws SQLException, ClassNotFoundException, IOException;

}
