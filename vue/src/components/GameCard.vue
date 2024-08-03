<template>
  <div class="card">
    <h2 class="game-title"> {{ game.title }} </h2>
    <p class="game-genre"> {{ game.genre }} </p>
    
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
    }

}
</script>

<style>

</style>