<template>
  <div id="capstone-app">

    
    <div id="nav">
      <NavBar/>
      
    </div>
    <router-view />
    <router-link v-bind:to="{ name: 'logout' }" v-if="$store.state.token != ''">Logout</router-link>
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
