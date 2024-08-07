<template>
    <div v-if="visible" :class="['toast', 'position-fixed', 'bottom-0', 'end-0', 'm-3', notificationClass]" role="alert" aria-live="assertive" aria-atomic="true">
      <div class="toast-body">
        {{ message }}
      </div>
    </div>
  </template>
  
  <script>
  export default {
    props: {
      message: String,
      type: String // 'success' or 'error'
    },
    data() {
      return {
        visible: true
      };
    },
    computed: {
      notificationClass() {
        return {
          'bg-success text-white': this.type === 'success',
          'bg-danger text-white': this.type === 'error'
        };
      }
    },
    watch: {
      visible(val) {
        if (val) {
          setTimeout(() => {
            this.visible = false;
          }, 3000); // Hide notification after 3 seconds
        }
      }
    }
  };
  </script>
  
  <style scoped>
  .toast {
    z-index: 1050; /* Bootstrap toast z-index */
    opacity: 1;
    transition: opacity 0.5s ease;
  }
  </style>
  