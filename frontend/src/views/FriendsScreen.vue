<script setup>
import NavBar from '@/components/NavBar.vue'
import ListItemFriend from '@/components/ListItemFriend.vue'
import { ref, onMounted } from 'vue'

const friends = ref([])

onMounted(() => {
  friends.value = [
    {
      name: 'Danillo',
      period: '5',
      degree: 'História'
    },
    {
      name: 'Alisson',
      period: '7',
      degree: 'TI'
    },
    {
      name: 'CR7 Jales',
      period: '4',
      degree: 'TI'
    },
    {
      name: 'Iñaki Marlon Lourenço',
      period: '7',
      degree: 'Geografia'
    },
    {
      name: 'Ían Gabriel',
      period: '5',
      degree: 'TI'
    },
    {
      name: 'Parabéns',
      period: '5',
      degree: 'TI'
    },
    {
      name: 'Mary Peace',
      period: '4',
      degree: 'TI'
    },
    {
      name: 'Tiago',
      period: '6',
      degree: 'TI'
    },
    {
      name: 'Natálya',
      period: '4',
      degree: 'TI'
    },
    {
      name: 'Dudu',
      period: '2',
      degree: 'Ciência da Computação'
    }
  ]
})
</script>

<template>
  <div class="min-h-screen bg-bp_neutral-900 text-bp_neutral-50">
    <NavBar />

    <main class="pl-16 pr-16">
      <div class="container-search-friends ml-[60px] mr-[60px]">
        <h2 class="text-3xl font-bold border-b border-bp_neutral-800 pb-6 mb-6">amigos</h2>
        <div class="flex items-center justify-between">
          <div class="flex items-center">
            <input
              type="text"
              name=""
              class="h-[60px] w-[510px] rounded-l-[10px] bg-bp_neutral-800 text-white border-[1px] border-solid border-[#636363] hover:bg-gray-700 indent-[5px]"
              placeholder=""
            />
            <button
              class="h-[60px] w-[60px] rounded-r-[10px] bg-bp_primary-400 text-white font-bold flex justify-center items-center"
            >
              <v-icon name="md-search-round" scale="1.5"></v-icon>
            </button>
            <button
              class="h-[60px] w-[158px] py-[18px] px-4 bg-bp_neutral-800 text-white font-bold ml-4 rounded-[10px] flex justify-center items-center border-[1px] border-solid"
            >
              Todos
              <v-icon name="md-arrowdropdown"></v-icon>
            </button>
          </div>

          <button
            class="h-[60px] py-[18px] px-[20px] rounded-[10px] bg-bp_primary-400 text-white font-bold flex justify-center items-center"
          >
            <v-icon class="mr-2" name="md-personaddalt1-outlined" scale="1.5"></v-icon>
            Adicionar Amigo
          </button>
        </div>

        <div class="grid grid-cols-1 gap-4 mt-10 lg:grid-cols-2">
          <ListItemFriend
            v-for="(friend, index) in friends"
            v-bind:key="index"
            :nome="friend.name"
            :curso="friend.degree"
            :periodo="friend.period"
          />
        </div>
      </div>
    </main>
  </div>
</template>

<style scoped></style>
