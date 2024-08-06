package com.techelevator.dao;

import com.techelevator.model.CollectionList;

import java.security.Principal;
import java.util.List;

public interface CollectionListDao {

   List<CollectionList> fetchCollectionByType(int collection_id, int userId);

   CollectionList fetchCollectionByName(String collectionName);

   List<CollectionList> addGameToCollection(CollectionList collectionList, int userId);

   CollectionList updateCollection(CollectionList collectionList, int userId);

   CollectionList fetchCollectionById(int collection_list_id);

   int deleteGameFromACollection(int collection_list_id);


}
