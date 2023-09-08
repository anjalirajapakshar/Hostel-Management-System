package org.hms.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.hms.bo.BOFactory;
import org.hms.bo.custom.ReservationBO;
import org.hms.bo.custom.StudentBO;
import org.hms.bo.custom.UserBO;

import java.io.IOException;
import java.sql.SQLException;

public class DashboardFromController {

    @FXML
    private Label lblTotalStudent;
    @FXML
    private Label lblTotalUser;

    @FXML
    private Label lblTotalReservation;

    StudentBO studentBO =(StudentBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.STUDENT);
    UserBO userBO =(UserBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.USER);

    ReservationBO reservationBO =(ReservationBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.RESERVATION);

    public void initialize() throws SQLException, IOException, ClassNotFoundException {
        loadLabel();

    }

    private void loadLabel() throws SQLException, IOException, ClassNotFoundException {
        lblTotalStudent.setText(String.valueOf(studentBO.getAllStudent().size()));
        lblTotalUser.setText(String.valueOf(userBO.getAllUsers().size()));
        lblTotalReservation.setText(String.valueOf(reservationBO.getAllReservation().size()));
    }
}
