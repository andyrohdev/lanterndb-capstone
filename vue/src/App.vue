<template>
  <div id="capstone-app">

    
    <div id="nav">
      <NavBar/>
    </div>
    <router-view />
    <div>
      <Footer></Footer>
    </div>
    
  </div>
</template>


<script>
import NavBar from './components/NavBar.vue';
import Footer from './components/Footer.vue'
export default{
  components: {
    NavBar,
    Footer
    
  }
}
</script>
