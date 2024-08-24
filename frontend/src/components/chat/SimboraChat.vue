<script setup>
import { ref } from 'vue'
import ChatMessage from './ChatMessage.vue'
import SimboraImg from '../SimboraImg.vue';

const isChatOpened = ref(false)
const messages = ref([])
const currentMessage = ref('')
const messageId = 0

const simboraResponse = ref('Digitando...')

function chatToggler() {
  isChatOpened.value = !isChatOpened.value
}

function sendMessage(message, user) {
  const newMessage = {
    id: messageId + 1,
    author: user,
    content: message
  }

  messages.value.push(newMessage)
  currentMessage.value = ''
}

function handleKeyPress(event) {
  if (event.key === 'Enter' && !event.shiftKey) {
    event.preventDefault()
    sendMessage(currentMessage.value, 'Tiago')

    setTimeout(() => {
      sendMessage(simboraResponse, 'simbora')
    }, 1000)

    setTimeout(() => {
      simboraResponse.value = "Olá, Tiago!"
    }, 4000)
    
  }

  if (event.key === 'Enter' && event.shiftKey) {
    event.preventDefault()
    currentMessage.value = currentMessage.value.concat('\n')
  }
}
</script>

<template>
  <button
    type="button"
    @click="chatToggler"
    class="bg-red-500 w-16 h-16 rounded-full fixed bottom-10 right-10"
    v-if="!isChatOpened"
  >
    Abrir
  </button>
  <article
    class="absolute bottom-10 right-5 md:right-10 h-4/5 z-10 border border-bp_neutral-700 rounded-lg max-w-sm md:max-w-md w-full text-sm bg-bp_neutral-800"
    v-else
  >
    <div class="flex flex-col justify-between w-full h-full">
      <header
        class="w-full h-20 p-4 border-b border-bp_neutral-700 flex items-center justify-between"
      >
        <div class="flex items-center gap-2">
          <SimboraImg />
          <div class="flex flex-col">
            <h4 class="text-xl font-bold">Simbora</h4>
            <p class="flex items-center gap-1 text-xs mt-1">
              <span class="w-2 h-2 bg-green-400 rounded-full"></span>
              <span>Online</span>
            </p>
          </div>
        </div>

        <button type="button" @click="chatToggler">
          <v-icon name="md-close" />
        </button>
      </header>

      <main class="w-full overflow-y-auto overflow-x-hidden h-full flex flex-col">
        <div class="w-full overflow-y-auto overflow-x-hidden h-full flex flex-col">
          <ChatMessage v-for="message in messages" :key="message.id" :author="message.author">
            <span>{{ message.content }}</span>
          </ChatMessage>
        </div>

        <div class="w-full p-4 border-t border-bp_neutral-700">
          <textarea
            autocomplete="off"
            name="message"
            placeholder="Aa"
            class="w-full border border-bp_neutral-500 rounded-full h-9 resize-none overflow-hidden bg-transparent py-2 px-3 text-start text-wrap"
            v-model="currentMessage"
            @keypress="handleKeyPress"
          >
          </textarea>
          
        </div>
      </main>
    </div>
  </article>
</template>
