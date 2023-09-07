package org.hms.bo.custom;

import org.hms.bo.SuperBO;
import org.hms.dto.ReservationDTO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface ReservationBO extends SuperBO {
    public List<ReservationDTO> getAllReservation() throws SQLException, ClassNotFoundException, IOException;
    public boolean addReservation(ReservationDTO dto) throws SQLException, ClassNotFoundException, IOException;

    public boolean updateReservation(ReservationDTO dto) throws SQLException, ClassNotFoundException, IOException;

    public boolean deleteReservation(String id) throws SQLException, ClassNotFoundException, IOException;

    public String generateNewReservationID() throws SQLException, ClassNotFoundException, IOException;
    public ReservationDTO searchReservation(String id) throws SQLException, ClassNotFoundException, IOException;


}
