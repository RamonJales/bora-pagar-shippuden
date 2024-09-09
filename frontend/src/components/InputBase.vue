<script setup>
const props = defineProps({
  placeholder: {
    type: String,
    defalut: 'pré requisito de fcmiii |'
  }
})
</script>

<template>
  <input
    type="text"
    class="w-full py-4 bg-bp_neutral-800 border border-bp_neutral-700 rounded py-2 px-4 text-bp_neutral-50  placeholder:text-bp_neutral-700"
    :placeholder="props.placeholder"
  />
</template>
