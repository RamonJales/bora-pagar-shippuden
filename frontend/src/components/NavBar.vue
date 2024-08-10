<script setup>
import { ref } from 'vue'
import { RouterLink } from 'vue-router'

import BadgeTag from '@/components/BadgeTag.vue'

const isMenuOpen = ref(false)

function handleMenuClick() {
  isMenuOpen.value = !isMenuOpen.value
}
</script>

<template>
  <header class="flex items-center gap-10 mb-10 border-b border-bp_neutral-800 p-6">
    <button
      type="button"
      class="text-bp_neutral-400 w-11 h-11 border border-bp_neutral-700 rounded-md"
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
    class="w-11/12 md:w-auto p-5 py-8 bg-neutral-950 absolute top-0 h-screen transition-all ease-in-out duration-300"
    :class="{
      '-left-full': !isMenuOpen,
      'left-0': isMenuOpen
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
      <BadgeTag> Tecnologia da Informação </BadgeTag>
      <BadgeTag>5º Período</BadgeTag>
    </div>

    <hr class="my-6" />

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
