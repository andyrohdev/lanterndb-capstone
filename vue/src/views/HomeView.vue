<template>
  <div class="home-page">
    <header class="header-section">
      <div class="header-section-one">
        <h1>
          Welcome to LanternDb, Lorem ipsum dolor,
           sit amet consectetur adipisicing elit.
            Harum omnis error nemo ullam fuga itaque doloremque voluptates. 
            Id dignissimos ducimus, optio, nemo.
          </h1>
      </div>
    </header>
    <div>
      <h2>Featured Games: </h2>
    </div>
  </div>
</template>

<script>


export default {
    data() {
        return {
        // Removed unused data
        };
    },
    methods: {
    // Removed unused methods
    },
    components:{
      
    },  
    
};
</script>

<style scoped>
.home-page{
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}
.header-section{
  background-image: url(../assets/homepage2-transformed.webp);
  width: 100%;
  height: 100vh;
  background-size:cover;
  background-position: center;
  background-repeat: no-repeat;
  display: flex;
  align-items: center;
  justify-content: flex-start;
}
.header-section-one{
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  text-align: left;
  color: white;
  padding: 0 20px;
  width: 40%;
  margin-left: 10vw;
  margin-top: -20vh;

}
.header-section h2{
  margin: 0;
  font-size: 2rem;
  line-height: 1.5rem;
  max-width: 600px;
 

}
@media (max-width: 768px) {
  .header-section h1 {
    font-size: 1.5rem; /* Smaller font size for smaller screens */
  }
}

@media (max-width: 480px) {
  .header-section h1 {
    font-size: 1.2rem; /* Even smaller font size for very small screens */
  }
}
</style>