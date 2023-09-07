package org.hms.dao.custom.Impl;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hms.dao.custom.StudentDAO;
import org.hms.entity.Student;
import org.hms.util.FactoryConfiguration;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class StudentDAOImpl implements StudentDAO {
    @Override
    public List<Student> getAll() throws SQLException, ClassNotFoundException, IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        NativeQuery nativeQuery = session.createNativeQuery("SELECT * FROM student");
        nativeQuery.addEntity(Student.class);
        List<Student> students = nativeQuery.getResultList();
        transaction.commit();
        session.close();
        return students;
    }

    @Override
    public boolean add(Student entity) throws SQLException, ClassNotFoundException, IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.persist(entity);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(Student entity) throws SQLException, ClassNotFoundException, IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.update(entity);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException, IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        String sql = "DELETE FROM student WHERE studentID = :id";
        NativeQuery<Student> nativeQuery = session.createNativeQuery(sql);
        nativeQuery.setParameter("id",id);
        nativeQuery.executeUpdate();

        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException, IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        NativeQuery<String> nativeQuery = session.createNativeQuery("SELECT studentID FROM Student ORDER BY studentID DESC LIMIT 1");
        String id = nativeQuery.uniqueResult();
        transaction.commit();
        session.close();
        if(id != null){
            String[] strings = id.split("S0");
            int newID = Integer.parseInt(strings[1]);
            newID++;
            String ID = String.valueOf(newID);
            int length = ID.length();
            if (length < 2){
                return "S00"+newID;
            }else {
                if (length < 3){
                    return "S0"+newID;
                }else {
                    return "S"+newID;
                }
            }
        }else {
            return "S001";
        }
    }

    @Override
    public Student search(String id) throws SQLException, ClassNotFoundException, IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        NativeQuery nativeQuery = session.createNativeQuery("SELECT * FROM student WHERE studentID = :studentID");
        nativeQuery.setParameter("studentID",id);

        nativeQuery.addEntity(Student.class);
        Student student = (Student) nativeQuery.uniqueResult();
        transaction.commit();
        session.close();
        return student;
    }
}
