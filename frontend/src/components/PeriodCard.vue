<script setup>
import { ref } from 'vue'

const isExpanded = ref(false)
const icon = ref('md-keyboardarrowright')

function toggleExpansion() {
  isExpanded.value = !isExpanded.value

  icon.value = isExpanded.value ? 'md-keyboardarrowdown' : 'md-keyboardarrowright'
}
</script>

<template>
  <article class="w-full bg-bp_neutral-800 rounded-md">
    <div
      class="cursor-pointer flex items-center justify-between p-6 border-bp_neutral-600"
      :class="{ 'border-b': isExpanded }"
      @click="toggleExpansion"
    >
      <h3 class="font-bold text-2xl">1º</h3>
      <div class="flex items-center gap-4">
        <span class="font-bold text-lg border border-bp_neutral-600 px-2 py-1 rounded-3xl">
          <v-icon name="md-schedule" />
          180h
        </span>
        <v-icon :name="icon" scale="1.5" />
      </div>
    </div>

    <div class="flex flex-col gap-4 p-4" v-show="isExpanded">
      <button
        type="button"
        class="flex items-center justify-center gap-2 bg-transparent max-w-max h-10 px-4 rounded-lg font-semibold text-bp_primary-200 border border-bp_primary-200 text-sm self-end"
      >
        <v-icon name="md-add" scale="1.25" />
        Adicionar matéria
      </button>
      <ul class="flex flex-col gap-4 mt-2">
        <li
          class="flex items-center justify-between gap-4 border border-bp_neutral-500 rounded-md px-4 py-2"
        >
          <p class="font-bold">Matemática Elementar (60h) - IMD1001</p>
          <v-icon name="md-infooutline" />
        </li>
        <li
          class="flex items-center justify-between gap-4 border border-bp_neutral-500 rounded-md px-4 py-2"
        >
          <p class="font-bold">Matemática Elementar (60h) - IMD1001</p>
          <v-icon name="md-infooutline" />
        </li>
        <li
          class="flex items-center justify-between gap-4 border border-bp_neutral-500 rounded-md px-4 py-2"
        >
          <p class="font-bold">Matemática Elementar (60h) - IMD1001</p>
          <v-icon name="md-infooutline" />
        </li>
        <li
          class="flex items-center justify-between gap-4 border border-bp_neutral-500 rounded-md px-4 py-2"
        >
          <p class="font-bold">Matemática Elementar (60h) - IMD1001</p>
          <v-icon name="md-infooutline" />
        </li>
        <li
          class="flex items-center justify-between gap-4 border border-bp_neutral-500 rounded-md px-4 py-2"
        >
          <p class="font-bold">Matemática Elementar (60h) - IMD1001</p>
          <v-icon name="md-infooutline" />
        </li>
        <li
          class="flex items-center justify-between gap-4 border border-bp_neutral-500 rounded-md px-4 py-2"
        >
          <p class="font-bold">Matemática Elementar (60h) - IMD1001</p>
          <v-icon name="md-infooutline" />
        </li>
      </ul>
    </div>
  </article>
</template>
