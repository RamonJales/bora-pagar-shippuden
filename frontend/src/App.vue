<script setup>
import NavBar from '@/components/NavBar.vue'
</script>

<template>
  <div class="h-screen w-screen bg-bp_neutral-900 text-bp_neutral-50">
    <NavBar />
    <RouterView />
  </div>
</template>
