<template>
    <footer class="custom-footer">
    <ul class="nav-list">
      <li class="nav-item"><a href="#" class="nav-link">Home</a></li>
      <li class="nav-item"><a href="#" class="nav-link">Features</a></li>
      <li class="nav-item"><a href="#" class="nav-link">Pricing</a></li>
      <li class="nav-item"><a href="#" class="nav-link">FAQs</a></li>
      <li class="nav-item"><a href="#" class="nav-link">About</a></li>
    </ul>
    <p class="footer-text">© 2024 Company, Inc</p>
  </footer>
</template>

<script>
    export default{
        name: 'Footer',
    };
</script>
    
<style scoped>
.custom-footer{
padding-top: 1rem;
padding-bottom: 1.5rem;
margin-top: 1.5rem;
text-align: center;
background-color: rgb(99, 88, 78);;
z-index: 100;
position: relative;
}
.nav-list{
    list-style-type: none;
    padding: 0;
    margin: 0;
    display: flex;
    justify-content: center;
    border-bottom: 1px solid rgba(201, 95, 8, 0.562);
    padding-bottom: 1rem;
    margin-bottom: 1rem;
}
.nav-link{
    text-decoration: none;
    color: rgb(255, 255, 255);
    padding-left: .5rem;
    padding-right: .5rem;
    font-size: 1rem;
}
.nav-link:hover{
    color: orange;
    text-decoration: underline;
    
}
.footer-text{
    color: white;
}


</style>