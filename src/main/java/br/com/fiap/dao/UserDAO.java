package br.com.fiap.dao;

import br.com.fiap.exception.IdNotFoundException;
import br.com.fiap.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    private static final String TABLE_NAME = "T_GS_USER";

    private static final String INSERT_SQL = "INSERT INTO " + TABLE_NAME + " (email, whats) VALUES (?, ?)";
    private static final String LOGIN_SQL = "SELECT * FROM " + TABLE_NAME + " WHERE email = ? AND whats = ?";
    private static final String SELECT_ALL_SQL = "SELECT * FROM " + TABLE_NAME;
    private static final String SELECT_BY_ID_SQL = "SELECT * FROM " + TABLE_NAME + " WHERE id = ?";
    private static final String UPDATE_SQL = "UPDATE " + TABLE_NAME + " SET email = ?, whats = ? WHERE id = ?";
    private static final String DELETE_SQL = "DELETE FROM " + TABLE_NAME + " WHERE id = ?";

    private Connection conn;

    public UserDAO(Connection conn) {
        this.conn = conn;
    }

    private User parseUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setEmail(rs.getString("email"));
        user.setWhats(rs.getString("whats"));
        return user;
    }

    private void fillStatement(PreparedStatement stmt, User user) throws SQLException {
        stmt.setString(1, user.getEmail());
        stmt.setString(2, user.getWhats());
    }

    public void create(User user) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(INSERT_SQL, new String[] {"id"});
        fillStatement(stmt, user);
        stmt.executeUpdate();

        ResultSet rs = stmt.getGeneratedKeys();
        rs.next();
        user.setId(rs.getInt(1));
    }

    public User login(User user) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(LOGIN_SQL);
        stmt.setString(1, user.getEmail());
        stmt.setString(2, user.getWhats());

        ResultSet rs = stmt.executeQuery();
        if (!rs.next()) throw new SQLException("User not found");
        return parseUser(rs);
    }

    public List<User> findAll() throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(SELECT_ALL_SQL);
        ResultSet rs = stmt.executeQuery();

        List<User> users = new ArrayList<>();
        while (rs.next()) users.add(parseUser(rs));
        return users;
    }

    public User findById(int id) throws SQLException, IdNotFoundException {
        PreparedStatement stmt = conn.prepareStatement(SELECT_BY_ID_SQL);
        stmt.setInt(1, id);

        ResultSet rs = stmt.executeQuery();
        if (!rs.next()) throw new IdNotFoundException("User not found");
        return parseUser(rs);
    }

    public void update(User user) throws SQLException, IdNotFoundException {
        PreparedStatement stmt = conn.prepareStatement(UPDATE_SQL);
        fillStatement(stmt, user);
        stmt.setInt(3, user.getId());
        if (stmt.executeUpdate() == 0) throw new IdNotFoundException("User not found");
    }

    public void delete(int id) throws SQLException, IdNotFoundException {
        PreparedStatement stmt = conn.prepareStatement(DELETE_SQL);
        stmt.setInt(1, id);
        if (stmt.executeUpdate() == 0) throw new IdNotFoundException("User not found");
    }
}
