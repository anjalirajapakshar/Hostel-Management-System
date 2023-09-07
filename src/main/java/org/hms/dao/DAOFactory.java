package org.hms.dao;

import org.hms.dao.custom.Impl.ReservationDAOImpl;
import org.hms.dao.custom.Impl.RoomDAOImpl;
import org.hms.dao.custom.Impl.StudentDAOImpl;
import org.hms.dao.custom.Impl.UserDAOImpl;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory() {
    }

    public static DAOFactory getDaoFactory() {
        return (daoFactory == null) ? daoFactory = new DAOFactory() : daoFactory;
    }

    public enum DAOTypes {
        USER,STUDENT,ROOM,RESERVATION
    }

    public SuperDAO getDAO(DAOTypes types){
        switch (types) {
            case USER:
                return new UserDAOImpl();
            case STUDENT:
                return new StudentDAOImpl();
            case ROOM:
                return new RoomDAOImpl();
            case RESERVATION:
                return new ReservationDAOImpl();
            default:
                return null;
        }
    }
}
