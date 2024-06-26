package dao;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import ru.itsjava.domain.User;
import ru.itsjava.utils.Props;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@AllArgsConstructor
public class UserDaoImpl implements UserDao{
    private final Props props;
    @SneakyThrows
    @Override
    public User findByNameAndPassword(String name, String password) {
        try(Connection connection = DriverManager.getConnection(
                props.getValue("db.url"),
                props.getValue("db.login"),
                props.getValue("db.password"))){
            PreparedStatement preparedStatement = connection.prepareStatement
                    ("select count(*) cnt from new_schema_java.Users_java where name = ? and password = ?");
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,password);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            int userCount = resultSet.getInt("cnt");
            if (userCount ==1 ){
                return new User(name,password);
            } throw  new UserNotFoundException("User not found!");


        }

    }

    @SneakyThrows
    public void insertUser(String name,String password){
        try(Connection connection = DriverManager.getConnection(
                props.getValue("db.url"),
                props.getValue("db.login"),
                props.getValue("db.password"))){
            PreparedStatement preparedStatement = connection.prepareStatement
                    ("INSERT INTO new_schema_java.Users_java (name, password) VALUES (?, ?)");
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,password);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("A new user has been inserted successfully.");
            } else {
                throw new RuntimeException("Failed to insert user.");
            }

        }

    }
}
