package dao;

import ru.itsjava.domain.User;

import java.sql.SQLException;

public interface UserDao {
    User findByNameAndPassword(String name, String password);
    void insertUser(String name, String password) throws SQLException;
}
