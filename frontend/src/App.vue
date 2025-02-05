<script setup>
import { RouterLink, RouterView } from 'vue-router'
import { Button } from "@/components/ui/button"
</script>

<template>
  <header>
    <nav>
      <RouterLink to="/">Home</RouterLink>
      <RouterLink to="/about">About</RouterLink>
      <Button>Click me</Button>
    </nav>
  </header>

  <RouterView />
</template>
