import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '@/views/HomeView.vue'
import SingIn from '@/views/SingIn.vue'
import SingUp from '@/views/SingUp.vue'
import ViewContainer from '@/views/sidebar/ViewContainer.vue'
import DeleteContainer from '@/views/sidebar/DeleteContainer.vue'
import CreateContainer from '@/views/sidebar/CreateContainer.vue'
import UpdateContainer from '@/views/sidebar/UpdateContainer.vue'
import Analitics from '@/views/Analitics.vue'
import KillerTeam from '@/views/KillerTeam.vue'
import { logout } from '@/services/AuthService'
import { useUserStore } from '@/stores/userStore'

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
      meta: { requiresAuth: true }
    },
    {
      path: '/view/:type',
      name: 'view',
      component: ViewContainer,
      props: route => ({ 
        type: route.params.type,
        id: route.query.id ? Number(route.query.id) : null
      })
    },
    {
      path: '/delete/:type',
      name: 'delete',
      component: DeleteContainer,
      props: route => ({ 
        type: route.params.type,
        id: route.query.id ? Number(route.query.id) : null
      }),
      meta: { requiresAuth: true }
    },
    {
      path: '/create/:type',
      name: 'create',
      component: CreateContainer,
      props: route => ({ 
        type: route.params.type,
        id: route.query.id ? Number(route.query.id) : null
      }),
      meta: { requiresAuth: true }
    },
    {
      path: '/update/:type',
      name: 'update',
      component: UpdateContainer,
      props: route => ({ 
        type: route.params.type,
        id: route.query.id ? Number(route.query.id) : null
      }),
      meta: { requiresAuth: true }
    },
    {
      path: '/analytics',
      name: 'analytics',
      component: Analitics,
    },
    {
      path: '/killer-team',
      name: 'killer-team',
      component: KillerTeam,
    },
  ],
})

router.beforeEach((to, from, next) => {
  // Проверяем, требует ли маршрут аутентификации
  const userStore = useUserStore()

  if (to.matched.some(record => record.meta.requiresAuth)) {
    if (!userStore.isLoggedIn) {
      next({
        path: '/sing-in',
      })
    } else {
      next()
    }
  } else {
    next()
  }
})
export default router
