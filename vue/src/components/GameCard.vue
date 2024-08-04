<template>
  <div class="card">
    <img :src="game.background_image || 'default-image.jpg'" alt="Game image" class="game-image" />
    <div class="card-content">
      <div class="text-content">
        <h2 class="game-title">{{ game.name || 'Unknown Title' }}</h2>
        <p class="game-rating">Rating: {{ game.rating || 'N/A' }}</p>
      </div>
      <button id="plus" class="btn btn-primary plus-button">
        <i class="bi bi-plus"></i>
      </button>
    </div>
  </div>
</template>


<script>

export default {
  props: {
    game: Object,
    enableAdd: {
      type: Boolean,
      default: false
    }
  },
};
</script>

<style>
.card {
  width: 200px; /* Fixed width */
  border: 1px solid #ccc; /* Add a light grey border */
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); /* Add a subtle shadow */
  display: flex; /* Flexbox for vertical alignment */
  flex-direction: column; /* Stack children vertically */
  overflow: hidden; /* Ensure content doesn't overflow */
  position: relative;

}

.game-image {
  width: 100%; /* Full width of the card */
  height: 300px; /* Adjust height as needed */
  object-fit: cover; /* Ensure image covers the area */
}

.text-content {
  flex: 1;
}

.card-content {
  padding: 16px; /* Add padding inside the content area */
  display: flex;
  flex-direction: column; /* Stack text vertically */
  align-items: flex-start; /* Align text to the left */
  background-color: rgba(240, 248, 255, 0);
  justify-content: space-around;
}

.game-title {
  font-size: 1.2em; /* Larger font size for the title */
  margin: 0; /* Remove default margin */
}

.game-rating {
  color: #666; /* Lighter color for the rating */
  margin-top: 8px; /* Space above the rating */
}

#plus {
  position: absolute; /* Absolute positioning to place button at the bottom right */
  bottom: 16px; /* Space from the bottom */
  right: 16px; /* Space from the right */
}
</style>
