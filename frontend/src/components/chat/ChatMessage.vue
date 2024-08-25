<script setup>
import { computed } from 'vue'
import SimboraImg from '../SimboraImg.vue'

const props = defineProps(['author', 'sentAt'])

const isMessageFromSimbora = computed(() => props.author === 'simbora')
</script>

<template>
  <div
    class="flex flex-col gap-2 p-4 whitespace-pre-wrap"
    :class="{ 'items-start': isMessageFromSimbora, 'items-end': !isMessageFromSimbora }"
  >
    <div class="flex gap-3 items-start" :class="{ 'flex-row-reverse': !isMessageFromSimbora }">
      <SimboraImg v-if="isMessageFromSimbora" />

      <img
        src="https://github.com/tiago-rodrigues1.png"
        alt="Simbora: o mascote do Bora Pagar"
        class="h-12 w-12 rounded-full"
        v-else
      />

      <div class="bg-bp_neutral-700 p-2 rounded-md max-w-xs text-sm font-bold flex">
        <slot />
        <span class="text-[10px] text-bp_primary-50 font-normal ml-4 self-end">{{
          props.sentAt
        }}</span>
      </div>
    </div>
  </div>
</template>
