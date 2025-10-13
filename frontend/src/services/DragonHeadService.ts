import { DragonHeadCreateDto } from '@/interfaces/dto/dragonheads/DragonHeadCreateDto'
import { DragonHeadDto } from '@/interfaces/dto/dragonheads/DragonHeadDto'
import { CrudService } from '@/interfaces/crud/CrudService';
import { DragonHeadUpdateDto } from '@/interfaces/dto/dragonheads/DragonHeadUpdateDto'
import DragonHeadController from '@/controllers/DragonHeadController'

const DragonHead = CrudService<DragonHeadDto, DragonHeadCreateDto, DragonHeadUpdateDto>(
  'DragonHead',
  DragonHeadController
);

export const {
  getAll: getAllDragonHead,
  getById: getDragonHead,
  create: createDragonHead,
  update: updateDragonHead,
  Delete: deleteDragonHead,
} = DragonHead