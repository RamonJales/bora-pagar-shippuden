<script setup lang="ts">
import CardSubjects from './CardSubjects.vue';
import InputSearch from './InputSearch.vue';
</script>

<template>
    <header class="bg-bp_neutral-900">
        <p class="text-bp_neutral-50 text-2xl font-bold">consultar-disciplinas</p>
        <hr>
        <div>
            <InputSearch/>
            <div>
                <button class=" bg-bp_neutral-800 text-bp_neutral-50  border border-bp_neutral-600 rounded-md p-2">Departamento </button>
                <v-icon name="ri-arrow-drop-down-fill" scale="1.5" />
            </div>
        </div>
        <CardSubjects/>
    </header>
</template>
