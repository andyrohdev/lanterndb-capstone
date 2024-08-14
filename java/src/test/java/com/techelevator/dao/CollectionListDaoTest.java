package com.techelevator.dao;

import com.techelevator.model.CollectionList;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.InvalidResultSetAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class CollectionListDaoTest {

    private JdbcCollectionListDao jdbcCollectionListDao;
    private JdbcTemplate jdbcTemplate;
    private JdbcUserDao jdbcUserDao;
    private int userId = 3;


    @BeforeEach
    void setUp() {
        jdbcTemplate = new JdbcTemplate() {
            @Override
            public int update(String sql, Object... args) {
                // Default behavior for update method
                return 0; // Override in specific tests
            }

            @Override
            public SqlRowSet queryForRowSet(String sql, Object... args) {
                // Determine which query is being executed
                if (sql.contains("collection_list_id")) {
                    return createMockRowSet();
                }
                return null;
            }

            @Override
            public <T> T queryForObject(String sql, Class<T> requiredType, Object... args) {

                if (sql.contains("RETURNING collection_id")) {
                    return (T) Integer.valueOf(1); // mock the new id being returned
                }
                return null;
            }
        };

        jdbcUserDao = new JdbcUserDao(jdbcTemplate);
        jdbcCollectionListDao = new JdbcCollectionListDao(jdbcTemplate, jdbcUserDao);
    }


    @Test
    public void fetchCollectionById_ShouldReturnCollectionList() {
        int collectionListId = 1;


        CollectionList result = jdbcCollectionListDao.fetchCollectionById(collectionListId);


        Assert.assertNotNull(result);
        Assert.assertEquals(1, result.getCollection_list_id());
        Assert.assertEquals(3, result.getUser_id());
        Assert.assertEquals(1, result.getCollection_id());
        Assert.assertEquals("Test Title", result.getTitle());
        Assert.assertEquals("Test Genre", result.getGenre());
    }

    @Test
    public void fetchCollectionByType_ShouldReturnCollectionList() {
        int collectionId = 1;

        List<CollectionList> result = jdbcCollectionListDao.fetchCollectionByType(collectionId, userId);

        Assert.assertEquals(1, result.size());

        CollectionList collectionList = result.get(0);
        Assert.assertEquals(1, collectionList.getCollection_list_id());
        Assert.assertEquals(userId, collectionList.getUser_id());
        Assert.assertEquals(collectionId, collectionList.getCollection_id());
        Assert.assertEquals("Test Title", collectionList.getTitle());
        Assert.assertEquals("Test Genre", collectionList.getGenre());
    }

    @Test
    public void addGameToCollection_ShouldAddGameAndReturnCollectionList() {
        CollectionList collectionListToAdd = new CollectionList();
        collectionListToAdd.setUser_id(3);
        collectionListToAdd.setCollection_id(1);
        collectionListToAdd.setTitle("New Game");
        collectionListToAdd.setGenre("New Genre");

        // implement fetchCollectionByType method returning a list with the new game
        jdbcCollectionListDao = new JdbcCollectionListDao(jdbcTemplate, jdbcUserDao) {
            @Override
            public List<CollectionList> fetchCollectionByType(int collectionId, int userId) {
                CollectionList addedCollectionList = new CollectionList();
                addedCollectionList.setCollection_list_id(1);
                addedCollectionList.setUser_id(3);
                addedCollectionList.setCollection_id(1);
                addedCollectionList.setTitle("New Game");
                addedCollectionList.setGenre("New Genre");
                return List.of(addedCollectionList);
            }
        };

        List<CollectionList> result = jdbcCollectionListDao.addGameToCollection(collectionListToAdd, 3);

        Assert.assertNotNull(result);
        Assert.assertEquals(1, result.size());

        CollectionList addedCollectionList = result.get(0);
        Assert.assertEquals(1, addedCollectionList.getCollection_list_id());
        Assert.assertEquals(3, addedCollectionList.getUser_id());
        Assert.assertEquals(1, addedCollectionList.getCollection_id());
        Assert.assertEquals("New Game", addedCollectionList.getTitle());
        Assert.assertEquals("New Genre", addedCollectionList.getGenre());
    }


    @Test
    public void updateCollection_ShouldUpdateAndReturnUpdatedCollection() {
        CollectionList collectionListToUpdate = new CollectionList();
        collectionListToUpdate.setCollection_list_id(1);
        collectionListToUpdate.setUser_id(3);
        collectionListToUpdate.setCollection_id(1);
        collectionListToUpdate.setTitle("Updated Title");
        collectionListToUpdate.setGenre("Updated Genre");

        //mock behavior for update
        jdbcTemplate = new JdbcTemplate() {
            @Override
            public int update(String sql, Object... args) {
                if (sql.contains("UPDATE collection_list")) {
                    //mock successful update
                    return 1;
                }
                return 0;
            }
        };

        jdbcCollectionListDao = new JdbcCollectionListDao(jdbcTemplate, jdbcUserDao) {
            @Override
            public CollectionList fetchCollectionById(int collectionListId) {
                //mock fetching updated collection
                CollectionList updatedCollectionList = new CollectionList();
                updatedCollectionList.setCollection_list_id(1);
                updatedCollectionList.setUser_id(3);
                updatedCollectionList.setCollection_id(1);
                updatedCollectionList.setTitle("Updated Title");
                updatedCollectionList.setGenre("Updated Genre");
                return updatedCollectionList;
            }
        };

        CollectionList result = jdbcCollectionListDao.updateCollection(collectionListToUpdate, 3);

        Assert.assertNotNull(result);
        Assert.assertEquals(1, result.getCollection_list_id());
        Assert.assertEquals(3, result.getUser_id());
        Assert.assertEquals(1, result.getCollection_id());
        Assert.assertEquals("Updated Title", result.getTitle());
        Assert.assertEquals("Updated Genre", result.getGenre());
    }

    @Test
    public void deleteGameFromACollection_ShouldReturnRowsAffected_WhenSuccess() {
        int collectionListId = 1;

        jdbcTemplate = new JdbcTemplate() {
            @Override
            public int update(String sql, Object... args) {
                return 1; //show that 1 row was affected
            }
        };

        jdbcCollectionListDao = new JdbcCollectionListDao(jdbcTemplate, jdbcUserDao);

        int rowsAffected = jdbcCollectionListDao.deleteGameFromACollection(collectionListId);

        Assert.assertEquals(1, rowsAffected);
    }



    private SqlRowSet createMockRowSet() {
        return new SqlRowSet() {
            private boolean hasData = true;

            @Override
            public boolean next() {
                if (hasData) {
                    hasData = false;
                    return true;
                }
                return false;
            }

            @Override
            public boolean previous() throws InvalidResultSetAccessException {
                return false;
            }

            @Override
            public boolean relative(int i) throws InvalidResultSetAccessException {
                return false;
            }

            @Override
            public boolean wasNull() throws InvalidResultSetAccessException {
                return false;
            }

            @Override
            public int getInt(String columnLabel) {
                switch (columnLabel) {
                    case "collection_list_id":
                        return 1;
                    case "user_id":
                        return 3;
                    case "collection_id":
                        return 1;
                    default:
                        return 0;
                }
            }

            @Override
            public long getLong(int i) throws InvalidResultSetAccessException {
                return 0;
            }

            @Override
            public long getLong(String s) throws InvalidResultSetAccessException {
                return 0;
            }

            @Override
            public String getNString(int i) throws InvalidResultSetAccessException {
                return null;
            }

            @Override
            public String getNString(String s) throws InvalidResultSetAccessException {
                return null;
            }

            @Override
            public Object getObject(int i) throws InvalidResultSetAccessException {
                return null;
            }

            @Override
            public Object getObject(String s) throws InvalidResultSetAccessException {
                return null;
            }

            @Override
            public Object getObject(int i, Map<String, Class<?>> map) throws InvalidResultSetAccessException {
                return null;
            }

            @Override
            public Object getObject(String s, Map<String, Class<?>> map) throws InvalidResultSetAccessException {
                return null;
            }

            @Override
            public <T> T getObject(int i, Class<T> aClass) throws InvalidResultSetAccessException {
                return null;
            }

            @Override
            public <T> T getObject(String s, Class<T> aClass) throws InvalidResultSetAccessException {
                return null;
            }

            @Override
            public short getShort(int i) throws InvalidResultSetAccessException {
                return 0;
            }

            @Override
            public short getShort(String s) throws InvalidResultSetAccessException {
                return 0;
            }

            @Override
            public String getString(int i) throws InvalidResultSetAccessException {
                return null;
            }

            @Override
            public String getString(String columnLabel) {
                switch (columnLabel) {
                    case "title":
                        return "Test Title";
                    case "genre":
                        return "Test Genre";
                    default:
                        return "";
                }
            }

            @Override
            public Time getTime(int i) throws InvalidResultSetAccessException {
                return null;
            }

            @Override
            public Time getTime(String s) throws InvalidResultSetAccessException {
                return null;
            }

            @Override
            public Time getTime(int i, Calendar calendar) throws InvalidResultSetAccessException {
                return null;
            }

            @Override
            public Time getTime(String s, Calendar calendar) throws InvalidResultSetAccessException {
                return null;
            }

            @Override
            public Timestamp getTimestamp(int i) throws InvalidResultSetAccessException {
                return null;
            }

            @Override
            public Timestamp getTimestamp(String s) throws InvalidResultSetAccessException {
                return null;
            }

            @Override
            public Timestamp getTimestamp(int i, Calendar calendar) throws InvalidResultSetAccessException {
                return null;
            }

            @Override
            public Timestamp getTimestamp(String s, Calendar calendar) throws InvalidResultSetAccessException {
                return null;
            }

            @Override
            public boolean absolute(int i) throws InvalidResultSetAccessException {
                return false;
            }

            @Override
            public void afterLast() throws InvalidResultSetAccessException {

            }

            @Override
            public void beforeFirst() throws InvalidResultSetAccessException {

            }

            @Override
            public boolean first() throws InvalidResultSetAccessException {
                return false;
            }

            @Override
            public int getRow() throws InvalidResultSetAccessException {
                return 0;
            }

            @Override
            public boolean isAfterLast() throws InvalidResultSetAccessException {
                return false;
            }

            @Override
            public boolean isBeforeFirst() throws InvalidResultSetAccessException {
                return false;
            }

            @Override
            public boolean isFirst() throws InvalidResultSetAccessException {
                return false;
            }

            @Override
            public boolean isLast() throws InvalidResultSetAccessException {
                return false;
            }

            @Override
            public boolean last() throws InvalidResultSetAccessException {
                return false;
            }

            @Override
            public SqlRowSetMetaData getMetaData() {
                return null;
            }

            @Override
            public int findColumn(String s) {
                return 0;
            }

            @Override
            public BigDecimal getBigDecimal(int i) throws InvalidResultSetAccessException {
                return null;
            }

            @Override
            public BigDecimal getBigDecimal(String s) throws InvalidResultSetAccessException {
                return null;
            }

            @Override
            public boolean getBoolean(int i) throws InvalidResultSetAccessException {
                return false;
            }

            @Override
            public boolean getBoolean(String s) throws InvalidResultSetAccessException {
                return false;
            }

            @Override
            public byte getByte(int i) throws InvalidResultSetAccessException {
                return 0;
            }

            @Override
            public byte getByte(String s) throws InvalidResultSetAccessException {
                return 0;
            }

            @Override
            public Date getDate(int i) throws InvalidResultSetAccessException {
                return null;
            }

            @Override
            public Date getDate(String s) throws InvalidResultSetAccessException {
                return null;
            }

            @Override
            public Date getDate(int i, Calendar calendar) throws InvalidResultSetAccessException {
                return null;
            }

            @Override
            public Date getDate(String s, Calendar calendar) throws InvalidResultSetAccessException {
                return null;
            }

            @Override
            public double getDouble(int i) throws InvalidResultSetAccessException {
                return 0;
            }

            @Override
            public double getDouble(String s) throws InvalidResultSetAccessException {
                return 0;
            }

            @Override
            public float getFloat(int i) throws InvalidResultSetAccessException {
                return 0;
            }

            @Override
            public float getFloat(String s) throws InvalidResultSetAccessException {
                return 0;
            }

            @Override
            public int getInt(int i) throws InvalidResultSetAccessException {
                return 0;
            }


        };
    }
}