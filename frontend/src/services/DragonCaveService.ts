import { DragonCaveCreateDto } from '@/interfaces/dto/dragoncaves/DragonCaveCreateDto'
import { DragonCaveDto } from '@/interfaces/dto/dragoncaves/DragonCaveDto'
import { CrudService } from '@/interfaces/crud/CrudService';
import { DragonCaveUpdateDto } from '@/interfaces/dto/dragoncaves/DragonCaveUpdateDto'
import DragonCaveController from '@/controllers/DragonCaveController'

const DragonCave = CrudService<DragonCaveDto, DragonCaveCreateDto, DragonCaveUpdateDto>(
  'DragonCave',
  DragonCaveController
);

export const {
  getAll: getAllDragonCave,
  getById: getDragonCave,
  create: createDragonCave,
  update: updateDragonCave,
  Delete: deleteDragonCave,
} = DragonCave