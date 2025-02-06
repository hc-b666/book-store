<script setup lang="ts">
import { RouterView } from 'vue-router'
import { Navbar } from "@/components/core/navbar"
</script>

<template>
  <div class="h-screen flex flex-col gap-10">
    <Navbar />
    <RouterView />
  </div>
</template>
