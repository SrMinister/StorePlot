package com.github.srminister.stores.misc.store;

import com.github.srminister.stores.StoresPlugin;
import com.github.srminister.stores.database.MySQLProvider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StoreStorage {
    private final MySQLProvider service = StoresPlugin.getInstance().getMySQLProvider();
    private final StoreCache storeCache = StoresPlugin.getInstance().getStoreCache();

    private static final String UPSERT_QUERY = "INSERT INTO store_plot (user_name, time, stars, visits, location) " +
            "VALUES(?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE time = ?, stars = ?, visits = ?, location = ?;";
    private static final String SELECT_QUERY = "SELECT * FROM store_plot WHERE user_name = ?";

    public void upsert(Store store) {
        try (Connection connection = service.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(UPSERT_QUERY)) {

                statement.setString(1, store.getName());
                statement.setLong(2, store.getTime());
                statement.setInt(3, store.getStars());
                statement.setInt(4, store.getVisits());
                statement.setString(5, store.getLocation());
                statement.setLong(6, store.getTime());
                statement.setInt(7, store.getStars());
                statement.setInt(8, store.getVisits());
                statement.setString(9, store.getLocation());
                statement.executeUpdate();
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void load(String name) {
        if (storeCache.getByUser(name) != null) return;

        try (Connection connection = service.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(SELECT_QUERY)) {
                statement.setString(1, name);

                try (ResultSet set = statement.executeQuery()) {
                    final Store store = new Store(name,
                            0,
                            0,
                            0,
                            null
                    );

                    if (set.next()) {
                        final long time = set.getLong("time");
                        final int stars = set.getInt("stars");
                        final int visits = set.getInt("visits");
                        final String location = set.getString("location");

                        store.setTime(time);
                        store.setStars(stars);
                        store.setVisits(visits);
                        store.setLocation(location);
                    }

                    storeCache.addCachedElement(store);
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}