package org.hms.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import org.hms.bo.BOFactory;
import org.hms.bo.custom.RoomBO;
import org.hms.dto.RoomDTO;
import org.hms.entity.Room;
import org.hms.util.Regex;
import org.hms.util.TextFields;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ManageRoomFromController implements Initializable {

    @FXML
    private JFXTextField txtKeyMoney;

    @FXML
    private Label lblRoomID;

    @FXML
    private JFXTextField txtQty;

    @FXML
    private ComboBox<String> cmbType;
    @FXML
    private TextField txtSearch;

    @FXML
    private TableView<Room> tblRooms;

    @FXML
    private TableColumn<?, ?> colRoomID;

    @FXML
    private TableColumn<?, ?> colType;

    @FXML
    private TableColumn<?, ?> colKeyMoney;

    @FXML
    private TableColumn<?, ?> colQty;


    RoomBO roomBO = (RoomBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ROOM);
    ObservableList<Room> observableList;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> list = FXCollections.observableArrayList("Non-AC","Non-AC / Food","AC","AC / Food");
        cmbType.setItems(list);

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

    private void setCellValueFactory() {
        colRoomID.setCellValueFactory(new PropertyValueFactory<>("roomId"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colKeyMoney.setCellValueFactory(new PropertyValueFactory<>("keyMoney"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));

    }

    private void generateNextOrderId() {
        try {
            String nextId = roomBO.generateNewRoomID();
            lblRoomID.setText(nextId);
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void clearAll() {
        cmbType.setValue(null);
        txtKeyMoney.setText(null);
        txtQty.setText(null);
    }

    private void getAll() throws SQLException, IOException, ClassNotFoundException {
        observableList = FXCollections.observableArrayList();
        List<RoomDTO> allRooms = roomBO.getAllRooms();

        for (RoomDTO roomDTO : allRooms){
            observableList.add(new Room(roomDTO.getRoomId(),roomDTO.getType(),roomDTO.getKeyMoney(),roomDTO.getQty()));
        }
        tblRooms.setItems(observableList);
    }

    public void btnSaveOnAction(ActionEvent actionEvent) throws SQLException, IOException, ClassNotFoundException {
        if (!isValid()){
            new Alert(Alert.AlertType.ERROR,"Check Fields").show();
            return;
        }
        RoomDTO roomDTO = new RoomDTO(lblRoomID.getText(), cmbType.getValue(), Double.parseDouble(txtKeyMoney.getText()), Integer.parseInt(txtQty.getText()));
        if (roomBO.addRoom(roomDTO)){
            new Alert(Alert.AlertType.CONFIRMATION,"Saved", ButtonType.OK).show();
        }else {
            new Alert(Alert.AlertType.WARNING,"Try again",ButtonType.OK).show();
        }
        getAll();
        clearAll();
        generateNextOrderId();
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) throws SQLException, IOException, ClassNotFoundException {
        if (!isValid()){
            new Alert(Alert.AlertType.ERROR,"Check Fields").show();
            return;
        }
        RoomDTO roomDTO = new RoomDTO(lblRoomID.getText(), cmbType.getValue(), Double.parseDouble(txtKeyMoney.getText()), Integer.parseInt(txtQty.getText()));
        if (roomBO.updateRoom(new RoomDTO(roomDTO.getRoomId(),roomDTO.getType(),roomDTO.getKeyMoney(),roomDTO.getQty()))){
            new Alert(Alert.AlertType.CONFIRMATION,"Updated", ButtonType.OK).show();
        }else {
            new Alert(Alert.AlertType.WARNING,"Try again", ButtonType.OK).show();
        }
        getAll();
        clearAll();
        generateNextOrderId();
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) throws SQLException, IOException, ClassNotFoundException {
        if (roomBO.deleteRoom(lblRoomID.getText())){
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
            RoomDTO roomDTO = roomBO.searchRoom(id);
            if (roomDTO != null){
                fillDate(roomDTO);
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

    private void fillDate(RoomDTO roomDTO) {
        lblRoomID.setText(roomDTO.getRoomId());
        cmbType.setValue(roomDTO.getType());
        txtKeyMoney.setText(String.valueOf(roomDTO.getKeyMoney()));
        txtQty.setText(String.valueOf(roomDTO.getQty()));
    }

    public void txtKeyMoneyOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextFields.DOUBLE,txtKeyMoney);
    }

    public void txtQtyOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextFields.INTEGER,txtQty);
    }

    public boolean isValid() {
        if(!Regex.setTextColor(TextFields.DOUBLE, txtKeyMoney))return false;
        if(!Regex.setTextColor(TextFields.INTEGER, txtQty))return false;
        return true;
    }
}
