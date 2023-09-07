package org.hms.dao.custom;

import org.hms.dao.CrudDAO;
import org.hms.entity.Reservation;

import java.io.IOException;
import java.util.List;

public interface ReservationDAO extends CrudDAO<Reservation> {
    List<String> loadStudentID() throws IOException;
    List<String> loadRoomID() throws IOException;
}
