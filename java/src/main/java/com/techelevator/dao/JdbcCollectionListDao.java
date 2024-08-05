package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.CollectionList;
import com.techelevator.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.security.Principal;
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

//        String userName = principal.getName();
//        User user = jdbcUserDao.getUserByUsername(userName);
//        userId = user.getId();

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
//        CollectionList addedGameToCollection = null;

        String sql = "INSERT INTO collection_list (user_id, collection_id, title, genre)\n" +
                "VALUES(?, ?, ?, ?)" +
                "RETURNING collection_list_id;";

//            int newCollectionType = jdbcTemplate.queryForObject(sql,int.class, collectionList.getUserId(), collectionList.getCollectionId(), collectionList.getTitle(), collectionList.getGenre());
//            collectionList.setCollectionListId(newCollectionType);
            Integer newId = jdbcTemplate.queryForObject(sql, new Object[]{
                    collectionList.getUserId(),
                    collectionList.getCollectionId(),
                    collectionList.getTitle(),
                    collectionList.getGenre()
            }, Integer.class);

            if (newId != null) {
                collectionList.setCollectionId(newId);// Assuming you have a setter for ID in your model
                collectionList.setCollectionListId(newId);
            }
            return collectionList;


           // addedGameToCollection = fetchCollectionByType(collectionList.getCollectionId(), collectionList.getUserId());
        //return addedGameToCollection;
    }

    private CollectionList mapRowToCollectionList(SqlRowSet rs) {
        CollectionList collectionList = new CollectionList();
        collectionList.setCollectionListId(rs.getInt("collection_list_id"));
        collectionList.setUserId(rs.getInt("user_id"));
        collectionList.setCollectionId(rs.getInt("collection_id"));
        collectionList.setTitle(rs.getString("title"));
        collectionList.setGenre(rs.getString("genre"));
        return collectionList;
    }
    // addedGameToCollection = fetchCollectionByType(collectionList.getCollectionId(), userId);
}
