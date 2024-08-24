<script setup>
import { computed } from 'vue'
import SimboraImg from '../SimboraImg.vue'

const props = defineProps(['author'])

const isMessageFromSimbora = computed(() => props.author === 'simbora')
</script>

<template>
  <div
    class="flex flex-col gap-2 p-4 whitespace-pre-wrap"
    :class="{ 'items-start': isMessageFromSimbora, 'items-end': !isMessageFromSimbora }"
  >
    <div class="flex gap-3 items-center" :class="{ 'flex-row-reverse': !isMessageFromSimbora }">
      <SimboraImg v-if="isMessageFromSimbora" />

      <img
        src="https://github.com/tiago-rodrigues1.png"
        alt="Simbora: o mascote do Bora Pagar"
        class="h-12 w-12 rounded-full"
        v-else
      />

      <div class="bg-bp_neutral-700 p-2 rounded-md max-w-xs text-sm">
        <slot />
      </div>
    </div>
  </div>
</template>
