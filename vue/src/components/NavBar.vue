<template>
    <nav class="navbar">
      <div class="navbar-left">
        <img/>
        <img v-if="showLogo" src="../assets/logoPlaceholder.png" alt="Logo" class="logo"/>
        <span class="website-name" v-if="showSiteName">LanternDB</span>
      </div>
      <div class="navbar-right">
        <router-link v-if="showHomeLink" to="/" class="navbar-link">Home</router-link>
        <router-link v-if="showProfileLink" to="/profile" class="navbar-link"><i class="bi bi-person-circle"></i></router-link>
      </div>
    </nav>
</template>

<script>
import 'bootstrap-icons/font/bootstrap-icons.css'

</script>

<style>

</style>