<script setup>
import { ref, computed } from 'vue'
import { useUserStore } from '@/stores/userStore'
import CreateForm from '@/components/AdminRequest/CreateForm.vue'
import PendingRequests from '@/components/AdminRequest/PendingRequests.vue'
import AllRequests from '@/components/AdminRequest/AllRequests.vue'

const userStore = useUserStore()
const activeTab = ref('create')

const isAdmin = computed(() => {
  return userStore.getUserRole == 'ROLE_ADMIN'
})

if (isAdmin.value) {
  activeTab.value = 'pending'
}
</script>

<template>
  <div class="admin-requests-view">
    <el-tabs v-model="activeTab" type="card">
      <el-tab-pane label="Заявки на рассмотрении" name="pending" v-if="isAdmin">
        <PendingRequests />
      </el-tab-pane>
      <el-tab-pane label="Подать заявку" name="create" v-else>
        <CreateForm />
      </el-tab-pane>
      <el-tab-pane label="Поданые заявки" name="created">
        <AllRequests />
      </el-tab-pane>
    </el-tabs>
  </div>
</template>


<style scoped>
.admin-requests-view {
  background: white;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}
</style>
