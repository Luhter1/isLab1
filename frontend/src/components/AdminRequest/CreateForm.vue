<script setup>
import { ref } from 'vue'
import { ElNotification } from 'element-plus'
import AdminRequestController from '@/controllers/AdminRequestController'

const loading = ref(false)
const requestStatus = ref(null)

const submitRequest = async () => {
  loading.value = true
  requestStatus.value = null
  
  try {
    await AdminRequestController.create()
    requestStatus.value = 'success'
    ElNotification({
      title: 'Успех',
      message: 'Заявка на получение прав администратора успешно отправлена',
      type: 'success'
    })
  } catch (error) {
    console.error('Error submitting admin request:', error)

    if (error.response && error.response.status === 409) {
      requestStatus.value = 'already-exists'
    } else {
      requestStatus.value = 'error'
    }
  } finally {
    loading.value = false
  }
}
</script>

<template>
  <div class="admin-request-form">
    <h2>Заявка на получение прав администратора</h2>
    <p>Вы можете подать заявку на получение прав администратора. <br>Администратор рассмотрит вашу заявку.</p>
    
    <el-alert
      v-if="requestStatus === 'success'"
      title="Заявка успешно отправлена"
      type="success"
      show-icon
      closable
      @close="requestStatus = null"
    >
      Ваша заявка была успешно отправлена и будет рассмотрена администратором.
    </el-alert>
    
    <el-alert
      v-if="requestStatus === 'error'"
      title="Ошибка при отправке заявки"
      type="error"
      show-icon
      closable
      @close="requestStatus = null"
    >
      Произошла ошибка при отправке заявки. Пожалуйста, попробуйте позже.
    </el-alert>
    
    <el-alert
      v-if="requestStatus === 'already-exists'"
      title="Заявка уже существует"
      type="warning"
      show-icon
      closable
      @close="requestStatus = null"
    >
      У вас уже есть активная заявка на получение прав администратора.
    </el-alert>
    
    <div class="form-actions">
      <el-button 
        type="primary" 
        @click="submitRequest" 
        :loading="loading"
        :disabled="requestStatus === 'success'"
      >
        Подать заявку
      </el-button>
    </div>
  </div>
</template>

<style scoped>
.admin-request-form {
  max-width: 600px;
  margin: 0 auto;
  padding: 20px;
  background: white;
  border-radius: 8px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.form-actions {
  margin-top: 20px;
  text-align: center;
}

.el-alert {
  margin-bottom: 20px;
}
</style>
