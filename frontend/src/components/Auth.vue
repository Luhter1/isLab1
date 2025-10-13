
<script lang="ts" setup>
import { reactive, ref } from 'vue'
import type { FormInstance, FormRules } from 'element-plus'
import { SingInUpDto } from '../interfaces/dto/auth/SingInUpDto'
import { useUserStore } from '@/stores/userStore'


interface Props {
  msg: string
  auth_func: (userData: SingInUpDto) => Promise<any>
}
const props = defineProps<Props>()

const userStore = useUserStore()

const ruleFormRef = ref<FormInstance>()
const user = reactive<SingInUpDto>({
  username: '',
  password: ''
});


const rules = reactive<FormRules<SingInUpDto>>({
  username: [
    { required: true, message: 'Input username', trigger: 'blur' },
    { min: 3, message: 'Length should be 3 to 5', trigger: 'blur' },
  ],
  password: [
    {required: true, message: 'Input password', trigger: 'blur'},
    {min: 6, message: 'Length should more than 6', trigger: 'blur'},
  ],
})

const submitForm = async (formEl: FormInstance | undefined) => {
  if (!formEl) return
  await formEl.validate(async (valid) => {
    if (valid) {
      userStore.setAuthentication(await props.auth_func(user))
    } else {
      console.log('error submit!')
    }
  })
}
</script>

<template>
  <el-form
    ref="ruleFormRef"
    style="max-width: 600px"
    :model="user"
    :rules="rules"
    label-width="auto"
  >

    <el-text class="mx-1" size="large">{{ props.msg }}</el-text>

    <el-form-item label="Username" prop="username">
      <el-input v-model="user.username" />
    </el-form-item>

    <el-form-item label="Password" prop="password">
      <el-input v-model="user.password" type="password" autocomplete="off" />
    </el-form-item>

    <el-form-item>
      <el-button type="primary" @click="submitForm(ruleFormRef)">Submit</el-button>
    </el-form-item>

  </el-form>
</template>
