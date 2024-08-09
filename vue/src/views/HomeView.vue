<template>
  <div class="home-page">
    <!-- Header Section -->
    <header class="header">
      <div class="brand">
        <img src="path_to_logo" alt="Brand Logo" class="logo" />
        <span class="brand-name">Brand Name</span>
      </div>
      <div class="nav-links">
        <button class="btn">Browse</button>
        <button class="btn">Profile</button>
        <button class="btn">Login</button>
      </div>
    </header>

    <!-- Main Navigation Bar -->
    

    <!-- Hero Section -->
    <section id="hero-1946">
      <div class="cs-container">
        <div class="cs-content">
          <h2 class="cs-title">LanternDB</h2>
          <div class="cs-flex">
            <p class="cs-text">
              The Baltimore Ravens were established in 1996 after Art Modell, then owner of the Cleveland Browns, announced plans in 1995 to relocate the franchise from Cleveland to Baltimore.[10] As part of a settlement between the league and the city of Cleveland, Modell was required to leave the Browns' history, team colors, and records in Cleveland for a replacement team and replacement personnel that would resume play in 1999. In return, he was allowed to take his own personnel and team to Baltimore, where such personnel would form an expansion team. The team is owned by Steve Bisciotti and was valued at $4.63 billion in 2023, making them the 28th-most valuable sports franchise globally.[11]
            </p>
            <a href="#" class="cs-button-solid">Read More</a>
          </div>
        </div>
        
      </div>
    </section>

    <!-- Footer Section -->
    <footer class="footer">
      <!-- Additional footer content here -->
    </footer>
  </div>
</template>

<script>
export default {
  data() {
    return {
      // Removed game-related data
    };
  },
  methods: {
    // Removed game-related methods
  },
};
</script>

<style scoped>
.home-page {
  display: flex;
  flex-direction: column;
  height: 100vh;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px 20px;
  background-color: #333;
  color: white;
}

.brand {
  display: flex;
  align-items: center;
}

.logo {
  height: 50px;
  margin-right: 10px;
}

.nav-links .btn {
  margin-left: 10px;
  background-color: #555;
  color: white;
  border: none;
  padding: 10px 15px;
  cursor: pointer;
  border-radius: 5px;
}

.main-nav {
  background-color: #444;
  text-align: center;
  padding: 10px;
}

.main-nav ul {
  list-style: none;
  margin: 0;
  padding: 0;
  display: inline-block;
}

.main-nav ul li {
  display: inline;
  margin: 0 15px;
}

.main-nav ul li a {
  color: white;
  text-decoration: none;
  font-size: 18px;
}



@media (max-width: 768px) {
  .header,
  .main-nav,
  .footer {
    flex-direction: column;
    text-align: center;
  }

  .nav-links .btn {
    margin: 5px 0;
  }
}

/* Hero Section CSS */
#hero-1946 {
  padding: var(--sectionPadding);
  /* 160px - 200px */
  padding-top: clamp(10rem, 20vw, 12.5rem);
  overflow: hidden;
  position: relative;
  z-index: 1;
}
#hero-1946 .cs-container {
  width: 100%;
  max-width: 80rem;
  margin: auto;
  display: flex;
  flex-direction: column;
  align-items: center;
  position: relative;
  z-index: 1;
}
#hero-1946 .cs-content {
  /* set text align to left if content needs to be left aligned */
  text-align: center;
  width: 100%;
  /* 40px - 52px */
  margin-bottom: clamp(2.5rem, 5vw, 3.25rem);
  display: flex;
  flex-direction: column;
  /* centers content horizontally, set to flex-start to left align */
  align-items: center;
  position: relative;
  z-index: 1;
}
#hero-1946 .cs-flex {
  /* prevents flexbox from squishing it */
  flex: none;
}
#hero-1946 .cs-title {
  /* 39px - 61px */
  font-size: clamp(2.438rem, 5vw, 3.813rem);
}
#hero-1946 .cs-text {
  margin-bottom: 1.25rem;
}
#hero-1946 .cs-button-solid {
  font-size: 1rem;
  font-weight: 700;
  /* 46px - 56px */
  line-height: clamp(2.875rem, 5.5vw, 3.5rem);
  text-align: center;
  text-decoration: none;
  min-width: 9.375rem;
  margin: 0;
  /* prevents padding from adding to the width */
  box-sizing: border-box;
  /* 32px - 48px */
  padding: 0 clamp(2rem, 4vw, 3rem);
  background-color: var(--primary);
  color: var(--bodyTextColorWhite);
  display: inline-block;
  position: relative;
  z-index: 1;
  transition: color 0.3s;
  transition: background-color 0.3s;
}
#hero-1946 .cs-picture {
  display: flex;
  justify-content: center;
  width: 100%;
}
#hero-1946 .cs-picture img {
  width: 100%;
  height: auto;
  display: block;
}
</style>