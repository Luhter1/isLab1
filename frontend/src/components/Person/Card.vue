<script lang="ts" setup>
import { toRef } from 'vue'
import { PersonDto } from '@/interfaces/dto/people/PersonDto'
import cardLocation from '../Location/Card.vue'

interface Props {
  obj: PersonDto
}
const props = defineProps<Props>()
const data = toRef(props, 'obj')
</script>

<template>

    <el-card style="max-width: 480px">
      <template #header>
        <div class="card-header">
          <span>Location</span>
        </div>
      </template>
      <p :key="data.id" class="text item">{{ 'ID: ' + data.id }}</p>
      <template #footer>
          <p :key="data.name" class="text item">{{ 'Name: ' + data.name }}</p>
          <p :key="data.eyeColor" class="text item">{{ 'Eye color: ' + data.eyeColor }}</p>
          <p :key="data.hairColor" class="text item">{{ 'Hair color: ' + data.hairColor }}</p>
          <p :key="data.location.name" class="text item">{{ 'Location name: ' + data.location.name }}</p>
          <p :key="data.birthday.toString()" class="text item">{{ 'Birthday: ' + data.birthday.toString() }}</p>
          <p :key="data.height" class="text item">{{ 'Height: ' + data.height }}</p>
          <p :key="data.weight" class="text item">{{ 'Weight: ' + data.weight }}</p>
          <p :key="data.passportId" class="text item">{{ 'PassportId: ' + data.passportId }}</p>
          <p :key="data.createdBy.username " class="text item">{{ 'Creator: ' + data.createdBy.username }}</p>
      </template>
    </el-card>

    <component
      :is="cardLocation"
      :obj="data.location"
      v-if="data.location"
    />

</template>