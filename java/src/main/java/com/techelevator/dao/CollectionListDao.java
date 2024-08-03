package com.techelevator.dao;

import com.techelevator.model.CollectionList;

import java.security.Principal;

public interface CollectionListDao {

    public CollectionList fetchCollectionByType(int collectionType, int userId);

    public CollectionList fetchCollectionByName(String collectionName);
}
