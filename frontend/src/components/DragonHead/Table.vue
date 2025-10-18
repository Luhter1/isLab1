<template>
  <el-table-column 
    prop="size" 
    label="Size" 
    min-width="120"
    align="center"
  >
    <template #default="{ row }">
      <el-tag v-if="row.size !== null" :type="row.size >= 0 ? 'success' : 'danger'">
        {{ row.size }}
      </el-tag>
      <span v-else class="empty-value">Unknown</span>
    </template>
  </el-table-column>
</template>

<style>
.empty-value {
  color: #c0c4cc;
  font-style: italic;
}
</style>