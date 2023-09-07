package org.hms.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginFromController {
    @FXML
    private AnchorPane loadFormContext;

    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private JFXButton btnLogin;
    public void btnLoginOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/main_from.fxml"));
        Stage stage = (Stage) btnLogin.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Main From");
        stage.centerOnScreen();
    }

    public void imgEyeOnAction(MouseEvent mouseEvent) {

    }

    public void btnSignInOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/signin_from.fxml"));
        Stage stage = (Stage) btnLogin.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.setTitle("SignIn From");
        stage.centerOnScreen();
    }
}
