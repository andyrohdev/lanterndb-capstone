package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.CollectionList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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
    public List <CollectionList> fetchCollectionByType(int collection_id, int userId) {
        List<CollectionList> collectionLists = new ArrayList<>();

        String sql = "SELECT collection_list_id, user_id, collection_id, title, genre " +
                "FROM collection_list " +
                "WHERE collection_id = ? AND user_id = ?";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, collection_id, userId);
            while (results.next()) {
                collectionLists.add( mapRowToCollectionList(results));
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return collectionLists;
    }

    @Override
    public CollectionList fetchCollectionByName(String collectionName) {
        return null;
    }

    @Override
    public List<CollectionList> addGameToCollection(CollectionList collectionList, int userId) {
        List <CollectionList> addedGame = null;

        String sql = "INSERT INTO collection_list (user_id, collection_id, title, genre) " +
                "VALUES(?, ?, ?, ?) " +
                "RETURNING collection_id;";

            int newId = jdbcTemplate.queryForObject(sql, int.class, collectionList.getUser_id(), collectionList.getCollection_id(), collectionList.getTitle(), collectionList.getGenre());
        addedGame = fetchCollectionByType(newId, userId);

            return addedGame;
    }
    @Override
    public CollectionList fetchCollectionById(int collection_list_id) {
        CollectionList collectionList = null;

        String sql = "SELECT collection_list_id, user_id, collection_id, title, genre " +
                "FROM collection_list " +
                "WHERE collection_list_id = ?";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, collection_list_id);
            if (results.next()) {
                collectionList = mapRowToCollectionList(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return collectionList;
    }

    @Override
    public int deleteGameFromACollection(int collection_list_id) {
        int rows = 0;
        String sql = "DELETE FROM collection_list WHERE collection_list_id = ?;";
        try{
            rows = jdbcTemplate.update(sql, collection_list_id);
        }
        catch (CannotGetJdbcConnectionException e) {
        throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
        throw new DaoException("Data integrity violation", e);
        }
        return rows;
    }


    @Override
    public CollectionList updateCollection(CollectionList collectionList, int userId) {
        CollectionList updatedCollection = null;
        String sql = "UPDATE collection_list " +
                    "SET user_id = ?, collection_id = ?, title = ?, genre = ? " +
                     "WHERE collection_list_id = ?;";

        try{
            int rowsCollection = jdbcTemplate.update(sql,collectionList.getUser_id(), collectionList.getCollection_id(), collectionList.getTitle(), collectionList.getGenre(), collectionList.getCollection_list_id());
            if(rowsCollection == 0){
                throw new DaoException("Zero rows affected, expected at least one");
            }
            else {
                updatedCollection = fetchCollectionById(collectionList.getCollection_list_id());
            }
        }
        catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return updatedCollection;
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
