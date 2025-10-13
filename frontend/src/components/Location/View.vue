<script lang="ts" setup>
import { reactive, ref } from 'vue'
import { getLocation } from '@/services/LocationService'
import type { FormInstance, FormRules } from 'element-plus'
import { LocationDto } from '@/interfaces/dto/locations/LocationDto'
import cardLocation from './Card.vue'

interface ViewId {
  id: number;
}
const getData = ref<LocationDto>()

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
  id: [
    { validator: validateId, trigger: 'blur' },
  ]
})

const submitForm = async (formEl: FormInstance | undefined) => {
  if (!formEl) return
  await formEl.validate(async (valid) => {
    if (valid) {
      getData.value = await getLocation(view.id)
    }
  })
}
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
      <el-input v-model="view.id" type="number"/>
    </el-form-item>

    <el-form-item>
      <el-button type="primary" @click="submitForm(ruleFormRef)">Submit</el-button>
    </el-form-item>

  </el-form>

  <cardLocation :obj="getData" v-if="getData"/>
</template>