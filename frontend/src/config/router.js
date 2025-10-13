import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '@/views/HomeView.vue'
import SingIn from '@/views/SingIn.vue'
import SingUp from '@/views/SingUp.vue'
import ViewObject from '@/views/sidebar/ViewContainer.vue'
import { logout } from '@/services/AuthService'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView,
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
    {
      path: '/logout',
      name: 'logout',
      beforeEnter: (to, from, next) => {
        logout()
        next('/')
      },
      component: () => null,
    },
    {
      path: '/view',
      name: 'view',
      component: ViewObject,
    },
  ],
})

export default router
