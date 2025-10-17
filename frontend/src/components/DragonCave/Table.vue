<template>
  <el-table-column 
    prop="depth" 
    label="Depth" 
    width="120"
    align="center"
  >
    <template #default="{ row }">
      <el-tag :type="row.depth >= 0 ? 'success' : 'danger'">
        {{ row.depth }}
      </el-tag>
    </template>
  </el-table-column>
</template>