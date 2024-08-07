<script setup>
import { computed } from 'vue'

const props = defineProps(['progress'])


const content = computed(() => {
  return "'" + props.progress + "%'"
})

const direction = computed(() => {
  return Number(props.progress) < 51 ? 'right' : 'left'
})

const color = computed(() => {
  return Number(props.progress) < 51 ? '#505050' : '#BBDCFF'
})

const degree = computed(() => {
    return `${90 + (3.6 * Number(props.progress))}deg`
})
</script>

<template>
  <span class="c-circular-progress" :class="content"></span>
</template>

<style scoped>
.c-circular-progress {
  border-radius: 100%;
  display: block;
  height: 10rem;
  position: relative;
  width: 10rem;

  background: linear-gradient(to v-bind(direction), v-bind(color) 50%, transparent 50%),
    linear-gradient(v-bind(degree),  #BBDCFF 50%, #505050 50%);
}

.c-circular-progress::before {
  align-items: center;
  background-color: #323232;
  border-radius: 100%;
  display: inline-flex;
  font-size: 250%;
  font-weight: bold;
  height: 100%;
  justify-content: center;
  left: 0;
  position: absolute;
  top: 0;
  transform: scale(0.8);
  width: 100%;

  content: v-bind(content);
}
</style>
