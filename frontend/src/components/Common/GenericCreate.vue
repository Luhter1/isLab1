// File: GenericGetByIdForm.vue

<script lang="ts" setup generic="TCreateDto extends { id: any }, TDto extends { id: any }">
import { ref } from 'vue'
import type { FormInstance, FormRules } from 'element-plus'
import type { Component } from 'vue'


const props = defineProps<{
  createT: (location: TCreateDto) => Promise<TDto>,
  formFieldsT: Component,
  formLabel?: string,
}>()

const data = ref<TDto>()
const ruleFormRef = ref<FormInstance>()
const Create = ref<TCreateDto>({} as TCreateDto);

// const validateLocation = (rule: any, value: TCreateDto, callback: any) => {
//   if (!value) {
//     return callback(new Error('Input ID'))
//   }
//   if (value < 1) {
//     return callback(new Error('ID must be greater than 0'))
//   }
//   callback()
// }

// const rules = reactive<FormRules<CreateId>>({
//   location: [{ validator: validateLocation, trigger: 'blur' }]
// })

// Загрузка данных
const loadData = async (location: TCreateDto) => {
  try {
    data.value = await props.createT(location)
  } catch (error) {
    data.value = undefined
  }
}

// При отправке формы меняем только URI, watch получит данные сам
const submitForm = async (formEl: FormInstance | undefined) => {
  if (!formEl) return
  await formEl.validate(async (valid) => {
    if (valid) {
      await loadData(Create.value)
    }
  })
}

</script>

<template>
  <el-form
    ref="ruleFormRef"
    style="max-width: 600px"
    :model="Create"
    label-width="auto"
  >
    <component
      :is="props.formFieldsT"
      v-model:obj="Create"
    />

    <el-form-item>
      <el-button type="primary" @click="submitForm(ruleFormRef)">Create</el-button>
    </el-form-item>
  </el-form>
</template>