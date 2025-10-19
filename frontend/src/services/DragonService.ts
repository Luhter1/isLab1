import { DragonCreateDto } from '@/interfaces/dto/dragons/DragonCreateDto'
import { DragonDto } from '@/interfaces/dto/dragons/DragonDto'
import { DragonUpdateDto } from '@/interfaces/dto/dragons/DragonUpdateDto'
import DragonController from '@/controllers/DragonController'
import CrudService from '@/interfaces/crud/CrudService';
import SpecialOperationController from '@/controllers/SpecialOperationController'
import type { DragonResultDto } from '@/interfaces/dto/specialoperations/DragonResultDto';
import type { AverageAgeDto } from '@/interfaces/dto/specialoperations/AverageAgeDto';
import { ElMessage } from 'element-plus'


class DragonService extends CrudService<DragonDto, DragonCreateDto, DragonUpdateDto> {
  constructor() {
    super("Dragon", DragonController);
  }

  handleObjectKill(object: DragonDto) {
    this.handleObjectUpdated(object)
  }

  async getAverageAge(){
    const res = await SpecialOperationController.averageAge()
    return res.data as AverageAgeDto
  }

  async getDeepestCaveDragon(){
    const res = await SpecialOperationController.deepestCaveDragon()
    return res.data as DragonResultDto
  }

}

export default new DragonService()