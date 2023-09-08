package org.hms.dao.custom.Impl;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hms.dao.custom.RoomDAO;
import org.hms.entity.Room;
import org.hms.util.FactoryConfiguration;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class RoomDAOImpl implements RoomDAO {
    @Override
    public List<Room> getAll() throws SQLException, ClassNotFoundException, IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        NativeQuery nativeQuery = session.createNativeQuery("SELECT * FROM room");
        nativeQuery.addEntity(Room.class);
        List<Room> rooms = nativeQuery.getResultList();
        transaction.commit();
        session.close();
        return rooms;
    }

    @Override
    public boolean add(Room entity) throws SQLException, ClassNotFoundException, IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.persist(entity);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(Room entity) throws SQLException, ClassNotFoundException, IOException {
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
        String sql = "DELETE FROM room WHERE roomId = :id";
        NativeQuery<Room> nativeQuery = session.createNativeQuery(sql);
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
        NativeQuery<String> nativeQuery = session.createNativeQuery("SELECT roomId FROM Room ORDER BY roomId DESC LIMIT 1");
        String id = nativeQuery.uniqueResult();
        transaction.commit();
        session.close();
        if(id != null){
            String[] strings = id.split("RM-");
            int newID = Integer.parseInt(strings[1]);
            newID++;
            String ID = String.valueOf(newID);
            int length = ID.length();
            if (length < 2){
                return "RM-00"+newID;
            }else {
                if (length < 3){
                    return "RM-0"+newID;
                }else {
                    return "RM"+newID;
                }
            }
        }else {
            return "RM-001";
        }
    }

    @Override
    public Room search(String id) throws SQLException, ClassNotFoundException, IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        NativeQuery nativeQuery = session.createNativeQuery("SELECT * FROM room WHERE roomId = :roomId");
        nativeQuery.setParameter("roomId",id);

        nativeQuery.addEntity(Room.class);
        Room room = (Room) nativeQuery.uniqueResult();
        transaction.commit();
        session.close();
        return room;
    }

    @Override
    public void updateRoomQut() throws IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        String sql = "UPDATE room SET qty = (qty - 1)";
        NativeQuery<Room> nativeQuery = session.createNativeQuery(sql);
        nativeQuery.executeUpdate();

        transaction.commit();
        session.close();
    }
}
