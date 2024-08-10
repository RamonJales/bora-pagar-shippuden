<template>
  <div
    class="bg-bp_neutral-800 rounded-md h-44 w-72 flex flex-col gap-4 items-center justify-center"
  >
    <p class="w-11/12 text-end">
      <span class="text-sm bg-bp_neutral-700 px-1.5 py-1 rounded-md font-semibold">60h</span>
    </p>
    <p class="text-center font-bold w-11/12">Práticas de Leitura e Escrita em Português I</p>
    <div
      class="w-full border-t-2 border-bp_neutral-700 flex pt-2 items-center justify-center gap-4"
    >
      <button
        type="button"
        class="flex items-center justify-center gap-2 p-2 text-sm bg-[#97E8AE] bg-opacity-15 rounded-md text-[#97E8AE]"
      >
        <v-icon name="md-checkbox-outlined" scale="0.8" />
        Concluída
      </button>
      <button
        type="button"
        class="flex items-center gap-2 p-2 text-sm bg-bp_danger-light bg-opacity-15 rounded-md text-bp_danger-light"
      >
        <v-icon name="md-disabledbydefault-outlined" scale="0.8" />
        Remover
      </button>
    </div>
  </div>
</template>
