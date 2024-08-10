<script setup>
import BadgeTag from './BadgeTag.vue'
defineProps(['nome', 'curso', 'periodo'])
</script>

<template>
  <span
    class="user-container bg-bp_neutral-800 rounded-[10px] h-[100px] text-white flex justify-between items-center"
  >
    <div class="flex justify-between items-center gap-4">
      <img
        class="ml-8 rounded-full w-[73px] h-[73px]"
        src="https://images.pexels.com/photos/21085439/pexels-photo-21085439/free-photo-of-preto-e-branco-p-b-olhando-vendo.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2"
        alt="foto do amigo"
      />
      <div class="user-data">
        <div class="text-xl font-bold">{{ nome }}</div>
        <div class="user-subject">
          <span class="inline-flex items-center gap-1">
            <v-icon class="text-bp_primary-100" name="md-school-outlined"></v-icon>
            {{ curso }}</span
          >
          <span class="ml-3 inline-flex items-center gap-1">
            <v-icon class="text-bp_primary-100" name="md-calendartoday-outlined"></v-icon> Período
            {{ periodo }}°</span
          >
        </div>
      </div>
    </div>

    <BadgeTag class="mr-8" type="secondary" size="small">8 minutos atrás</BadgeTag>
  </span>
</template>
