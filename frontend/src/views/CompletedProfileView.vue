<script setup>
import InputBase from '@/components/InputBase.vue'
import NavBar from '@/components/NavBar.vue'
import BpInputUpload from '@/components/BpInputUpload.vue'
import BpButton from '@/components/BpButton.vue'
import TooltipInfo from '@/components/TooltipInfo.vue'
import { ref } from 'vue';
import { useDark, useToggle } from '@vueuse/core'
const isDark = useDark()
const toggleDark = useToggle(isDark)
console.log(isDark.value);

const refInputHistory = ref()
const refInputAcademicIndexes = ref()
const acceptedTerms = ref(false)

const dateValue = ref([]);
const formatter = ref({
  date: 'DD MM YYYY',
  month: 'MMM',
})
</script>

<template>
  <div>
    <NavBar />
    <button
        @click="toggleDark()"
        class="flex items-center justify-between py-1 px-1 bg-black text-white border border-white dark:bg-white dark:text-black dark:border-black rounded-md"
      >
        <v-icon class="mr-2 ml-2" name="md-darkmode-outlined" scale="1.1"></v-icon>
      </button>

  <main class="space-y-8 h-full container mx-auto p-6 xl:max-w-7xl">
    <header class="flex items-center justify-between border-b border-bp_neutral-800 pb-4">
      <h1 class="title-h1">completar-perfil</h1>
    </header>
    <div class="grid space-y-4 lg:grid-cols-3 lg:space-x-6 lg:space-y-0">
      <div class="card-profile">
        <div class="flex items-center space-x-2">
          <v-icon name="md-accountcircle-outlined" scale="1.5" />
          <p class="text-2xl font-bold">Dados Pessoais</p>
        </div>

        <hr class="border-bp_neutral-700" />

        <div class="space-y-2">
          <label class="text-lg font-bold" for="">Nome <span class="text-bp_primary-300">*</span></label>
          <div class="relative flex items-center">
            <v-icon
              name="md-person-outlined"
              scale="1.2"
              class="absolute left-3 text-bp_neutral-700"
            />
            <InputBase class="indent-6" placeholder="Nome" />
          </div>
        </div>

        <div class="space-y-2">
          <div class="flex space-x-2">
            <label class="text-lg font-bold" for="">Data de Nascimento</label>
            <TooltipInfo>
              Recomendamos fortemente que você insira sua data de nascimento. Esse dado é essencial para a geração de relatórios precisos e para fornecer recomendações de eventos que possam contar como horas complementares.
            </TooltipInfo>
          </div>
          <div>
            <vue-tailwind-datepicker v-model="dateValue" as-single :formatter="formatter" input-classes="bg-bp_neutral-800 border border-bp_neutral-700 rounded py-4 px-4 text-bp_neutral-50 placeholder:text-bp_neutral-700"/>
          </div>
        </div>
      </div>

      <div class="card-profile">
        <div class="flex items-center space-x-2">
          <v-icon name="md-school-outlined" scale="1.5" />
          <p class="text-2xl font-bold">Dados Institucionais</p>
        </div>

        <hr class="border-bp_neutral-700" />
        <div class="space-y-2">
          <label class="text-lg font-bold" for="">Curso <span class="text-bp_primary-300">*</span></label>
          <div>
            <select
              class="py-4 card-options w-full bg-bp_neutral-800 text-bp_neutral-50"
              name="course"
              id=""
            >
              <option value="TI">TI</option>
            </select>
          </div>
        </div>
        <div class="space-y-2">
          <div class="flex space-x-2">
            <label class="text-lg font-bold" for="">Histórico Acadêmico</label>
            <TooltipInfo>
              Recomendamos fortemente que você insira seu histórico acadêmico. Esses dados serão utilizados para o preenchimento automático de disciplinas cursadas.
            </TooltipInfo>
          </div>
          <BpInputUpload name="historic" placeholder="Histórico" v-model="refInputHistory" />
          <span class="text-bp_neutral-400">Para preenchimento automático de disciplinas.</span>
        </div>
        <div class="space-y-2">
          <div class="flex space-x-2">
            <label class="text-lg font-bold" for="">Índices Acadêmico</label>
            <TooltipInfo>
              Recomendamos fortemente que você insira seus índices acadêmicos. Esses dados serão utilizados para ajudar a calcular sua probabilidade de entrar em uma turma, com base nos índices de outros usuários na plataforma.
            </TooltipInfo>
          </div>
          <BpInputUpload name="indices" placeholder="Índices" v-model="refInputAcademicIndexes" />
        </div>
      </div>

      <div class="card-profile">
        <div class="flex items-center space-x-2">
          <v-icon name="md-assignmentturnedin-outlined" scale="1.5" />
          <p class="text-2xl font-bold">Termos e Condições</p>
        </div>

        <hr class="border-bp_neutral-700" />
        <div>
          <p class="text-bp_neutral-400">
            É importante ler os termos e condições antes de aceitar, pois eles contêm informações
            cruciais sobre seus direitos e responsabilidades ao usar nosso serviço. garantir que
            você esteja ciente de como seus dados serão utilizados e quais são suas obrigações.
          </p>
        </div>
        <div class="flex items-center space-x-2 mt-4">
          <div class="relative flex items-center">
            <input
              type="checkbox"
              v-model="acceptedTerms"
              class="cursor-pointer w-6 h-6 appearance-none bg-bp_neutral-800 border-2 border-bp_neutral-600 rounded-lg checked:bg-bp_neutral-500 focus:ring-2 focus:ring-bp_neutral-500"
            />
            <v-icon
              v-if="acceptedTerms"
              name="md-check-outlined"
              scale="1.2"
              class="absolute top-0 left-0 text-white pointer-events-none"
            />
          </div>
          <label class="text-lg font-bold" for="terms-checkbox">
            Aceitar <span class="text-bp_primary-600">Termos e Condições</span>
          </label>
        </div>
      </div>
    </div>
    <div class="grid">
      <BpButton class="w-40 justify-self-end" type="primary" size="medium">Avançar</BpButton>
    </div>
  </main>
  </div>
  
</template>
