<script setup>
import InputBase from './InputBase.vue'
</script>
<template>
  <div class="flex">
    <input
      type="text"
      class="w-full bg-bp_neutral-800 border border-bp_neutral-700 rounded-l-lg py-2 px-4 text-bp_neutral-500"
      placeholder="pré requisito de fmciii |"
    />
    <button class="bg-bp_primary-300 text-bp_primary-50 rounded-r-lg py-2 px-4">
      <v-icon name="md-search" scale="1.5" />
    </button>
  </div>
</template>
