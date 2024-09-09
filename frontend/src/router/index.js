import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import ConsultSubjectsView from '@/views/ConsultSubjectsView.vue'
import DashboardView from '@/views/DashboardView.vue'
import CompletedProfileView from '@/views/CompletedProfileView.vue'
import FriendsView from '@/views/FriendsView.vue'

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
      name: 'friends',
      component: FriendsView
    },
    {
      path: '/completar-perfil',
      name: 'completed-profile',
      component: CompletedProfileView
    }
  ]
})

export default router
