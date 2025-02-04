<script lang="ts">
export default {
  props: {
    id: {
      type: String,
      required: true,
    }
  }
}
</script>

<template>
  <h1>dynamic route: {{ id }}</h1>
</template>