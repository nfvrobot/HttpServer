package com.chernyavsky.dao;

import com.chernyavsky.connection.ConnectionPool;
import com.chernyavsky.dto.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Optional;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserDao {

    public static final UserDao INSTANCE = new UserDao();

    private static final String GET_BY_ID = "SELECT to_json(balance), to_json(rate) " + "FROM user_storage.users " + "WHERE id = ?";


    public Optional<User> getById(Long id) {
        Optional<User> user = Optional.empty();
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_ID)) {
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = Optional.of(getUserFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public static Document toDocument(ResultSet rs) throws SQLException, ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.newDocument();

        Element results = doc.createElement("Results");
        doc.appendChild(results);

        ResultSetMetaData rsmd = rs.getMetaData();
        int colCount = rsmd.getColumnCount();

        while (rs.next()) {
            Element row = doc.createElement("Row");
            results.appendChild(row);

            for (int i = 1; i <= colCount; i++) {
                String columnName = rsmd.getColumnName(i);
                Object value = rs.getObject(i);

                Element node = doc.createElement(columnName);
                node.appendChild(doc.createTextNode(value.toString()));
                row.appendChild(node);
            }
        }
        return doc;
    }

        private User getUserFromResultSet (ResultSet resultSet) throws SQLException {
            return User.builder()
                    .balance(resultSet.getString("balance"))
                    .rate(resultSet.getString("rate"))
                    .build();
        }

    }
