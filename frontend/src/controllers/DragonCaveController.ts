
import { DragonCaveDto } from '@/interfaces/dto/dragoncaves/DragonCaveDto';
import { DragonCaveCreateDto } from '@/interfaces/dto/dragoncaves/DragonCaveCreateDto';
import { DragonCaveUpdateDto } from '@/interfaces/dto/dragoncaves/DragonCaveUpdateDto';
import CrudController from '@/interfaces/crud/CrudController';


export default new CrudController<DragonCaveDto, DragonCaveCreateDto, DragonCaveUpdateDto>("dragon-caves")