<script setup lang="ts">
import ButtonDetails from './ButtonDetails.vue'
</script>

<template>
  <div class="text-bp_neutral-50 w-full h-auto rounded-lg bg-bp_neutral-800">
    <div class="flex flex-col md:flex-row md:justify-start md:items-center space-x-6 pr-4">
      <div class="flex flex-col items-center py-6 pl-8">
        <h2 class="text-xl sm:text-2xl font-semibold">IMD0126</h2>
        <p class="text-lg sm:text-xl">60h</p>
      </div>
      <div class="hidden md:block w-0.5 h-24 bg-bp_neutral-700"></div>
      <div class="flex flex-col space-y-2 lg:space-y-4 py-4">
        <div class="flex items-center space-x-2 lg:space-x-4">
          <p class="text-base sm:text-lg font-semibold">INTRODUÇÃO A IOT E SISTEMAS EMBARCADOS</p>
          <div class="bg-bp_sucess w-4 h-4 sm:w-5 sm:h-5 rounded-full"></div>
        </div>
        <div class="flex items-center space-x-2 lg:space-x-4">
          <p class="text-sm sm:text-base">INSTITUTO METROPOLE DIGITAL</p>
          <p>|</p>
          <p class="text-sm sm:text-base">TECNOLOGIA DA INFORMAÇÃO</p>
        </div>
      </div>
      <ButtonDetails />
    </div>
  </div>
</template>
