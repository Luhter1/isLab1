<script lang="ts" setup generic="TUpdateDto, TDto extends { id: any }">
import { ref, computed, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElNotification } from 'element-plus'
import type { FormInstance, FormRules } from 'element-plus'
import type { Component } from 'vue'
import EntitySelectField from '@/components/Common/EntitySelectField.vue'



const props = defineProps<{
  service,
  tableComponent: Component,
  updateT: (id: number, location: TUpdateDto) => Promise<TDto>,
  formFieldsT: Component,
  formLabel?: string,
  validationRules?: FormRules,
}>()

const route = useRoute()
const router = useRouter()

const data = ref<TDto>()
const ruleFormRef = ref<FormInstance>()
const Update = ref<TUpdateDto>({} as TUpdateDto);
const UpdateId = ref(null);

const localRules = computed<FormRules>(() => {
  const rules: FormRules = { ...props.validationRules }
  
  rules.UpdateId = [
    { 
      validator: (rule, value, callback) => {
        value = UpdateId.value
        if(!value){
          callback(new Error('ID is required'))
        }else if (!Number.isInteger(value) || value <= 0) {
          callback(new Error('ID must be a positive integer'))
        } else {
          callback()
        }
      }, 
      trigger: 'change' 
    }
  ]
  
  return rules
})

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

const submitForm = async (formEl: FormInstance | undefined) => {
  if (!formEl) return

  await formEl.validate(async (valid) => {
    if (valid) {
      try {
        data.value = await props.updateT(UpdateId.value, Update.value)
        updateUrl(UpdateId.value)
        formEl.resetFields()
        ElNotification({
          title: props.formLabel,
          message: 'Updated object with ID: ' + data.value.id,
          type: 'success',
          offset: 80,
          duration: 0,
        })
      } catch (error) {
        data.value = undefined
      }
    }
  })
}

// Следим за изменениями query параметра id в URL
watch(() => route.query.id, async (newId) => {
  if (newId) {
    const id = Number(newId)
    if (!isNaN(id) && id > 0) {
      UpdateId.value = id
    }
  } else {
    UpdateId.value = null
    data.value = undefined
  }
}, { immediate: true })
</script>

<template>
  <el-form
    ref="ruleFormRef"
    style="max-width: 600px"
    :model="Update"
    :rules="localRules"
    label-width="auto"
  >
    <el-form-item label="ID" prop="UpdateId">
      <EntitySelectField
      v-model="UpdateId"
      label="Select ID"
      placeholder="Нажмите для выбора ID обьекта"
      :service="service"
      :table-component="tableComponent"
      />
    </el-form-item>

    <component
      :is="props.formFieldsT"
      v-model:obj="Update"
    />

    <el-form-item>
      <el-button type="primary" @click="submitForm(ruleFormRef)">Update</el-button>
    </el-form-item>
  </el-form>
</template>