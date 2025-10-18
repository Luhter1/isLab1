<template>
  <el-table-column 
    prop="name" 
    label="Name" 
    min-width="100"
    sortable="custom"
  >
    <template #default="{ row }">
      <strong>{{ row.name }}</strong>
    </template>
  </el-table-column>

  <!-- Отдельная колонка X -->
  <el-table-column 
    prop="x" 
    label="X" 
    min-width="120"
    align="center"
  >
    <template #default="{ row }">
      <el-tag :type="row.x >= 0 ? 'success' : 'danger'">
        {{ row.x.toFixed(2) }}
      </el-tag>
    </template>
  </el-table-column>
  
  <!-- Отдельная колонка Y -->
  <el-table-column 
    prop="y" 
    label="Y" 
    min-width="120"
    align="center"
  >
    <template #default="{ row }">
      <el-tag v-if="row.y !== null" :type="row.y >= 0 ? 'success' : 'danger'">
        {{ row.y.toFixed(2) }}
      </el-tag>
      <span v-else class="empty-value">Unknown</span>
    </template>
  </el-table-column>

  <!-- Отдельная колонка Z -->
  <el-table-column 
    prop="z" 
    label="Z" 
    min-width="120"
    align="center"
  >
    <template #default="{ row }">
      <el-tag :type="row.z >= 0 ? 'success' : 'danger'">
        {{ row.z.toFixed(2) }}
      </el-tag>
    </template>
  </el-table-column>
</template>

<style>
.empty-value {
  color: #c0c4cc;
  font-style: italic;
}
</style>