// stores/userStore.ts
import { defineStore } from 'pinia'
import User from '../interfaces/models/User'
import { SingInUpDto } from '../interfaces/dto/auth/SingInUpDto'
import { AuthenticationDto } from '../interfaces/dto/auth/AuthenticationDto'

interface UserState {
    currentUser: User | null
    token: String | null
    credentials: SingInUpDto | null
    isAuthenticated: boolean
}

export const useUserStore = defineStore('user', {
  state: (): UserState => ({
    currentUser: null,
    token: null,
    credentials: null,
    isAuthenticated: false
  }),

  getters: {
    getUser: (state) => state.currentUser,
    getToken: (state) => state.token,
    getCredentials: (state) => state.credentials,
    isLoggedIn: (state) => state.isAuthenticated,
    getUsername: (state) => state.currentUser?.username || '',
    getUserRole: (state) => state.currentUser?.role || '',
  },

  actions: {
    setAuthentication(auth: AuthenticationDto) {
      this.currentUser = auth.user
      this.token = auth.token
      this.isAuthenticated = true
      localStorage.setItem('auth', JSON.stringify(auth))
    },

    clearAuthentication() {
      this.currentUser = null
      this.isAuthenticated = false
      localStorage.removeItem('auth')
    },

    setCredentials(credentials: SingInUpDto) {
      this.credentials = credentials
      sessionStorage.setItem('credentials', JSON.stringify(credentials))
    },

    clearCredentials() {
      this.credentials = null
      sessionStorage.removeItem('credentials')
    },

    initializeFromStorage() {
        const storedData = localStorage.getItem('auth')

        if (storedData) {
            const authData: AuthenticationDto = JSON.parse(storedData);
            this.user = authData.user
            this.token = authData.token
            this.isAuthenticated = true
        }
    }
  }
})