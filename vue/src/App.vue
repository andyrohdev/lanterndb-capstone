<template>
  <div id="capstone-app">
    <div id="nav">
      <NavBar/>
    </div>
    <div id="main-content">
      <router-view />
    </div>
    <div class="footer-bottom">
      <Footer></Footer>
    </div>
    
  </div>
</template>

<script>
import NavBar from './components/NavBar.vue';
import Footer from './components/Footer.vue';

export default {
  components: {
    NavBar,
    Footer,
  },
};
</script>

<style scoped>
#capstone-app {
  background-color: #121212;
}




</style>
