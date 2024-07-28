<script setup>
import { ref } from 'vue'
import { RouterLink } from 'vue-router'

const isMenuOpen = ref(false)

function handleMenuClick() {
  isMenuOpen.value = !isMenuOpen.value
}
</script>

<template>
  <header class="w-screen flex gap-10 mb-10 border-b border-bp_neutral-800 p-6">
    <button
      type="button"
      class="text-bp_neutral-400 p-1 border border-bp_neutral-700 rounded-md"
      @click="handleMenuClick"
    >
      <v-icon name="md-menu-round" scale="1.5" />
    </button>
    <div class="flex gap-4 items-center">
      <img
        src="https://images.pexels.com/photos/21085439/pexels-photo-21085439/free-photo-of-preto-e-branco-p-b-olhando-vendo.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2"
        alt="Simbora: o mascote do Bora Pagar"
        class="h-12 w-12 rounded-full"
      />
      <h4 class="text-xl font-bold">Bora Pagar</h4>
    </div>
  </header>
  <nav
    class="w-11/12 md:w-auto"
    :class="{
      'left-[0px] top-0 animate-openmenu p-5 py-8 bg-neutral-950 absolute h-screen': isMenuOpen,
      'animate-closemenu left-[-1000px] top-0 p-5 py-8 bg-neutral-950 absolute h-full': !isMenuOpen
    }"
  >
    <div class="flex items-center justify-between">
      <div class="flex gap-3 items-center">
        <img
          src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQb-NGEQDekk2BwsllLjk4tcIM_BPIzXECdsg&s"
          alt="Foto de CR7"
          class="h-12 w-12 rounded-full"
        />
        <span class="font-bold text-lg">CR7 Jales</span>
      </div>
      <button type="button" @click="handleMenuClick">
        <v-icon name="md-close" scale="1.5" />
      </button>
    </div>
    <div class="flex items-center gap-6 text-sm mt-7">
      <span class="text-center bg-bp_primary-300 font-bold p-1.5 rounded-xl"
        >Tecnologia da Informação</span
      >
      <span class="text-center bg-bp_primary-300 font-bold p-1.5 rounded-xl">5º Período</span>
    </div>

    <hr class="my-10" />

    <ul class="flex flex-col gap-4">
      <RouterLink class="menu-item" to="/">
        <v-icon name="md-home" />
        <span>Home</span>
      </RouterLink>
      <RouterLink class="menu-item" to="/">
        <v-icon name="md-menubook" />
        <span>Disciplinas</span>
      </RouterLink>
      <RouterLink class="menu-item" to="/">
        <v-icon name="md-group" />
        <span>Amigos</span>
      </RouterLink>
      <RouterLink class="menu-item" to="/">
        <v-icon name="md-person" />
        <span>Perfil</span>
      </RouterLink>
      <RouterLink
        class="flex items-center gap-4 mt-4 text-bp_danger hover:border-b border-bp_danger"
        to="/"
      >
        <v-icon name="md-logout" />
        <span>Sair</span>
      </RouterLink>
    </ul>
  </nav>
</template>
