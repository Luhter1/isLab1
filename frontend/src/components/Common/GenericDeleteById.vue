// File: GenericGetByIdForm.vue

<script lang="ts" setup generic="TDto extends { id: any }">
import { reactive, ref, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import type { FormInstance, FormRules } from 'element-plus'

interface DeleteId {
  id: number;
}

const props = defineProps<{
  deleteT: (id: number) => Promise<TDto>,
  formLabel?: string,
}>()

const route = useRoute()
const router = useRouter()

const data = ref<TDto>()
const ruleFormRef = ref<FormInstance>()
const Delete = reactive<DeleteId>({
  id: null
});

const validateId = (rule: any, value: number, callback: any) => {
  if (!value) {
    return callback(new Error('Input ID'))
  }
  if (value < 1) {
    return callback(new Error('ID must be greater than 0'))
  }
  callback()
}

const rules = reactive<FormRules<DeleteId>>({
  id: [{ validator: validateId, trigger: 'blur' }]
})

// Загрузка данных
const loadData = async (id: number) => {
  try {
    data.value = await props.deleteT(id)
  } catch (error) {
    data.value = undefined
  }
}

// Изменение URI
const updateUrl = (id: number | null) => {
  const currentQuery = { ...route.query }
  
  if (id) {
    currentQuery.id = String(id)
  } else {
    delete currentQuery.id
  }

  router.push({ 
    path: route.path,
    query: currentQuery 
  })
}

// При отправке формы меняем только URI, watch получит данные сам
const submitForm = async (formEl: FormInstance | undefined) => {
  if (!formEl) return
  await formEl.validate(async (valid) => {
    if (valid) {
      updateUrl(Delete.id)
    }
  })
}

// Следим за изменениями query параметра id в URL
watch(() => route.query.id, async (newId) => {
  if (newId) {
    const id = Number(newId)
    if (!isNaN(id) && id > 0) {
      Delete.id = id
      await loadData(id)
    }
  } else {
    Delete.id = null
    data.value = undefined
  }
}, { immediate: true })
</script>

<template>
  <el-form
    ref="ruleFormRef"
    style="max-width: 600px"
    :model="Delete"
    :rules="rules"
    label-width="auto"
  >
    <el-form-item label="ID" prop="id">
      <el-input v-model.number="Delete.id" type="number" placeholder="Enter ID"/>
    </el-form-item>

    <el-form-item>
      <el-button type="primary" @click="submitForm(ruleFormRef)">Delete</el-button>
    </el-form-item>
  </el-form>
</template>