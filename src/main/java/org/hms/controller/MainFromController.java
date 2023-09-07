package org.hms.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;

public class MainFromController implements Initializable {
    @FXML
    private AnchorPane loadFormContext;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblTime;

    public void btnDashBoardOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("/view/dashboard_from.fxml");
        assert  resource != null;
        Parent load = FXMLLoader.load(resource);
        loadFormContext.getChildren().clear();
        loadFormContext.getChildren().add(load);
    }
    public void btnManageStudentOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("/view/student_from.fxml");
        assert  resource != null;
        Parent load = FXMLLoader.load(resource);
        loadFormContext.getChildren().clear();
        loadFormContext.getChildren().add(load);
    }

    public void btnManageRoomOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("/view/room_from.fxml");
        assert  resource != null;
        Parent load = FXMLLoader.load(resource);
        loadFormContext.getChildren().clear();
        loadFormContext.getChildren().add(load);
    }

    public void btnManageUserOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("/view/user_from.fxml");
        assert  resource != null;
        Parent load = FXMLLoader.load(resource);
        loadFormContext.getChildren().clear();
        loadFormContext.getChildren().add(load);
    }

    public void btnManageReservationOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("/view/reservation_from.fxml");
        assert  resource != null;
        Parent load = FXMLLoader.load(resource);
        loadFormContext.getChildren().clear();
        loadFormContext.getChildren().add(load);
    }

    public void btnLogoutOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/view/login_from.fxml"));
        AnchorPane anchorPane = loader.load();
        Scene scene = new Scene(anchorPane);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        loadFormContext.getScene().getWindow().hide();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initClock();
    }

    private void initClock() {
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            lblTime.setText(LocalDateTime.now().format(formatter));

            SimpleDateFormat formatter2 = new SimpleDateFormat("dd/MM/yyyy");
            Date date = new Date();
            lblDate.setText(formatter2.format(date));
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }
}
