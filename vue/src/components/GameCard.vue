<template>
  <div class="card" :style="{ backgroundImage: 'url(' + game.background_image + ')' }">
    <div class="card-content">
      <h2 class="game-title">{{ game.name }}</h2>
      <p class="game-genre">Rating: {{ game.rating }}</p>
    </div>
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
  /* Card Sizing */
  width: 200px;
  height: 300px;

  /* Card Borders/Margin/Padding */
  border: 1px solid #ccc;
  border-radius: 8px;
  padding: 16px;
  margin: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  
  /* Alignment */
  display: flex;
  /* Alignment of the Card Content */
  flex-direction: column;
  justify-content: flex-end;

  /* Background */
  background-size: cover;
  background-position: center;

  /* This is Text Color */
  color: white;
  text-align: center;
  font-family: Arial, Helvetica, sans-serif;
}

.card-content {
  background: rgba(0, 0, 0, 0.5); /* Semi-transparent background for readability */
  padding: 10px;
  border-radius: 8px;
}
</style>
