<template background-clip>
    <div>
        <iframe
            src = "https://www.youtube.com/embed/qqHpvKY-vWQ?autoplay=1&loop=1&playlist=qqHpvKY-vWQ&mute=1&controls=0&modestbranding=1&showinfo=0&iv_load_policy=3"
            frameborder="0"
            allow="autoplay; fullscreen"
            allowfullscreen
            class="video-iframe"
        ></iframe>
    </div>
</template>

<script>
    export default{
        name: 'BackgroundClip',
    };
    
</script>

<style scoped>
.background-clip{
    position: relative;
    width: 100%;
    height: 100vh;
    overflow: hidden;
}
.video-iframe{
    position: absolute;
    top:0;
    left: 0;
    width: 100%;
    height: 100%;
    object-fit: cover;
    z-index: -1;
}
</style>