package org.hms.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.hms.bo.BOFactory;
import org.hms.bo.custom.UserBO;
import org.hms.dto.UserDTO;
import org.hms.util.Regex;
import org.hms.util.TextFields;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SignInFromController implements Initializable {
    @FXML
    private Button btnRegister;

    @FXML
    private Button btnBack;

    @FXML
    private JFXTextField txtUsername;

    @FXML
    private JFXTextField txtPassword;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private Label lblUserID;
    UserBO userBO = (UserBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.USER);
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        generateNextOrderId();
        clearAll();
    }
    public void btnRegisterOnAction(ActionEvent actionEvent) throws SQLException, IOException, ClassNotFoundException {
        if (!isValid()){
            new Alert(Alert.AlertType.ERROR,"Check Fields").show();
            return;
        }
        UserDTO userDTO = new UserDTO(lblUserID.getText(), txtUsername.getText(), txtPassword.getText(), txtEmail.getText());
        if (userBO.addUser(userDTO)){
            new Alert(Alert.AlertType.CONFIRMATION, "Register", ButtonType.OK).show();
        } else {
            new Alert(Alert.AlertType.WARNING, "Try again", ButtonType.OK).show();
        }
        generateNextOrderId();
        clearAll();
    }

    private void clearAll() {
        txtUsername.setText(null);
        txtPassword.setText(null);
        txtEmail.setText(null);
    }

    private void generateNextOrderId() {
        try {
            String nextId = userBO.generateNewUserID();
            lblUserID.setText(nextId);
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/login_from.fxml"));
        Stage stage = (Stage) btnBack.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.setTitle("SignIn From");
        stage.centerOnScreen();
    }

    public void txtUserNameOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextFields.NAME,txtUsername);
    }

    public void txtEmailOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextFields.EMAIL,txtEmail);
    }

    public boolean isValid() {
        if(!Regex.setTextColor(TextFields.NAME, txtUsername))return false;
        if(!Regex.setTextColor(TextFields.EMAIL, txtEmail))return false;
        return true;
    }
}
