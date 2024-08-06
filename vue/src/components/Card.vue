<template>
  <div class="dashboard-card">
    <h3>{{ title }}</h3>
    <ul>
      <li v-for="item in items" :key="item.collectionListId">
        {{ item.collection_list_id }}
        {{ item.title }}
        <div class="dropdown">
          <button
            class="btn btn-secondary dropdown-toggle"
            type="button"
            data-bs-toggle="dropdown"
            aria-expanded="false"
          ></button>
          <ul class="dropdown-menu">
            <li v-if="title === 'Wishlist'">
              <a class="dropdown-item" href="#" @click="moveToCollection(2, item)">Move to Playing</a>
            </li>
            <li v-if="title === 'Wishlist'">
              <a class="dropdown-item" href="#" @click="moveToCollection(3, item)">Move to Played</a>
            </li>
            <li v-if="title === 'Wishlist'">
              <a class="dropdown-item" href="#" @click="deleteFromCollection(1, item)">Delete</a>
            </li>

            <li v-if="title === 'Playing'">
              <a class="dropdown-item" href="#" @click="moveToCollection(1, item)">Move to Wishlist</a>
            </li>
            <li v-if="title === 'Playing'">
              <a class="dropdown-item" href="#" @click="moveToCollection(3, item)">Move to Played</a>
            </li>
            <li v-if="title === 'Playing'">
              <a class="dropdown-item" href="#" @click="deleteFromCollection(2, item)">Delete</a>
            </li>

            <li v-if="title === 'Played'">
              <a class="dropdown-item" href="#" @click="moveToCollection(1, item)">Move to Wishlist</a>
            </li>
            <li v-if="title === 'Played'">
              <a class="dropdown-item" href="#" @click="moveToCollection(2, item)">Move to Playing</a>
            </li>
            <li v-if="title === 'Played'">
              <a class="dropdown-item" href="#" @click="deleteFromCollection(3, item)">Delete</a>
            </li>
          </ul>
        </div>
      </li>
    </ul>
  </div>
</template>

<script>
import CollectionService from '../services/CollectionService';

export default {
  props: {
    title: {
      type: String,
      required: true
    },
    items: {
      type: Array,
      required: true
    }
  },
  methods: {
    moveToCollection(collection_id, item) {
      console.log("Game object:", item);

      const genre = item.genre ? item.genre : 'Unknown Genre';
      const gameData = {
        title: item.title,
        genre: genre,
        collection_id,
        collection_list_id: item.collection_list_id
      };

      console.log("Sending data to backend:", gameData);

      CollectionService.updateToCollections(gameData)
        .then((response) => {
          console.log(`Game added to ${collection_id} collection`, response);
          item.collection_id = collection_id; // Update the collection_id locally
        })
        .catch((error) => {
          console.error(`Error adding game to ${collection_id} collection`, error);
        });
    },
    deleteFromCollection(collection_id, item) {
      const genre = item.genre ? item.genre : 'Unknown Genre';
      const gameData = {
        title: item.title,
        genre: genre,
        collection_id,
        collection_list_id: item.collection_list_id };

      CollectionService.deleteToCollections(gameData)
        .then((response) => {
          console.log("Game deleted from collection", response);
          item.collection_id = null; // Set the collection_id to null or remove item locally
        })
        .catch((error) => {
          console.error("Error deleting game from collection", error);
        });
    }
  }
};
</script>

<style scoped>
.dashboard-card {
  background-color: #ffffff;
  border: 1px solid #dcdcdc;
  border-radius: 8px;
  padding: 16px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  margin: 16px;
}
.dashboard-card h3 {
  margin-top: 0;
}
.dashboard-card ul {
  list-style-type: none;
  padding-left: 0;
}
.dashboard-card li {
  padding: 8px 0;
  border-bottom: 1px solid #dcdcdc;
}
.dashboard-card li:last-child {
  border-bottom: none;
}
</style>
