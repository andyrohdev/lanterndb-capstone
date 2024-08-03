<template>
    <div class="dashboard-container">
      <header class="dashboard-header">
       <div class = "logo">Game Logo</div>
       <div class="navigation">
        <div >
            <router-link to = "/home">Home</router-link>
        </div>
        <div>
            <router-link to = "/profile">Profile</router-link>
        </div>
        <div>
            <router-link to = "/settings">Settings</router-link>
        </div>
        
        <div>
            <router-link to = "/store">Store</router-link>
        </div>
        <div>
            <router-link to = "/wishlist">wishlist</router-link>
        </div>
        <div>
            <router-link to = "/playing">playing</router-link>
        </div>
        <router-link to = "/played">played</router-link>
       </div>
       
       <div class="user-info"></div>
       
      </header>
  
      <main class="dashboard-main">
       
      </main>
     
      <footer class="dashboard-footer">
       
      </footer>
    </div>
  </template>
  