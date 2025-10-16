// File: GenericGetByIdForm.vue

<script lang="ts" setup generic="TDto extends { id: any }">
import { reactive, ref, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import type { FormInstance, FormRules } from 'element-plus'
import type { Component } from 'vue'
import validatorID from '@/utils/validateId'

interface ViewId {
  id: number;
}

const props = defineProps<{
  getT: (id: number) => Promise<TDto>,
  cardT: Component,
  formLabel?: string,
  initialId?: number | null
}>()

const route = useRoute()
const router = useRouter()

const data = ref<TDto>()
const ruleFormRef = ref<FormInstance>()
const view = reactive<ViewId>({
  id: null
});

const rules = reactive<FormRules<ViewId>>({
  id: [{ validator: validatorID, trigger: 'blur' }]
})

// Загрузка данных
const loadData = async (id: number) => {
  try {
    data.value = await props.getT(id)
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
      updateUrl(view.id)
    }
  })
}

// Следим за изменениями query параметра id в URL
watch(() => route.query.id, async (newId) => {
  if (newId) {
    const id = Number(newId)
    if (!isNaN(id) && id > 0) {
      view.id = id
      await loadData(id)
    }
  } else {
    view.id = null
    data.value = undefined
  }
}, { immediate: true })
</script>

<template>
  <el-form
    ref="ruleFormRef"
    style="max-width: 600px"
    :model="view"
    :rules="rules"
    label-width="auto"
  >
    <el-form-item label="ID" prop="id">
      <el-input v-model.number="view.id" type="number" placeholder="Enter ID"/>
    </el-form-item>

    <el-form-item>
      <el-button type="primary" @click="submitForm(ruleFormRef)">Submit</el-button>
    </el-form-item>
  </el-form>

  <component
    :is="props.cardT"
    :obj="data"
    v-if="data"
  />
</template>