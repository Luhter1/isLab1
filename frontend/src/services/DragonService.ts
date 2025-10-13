import { DragonCreateDto } from '@/interfaces/dto/dragons/DragonCreateDto'
import { DragonDto } from '@/interfaces/dto/dragons/DragonDto'
import { CrudService } from '@/interfaces/crud/CrudService';
import { DragonUpdateDto } from '@/interfaces/dto/dragons/DragonUpdateDto'
import DragonController from '@/controllers/DragonController'

const Dragon = CrudService<DragonDto, DragonCreateDto, DragonUpdateDto>(
  'location',
  DragonController
);

export const {
  getAll: getAllDragon,
  getById: getDragon,
  create: createDragon,
  update: updateDragon,
  Delete: deleteDragon,
} = Dragon