package org.hms.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.hms.dao.DAOFactory;
import org.hms.dao.custom.UserDAO;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginFromController implements Initializable {
    @FXML
    private AnchorPane loadFormContext;

    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private JFXButton btnLogin;

    UserDAO userDAO = (UserDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.USER);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtPassword.setVisible(true);
    }

    public void btnLoginOnAction(ActionEvent actionEvent) throws IOException {
        String username = txtUsername.getText();
        String password = txtPassword.getText();

        if (userDAO.checkPassword(username,password)){
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/main_from.fxml"));
            Stage stage = (Stage) btnLogin.getScene().getWindow();

            stage.setScene(new Scene(anchorPane));
            stage.setTitle("Main From");
            stage.centerOnScreen();
        }else {
            new Alert(Alert.AlertType.ERROR,"Please Check Username and password !!").show();
        }

        txtUsername.clear();
        txtPassword.clear();
    }

    public void imgEyeOnAction(MouseEvent mouseEvent) {
        txtPassword.setVisible(true);
    }


    public void btnSignInOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/signin_from.fxml"));
        Stage stage = (Stage) btnLogin.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.setTitle("SignIn From");
        stage.centerOnScreen();
    }

}
