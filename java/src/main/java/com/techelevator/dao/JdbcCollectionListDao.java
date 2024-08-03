package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.CollectionList;
import com.techelevator.model.User;
import org.springframework.beans.factory.annotation.Autowired;
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

    private CollectionList mapRowToCollectionList(SqlRowSet rs) {
        CollectionList collectionList = new CollectionList();
        collectionList.setCollectionListId(rs.getInt("collection_list_id"));
        collectionList.setUserId(rs.getInt("user_id"));
        collectionList.setCollectionId(rs.getInt("collection_id"));
        collectionList.setTitle(rs.getString("title"));
        collectionList.setGenre(rs.getString("genre"));
        return collectionList;
    }
}
