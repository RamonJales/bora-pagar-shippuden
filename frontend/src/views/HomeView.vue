<script setup>
import { useDark, useToggle } from '@vueuse/core'

const isDark = useDark()
const toggleDark = useToggle(isDark)
</script>

<template>
  <main class="bg-black text-white rounded-md dark:bg-white dark:text-black rounded-md h-screen">
    Home
    <div class="flex">
      <span class="text-white dark:text-black"> {{ isDark ? 'Dark' : 'Light' }} Mode </span>
      <button
        @click="toggleDark()"
        class="py-2 px-4 bg-black text-white border border-white dark:bg-white dark:text-black dark:border-black rounded-md"
      ></button>
    </div>
  </main>
</template>
