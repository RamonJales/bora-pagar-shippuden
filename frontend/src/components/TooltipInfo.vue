<script>
export default {
  props: {
    text: {
      type: String,
      required: true
    }
  }
}
</script>

<template>
  <div class="relative group flex items-center">
    <v-icon name="md-infooutline" scale="1.5" />
    <div
      class="absolute bottom-full mb-2 w-72 p-2 bg-bp_neutral-800 text-white text-xxs md:text-xs rounded-md opacity-0 group-hover:opacity-100 transition-opacity duration-400 break-words z-50 left-1/2 transform -translate-x-1/2"
    >
      {{ text }}
      <v-icon
        name="md-playarrow-sharp"
        class="absolute left-1/2 transform -translate-x-1/2 -bottom-2.5 rotate-90 text-bp_neutral-800"
        scale="1.0"
      />
    </div>
  </div>
</template>
