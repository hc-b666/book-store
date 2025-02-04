<script>
import { RouterView } from 'vue-router'

export default { name: "App" };
</script>

<template>
  <nav>
    <RouterLink to="/">Home</RouterLink>
    <RouterLink to="/about">About</RouterLink>
    <RouterLink to="/dynamic/25">Dynamic route</RouterLink>
    <RouterLink to="/blabla">Some not found page</RouterLink>
  </nav>
  <RouterView />
</template>
