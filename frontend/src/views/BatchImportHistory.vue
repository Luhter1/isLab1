<script lang="ts" setup>
import { ref, onMounted, computed } from 'vue'
import { Clock } from '@element-plus/icons-vue'
import { State, getHistory } from '@/services/BatchImportHistoryService'



const loading = ref(false)
const tableData = computed(() => State.objects.content)
const totalItems = computed(() => State.objects.totalElements)

const formatDate = (date: string | null | undefined) => {
  if (!date) return '-'
  return new Date(date).toLocaleDateString('en-US', {
    year: 'numeric',
    month: 'short',
    day: 'numeric'
  })
}

const fetchHistory = async () => {
  loading.value = true
  try {
    await getHistory();
  } catch (error: any) {
    console.error('Failed to fetch import history:', error)
  } finally {
    loading.value = false
  }
}

const handlePageChange = async (page: number) => {
  State.currentPage = page
  await fetchHistory()
}

const handleSizeChange = async (size: number) => {
  State.pageSize = size
  State.currentPage = 1
  await fetchHistory()
}

onMounted(async () => {
  await fetchHistory()
})
</script>

<template>
  <div class="batch-import-history-page">
    <el-card>
      <template #header>
        <div class="card-header">
          <h2>
            <el-icon><Clock /></el-icon>
            Import History
          </h2>
        </div>
      </template>

      <el-table
        :data="tableData"
        v-loading="loading"
        style="width: 100%"
        :default-sort="{ prop: 'createdAt', order: 'descending' }"
      >
        <el-table-column prop="id" label="ID" width="80"/>
        
        <el-table-column prop="status" label="Status" width="120">
          <template #default="{ row }">
            <el-tag :type="row.status === 'SUCCESS' ? 'success' : 'danger'">
              {{ row.status }}
            </el-tag>
          </template>
        </el-table-column>

        <el-table-column prop="successfulOperations" label="Successful Operations" width="180">
          <template #default="{ row }">
            <el-tag type="success">
              {{ row.successfulOperations }}
            </el-tag>
          </template>
        </el-table-column>

        <el-table-column prop="createdBy.username" label="User" width="150">
          <template #default="{ row }">
            <div v-if="row.createdBy" class="user-cell">
              <span>{{ row.createdBy.username }}</span>
            </div>
            <span v-else class="empty-value">-</span>
          </template>
        </el-table-column>

        <el-table-column prop="createdAt" label="Created At" width="200">
          <template #default="{ row }">
            <div class="date-cell">
              <span>{{ formatDate(row.createdAt) }}</span>
            </div>
          </template>
        </el-table-column>
      </el-table>

      <el-pagination
        v-model:current-page="State.currentPage"
        v-model:page-size="State.pageSize"
        :page-sizes="[10, 20, 50, 100]"
        :total="totalItems"
        layout="total, sizes, prev, pager, next, jumper"
        class="pagination"
        @size-change="handleSizeChange"
        @current-change="handlePageChange"
      />
    </el-card>
  </div>
</template>

<style scoped lang="scss">
.batch-import-history-page {
  padding: 20px;
  max-width: 1400px;
  margin: 0 auto;

  .card-header {
    display: flex;
    align-items: center;
    gap: 10px;

    h2 {
      margin: 0;
      display: flex;
      align-items: center;
      gap: 10px;
    }
  }

  .user-cell {
    display: flex;
    align-items: center;
    gap: 8px;
  }

  .date-cell {
    cursor: help;
  }

  .empty-value {
    color: #999;
    font-style: italic;
  }

  .pagination {
    margin-top: 20px;
    display: flex;
    justify-content: center;
  }
}
</style>

