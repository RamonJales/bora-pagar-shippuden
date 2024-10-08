import './assets/main.css'

import { createApp } from 'vue'
import App from './App.vue'
import router from './router'

import { OhVueIcon, addIcons } from 'oh-vue-icons'
import * as MdIcons from 'oh-vue-icons/icons/md'
import VueTailwindDatePicker from "vue-tailwind-datepicker";

const Md = Object.values({ ...MdIcons })
addIcons(...Md)

const app = createApp(App)

app.use(router)
app.component('v-icon', OhVueIcon)
app.use(VueTailwindDatePicker);
app.mount('#app')
