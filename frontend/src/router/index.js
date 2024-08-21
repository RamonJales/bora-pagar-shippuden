import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import ConsultSubjectsView from '@/views/ConsultSubjectsView.vue'
import DashboardView from '@/views/DashboardView.vue'
import FriendsScreen from '@/views/FriendsScreen.vue'
import CompletedProfileView from '@/views/CompletedProfileView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView
    },
    {
      path: '/disciplinas',
      name: 'subjects',
      component: ConsultSubjectsView
    },
    {
      path: '/dashboard',
      name: 'dashboard',
      component: DashboardView
    },
    {
      path: '/amigos',
      name: 'friends-screen',
      component: FriendsScreen
    },
    {
      path: '/perfil',
      name: 'completed-profile',
      component: CompletedProfileView
    }
  ]
})

export default router
