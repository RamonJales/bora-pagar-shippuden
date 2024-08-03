<script setup>
import BadgeTag from './BadgeTag.vue';
const props = defineProps([
    'nome', 'curso', 'periodo'
])

</script>

<template>
    <!-- bg-gray-800 -->
  <span class="user-container bg-bp_neutral-800 rounded-[10px] h-[140px] text-white flex justify-between items-center">



    <div class="flex justify-between items-center gap-4">

    <img class="ml-8 rounded-full w-20 h-20" src="https://images.pexels.com/photos/21085439/pexels-photo-21085439/free-photo-of-preto-e-branco-p-b-olhando-vendo.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2" alt="" />
    <div class="user-data">
      <div class="text-2xl font-bold">{{ nome }}</div>
      <div class="user-subject">
        <span class="inline-flex items-center gap-1"> <v-icon class="align-middle" name="md-school-outlined"></v-icon> {{ curso }}</span>
         <span class="ml-3 inline-flex items-center gap-1"> <v-icon name="md-calendartoday-outlined"></v-icon> Período {{ periodo }}°</span>
      </div>
    </div>

    </div>



    <BadgeTag class="mr-8" type="true">8 minutos atrás</BadgeTag>



  </span>
</template>
