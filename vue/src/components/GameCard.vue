<template>
  <div class="game-card">
    <div>
      <img
        :src="game.background_image || 'default-image.jpg'"
        alt="Game image"
        class="game-image"
        loading="lazy"
      />
      <div class="card-content">
        <div class="text-content">
          <h2 class="game-title">{{ game.name || "Unknown Title" }}</h2>
          <p class="game-rating">Rating: {{ game.rating || "N/A" }}</p>
        </div>
        <div class="dropdown">
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
    </div>
  </div>
</template>

<script>
import CollectionService from '../services/CollectionService';

export default {
  props: {
    game: Object,
    userId: String, // Ensure userId is passed as a prop
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
  /* Fixed width */
  border: 1px solid #ccc;
  /* Add a light grey border */
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  /* Add a subtle shadow */
  display: flex;
  /* Flexbox for vertical alignment */
  flex-direction: column;
  /* Stack children vertically */
  overflow: hidden;
  /* Ensure content doesn't overflow */
  position: relative;
  margin-left: 2%;
  margin-right: 2%;
  margin-bottom: 2%;
}

.game-image {
  width: 100%;
  /* Full width of the card */
  height: 300px;
  /* Adjust height as needed */
  object-fit: cover;
  /* Ensure image covers the area */
}

.text-content {
  flex: 1;
}

.card-content {
  display: flex;
  flex-direction: column;
  /* Stack text vertically */
  align-items: flex-start;
  /* Align text to the left */
  background-color: rgba(240, 248, 255, 0);
  justify-content: space-around;
}

.game-title {
  font-size: 1.2em;
  /* Larger font size for the title */
  margin: 0;
  /* Remove default margin */
}

.game-rating {
  color: #666;
  /* Lighter color for the rating */
  margin-top: 8px;
  /* Space above the rating */
}

.dropdown {
  position: absolute;
  /* Absolute positioning to place button at the bottom right */
  bottom: 16px;
  /* Space from the bottom */
  right: 16px;
  /* Space from the right */
}

@media (max-width: 600px) {
  .game-card {
    width: 20%; /* Adjust width to 48% to allow for margins */
    height: auto;
    margin: 1%;
  }

}
</style>

