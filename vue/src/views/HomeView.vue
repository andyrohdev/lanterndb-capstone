<template>
  <div class="home-page">
    <!-- Header Section -->
    

    <!-- Hero Section -->
    <section id="hero-1946">
      <div class="cs-container">
        <div class="cs-content">
          <h2 class="cs-title">LanternDB</h2>
          <div class="cs-flex">
            <p class="cs-text">
              The Baltimore Ravens were established in 1996 after Art Modell, then owner of the Cleveland Browns, announced plans in 1995 to relocate the franchise from Cleveland to Baltimore.[10] As part of a settlement between the league and the city of Cleveland, Modell was required to leave the Browns' history, team colors, and records in Cleveland for a replacement team and replacement personnel that would resume play in 1999. In return, he was allowed to take his own personnel and team to Baltimore, where such personnel would form an expansion team. The team is owned by Steve Bisciotti and was valued at $4.63 billion in 2023, making them the 28th-most valuable sports franchise globally.[11]
            </p>
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
      // Removed unused data
    };
  },
  methods: {
    // Removed unused methods
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

.footer {
  padding: 20px;
  color: white;
  text-align: center;
}

@media (max-width: 768px) {
  .header,
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
  text-align: center;
  width: 100%;
  margin-bottom: clamp(2.5rem, 5vw, 3.25rem);
  display: flex;
  flex-direction: column;
  align-items: center;
  position: relative;
  z-index: 1;
}

#hero-1946 .cs-flex {
  flex: none;
}

#hero-1946 .cs-title {
  font-size: clamp(2.438rem, 5vw, 3.813rem);
  color: white; /* Ensure title text color is white */
}

#hero-1946 .cs-text {
  margin-bottom: 1.25rem;
  color: white; /* Ensure text color is white */
}
</style>