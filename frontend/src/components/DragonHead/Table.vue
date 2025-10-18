<template>
  <el-table-column 
    prop="size" 
    label="Size" 
    width="120"
    align="center"
  >
    <template #default="{ row }">
      <el-tag v-if="row.size !== null" :type="row.size >= 0 ? 'success' : 'danger'">
        {{ row.size }}
      </el-tag>
      <el-tag v-else type="info" effect="plain">
        <el-icon><QuestionFilled /></el-icon>
        Unknown
      </el-tag>
    </template>
  </el-table-column>
</template>