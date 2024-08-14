<template>
    <footer class="custom-footer">
        <nav>
            <ul class="nav-list">
                <li class="nav-item">
                    <router-link to="/" class="nav-link">
                        <i class="bi bi-house-door"></i> Home
                    </router-link>
                </li>
                <li class="nav-item">
                    <router-link to="/features" class="nav-link">
                        <i class="bi bi-star"></i> Features
                    </router-link>
                </li>
                <li class="nav-item">
                    <router-link to="/about" class="nav-link">
                        <i class="bi bi-info-circle"></i> About
                    </router-link>
                </li>
            </ul>
        </nav>
        <p class="footer-text">© 2024 Company, Inc</p>
    </footer>
</template>

<script>
export default {
    name: 'Footer',
};
</script>

<style scoped>
.custom-footer {
    padding: 2rem 1rem;
    text-align: center;
    background-color: #33333300;
    color: #fff;
    border-top: 1px solid #444;
    position: relative;
    bottom: 0;
    z-index: 1;
    width: 100%;
}

.nav-list {
    list-style: none;
    padding: 0;
    margin: 0 0 1rem;
    display: flex;
    justify-content: center;
    gap: 1rem;
}

.nav-item {
    margin: 0;
}

.nav-link {
    text-decoration: none;
    color: #fff;
    padding: 0.5rem 1rem;
    font-size: 1rem;
    display: flex;
    align-items: center;
    gap: 0.5rem;
    transition: color 0.3s, text-decoration 0.3s;
}

.nav-link:hover {
    color: #ffa500; /* Orange color */
    text-decoration: underline;
}

.footer-text {
    margin: 0;
    font-size: 0.875rem;
}
</style>
