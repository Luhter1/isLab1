// File: GenericGetByIdForm.vue

<script lang="ts" setup generic="TDto extends { id: any }">
import { reactive, ref, type Component } from 'vue'
import type { FormInstance, FormRules } from 'element-plus'

interface ViewId {
  id: number;
}

const props = defineProps<{
  getT: (id: number) => Promise<TDto>,
  cardT: Component,
  formLabel?: string
}>()

const data = ref<TDto>()
const ruleFormRef = ref<FormInstance>()
const view = reactive<ViewId>({
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

const rules = reactive<FormRules<ViewId>>({
  id: [{ validator: validateId, trigger: 'blur' }]
})

const submitForm = async (formEl: FormInstance | undefined) => {
  if (!formEl) return
  await formEl.validate(async (valid) => {
    if (valid) {
      try {
        data.value = await props.getT(view.id!)
      } catch (error) {
        data.value = undefined
      }
    }
  })
}
</script>

<template>
  <h3 v-if="formLabel">{{ formLabel }}</h3>
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