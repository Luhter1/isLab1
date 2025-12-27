<script setup>
import { ref, onMounted } from 'vue'
import { ElNotification } from 'element-plus'
import AdminRequestController from '@/controllers/AdminRequestController'
import { Status } from '@/interfaces/models/Status'

const requests = ref([])
const loading = ref(false)
const processingIds = ref([])
const currentPage = ref(1)
const pageSize = ref(10)
const totalItems = ref(0)

const formatDate = (date) => {
  return new Date(date).toLocaleString('ru-RU')
}

const getStatusTagType = (status) => {
  switch (status) {
    case Status.PENDING:
      return 'warning'
    case Status.APPROVED:
      return 'success'
    case Status.REJECTED:
      return 'danger'
    default:
      return 'info'
  }
}

const getStatusText = (status) => {
  switch (status) {
    case Status.PENDING:
      return 'Ожидает'
    case Status.APPROVED:
      return 'Одобрено'
    case Status.REJECTED:
      return 'Отклонено'
    default:
      return status
  }
}

const loadRequests = async () => {
  loading.value = true
  try {
    const response = await AdminRequestController.getPending(
      currentPage.value - 1, 
      pageSize.value, 
      ['createdAt,desc']
    )
    
    requests.value = response.data.content
    totalItems.value = parseInt(response.headers['x-total-count']) || 0
  } catch (error) {
    ElNotification({
      title: 'Ошибка',
      message: 'Не удалось загрузить заявки',
      type: 'error'
    })
  } finally {
    loading.value = false
  }
}

const processRequest = async (id, approved) => {
  processingIds.value.push(id)
  
  try {
    await AdminRequestController.process(id, approved)
    
    processingIds.value = processingIds.value.filter(item => item !== id)
    
    await loadRequests()
    
    ElNotification({
      title: 'Успех',
      message: `Заявка ${approved ? 'одобрена' : 'отклонена'}`,
      type: 'success'
    })
  } catch (error) {
    processingIds.value = processingIds.value.filter(item => item !== id)
    
    ElNotification({
      title: 'Ошибка',
      message: `Не удалось ${approved ? 'одобрить' : 'отклонить'} заявку`,
      type: 'error'
    })
  }
}

const handleSizeChange = (val) => {
  pageSize.value = val
  currentPage.value = 1
  loadRequests()
}

const handleCurrentChange = (val) => {
  currentPage.value = val
  loadRequests()
}

onMounted(() => {
  loadRequests()
})
</script>

<template>
  <div class="pending-requests">
    <h2>Заявки на получение прав администратора</h2>
    <p>Список заявок, ожидающих рассмотрения</p>
    
    <el-table 
      :data="requests" 
      v-loading="loading"
      style="width: 100%"
      stripe
    >
      <el-table-column prop="id" label="ID" width="80" />
      <el-table-column prop="user.username" label="Пользователь" width="200" />
      <el-table-column prop="createdAt" label="Дата создания" width="200">
        <template #default="scope">
          {{ formatDate(scope.row.createdAt) }}
        </template>
      </el-table-column>
      <el-table-column label="Статус" width="150">
        <template #default="scope">
          <el-tag :type="getStatusTagType(scope.row.status)">
            {{ getStatusText(scope.row.status) }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="Действия" width="200">
        <template #default="scope">
          <div class="action-buttons">
            <el-button 
              type="success" 
              size="small" 
              @click="processRequest(scope.row.id, true)"
              :loading="processingIds.includes(scope.row.id)"
            >
              Одобрить
            </el-button>
            <el-button 
              type="danger" 
              size="small" 
              @click="processRequest(scope.row.id, false)"
              :loading="processingIds.includes(scope.row.id)"
            >
              Отклонить
            </el-button>
          </div>
        </template>
      </el-table-column>
    </el-table>
    
    <div class="pagination-container">
      <el-pagination
        v-model:current-page="currentPage"
        v-model:page-size="pageSize"
        :total="totalItems"
        :page-sizes="[10, 20, 50]"
        layout="total, sizes, prev, pager, next"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
      />
    </div>
  </div>
</template>

<style scoped>
.pending-requests {
  background: white;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.action-buttons {
  display: flex;
  gap: 10px;
}

.pagination-container {
  margin-top: 20px;
  display: flex;
  justify-content: center;
}
</style>
