<script setup lang="ts">
import InputSearch from '@/components/InputSearch.vue'
import CardSubjects from '@/components/CardSubjects.vue'
import NavBar from '@/components/NavBar.vue'
</script>

<template>
  <NavBar />
  <header class="h-full container mx-auto p-6 xl:max-w-7xl bg-bp_neutral-900 space-y-6">
    <p class="text-bp_neutral-50 text-2xl font-bold">consultar-disciplinas</p>
    <hr class="border-bp_neutral-700" />
    <div class="flex flex-col lg:flex-row lg:space-x-6">
      <InputSearch class="w-full lg:w-2/3 mb-4 lg:mb-0" />
      <div class="flex flex-wrap justify-end lg:w-1/2 space-x-4">
        <div class="flex items-center space-x-2">
          <select class="card-options">
            <option value="">Departamento</option>
            <option value="imd">IMD</option>
            <option value="c&t">C&T</option>
            <option value="dimap">DIMAP</option>
          </select>
        </div>
        <div class="flex items-center space-x-2">
          <button class="card-options flex items-center">
            <span>Dificuldade</span>
            <v-icon name="md-arrowdropdown-outlined" scale="1.2" />
          </button>
        </div>
        <div class="flex items-center space-x-2">
          <button class="card-options flex items-center">
            <span>Horas</span>
            <v-icon name="md-arrowdropdown-outlined" scale="1.2" />
          </button>
        </div>
      </div>
    </div>
    <div class="space-y-4">
      <CardSubjects name="Programação" />
      <CardSubjects name="Matematica elementar" />
    </div>
  </header>
</template>
