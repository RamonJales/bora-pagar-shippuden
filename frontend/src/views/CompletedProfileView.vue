<script setup>
import InputBase from '@/components/InputBase.vue';
import NavBar from '@/components/NavBar.vue';
import BpInputUpload from '@/components/BpInputUpload.vue';
import BpButton from '@/components/BpButton.vue';
import { ref } from 'vue';

const refInput = ref()
const acceptedTerms = ref(false);

</script>

<template>
<NavBar/>

  <main class="space-y-8 h-full container mx-auto p-6 xl:max-w-7xl">
    <header class="flex items-center justify-between border-b border-bp_neutral-800 pb-4">
      <h1 class="title-h1">complete-perfil</h1>
    </header>
        <div class="grid grid-cols-3 space-x-6">
          <div class="card-profile">
            <div class="flex items-center space-x-2">
                <v-icon name="md-accountcircle-outlined" scale="1.5" />
                <p class="title-h1 ">Dados Pessoais</p>
            </div>
            
            <hr class="border-bp_neutral-700"/>

            <div>
              <label class="text-lg" for="">Nome <span class="text-bp_primary-300">*</span></label>
              <div class="relative flex items-center">
                <v-icon name="md-person-outlined" scale="1.5" class="absolute left-3 text-bp_neutral-600"/>
                <InputBase class="pl-10" placeholder="Nome"/>
              </div>
            </div>

            <div class="space-y-2">
                <v-icon name="md-calendartoday-outlined" scale="1.5"/>
              <div class="flex justify-between">
                <label class="text-lg" for="">Data de Nascimento</label>
                <v-icon name="md-infooutline" scale="1.5"/>
              </div>
              <InputBase placeholder="Escolha a data"/>
            </div>
          </div>
    
          <div class="card-profile">
            <div class="flex items-center space-x-2">
                <v-icon name="md-school-outlined" scale="1.5"/>
                <p class="title-h1">Dados Institucionais</p>
            </div>
            
            <hr class="border-bp_neutral-700"/>
            <div class="space-y-2">
                <label class="text-lg" for="">Curso <span class="text-bp_primary-300">*</span></label> 
                <div>
                  <select class="card-options w-full bg-bp_neutral-900 text-bp_neutral-600" name="course" id="">
                    <option value="TI">TI</option>
                  </select>
                </div>
                
            </div>
            <div class="space-y-2">
                <div class="flex justify-between">
                    <label class="text-lg" for="">Histórico Acadêmico</label>
                    <v-icon name="md-infooutline" scale="1.5"/>
                  </div>
                <BpInputUpload name="historic" placeholder="Histórico" v-model="refInput"/>
                <span class="text-bp_neutral-400">Para preenchimento automático de disciplinas.</span>
            </div>
            <div class="space-y-2">
                <div class="flex justify-between">
                    <label class="text-lg" for="">Índices Acadêmico</label>
                    <v-icon name="md-infooutline" scale="1.5"/>
                  </div>
                  <BpInputUpload name="indices" placeholder="Histórico" v-model="refInput"/>
            </div>
          </div>
    
          <div class="card-profile">
            <div class="flex items-center space-x-2">
                <v-icon name="md-assignmentturnedin-outlined" scale="1.5"/>
                <p class="title-h1">Termos e Condições</p>
            </div>
            
            <hr class="border-bp_neutral-700"/>
            <div>
                <p class="text-bp_neutral-400">É importante ler os termos e condições antes de aceitar, pois eles contêm informações cruciais sobre seus direitos e responsabilidades ao usar nosso serviço. garantir que você esteja ciente de como seus dados serão utilizados e quais são suas obrigações. </p>
            </div>
            <div class="flex items-center space-x-2 mt-4">
              <div class="relative flex items-center">
                <input 
                  type="checkbox" 
                  v-model="acceptedTerms" 
                  id="terms-checkbox" 
                  class="cursor-pointer appearance-none w-6 h-6 bg-bp_neutral-800 border-2 border-bp_neutral-600 rounded-lg checked:bg-bp_neutral-500 focus:ring-2 focus:ring-bp_neutral-500"
                />
                <v-icon 
                  v-if="acceptedTerms" 
                  name="md-check-outlined" 
                  scale="1.2" 
                  class="absolute top-0 left-0 text-white"
                />
              </div>
              <label class="text-lg" for="terms-checkbox">
                Aceitar <span class="text-bp_primary-600">Termos e Condições</span>
              </label>
            </div>
          </div>
        </div>
        <div class="grid">
          <BpButton class="justify-self-end" type="primary" size="medium" >Avançar</BpButton>
        </div>
  </main>
</template>