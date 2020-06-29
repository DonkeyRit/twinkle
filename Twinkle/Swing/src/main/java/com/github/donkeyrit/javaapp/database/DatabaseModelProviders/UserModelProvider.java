package com.github.donkeyrit.javaapp.database.DatabaseModelProviders;

import com.github.donkeyrit.javaapp.database.DatabaseProvider;
import com.github.donkeyrit.javaapp.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class UserModelProvider {

    private DatabaseProvider provider;

    public UserModelProvider(DatabaseProvider provider) {
        this.provider = provider;
    }

    public Stream<User> getUsers() {
        final String query = "SELECT * FROM user";
        List<User> userList = new ArrayList<>();

        try (ResultSet userSet = provider.select(query)) {

            while (userSet.next()) {

                String login = userSet.getString("login");
                String password = userSet.getString("password");
                boolean role = userSet.getBoolean("role");

                userList.add(new User(login, password, role));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return userList.stream();
    }

    public void addUser(User user) {
        final String query = String.format(
                "INSERT INTO user(login,password,role) VALUES ('%s','%s',0)",
                user.getLogin(),
                user.getPassword()
        );
        provider.insert(query);
    }

    public User getSpecificUserByCredentials(String login, String password) {
        return getUsers()
                .filter(u -> u.getLogin().equals(login) && u.getPassword().equals(password))
                .findFirst()
                .orElse(null);
    }

    public User getSpecificUserByLogin(String login) {
        return getUsers()
                .filter(u -> u.getLogin().equals(login))
                .findFirst()
                .orElse(null);
    }
}
