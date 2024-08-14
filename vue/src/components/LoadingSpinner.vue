<template>
    <div class="loading-spinner-overlay">
      <div class="loader">
        <span v-for="n in 20" :key="n" :style="{'--i': n}"></span>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    name: 'LoadingSpinner',
  };
  </script>
  
  <style scoped>
  .loading-spinner-overlay {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.7); /* Semi-transparent background */
    display: flex;
    justify-content: center;
    align-items: center;
    z-index: 9999;
  }
  
  .loader {
    position: relative;
    width: 120px;
    height: 120px;
    display: flex;
    flex-wrap: wrap;
    transform: rotate(45deg);
  }
  
  .loader span {
    position: absolute;
    width: 30%;
    height: 30%;
    background: #00ff0a;
    animation: animate 2s linear infinite;
    animation-delay: calc(0.1s * var(--i));
    filter: hue-rotate(calc(18deg * var(--i)));
    border-radius: 5px;
  }
  
  .loader span:nth-child(1)  { top: 0; left: 0; }
  .loader span:nth-child(2)  { top: 0; left: 35%; }
  .loader span:nth-child(3)  { top: 0; left: 70%; }
  .loader span:nth-child(4)  { top: 35%; left: 0; }
  .loader span:nth-child(5)  { top: 35%; left: 35%; }
  .loader span:nth-child(6)  { top: 35%; left: 70%; }
  .loader span:nth-child(7)  { top: 70%; left: 0; }
  .loader span:nth-child(8)  { top: 70%; left: 35%; }
  .loader span:nth-child(9)  { top: 70%; left: 70%; }
  
  @keyframes animate {
    0%, 100% {
      opacity: 0.2;
      transform: scale(1);
    }
    50% {
      opacity: 1;
      transform: scale(1.5);
    }
  }
  </style>
  