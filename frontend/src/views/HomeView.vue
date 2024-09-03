<script setup>
import { useDark, useToggle } from '@vueuse/core'

const isDark = useDark()
const toggleDark = useToggle(isDark)
console.log(isDark.value);
</script>

<template>
  <main class="bg-black text-white rounded-md dark:bg-white dark:text-black rounded-md h-screen">
    Home
    <div class="flex">
      <span class="text-white dark:text-black mr-2"> {{ isDark ? 'Light ' : 'Dark ' }} Mode </span>
      <button
        v-if="isDark"
        @click="toggleDark()"
        class="flex items-center justify-between py-1 px-1 bg-black text-white border border-white dark:bg-white dark:text-black dark:border-black rounded-md"
      >
        <v-icon class="mr-2 ml-2" name="md-darkmode-outlined" scale="1.1"></v-icon>
      </button>
      <button
        v-else
        @click="toggleDark()"
        class="flex items-center justify-between py-1 px-1 bg-black text-white border border-white dark:bg-white dark:text-black dark:border-black rounded-md"
      >
        <v-icon class="mr-2 ml-2" name="md-lightmode-outlined" scale="1.1"></v-icon>
      </button>
    </div>
  </main>
</template>
