import { DragonHeadCreateDto } from '@/interfaces/dto/dragonheads/DragonHeadCreateDto'
import { DragonHeadDto } from '@/interfaces/dto/dragonheads/DragonHeadDto'
import { DragonHeadUpdateDto } from '@/interfaces/dto/dragonheads/DragonHeadUpdateDto'
import DragonHeadController from '@/controllers/DragonHeadController'
import CrudService from '@/interfaces/crud/CrudService';

class DragonHeadService extends CrudService<DragonHeadDto, DragonHeadCreateDto, DragonHeadUpdateDto> {
    constructor() {
        super("DragonHead", DragonHeadController);
    }

    handleObjectKill(object: DragonHeadDto) {
    }
}

export default new DragonHeadService()