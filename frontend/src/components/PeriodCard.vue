<script setup>
import { ref } from 'vue'
import BpButton from './BpButton.vue'

const isExpanded = ref(false)
const icon = ref('md-keyboardarrowleft')

function toggleExpansion() {
  isExpanded.value = !isExpanded.value
}
</script>

<template>
  <article class="w-full">
    <div
      class="rounded-t-md bg-bp_neutral-800 cursor-pointer flex items-center justify-between p-6 border-bp_neutral-600"
      :class="{ 'border-b': isExpanded }"
      @click="toggleExpansion"
    >
      <h3 class="font-bold text-2xl">1º</h3>
      <div class="flex items-center gap-4">
        <span class="font-bold border border-bp_neutral-600 px-2 py-1 rounded-3xl">
          <v-icon name="md-schedule" />
          180h
        </span>
        <v-icon
          :name="icon"
          scale="1.5"
          class="transition-transform"
          :class="{ '-rotate-90': isExpanded }"
        />
      </div>
    </div>

    <div
      class="rounded-b-md bg-bp_neutral-800 flex flex-col gap-4 h-min overflow-hidden transition-[max-height] duration-200 ease-in-out"
      :class="{ 'max-h-0': !isExpanded, 'max-h-[40rem]': isExpanded }"
    >
      <BpButton type="outlined" size="small" class="m-4 max-w-max self-end">
        <v-icon name="md-add" scale="1.25" />
        Adicionar matéria
      </BpButton>
      <ul class="flex flex-col gap-4 mt-2 m-4">
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
