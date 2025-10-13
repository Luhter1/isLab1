import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import SingIn from '../views/SingIn.vue'
import SingUp from '../views/SingUp.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView,
    },
    {
      path: '/about',
      name: 'about',
      component: () => import('../views/AboutView.vue'),
    },
    {
      path: '/sing-in',
      name: 'sing-in',
      component: SingIn,
    },
    {
      path: '/sing-up',
      name: 'sing-up',
      component: SingUp,
    },
  ],
})

export default router
