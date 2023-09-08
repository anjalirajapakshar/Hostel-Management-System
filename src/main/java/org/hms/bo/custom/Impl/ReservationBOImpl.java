package org.hms.bo.custom.Impl;

import org.hms.bo.custom.ReservationBO;
import org.hms.dao.DAOFactory;
import org.hms.dao.custom.ReservationDAO;
import org.hms.dto.ReservationDTO;
import org.hms.entity.Reservation;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReservationBOImpl implements ReservationBO {
    ReservationDAO reservationDAO = (ReservationDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.RESERVATION);

    @Override
    public List<ReservationDTO> getAllReservation() throws SQLException, ClassNotFoundException, IOException {
        List<ReservationDTO> allReservations = new ArrayList<>();
        List<Reservation> all = reservationDAO.getAll();
        for (Reservation reservation : all){
            allReservations.add(new ReservationDTO(reservation.getReservationId(),reservation.getDate(),
                    reservation.getRoomId(),reservation.getStudentId(),reservation.getStatus()));
        }
        return allReservations;
    }

    @Override
    public boolean addReservation(ReservationDTO dto) throws SQLException, ClassNotFoundException, IOException {
        return reservationDAO.add(new Reservation(dto.getReservationId(),dto.getDate(),dto.getRoomId(),
                dto.getStudentId(),dto.getStatus()));
    }

    @Override
    public boolean updateReservation(ReservationDTO dto) throws SQLException, ClassNotFoundException, IOException {
        return reservationDAO.update(new Reservation(dto.getReservationId(),dto.getDate(),dto.getRoomId(),
                dto.getStudentId(),dto.getStatus()));
    }

    @Override
    public boolean deleteReservation(String id) throws SQLException, ClassNotFoundException, IOException {
        return reservationDAO.delete(id);
    }

    @Override
    public String generateNewReservationID() throws SQLException, ClassNotFoundException, IOException {
        return reservationDAO.generateNewID();
    }

    @Override
    public ReservationDTO searchReservation(String id) throws SQLException, ClassNotFoundException, IOException {
        Reservation reservation = reservationDAO.search(id);
        return new ReservationDTO(reservation.getReservationId(),reservation.getDate(),reservation.getRoomId(),reservation.getStudentId(),reservation.getStatus());
    }

}
