<script setup>
import NavBar from '@/components/NavBar.vue'
import ListItemFriend from '@/components/ListItemFriend.vue'
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
              class="h-[60px] w-[158px] py-[18px] px-[20px] bg-bp_neutral-800 text-white font-bold ml-4 px-4 rounded-[10px] flex justify-center items-center border-[1px] border-solid"
            >
              Todos
              <v-icon name="md-arrowdropdown"></v-icon>
            </button>
          </div>
          <!-- o padding quebra o Adicionar Amigos -->
          <button
            class=" h-[60px] py-[18px] px-[20px] rounded-[10px] bg-bp_primary-400 text-white font-bold flex justify-center items-center"
          >
            <v-icon class="mr-2" name="md-personaddalt1-outlined" scale="1.5"></v-icon>
            Adicionar Amigo
          </button>
        </div>

        <div class="grid grid-cols-1 gap-4 mt-4 lg:grid-cols-2">


          <ListItemFriend nome="Danillo" curso="TI" periodo="3"/>
          <ListItemFriend nome="Alisson" curso="TI" periodo="5"/>

          <ListItemFriend/>
          <ListItemFriend/>

          <ListItemFriend/>
          <ListItemFriend/>



        </div>
      </div>
    </main>
  </div>
</template>

<style scoped></style>
