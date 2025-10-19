import { DragonCreateDto } from '@/interfaces/dto/dragons/DragonCreateDto'
import { DragonDto } from '@/interfaces/dto/dragons/DragonDto'
import { DragonUpdateDto } from '@/interfaces/dto/dragons/DragonUpdateDto'
import DragonController from '@/controllers/DragonController'
import CrudService from '@/interfaces/crud/CrudService';

class DragonService extends CrudService<DragonDto, DragonCreateDto, DragonUpdateDto> {
  constructor() {
    super("Dragon", DragonController);
  }

    handleObjectKill(object: DragonDto) {
      this.handleObjectUpdated(object)
    }

}

export default new DragonService()