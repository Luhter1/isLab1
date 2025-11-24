<script lang="ts" setup generic="TCreateDto, TDto extends { id: any }">
import { ref } from 'vue'
import { ElNotification } from 'element-plus'
import type { FormInstance, FormRules } from 'element-plus'
import type { Component } from 'vue'


const props = defineProps<{
  createT: (location: TCreateDto) => Promise<TDto>,
  formFieldsT: Component,
  formLabel?: string,
  validationRules?: FormRules,
}>()

const data = ref<TDto>()
const ruleFormRef = ref<FormInstance>()
const Create = ref<TCreateDto>({} as TCreateDto);

const submitForm = async (formEl: FormInstance | undefined) => {
  if (!formEl) return
  
  await formEl.validate(async (valid) => {
    if (valid) {
      try {
        data.value = await props.createT(Create.value)
        formEl.resetFields()
        ElNotification({
          title: props.formLabel,
          message: 'Created object with ID: ' + data.value.id,
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

</script>

<template>
  <el-form
    ref="ruleFormRef"
    style="max-width: 600px"
    :model="Create"
    :rules="validationRules"
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