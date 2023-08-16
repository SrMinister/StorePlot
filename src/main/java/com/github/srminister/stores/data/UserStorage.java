package com.github.srminister.stores.data;

import com.github.srminister.stores.StoresPlugin;
import com.github.srminister.stores.data.store.Stores;
import com.github.srminister.stores.database.MySQLProvider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserStorage {
    private final MySQLProvider service = StoresPlugin.getInstance().getMySQLProvider();

    public void insert(User user) {
        try (Connection connection = service.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("INSERT INTO `store_plot` VALUES(?,?,?,?,?);")) {
                statement.setString(1, user.getName());
                statement.setLong(2, user.getTime());
                statement.setInt(3, user.getStores().getStars());
                statement.setInt(4, user.getStores().getVisits());
                statement.setString(5, user.getStores().getLocation());
                statement.executeUpdate();
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void update(User user) {
        try (Connection connection = service.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("UPDATE `store_plot` SET time=?, stars=?, visits=?, location=? WHERE user_name=?;")) {
                statement.setLong(1, user.getTime());
                statement.setInt(2, user.getStores().getStars());
                statement.setInt(3, user.getStores().getVisits());
                statement.setString(4, user.getStores().getLocation());
                statement.setString(5, user.getName());
                statement.executeUpdate();
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public User find(String id) {
        try (Connection connection = service.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM `store_plot` WHERE user_name=?;")) {
                statement.setString(1, id);
                try (ResultSet set = statement.executeQuery()) {
                    if (!set.next()) return null;

                    final String name = set.getString("user_name");
                    final long time = set.getLong("time");
                    final int stars = set.getInt("stars");
                    final int visits = set.getInt("visits");
                    final String location = set.getString("location");

                    return new User(
                            name,
                            time,
                            new Stores(
                                    stars,
                                    visits,
                                    location
                            )
                    );
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
            return null;
        }
    }
}