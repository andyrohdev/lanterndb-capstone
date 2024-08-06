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
            public SqlRowSet queryForRowSet(String sql, Object... args) {
                return createMockRowSet();
            }
        };

        jdbcUserDao = new JdbcUserDao(jdbcTemplate);

        jdbcCollectionListDao = new JdbcCollectionListDao(jdbcTemplate, jdbcUserDao);
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