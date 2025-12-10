package com.jdbc.td1.dao;

import com.jdbc.td1.model.Category;
import com.jdbc.td1.model.Product;
import com.jdbc.td1.config.DBConnection;
import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class DataRetriever {

    private final DBConnection dbConnection = new DBConnection();

    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT id, name, product_id FROM product_category ORDER BY name";

        try (Connection conn = dbConnection.getDBConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Category cat = new Category();
                cat.setId(rs.getInt("id"));
                cat.setName(rs.getString("name"));
                categories.add(cat);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la lecture des catégories", e);
        }
        return categories;
    }

    public List<Product> getProductList(int page, int size) {
        if (page < 1) page = 1;
        if (size < 1) size = 10;

        List<Product> products = new ArrayList<>();
        int offset = (page - 1) * size;

        String sql = """
            SELECT 
                p.id, p.name, p.price, p.creation_datetime,
                pc.id AS cat_id, pc.name AS cat_name
            FROM product p
            LEFT JOIN product_category pc ON p.id = pc.product_id
            ORDER BY p.id
            LIMIT ? OFFSET ?
            """;

        try (Connection conn = dbConnection.getDBConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, size);
            ps.setInt(2, offset);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Product p = new Product();
                    p.setId(rs.getInt("id"));
                    p.setName(rs.getString("name"));
                    p.setPrice(rs.getDouble("price"));

                    Timestamp ts = rs.getTimestamp("creation_datetime");
                    p.setCreationDateTime(ts != null ? ts.toInstant() : Instant.now());

                    Integer catId = rs.getObject("cat_id", Integer.class);
                    if (catId != null) {
                        Category cat = new Category();
                        cat.setId(catId);
                        cat.setName(rs.getString("cat_name"));
                        p.setCategory(cat);
                    } else {
                        p.setCategory(null);
                    }

                    products.add(p);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur pagination produits", e);
        }
        return products;
    }
    /**
     * Recherche filtrée de produits (sans pagination)
     */
    public List<Product> getProductsByCriteria(String productName,
                                               String categoryName,
                                               Instant creationMin,
                                               Instant creationMax) {
        return getProductsByCriteria(productName, categoryName, creationMin, creationMax, 1, Integer.MAX_VALUE);
    }

    public List<Product> getProductsByCriteria(String productName,
                                               String categoryName,
                                               Instant creationMin,
                                               Instant creationMax,
                                               int page,
                                               int size) {
        if (page < 1) page = 1;
        if (size < 1) size = 10;

        List<Product> products = new ArrayList<>();
        int offset = (page - 1) * size;

        StringBuilder sql = new StringBuilder("""
            SELECT 
                p.id, p.name, p.price, p.creation_datetime,
                pc.id AS cat_id, pc.name AS cat_name
            FROM product p
            LEFT JOIN product_category pc ON p.id = pc.product_id
            WHERE 1 = 1
            """);

        List<Object> params = new ArrayList<>();

        if (productName != null && !productName.trim().isEmpty()) {
            sql.append(" AND p.name ILIKE ?");
            params.add("%" + productName.trim() + "%");
        }

        if (categoryName != null && !categoryName.trim().isEmpty()) {
            sql.append(" AND pc.name ILIKE ?");
            params.add("%" + categoryName.trim() + "%");
        }

        if (creationMin != null) {
            sql.append(" AND p.creation_datetime >= ?");
            params.add(Timestamp.from(creationMin));
        }

        if (creationMax != null) {
            sql.append(" AND p.creation_datetime <= ?");
            params.add(Timestamp.from(creationMax));
        }

        sql.append(" ORDER BY p.id");
        sql.append(" LIMIT ? OFFSET ?");
        params.add(size);
        params.add(offset);

        try (Connection conn = new DBConnection().getDBConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Product p = new Product();
                    p.setId(rs.getInt("id"));
                    p.setName(rs.getString("name"));
                    p.setPrice(rs.getDouble("price"));

                    Timestamp ts = rs.getTimestamp("creation_datetime");
                    p.setCreationDateTime(ts != null ? ts.toInstant() : Instant.now());

                    Integer catId = rs.getObject("cat_id", Integer.class);
                    if (catId != null) {
                        Category cat = new Category();
                        cat.setId(catId);
                        cat.setName(rs.getString("cat_name"));
                        p.setCategory(cat);
                    }
                    products.add(p);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la recherche filtrée des produits", e);
        }
        return products;
    }
}