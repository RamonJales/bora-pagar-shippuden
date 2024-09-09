<script setup>
import { computed } from 'vue'

const props = defineProps(['type', 'size'])

const sizes = {
  small: 'h-10 w-10',
  medium: 'w-12 h-12',
  large: 'h-16 w-16'
}

const sizeClass = computed(() => {
  return sizes[props.size] || sizes['medium']
})
</script>

<template>
  <img
    src="/images/simbora.png"
    alt="Simbora: o mascote do Bora Pagar"
    :class="sizeClass"
    class="rounded-full bg-bp_primary-50"
  />
</template>
