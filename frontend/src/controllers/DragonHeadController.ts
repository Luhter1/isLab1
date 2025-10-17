import { DragonHeadDto } from '@/interfaces/dto/dragonheads/DragonHeadDto';
import { DragonHeadCreateDto } from '@/interfaces/dto/dragonheads/DragonHeadCreateDto';
import { DragonHeadUpdateDto } from '@/interfaces/dto/dragonheads/DragonHeadUpdateDto';
import CrudController from '@/interfaces/crud/CrudController';


export default new CrudController<DragonHeadDto, DragonHeadCreateDto, DragonHeadUpdateDto>("dragon-heads")
