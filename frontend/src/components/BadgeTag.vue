<script setup>
const props = defineProps({
  type: {
    type: String,
    required: true,
    validator: (value) => ['primary', 'secondary'].includes(value)
  },
  size: {
    type: String,
    required: true,
    validator: (value) => ['large', 'small'].includes(value)
  }
})
</script>

<template>
  <span
    class="text-center text-bp_primary-400 border font-semibold rounded-3xl text-xs hover:border-2 cursor-default"
    :class="{
      'bg-bp_primary-400 bg-opacity-20 border-bp_primary-300': props.type === 'primary',
      'bg-bp_neutral-700 text-white border border-bp_primary-50': props.type === 'secondary',
      'p-2': props.size === 'large',
      'p-1': props.size === 'small'
    }"
  >
    <slot />
  </span>
</template>
