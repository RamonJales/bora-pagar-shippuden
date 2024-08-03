<script setup>
import { ref } from 'vue'
const props = defineProps({
  type: Boolean
})

console.log(props.type)
//const bagType = ref(props.type)
</script>

<template>
  <span
    class="text-center text-bp_primary-400 border font-semibold p-2 rounded-3xl text-xs hover:border-2 cursor-default"
    :class="{
      'bg-bp_neutral-700 text-white border border-bp_primary-50': props.type,
      'bg-bp_primary-400 bg-opacity-20 border-bp_primary-300': !props.type
    }"
    
  >
    <slot />
  </span>
</template>
