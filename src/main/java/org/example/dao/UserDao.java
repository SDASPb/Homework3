package org.example.dao;

import org.example.model.User;

import javax.sql.DataSource;
import java.sql.*;

public class UserDao {

    private final DataSource dataSource;

    public UserDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void save(User user) throws SQLException {
        try (Connection c = dataSource.getConnection();
             PreparedStatement ps = c.prepareStatement("INSERT INTO users(name) VALUES (?)")) {
            ps.setString(1, user.getName());
            ps.executeUpdate();
        }
    }

    public int count() throws SQLException {
        try (Connection c = dataSource.getConnection();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery("SELECT count(*) FROM users")) {
            rs.next();
            return rs.getInt(1);
        }
    }
}
