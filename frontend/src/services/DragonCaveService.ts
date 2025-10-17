import { DragonCaveCreateDto } from '@/interfaces/dto/dragoncaves/DragonCaveCreateDto'
import { DragonCaveDto } from '@/interfaces/dto/dragoncaves/DragonCaveDto'
import { DragonCaveUpdateDto } from '@/interfaces/dto/dragoncaves/DragonCaveUpdateDto'
import DragonCaveController from '@/controllers/DragonCaveController'
import CrudService from '@/interfaces/crud/CrudService';

class DragonCaveService extends CrudService<DragonCaveDto, DragonCaveCreateDto, DragonCaveUpdateDto> {
    constructor() {
        super("DragonCave", DragonCaveController);
    }

    getTable() {

    }
}

export default new DragonCaveService()