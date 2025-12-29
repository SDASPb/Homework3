package org.example.dao;

import org.example.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.postgresql.ds.PGSimpleDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertEquals;


@Testcontainers
public class UserDaoIT {

    @Container
    private static final PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:15")
                    .withDatabaseName("test")
                    .withUsername("test")
                    .withPassword("test");

    private UserDao userDao;

    @BeforeEach
    void setUp() throws SQLException {
        PGSimpleDataSource ds = new PGSimpleDataSource();
        ds.setURL(postgres.getJdbcUrl());
        ds.setUser(postgres.getUsername());
        ds.setPassword(postgres.getPassword());

        userDao = new UserDao(ds);

        try (Connection c = ds.getConnection();
             Statement st = c.createStatement()) {
            st.execute("CREATE TABLE IF NOT EXISTS users (id SERIAL, name TEXT)");
            st.execute("TRUNCATE TABLE users");
        }
    }

    @Test
    void ДолженСохранитьПользователя() throws SQLException {
        userDao.save(new User(null, "Dima"));

        int count = userDao.count();

        assertEquals(1, count);
    }
}

