package org.example.config;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DataBaseConnectionTest {
    @Test
    void testGetConnection() throws SQLException {
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        assertNotNull(connection);
    }

    @Test
    void testCloseConnection() throws SQLException {
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        Connection connection = dataBaseConnection.getConnection();
        assertNotNull(connection);
        connection.close();
        assertTrue(connection.isClosed());
    }

    @Test
    void testConnectionCloseException() throws SQLException {
        Connection mockConnection = mock(Connection.class);
        doThrow(new SQLException("Close failed")).when(mockConnection).close();

        DataBaseConnection.closeConnection(mockConnection);

        verify(mockConnection).close();
    }
}
