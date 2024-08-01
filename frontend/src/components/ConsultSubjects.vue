<script setup lang="ts">
import CardSubjects from './CardSubjects.vue';
import InputSearch from './InputSearch.vue';
</script>

<template>
    <header class="bg-bp_neutral-900 space-y-6">
        <p class="text-bp_neutral-50 text-2xl font-bold">consultar-disciplinas</p>
        <hr>
        <div class="flex space-x-6">
            <InputSearch/>
            <div class="flex space-x-3">
                <div>
                    <button class="card-options">Departamento </button>
                    <v-icon class="text-bp_primary-" name="ri-arrow-drop-down-fill" scale="1.5" />
                </div>
                <div>
                    <button class="card-options">Dificuldade </button>
                    <v-icon class="text-bp_primary-" name="ri-arrow-drop-down-fill" scale="1.5" />
                </div>
                <div>
                    <button class="card-options">Horas </button>
                    <v-icon class="text-bp_primary-300" name="ri-arrow-drop-down-fill" scale="1.5" />
                </div>
            </div>
        </div>
        <div class="space-y-4">
            <CardSubjects/>
            <CardSubjects/>
        </div>       
    </header>
</template>
