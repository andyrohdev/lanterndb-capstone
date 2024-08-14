<template>
  <router-link
    v-if="gameId"
    :to="{ name: 'game-details', params: { gameId: gameId } }"
    class="collection-game-card-link"
    @click.native.stop="navigateToDetails"
    draggable="true"
    @dragstart="onDragStart"
    @dragend="onDragEnd"
  >
    <div class="collection-game-card">
      <img :src="gameImage" alt="Game Image" class="game-image" draggable="false" />
      <div class="game-title-overlay">
        <p class="game-title">{{ game.title }}</p>
        <div class="dropdown" @click.stop>
          <button
            class="three-dots-btn"
            type="button"
            data-bs-toggle="dropdown"
            aria-expanded="false"
          >
            &#x22EE;
          </button>
          <ul class="dropdown-menu">
            <li v-if="collectionTitle !== 'Wishlist'">
              <a class="dropdown-item" href="#" @click="moveToCollection(1)">Move to Wishlist</a>
            </li>
            <li v-if="collectionTitle !== 'Playing'">
              <a class="dropdown-item" href="#" @click="moveToCollection(2)">Move to Playing</a>
            </li>
            <li v-if="collectionTitle !== 'Played'">
              <a class="dropdown-item" href="#" @click="moveToCollection(3)">Move to Played</a>
            </li>
            <li>
              <a class="dropdown-item" href="#" @click="deleteFromCollection">Delete</a>
            </li>
          </ul>
        </div>
      </div>
    </div>
  </router-link>
  <div v-else class="collection-game-card">
    <!-- Fallback if gameId is not available -->
    <p>Loading...</p>
  </div>
</template>

<script>
import CollectionService from '../services/CollectionService';
import GameService from '../services/GameService';

export default {
  props: {
    game: {
      type: Object,
      required: true
    },
    collectionTitle: {
      type: String,
      required: true
    }
  },
  data() {
    return {
      gameImage: 'https://via.placeholder.com/300x400.png?text=No+Image+Available', // Default placeholder image
      gameId: null,
    };
  },
  created() {
    this.fetchGameImageAndId();
  },
  methods: {
    fetchGameImageAndId() {
      GameService.searchGames(1, this.game.title)
        .then(response => {
          const results = response.data.results;
          if (results && results.length > 0) {
            this.gameImage = results[0].background_image;
            this.gameId = results[0].id;
          } else {
            console.log('No image found for the game:', this.game.title);
          }
        })
        .catch(error => {
          console.error('Error fetching game image and ID:', error);
        });
    },
    moveToCollection(collection_id) {
      const gameData = {
        title: this.game.title,
        genre: this.game.genre || 'Unknown Genre',
        collection_id,
        collection_list_id: this.game.collection_list_id
      };

      CollectionService.updateToCollections(gameData)
        .then((response) => {
          console.log(`Game moved to collection ${collection_id}`, response);
          this.$emit('updateCollections'); // Emit event to refresh collections
        })
        .catch((error) => {
          console.error(`Error moving game to collection ${collection_id}`, error);
        });
    },
    deleteFromCollection() {
      const gameData = {
        title: this.game.title,
        genre: this.game.genre || 'Unknown Genre',
        collection_id: this.game.collection_id,
        collection_list_id: this.game.collection_list_id
      };

      CollectionService.deleteToCollections(gameData)
        .then((response) => {
          console.log("Game deleted from collection", response);
          this.$emit('updateCollections'); // Emit event to refresh collections
        })
        .catch((error) => {
          console.error("Error deleting game from collection", error);
        });
    },
    navigateToDetails(event) {
      if (event.target.closest('.dropdown')) {
        event.preventDefault();
      }
    },
    onDragStart(event) {
      event.dataTransfer.setData('game', JSON.stringify(this.game));
      event.dataTransfer.effectAllowed = 'move';
      event.target.classList.add('hidden-during-drag');
      this.$emit('drag-start', this.game);
      
    },
    onDragEnd(event) {
      event.target.classList.remove('hidden-during-drag');
      this.$emit('drag-end');
    },
    
    
  }
};
</script>

<style scoped>

.collection-game-card.dragging {
  opacity: 0.5;
}

.collection-game-card-link {
  text-decoration: none;
  color: inherit;
}

.collection-game-card {
  position: relative;
  width: 100%;
  height: 250px;
  border-radius: 8px;
  overflow: hidden;
  background-color: #1e1e1e;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  cursor: grab; /* Change cursor to indicate the card can be dragged */
}

.collection-game-card:active {
  cursor: grabbing; /* Change cursor when dragging */
}

.game-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
  pointer-events: none; /* Ensure the image does not interfere with drag events */
}

.game-title-overlay {
  position: absolute;
  bottom: 0;
  left: 0;
  width: 100%;
  background-color: rgba(0, 0, 0, 0.6);
  padding: 10px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.game-title {
  color: #fff;
  font-size: 1rem;
  margin: 0;
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
}

.three-dots-btn {
  background: none;
  border: none;
  color: #fff;
  font-size: 1.5rem;
  cursor: pointer;
}

.dropdown-menu {
  right: 0;
  left: auto;
  top: 30px;
  background-color: #2e2e2e;
}

.dragging {
  opacity: 0.5; /* Example: Make the dragged card semi-transparent */
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.5); /* Example: Add a shadow to indicate it's being dragged */
}

.highlight-drop-zone {
  border: 2px dashed #4a90e2; /* Example: Highlight drop zones with a dashed border */
  background-color: rgba(74, 144, 226, 0.1); /* Example: Slight background color change */
}



</style>
