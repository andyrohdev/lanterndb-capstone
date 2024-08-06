<template>
  <div class="home">
    <h2>This is the home page {{userRole}}</h2>
  
  </div>
</template>

<script>

export default {
  computed: {
    userRole() {
      console.log(this.$store.state.user)
      return this.$store.state.user.authorities[0].name;
    }
  }
};
</script>
