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
    class="text-center font-semibold rounded-3xl text-xs cursor-default"
    :class="{
      'bg-bp_primary-400 bg-opacity-20 border border-bp_primary-300 text-bp_primary-400':
        props.type === 'primary',
      'bg-bp_neutral-700 text-white leading-[15px]': props.type === 'secondary',
      'p-2 hover-border-2': props.size === 'large',
      'py-[2px] px-[7px] hover-border-1': props.size === 'small'
    }"
  >
    <slot />
  </span>
</template>
