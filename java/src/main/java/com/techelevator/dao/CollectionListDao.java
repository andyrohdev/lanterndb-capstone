package com.techelevator.dao;

import com.techelevator.model.CollectionList;

import java.security.Principal;

public interface CollectionListDao {

   CollectionList fetchCollectionByType(int collectionType, int userId);

   CollectionList fetchCollectionByName(String collectionName);

   CollectionList addGameToCollection(CollectionList collectionList, int userId);


}
