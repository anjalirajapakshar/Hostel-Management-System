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
import org.hms.bo.custom.StudentBO;
import org.hms.dto.StudentDTO;
import org.hms.entity.Student;
import org.hms.util.Regex;
import org.hms.util.TextFields;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

public class ManageStudentFromController implements Initializable {
    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtAddress;

    @FXML
    private Label lblStudentId;

    @FXML
    private JFXTextField txtContactNo;

    @FXML
    private DatePicker datePicker;

    @FXML
    private ComboBox<String> cmbGender;

    @FXML
    private TextField txtSearch;

    @FXML
    private TableView<Student> tblStudent;

    @FXML
    private TableColumn<?, ?> colID;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colContactNo;

    @FXML
    private TableColumn<?, ?> colDOB;

    @FXML
    private TableColumn<?, ?> colGender;

    StudentBO studentBO = (StudentBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.STUDENT);
    ObservableList<Student> observableList;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> list = FXCollections.observableArrayList("Male","Female");
        cmbGender.setItems(list);

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
        colID.setCellValueFactory(new PropertyValueFactory<>("studentID"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colContactNo.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colDOB.setCellValueFactory(new PropertyValueFactory<>("dbo"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
    }

    private void generateNextOrderId() {
        try {
            String nextId = studentBO.generateNewStudentID();
            lblStudentId.setText(nextId);
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void clearAll() {
        txtName.setText(null);
        txtAddress.setText(null);
        txtContactNo.setText(null);
        datePicker.setValue(null);
        cmbGender.setValue(null);
    }


    private void getAll() throws SQLException, IOException, ClassNotFoundException {
        observableList = FXCollections.observableArrayList();
        List<StudentDTO> allStudent = studentBO.getAllStudent();

        for (StudentDTO studentDTO : allStudent){
            observableList.add(new Student(studentDTO.getStudentID(),studentDTO.getName(),studentDTO.getAddress(),studentDTO.getContact(),studentDTO.getDbo(),studentDTO.getGender()));
        }
        tblStudent.setItems(observableList);
    }

    public void btnSaveOnAction(ActionEvent actionEvent) throws SQLException, IOException, ClassNotFoundException {
        if (!isValid()){
            new Alert(Alert.AlertType.ERROR,"Check Fields").show();
            return;
        }
        String date=datePicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        StudentDTO studentDTO = new StudentDTO(lblStudentId.getText(), txtName.getText(), txtAddress.getText(), txtContactNo.getText(),date,cmbGender.getValue());

        if (studentBO.addStudent(studentDTO)){
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
        String date=datePicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        StudentDTO studentDTO = new StudentDTO(lblStudentId.getText(), txtName.getText(), txtAddress.getText(), txtContactNo.getText(), date,cmbGender.getValue());

        if (studentBO.updateStudent(new StudentDTO(studentDTO.getStudentID(),studentDTO.getName(),studentDTO.getAddress(),studentDTO.getContact(),studentDTO.getDbo(),studentDTO.getGender()))){
            new Alert(Alert.AlertType.CONFIRMATION,"Updated", ButtonType.OK).show();
        }else {
            new Alert(Alert.AlertType.WARNING,"Try again", ButtonType.OK).show();
        }
        getAll();
        clearAll();
        generateNextOrderId();
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) throws SQLException, IOException, ClassNotFoundException {
        if (studentBO.deleteStudent(lblStudentId.getText())){
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
            StudentDTO studentDTO = studentBO.searchStudent(id);
            if (studentDTO != null){
                fillDate(studentDTO);
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

    private void fillDate(StudentDTO studentDTO) {
        lblStudentId.setText(studentDTO.getStudentID());
        txtName.setText(studentDTO.getName());
        txtAddress.setText(studentDTO.getAddress());
        txtContactNo.setText(studentDTO.getContact());
        datePicker.setValue(LocalDate.parse(studentDTO.getDbo()));
        cmbGender.setValue(studentDTO.getGender());
    }

    public void txtNameOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextFields.NAME,txtName);
    }

    public void txtAddressOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextFields.ADDRESS,txtAddress);
    }

    public void txtContactOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextFields.PHONE,txtContactNo);
    }

    public boolean isValid() {
        if(!Regex.setTextColor(TextFields.NAME, txtName))return false;
        if(!Regex.setTextColor(TextFields.ADDRESS, txtAddress))return false;
        if(!Regex.setTextColor(TextFields.PHONE, txtContactNo))return false;
        return true;
    }
}
