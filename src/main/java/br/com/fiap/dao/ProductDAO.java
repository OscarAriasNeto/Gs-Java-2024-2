package br.com.fiap.dao;

import br.com.fiap.exception.IdNotFoundException;
import br.com.fiap.model.Availability;
import br.com.fiap.model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    private static final String TABLE_NAME = "T_GS_PRODUCT";

    private static final String INSERT_SQL = "INSERT INTO " + TABLE_NAME + " (user_id, name, price, voltage, availability) VALUES (?, ?, ?, ?, ?)";
    private static final String SELECT_ALL_SQL = "SELECT * FROM " + TABLE_NAME;
    private static final String SELECT_BY_ID_SQL = "SELECT * FROM " + TABLE_NAME + " WHERE id = ?";
    private static final String UPDATE_SQL = "UPDATE " + TABLE_NAME + " SET user_id = ?, name = ?, price = ?, voltage = ?, availability = ? WHERE id = ?";
    private static final String DELETE_SQL = "DELETE FROM " + TABLE_NAME + " WHERE id = ?";

    private Connection conn;

    public ProductDAO(Connection conn) {
        this.conn = conn;
    }

    private Product parseProduct(ResultSet rs) throws SQLException {
        Product product = new Product();
        product.setId(rs.getInt("id"));
        product.setUserId(rs.getInt("user_id"));
        product.setName(rs.getString("name"));
        product.setPrice(rs.getDouble("price"));
        product.setVoltage(rs.getInt("voltage"));
        product.setAvailability(Availability.valueOf(rs.getString("availability")));
        return product;
    }

    private void fillStatement(PreparedStatement stmt, Product product) throws SQLException {
        stmt.setInt(1, product.getUserId());
        stmt.setString(2, product.getName());
        stmt.setDouble(3, product.getPrice());
        stmt.setInt(4, product.getVoltage());
        stmt.setString(5, product.getAvailability().name());
    }

    public void create(Product product) throws SQLException {
        System.out.println(product.getAvailability());
        PreparedStatement stmt = conn.prepareStatement(INSERT_SQL, new String[] {"id"});
        fillStatement(stmt, product);
        stmt.executeUpdate();

        ResultSet rs = stmt.getGeneratedKeys();
        rs.next();
        product.setId(rs.getInt(1));
    }

    public List<Product> findAll() throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(SELECT_ALL_SQL);
        ResultSet rs = stmt.executeQuery();

        List<Product> products = new ArrayList<>();
        while (rs.next()) products.add(parseProduct(rs));
        return products;
    }

    public Product findById(int id) throws SQLException, IdNotFoundException {
        PreparedStatement stmt = conn.prepareStatement(SELECT_BY_ID_SQL);
        stmt.setInt(1, id);

        ResultSet rs = stmt.executeQuery();
        if (!rs.next()) throw new IdNotFoundException("Product not found");
        return parseProduct(rs);
    }

    public void update(Product product) throws SQLException, IdNotFoundException {
        PreparedStatement stmt = conn.prepareStatement(UPDATE_SQL);
        fillStatement(stmt, product);
        stmt.setInt(5, product.getId());
        if (stmt.executeUpdate() == 0) throw new IdNotFoundException("Product not found");
    }

    public void delete(int id) throws SQLException, IdNotFoundException {
        PreparedStatement stmt = conn.prepareStatement(DELETE_SQL);
        stmt.setInt(1, id);
        if (stmt.executeUpdate() == 0) throw new IdNotFoundException("Product not found");
    }
}
