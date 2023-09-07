package org.hms.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.hms.bo.BOFactory;
import org.hms.bo.custom.ReservationBO;
import org.hms.bo.custom.RoomBO;
import org.hms.bo.custom.StudentBO;
import org.hms.dao.DAOFactory;
import org.hms.dao.custom.ReservationDAO;
import org.hms.dao.custom.RoomDAO;
import org.hms.dto.ReservationDTO;
import org.hms.entity.Reservation;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

public class ManageReservationFromController implements Initializable {
    @FXML
    private ComboBox<String> cmbRoomID;

    @FXML
    private Label lblReservationId;

    @FXML
    private DatePicker Date;

    @FXML
    private ComboBox<String> cmbStudentID;

    @FXML
    private ComboBox<String> cmbStatus;
    @FXML
    private TextField txtSearch;

    @FXML
    private TableView<Reservation> tblReservation;

    @FXML
    private TableColumn<?, ?> colReservationID;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colRoomID;

    @FXML
    private TableColumn<?, ?> colStudentID;

    @FXML
    private TableColumn<?, ?> colStatus;

    ReservationBO reservationBO =(ReservationBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.RESERVATION);
    RoomBO roomBO =(RoomBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ROOM);
    StudentBO studentBO =(StudentBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.STUDENT);

    ReservationDAO reservationDAO = (ReservationDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.RESERVATION);
    RoomDAO roomDAO = (RoomDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ROOM);

    ObservableList<Reservation> observableList;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> list = FXCollections.observableArrayList("PAID","UNPAID");
        cmbStatus.setItems(list);

        try {
            loadStudentID();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            loadRoomID();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        setCellValueFactory();
        clearAll();
        generateNextOrderId();
    }

    private void loadRoomID() throws IOException {
        List<String> id = reservationDAO.loadRoomID();
        ObservableList<String> obList = FXCollections.observableArrayList();

        for (String un : id){
            obList.add(un);
        }
        cmbRoomID.setItems(obList);
    }

    private void loadStudentID() throws IOException {
        List<String> id = reservationDAO.loadStudentID();
        ObservableList<String> obList = FXCollections.observableArrayList();

        for (String un : id){
            obList.add(un);
        }
        cmbStudentID.setItems(obList);
    }

    private void setCellValueFactory() {
        colReservationID.setCellValueFactory(new PropertyValueFactory<>("reservationId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colRoomID.setCellValueFactory(new PropertyValueFactory<>("roomId"));
        colStudentID.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    private void generateNextOrderId() {
        try {
            String nextId = reservationBO.generateNewReservationID();
            lblReservationId.setText(nextId);
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void clearAll() {
        Date.setValue(null);
        cmbRoomID.setValue(null);
        cmbStudentID.setValue(null);
        cmbStatus.setValue(null);
    }

    private void getAll() throws SQLException, IOException, ClassNotFoundException {
        observableList = FXCollections.observableArrayList();
        List<ReservationDTO> allReservation = reservationBO.getAllReservation();

        for (ReservationDTO reservationDTO : allReservation){
            observableList.add(new Reservation(reservationDTO.getReservationId(),reservationDTO.getDate(),reservationDTO.getRoomId(),reservationDTO.getStudentId(),reservationDTO.getStatus()));
        }
        tblReservation.setItems(observableList);
    }

    public void btnSaveOnAction(ActionEvent actionEvent) throws SQLException, IOException, ClassNotFoundException {
        String date=Date.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        ReservationDTO reservationDTO = new ReservationDTO(lblReservationId.getText(),date, cmbRoomID.getValue(), cmbStudentID.getValue(), cmbStatus.getValue());
        if (reservationBO.addReservation(reservationDTO)){
            new Alert(Alert.AlertType.CONFIRMATION,"Saved", ButtonType.OK).show();
        }else {
            new Alert(Alert.AlertType.WARNING,"Try again",ButtonType.OK).show();
        }
        getAll();
        clearAll();
        generateNextOrderId();
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) throws SQLException, IOException, ClassNotFoundException {
        String date=Date.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        ReservationDTO reservationDTO = new ReservationDTO(lblReservationId.getText(),date,cmbRoomID.getValue(),cmbStudentID.getValue(),cmbStatus.getValue());

        if(reservationBO.updateReservation(new ReservationDTO(reservationDTO.getReservationId(),reservationDTO.getDate(),reservationDTO.getRoomId(),reservationDTO.getStudentId(),reservationDTO.getStatus()))){
            new Alert(Alert.AlertType.CONFIRMATION,"Updated", ButtonType.OK).show();
        }else {
            new Alert(Alert.AlertType.WARNING,"Try again", ButtonType.OK).show();
        }
        getAll();
        clearAll();
        generateNextOrderId();
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) throws SQLException, IOException, ClassNotFoundException {
        if (reservationBO.deleteReservation(lblReservationId.getText())){
            new Alert(Alert.AlertType.CONFIRMATION,"Deleted", ButtonType.OK).show();
        }else {
            new Alert(Alert.AlertType.WARNING,"Try again", ButtonType.OK).show();
        }
        getAll();
        clearAll();
        generateNextOrderId();
    }

    public void txtSearchOnAction(ActionEvent actionEvent) {
        String id = txtSearch.getText();
        try {
            ReservationDTO reservationDTO = reservationBO.searchReservation(id);
            if (reservationDTO != null){
                fillDate(reservationDTO);
            }else {
                new Alert(Alert.AlertType.WARNING,"Try again", ButtonType.OK).show();
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        txtSearch.setText("");
    }

    private void fillDate(ReservationDTO reservationDTO) {
        lblReservationId.setText(reservationDTO.getReservationId());
        Date.setValue(LocalDate.parse(reservationDTO.getDate()));
        cmbRoomID.setValue(reservationDTO.getRoomId());
        cmbStudentID.setValue(reservationDTO.getStudentId());
        cmbStatus.setValue(reservationDTO.getStatus());
    }
}