<template>
  <div class="game-card">
    <router-link :to="{ name: 'game-details', params: { gameId: game.id } }" class="game-link">
      <img
        :src="game.background_image || 'https://via.placeholder.com/300x400.png?text=No+Image+Available'"
        alt="Game image"
        class="game-image"
        loading="lazy"
      />
      <div class="card-content">
        <div class="text-content">
          <h2 class="game-title">{{ game.name || "Unknown Title" }}</h2>
          <p class="game-rating">Rating: {{ game.rating || "N/A" }}</p>
        </div>
      </div>
    </router-link>
    <!-- Show dropdown only if user is logged in -->
    <div v-if="loggedIn" class="dropdown" @click.stop>
      <button
        class="btn btn-secondary dropdown-toggle"
        type="button"
        data-bs-toggle="dropdown"
        aria-expanded="false"
      >
      </button>
      <ul class="dropdown-menu">
        <li><a class="dropdown-item" href="#" @click="addToCollection(1)">Add to Wishlist</a></li>
        <li><a class="dropdown-item" href="#" @click="addToCollection(2)">Add to Playing</a></li>
        <li><a class="dropdown-item" href="#" @click="addToCollection(3)">Add to Played</a></li>
      </ul>
    </div>
  </div>
</template>

<script>
import CollectionService from '../services/CollectionService';

export default {
  props: {
    game: Object,
    loggedIn: Boolean, // Add prop to check if user is logged in
  },
  methods: {
    addToCollection(collection_id) {
      console.log("Game object:", this.game);

      // Extract the first genre name from the genres array
      const genre = this.game.genres && this.game.genres.length > 0
        ? this.game.genres[0].name
        : 'Unknown Genre';

      const gameData = {
        title: this.game.name,
        genre: genre,
        collection_id,
      };

      console.log("Sending data to backend:", gameData);

      CollectionService.addToCollections(gameData)
        .then((response) => {
          console.log(`Game added to ${collection_id} collection`, response);
        })
        .catch((error) => {
          console.error(`Error adding game to ${collection_id} collection`, error);
        });
    }
  }
};
</script>

<style scoped>
.game-card {
  width: 200px;
  border: 1px solid #ccc;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  display: flex;
  flex-direction: column;
  overflow: hidden;
  position: relative;
  margin-left: 2%;
  margin-right: 2%;
  margin-bottom: 2%;
}

.game-link {
  text-decoration: none;
  color: inherit;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
}

.game-image {
  width: 100%;
  height: 300px;
  object-fit: cover;
}

.text-content {
  flex: 1;
}

.card-content {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  background-color: rgba(240, 248, 255, 0);
  justify-content: space-around;
}

.game-title {
  font-size: 1.2em;
  margin: 0;
}

.game-rating {
  color: #666;
  margin-top: 8px;
}

.dropdown {
  position: absolute;
  bottom: 16px;
  right: 16px;
}

@media (max-width: 600px) {
  .game-card {
    width: 20%;
    height: auto;
    margin: 1%;
  }
}
</style>
