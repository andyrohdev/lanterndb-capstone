package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.CollectionList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component
public class JdbcCollectionListDao implements CollectionListDao {
    @Autowired
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    private final JdbcUserDao jdbcUserDao;



    public JdbcCollectionListDao(JdbcTemplate jdbcTemplate, JdbcUserDao jdbcUserDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcUserDao = jdbcUserDao;

    }

    @Override
    public CollectionList fetchCollectionByType(int collectionType, int userId) {
        CollectionList collectionList = null;

        String sql = "SELECT collection_list_id, user_id, collection_id, title, genre " +
                "FROM collection_list " +
                "WHERE collection_id = ? AND user_id = ?";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, collectionType, userId);
            if (results.next()) {
                collectionList = mapRowToCollectionList(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return collectionList;
    }

    @Override
    public CollectionList fetchCollectionByName(String collectionName) {
        return null;
    }

    @Override
    public CollectionList addGameToCollection(CollectionList collectionList, int userId) {
        CollectionList addedGame = null;

        String sql = "INSERT INTO collection_list (user_id, collection_id, title, genre) " +
                "VALUES(?, ?, ?, ?) " +
                "RETURNING collection_id;";

            int newId = jdbcTemplate.queryForObject(sql, int.class, collectionList.getUser_id(), collectionList.getCollection_id(), collectionList.getTitle(), collectionList.getGenre());
        addedGame = fetchCollectionByType(newId, userId);

            return addedGame;
    }

    private CollectionList mapRowToCollectionList(SqlRowSet rs) {
        CollectionList collectionList = new CollectionList();
        collectionList.setCollection_list_id(rs.getInt("collection_list_id"));
        collectionList.setUser_id(rs.getInt("user_id"));
        collectionList.setCollection_id(rs.getInt("collection_id"));
        collectionList.setTitle(rs.getString("title"));
        collectionList.setGenre(rs.getString("genre"));
        return collectionList;
    }
    // addedGameToCollection = fetchCollectionByType(collectionList.getCollectionId(), userId);
}
