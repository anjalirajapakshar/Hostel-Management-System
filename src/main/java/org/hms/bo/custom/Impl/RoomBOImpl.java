package org.hms.bo.custom.Impl;

import org.hms.bo.custom.RoomBO;
import org.hms.dao.DAOFactory;
import org.hms.dao.custom.RoomDAO;
import org.hms.dto.RoomDTO;
import org.hms.entity.Room;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoomBOImpl implements RoomBO {
    RoomDAO roomDAO = (RoomDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ROOM);

    @Override
    public List<RoomDTO> getAllRooms() throws SQLException, ClassNotFoundException, IOException {
        List<RoomDTO> allRooms = new ArrayList<>();
        List<Room> all = roomDAO.getAll();
        for (Room room : all){
            allRooms.add(new RoomDTO(room.getRoomId(),room.getType(),room.getKeyMoney(),room.getQty()));
        }
        return allRooms;
    }

    @Override
    public boolean addRoom(RoomDTO dto) throws SQLException, ClassNotFoundException, IOException {
        return roomDAO.add(new Room(dto.getRoomId(),dto.getType(),dto.getKeyMoney(),dto.getQty()));
    }

    @Override
    public boolean updateRoom(RoomDTO dto) throws SQLException, ClassNotFoundException, IOException {
        return roomDAO.update(new Room(dto.getRoomId(),dto.getType(),dto.getKeyMoney(),dto.getQty()));
    }

    @Override
    public boolean deleteRoom(String id) throws SQLException, ClassNotFoundException, IOException {
        return roomDAO.delete(id);
    }

    @Override
    public String generateNewRoomID() throws SQLException, ClassNotFoundException, IOException {
        return roomDAO.generateNewID();
    }

    @Override
    public RoomDTO searchRoom(String id) throws SQLException, ClassNotFoundException, IOException {
        Room room = roomDAO.search(id);
        return new RoomDTO(room.getRoomId(),room.getType(),room.getKeyMoney(),room.getQty());
    }
}
