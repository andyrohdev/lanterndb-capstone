<template>
  <div id="capstone-app">

    
    <div id="nav">
      <NavBar/>
      <router-link v-bind:to="{ name: 'logout' }" v-if="$store.state.token != ''">Logout</router-link>

      
    </div>
    <router-view />
  </div>
</template>


<script>
import NavBar from './components/NavBar.vue';
export default{
  components: {
    NavBar
    
  }
}
</script>
