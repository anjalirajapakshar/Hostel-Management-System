package org.hms.bo.custom.Impl;

import org.hms.bo.custom.StudentBO;
import org.hms.dao.DAOFactory;
import org.hms.dao.custom.StudentDAO;
import org.hms.dto.StudentDTO;
import org.hms.entity.Student;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentBOImpl implements StudentBO {
    StudentDAO studentDAO = (StudentDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.STUDENT);

    @Override
    public List<StudentDTO> getAllStudent() throws SQLException, ClassNotFoundException, IOException {
        List<StudentDTO> allStudents = new ArrayList<>();
        List<Student> all = studentDAO.getAll();
        for (Student student : all){
            allStudents.add(new StudentDTO(student.getStudentID(),student.getName(),student.getAddress(),student.getDbo(),
                    student.getContact(),student.getGender()));
        }
        return allStudents;
    }

    @Override
    public boolean addStudent(StudentDTO dto) throws SQLException, ClassNotFoundException, IOException {
        return studentDAO.add(new Student(dto.getStudentID(),dto.getName(),dto.getAddress(),dto.getDbo(),dto.getContact(),dto.getGender()));
    }

    @Override
    public boolean updateStudent(StudentDTO dto) throws SQLException, ClassNotFoundException, IOException {
        return studentDAO.update(new Student(dto.getStudentID(),dto.getName(),dto.getAddress(),
                dto.getDbo(),dto.getContact(),dto.getGender()));
    }

    @Override
    public boolean deleteStudent(String id) throws SQLException, ClassNotFoundException, IOException {
        return studentDAO.delete(id);
    }

    @Override
    public String generateNewStudentID() throws SQLException, ClassNotFoundException, IOException {
        return studentDAO.generateNewID();
    }

    @Override
    public StudentDTO searchStudent(String id) throws SQLException, ClassNotFoundException, IOException {
        Student student = studentDAO.search(id);
        return new StudentDTO(student.getStudentID(),student.getName(),student.getAddress(),student.getContact(),student.getDbo(),student.getGender());
    }
}
