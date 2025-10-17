import { DragonDto } from '@/interfaces/dto/dragons/DragonDto';
import { DragonCreateDto } from '@/interfaces/dto/dragons/DragonCreateDto';
import { DragonUpdateDto } from '@/interfaces/dto/dragons/DragonUpdateDto';
import CrudController from '@/interfaces/crud/CrudController';


export default new CrudController<DragonDto, DragonCreateDto, DragonUpdateDto>("dragons")