<script lang="ts" setup>
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import type { FormInstance, FormRules } from 'element-plus'
import { ElMessage } from 'element-plus'
import { User, Lock } from '@element-plus/icons-vue'
import type { SingInUpDto } from '@/interfaces/dto/auth/SingInUpDto'

const router = useRouter()

interface Props {
  msg: string
  auth_func: (userData: SingInUpDto) => Promise<any>
}
const props = defineProps<Props>()

const ruleFormRef = ref<FormInstance>()
const isLoading = ref(false)

const user = reactive<SingInUpDto>({
  username: '',
  password: ''
});

const rules = reactive<FormRules<SingInUpDto>>({
  username: [
    { required: true, message: 'Пожалуйста, введите имя пользователя', trigger: 'blur' },
    { min: 3, message: 'Имя пользователя должно быть не короче 3 символов', trigger: 'blur' },
  ],
  password: [
    { required: true, message: 'Пожалуйста, введите пароль', trigger: 'blur' },
    { min: 6, message: 'Пароль должен быть не короче 6 символов', trigger: 'blur' },
  ],
})

const submitForm = async (formEl: FormInstance | undefined) => {
  if (!formEl) return
  
  await formEl.validate(async (valid) => {
    if (valid) {
      isLoading.value = true;
      try {
        await props.auth_func(user)
        ElMessage({
          message: 'Успешная авторизация!',
          type: 'success',
        })

        router.push('/')
      } catch (error) {
        console.error("Auth error:", error)
        ElMessage.error('Ошибка авторизации. Проверьте логин или пароль.')
      } finally {
        isLoading.value = false;
      }
    }
  })
}
</script>

<template>
  <el-row justify="center" align="middle" class="auth-container">
    <el-col :xs="22" :sm="16" :md="12" :lg="8" :xl="6">
      
      <el-card class="auth-card">
        <template #header>
          <div class="card-header">
            <span>{{ props.msg }}</span>
          </div>
        </template>

        <el-form
          ref="ruleFormRef"
          :model="user"
          :rules="rules"
          label-position="top"
          @submit.prevent="submitForm(ruleFormRef)" 
        >
          <el-form-item label="Имя пользователя" prop="username">
            <el-input
              v-model="user.username"
              placeholder="Введите имя пользователя"
              :prefix-icon="User"
              size="large"
            />
          </el-form-item>

          <el-form-item label="Пароль" prop="password">
            <el-input
              v-model="user.password"
              type="password"
              placeholder="Введите пароль"
              autocomplete="off"
              show-password
              :prefix-icon="Lock"
              size="large"
            />
          </el-form-item>

          <el-form-item>
            <el-button
              type="primary"
              @click="submitForm(ruleFormRef)"
              :loading="isLoading"
              native-type="submit"
              style="width: 100%;"
              size="large"
            >
              {{ isLoading ? 'Проверка...' : 'Войти' }}
            </el-button>
          </el-form-item>
        </el-form>
      </el-card>

    </el-col>
  </el-row>
</template>

<style scoped>
.auth-container {
  min-height: 80vh;
}

.auth-card {
  border-radius: 12px;
}

.card-header {
  text-align: center;
  font-size: 1.5rem;
  font-weight: 600;
}

.el-form-item {
  margin-bottom: 20px;
}
</style>