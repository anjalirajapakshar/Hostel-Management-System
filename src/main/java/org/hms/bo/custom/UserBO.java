package org.hms.bo.custom;

import org.hms.bo.SuperBO;
import org.hms.dto.UserDTO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface UserBO extends SuperBO {
    public List<UserDTO> getAllUsers() throws SQLException, ClassNotFoundException, IOException;
    public boolean addUser(UserDTO dto) throws SQLException, ClassNotFoundException, IOException;

    public boolean updateUser(UserDTO dto) throws SQLException, ClassNotFoundException, IOException;

    public boolean deleteUser(String id) throws SQLException, ClassNotFoundException, IOException;

    public UserDTO searchUser(String id) throws SQLException, ClassNotFoundException, IOException;

    public String generateNewUserID() throws SQLException, ClassNotFoundException, IOException;


}
