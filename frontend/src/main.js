import './assets/main.css'

import { createApp } from 'vue'
import { createPinia } from 'pinia'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'

import App from './App.vue'
import router from './config/router'
import { useUserStore } from '@/stores/userStore'
import DragonService from '@/services/DragonService'

const app = createApp(App)

app.use(createPinia())
await DragonService.fetchObjects()
const userStore = useUserStore()
userStore.initializeFromStorage()

app.use(router)
app.use(ElementPlus)


// Register all icons
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
  app.component(key, component)
}

app.mount('#app')
