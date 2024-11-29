package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    Connection connection = Util.getDBConnection();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try {
            String sql = "CREATE TABLE IF EXIST USERS (`id` INT NOT NULL, `name` VARCHAR(45) NOT NULL, `lastName` VARCHAR(45) NOT NULL, " +
                    "`age` INT NOT NULL, PRIMARY KEY (`id`), UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE);";

            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate(sql);
                System.out.println("Table created");
            }
        } catch (Exception e) {
            System.out.println("Connection while creating the table failed");
        }
    }

    public void dropUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.execute("DROP TABLE IF EXISTS USERS");
        } catch (SQLException e) {
        }
    }

    public void saveUser(String name, String lastName, byte age) {
            try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO USERS (name, lastName, age) VALUES (?, ?, ?)")) {

                preparedStatement.setString(1, name);
                preparedStatement.setString(2, lastName);
                preparedStatement.setByte(3, age);
                preparedStatement.executeUpdate();

                System.out.println("User created");
            } catch (Exception e) {
            System.out.println("Connection while saving the user failed");
        }
    }

    public void removeUserById(long id) {

            try (PreparedStatement preparedStatement = connection.prepareStatement("TRUNCATE FROM USERS WHERE id = ?")) {
                preparedStatement.executeUpdate("TRUNCATE FROM USERS WHERE id = ?");
                System.out.println("User removed");
            } catch (Exception e) {
            System.out.println("Connection while removing the user failed");
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try {
            String sql = "SELECT * FROM USERS";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.executeQuery(sql);
                ResultSet resultSet = preparedStatement.getResultSet();

                while (resultSet.next()) {
                    User user = new User();
                    user.setId(resultSet.getLong("id"));
                    user.setName(resultSet.getString("name"));
                    user.setLastName(resultSet.getString("lastName"));
                    user.setAge(resultSet.getByte("age"));
                    users.add(user);
                }
        } catch (Exception e) {
                System.out.println("Connection while getting the users failed");
            }
    } catch (Exception e) {
            System.out.println("Connection while getting the users failed");
        }
        return users;
    }

    public void cleanUsersTable() {
            try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE TABLE FROM `kata`.`USERS`")) {
                preparedStatement.executeUpdate("DELETE TABLE FROM `kata`.`USERS`;");
                System.out.println("Table cleaned");
            } catch (Exception e) {
            System.out.println("Connection while cleaning the table failed");
        }
    }
}
