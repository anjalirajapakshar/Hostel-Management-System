package org.hms.dao.custom;

import org.hms.dao.CrudDAO;
import org.hms.entity.Room;

import java.io.IOException;

public interface RoomDAO extends CrudDAO<Room> {
    void updateRoomQut() throws IOException;
}
