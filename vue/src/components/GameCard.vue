<template>
  <div class="card">
    <h2 class="game-title">{{ game.name }}</h2>
    <p class="game-genre">{{ game.genre }}</p>
    <!-- Add more game details here if needed -->
  </div>
</template>

<script>
export default {
  props: {
    game: Object,
  },
};
</script>

<style>
.card {
  width: 200px; /* Fixed width */
  height: 300px; /* Proportionally larger height */
  border: 1px solid #ccc; /* Add a light grey border */
  border-radius: 8px; /* Rounded corners */
  padding: 16px; /* Add some padding */
  margin: 8px; /* Add some margin */
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); /* Add a subtle shadow */
  display: flex; /* Flexbox for vertical alignment */
  flex-direction: column; /* Stack children vertically */
  justify-content: space-between; /* Space out children */
  align-items: center; /* Center children horizontally */
  background-color: #fff; /* White background for the card */
}

.game-title {
  font-size: 1.2em; /* Larger font size for the title */
  margin-bottom: 8px; /* Space between title and genre */
  text-align: center; /* Center align the text */
}

.game-genre {
  color: #666; /* Lighter color for the genre */
  text-align: center; /* Center align the text */
}
</style>
