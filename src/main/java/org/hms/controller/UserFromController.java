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
import org.hms.bo.custom.UserBO;
import org.hms.dto.UserDTO;
import org.hms.entity.User;
import org.hms.util.Regex;
import org.hms.util.TextFields;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class UserFromController implements Initializable {
    @FXML
    private JFXTextField txtUsername;

    @FXML
    private JFXTextField txtPassword;

    @FXML
    private JFXTextField txtEmail;
    @FXML
    private TextField txtSearch;

    @FXML
    private Label lblUser_id;

    @FXML
    private TableView<User> tblUser;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colPassword;

    @FXML
    private TableColumn<?, ?> colEmail;
    UserBO userBO = (UserBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.USER);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            getAll();
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        setCellValueFactory();
        clearAll();
        generateNextOrderId();
    }

    private void generateNextOrderId() {
        try {
            String nextId = userBO.generateNewUserID();
            lblUser_id.setText(nextId);
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void clearAll() {
        txtUsername.setText(null);
        txtPassword.setText(null);
        txtEmail.setText(null);
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("userID"));
        colName.setCellValueFactory(new PropertyValueFactory<>("userName"));
        colPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

    }

    ObservableList<User> observableList;

    private void getAll() throws SQLException, IOException, ClassNotFoundException {
        observableList = FXCollections.observableArrayList();
        List<UserDTO> allUser = userBO.getAllUsers();

        for (UserDTO userDTO : allUser) {
            observableList.add(new User(userDTO.getUserID(), userDTO.getUserName(), userDTO.getPassword(), userDTO.getEmail()));
        }
        tblUser.setItems(observableList);
    }

    public void btnSaveOnAction(ActionEvent actionEvent) throws SQLException, IOException, ClassNotFoundException {
        if (!isValid()){
            new Alert(Alert.AlertType.ERROR,"Check Fields").show();
            return;
        }
        UserDTO userDTO = new UserDTO(lblUser_id.getText(), txtUsername.getText(), txtPassword.getText(), txtEmail.getText());

        if (userBO.addUser(userDTO)) {
            new Alert(Alert.AlertType.CONFIRMATION, "Saved", ButtonType.OK).show();
        } else {
            new Alert(Alert.AlertType.WARNING, "Try again", ButtonType.OK).show();
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
        UserDTO userDTO = new UserDTO(lblUser_id.getText(), txtUsername.getText(), txtPassword.getText(), txtEmail.getText());
        if (userBO.updateUser(new UserDTO(userDTO.getUserID(), userDTO.getUserName(), userDTO.getPassword(), userDTO.getEmail()))) {
            new Alert(Alert.AlertType.CONFIRMATION, "Updated", ButtonType.OK).show();
        } else {
            new Alert(Alert.AlertType.WARNING, "Try again", ButtonType.OK).show();
        }
        getAll();
        clearAll();
        generateNextOrderId();
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) throws SQLException, IOException, ClassNotFoundException {
        if (userBO.deleteUser(lblUser_id.getText())) {
            new Alert(Alert.AlertType.CONFIRMATION, "Deleted", ButtonType.OK).show();
        } else {
            new Alert(Alert.AlertType.WARNING, "Try again", ButtonType.OK).show();
        }
        getAll();
        clearAll();
        generateNextOrderId();
    }

    public void txtSearchOnAction(ActionEvent actionEvent) {
        String id = txtSearch.getText();
        try {
            UserDTO userDTO = userBO.searchUser(id);
            if (userDTO != null) {
                fillDate(userDTO);
            } else {
                new Alert(Alert.AlertType.WARNING, "Try again", ButtonType.OK).show();
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        txtSearch.setText("");
    }

    private void fillDate(UserDTO userDTO) {
        lblUser_id.setText(userDTO.getUserID());
        txtUsername.setText(userDTO.getUserName());
        txtPassword.setText(userDTO.getPassword());
        txtEmail.setText(userDTO.getEmail());
    }

    public void txtUserNameOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextFields.NAME, txtUsername);
    }

    public void txtEmailOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextFields.EMAIL, txtEmail);
    }

    public boolean isValid() {
        if(!Regex.setTextColor(TextFields.NAME, txtUsername))return false;
        if(!Regex.setTextColor(TextFields.EMAIL, txtEmail))return false;
        return true;
    }
}
