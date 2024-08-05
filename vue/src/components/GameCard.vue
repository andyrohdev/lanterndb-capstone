<template>
  <div class="game-card">
    <div>
      <img
        :src="game.background_image || 'default-image.jpg'"
        alt="Game image"
        class="game-image"
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
            <li><a class="dropdown-item" href="#" @click="addToCollection('wishlist')">Add to Wishlist</a></li>
            <li><a class="dropdown-item" href="#" @click="addToCollection('playing')">Add to Playing</a></li>
            <li><a class="dropdown-item" href="#" @click="addToCollection('played')">Add to Played</a></li>
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
  },
  methods: {
    addToCollection(collectionType) {

      const title = this.game.name;
      const genre = this.game.genre;

      CollectionService.addToCollections(title, this.userId, genre, collectionType).then((response) => {
        console.log(`Game added to ${collectionType} collection`)

      })
      .catch((error) => {
        console.error(`Error adding game to ${collectionType} collection`, error)
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

  /* margin-left: 2%;
  margin-right: 2%;
  margin-bottom: 2%; */

  border: 1px solid red;
}

.game-image {
  width: 100%;
  /* Full width of the card */
  height: 300px;
  /* Adjust height as needed */
  object-fit: cover;
  /* Ensure image covers the area */

  border: 1px solid rgb(124, 170, 26);
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

  border: 1px solid rgb(106, 0, 255);
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
</style>
