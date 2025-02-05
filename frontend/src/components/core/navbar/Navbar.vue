<script setup lang="ts">
import { RouterLink } from 'vue-router'
import { Button } from '@/components/ui/button'
</script>

<template>
  <header class="py-5 border">
    <nav class="container flex items-center justify-between">
      <RouterLink to="/" class="text-2xl font-bold">BookStore</RouterLink>
      <RouterLink to="/create-book">
        <Button>Create Book</Button>
      </RouterLink>
    </nav>
  </header>
</template>
