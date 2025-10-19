import { CoordinateCreateDto } from '@/interfaces/dto/coordinates/CoordinateCreateDto'
import { CoordinateDto } from '@/interfaces/dto/coordinates/CoordinateDto'
import { CoordinateUpdateDto } from '@/interfaces/dto/coordinates/CoordinateUpdateDto'
import CoordinateController from '@/controllers/CoordinatesController'
import CrudService from '@/interfaces/crud/CrudService';

class CoordinateService extends CrudService<CoordinateDto, CoordinateCreateDto, CoordinateUpdateDto> {
    constructor() {
        super("Coordinate", CoordinateController);
    }

    handleObjectKill(object: CoordinateDto) {
    }
}

export default new CoordinateService()