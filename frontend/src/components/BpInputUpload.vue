<script setup>
import { ref } from 'vue';

const props = defineProps(['placeholder', 'inputName', 'class'])
const model = defineModel()
const fileInput = ref(null);
const fileStatusText = ref(props.placeholder);

const triggerFileInput = (event) => {
    if (fileStatusText.value === props.placeholder) fileInput.value.click();
    else clearFileChanged(event)
};

const handleFileChange = (event) => {
    const file = event.target.files[0];
    fileStatusText.value = processStringFileStatus(file.name)
    model.value = file
};

const clearFileChanged = (event) => {
    event.preventDefault()
    fileInput.value.value = ''
    model.value = ''
    fileStatusText.value = props.placeholder
}

const processStringFileStatus = (fileName) => {
    if (fileName.length > 22) return `${fileName.slice(0, 22)}...`
    else return fileName
}

</script>

<template>
    <input type="file" ref="fileInput" class="hidden" :id="props.inputName" :name="props.inputName"
        @change="handleFileChange" />
    <label :for="props.inputName"
        class="inline-block py-4 pl-4 rounded-[5px] border border-bp_neutral-700 w-full text-base text-bp_neutral-700 relative cursor-text"
        :class="{ [props.class]: true, 'transition-colors text-bp_primary-50': fileStatusText !== props.placeholder }">
        {{ fileStatusText }}
        <v-icon v-if="fileStatusText !== props.placeholder" name="md-check" class="text-green-400" />
        <button @click="triggerFileInput" class="bg-bp_neutral-800 h-full absolute top-0 right-0 w-12">
            <v-icon v-if="fileStatusText === props.placeholder" name="md-download-outlined"
                class="text-bp_primary-50" />
            <v-icon v-else name="md-close-outlined" class="text-bp_primary-50" />
        </button>
    </label>
</template>