package org.hms.dao.custom;

import org.hms.dao.CrudDAO;
import org.hms.entity.User;

import java.io.IOException;

public interface UserDAO extends CrudDAO<User> {
    boolean checkPassword(String username, String password) throws IOException;
}
