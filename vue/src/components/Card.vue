<template>
  <div class="dashboard-card">
    <h3>{{ title }}</h3>
    <ul v-if="items.length > 0">
      <li v-for="item in items" :key="item.collectionListId">
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
    <div v-else class="no-games-message">
      No games found in collection.
    </div>
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
  background-color: #1e1e1e;
  border: 1px solid #333;
  border-radius: 8px;
  padding: 16px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  margin: 16px;
  width: 25%; /* Adjusted width */
  height: 350px; /* Adjusted height */
  display: flex;
  flex-direction: column;
  overflow: visible; /* Allow dropdown to show outside the card */
  position: relative; /* Establishes a positioning context for dropdown */
  z-index: 1; /* Ensure card is above normal content but below active dropdown */
}

.dashboard-card h3 {
  margin-top: 0;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  flex-shrink: 0; /* Prevent the title from shrinking */
}

.dashboard-card ul {
  list-style-type: none;
  padding-left: 0;
  overflow-y: auto; /* Enables scrolling within the list */
  flex-grow: 1; /* Allows the list to take up the rest of the space in the card */
  position: relative;
}

/* Custom scrollbar for card ul */
.dashboard-card ul::-webkit-scrollbar {
  width: 8px;
}

.dashboard-card ul::-webkit-scrollbar-track {
  background: #2e2e2e;
  border-radius: 6px;
}

.dashboard-card ul::-webkit-scrollbar-thumb {
  background-color: #888;
  border-radius: 6px;
  border: 2px solid #2e2e2e;
}

.dashboard-card ul::-webkit-scrollbar-thumb:hover {
  background-color: #555;
}

.dashboard-card .dropdown {
  position: static; /* Reset position to allow dropdown to escape overflow */
}

.dashboard-card .dropdown-menu {
  position: absolute; /* Position absolute relative to nearest positioned ancestor */
  z-index: 1000; /* High z-index to ensure dropdown is on top of other elements */
  width: 100%; /* Match width of button */
  box-shadow: 0 8px 16px rgba(0,0,0,0.15); /* Add some shadow for better visibility */
}

.dashboard-card li {
  padding: 8px 0;
  border-bottom: 1px solid #dcdcdc;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.dashboard-card li:last-child {
  border-bottom: none;
}

.no-games-message {
  text-align: center;
  color: #888;
  font-size: 1rem;
  margin-top: 20px;
}

@media (max-width: 900px) {
  .dashboard-card {
    width: 90%; /* Full width on smaller screens */
    margin: 16px 0; /* Adjusted margin for vertical stacking */
  }
}
</style>
