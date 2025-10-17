import { DragonCreateDto } from '@/interfaces/dto/dragons/DragonCreateDto'
import { DragonDto } from '@/interfaces/dto/dragons/DragonDto'
import { DragonUpdateDto } from '@/interfaces/dto/dragons/DragonUpdateDto'
import DragonController from '@/controllers/DragonController'
import CrudService from '@/interfaces/crud/CrudService';

class DragonService extends CrudService<DragonDto, DragonCreateDto, DragonUpdateDto> {
  constructor() {
    super("Dragon", DragonController);

    this.pageSize = 10;
    this.currentPage = 1;
  }

  getTable() {
  }

  // handleDragonCreated(dragon) {
  //   this.dragons.push(dragon)
  //   this.totalDragons++
  // }

  // handleDragonUpdated(dragon) {
  //   const index = this.dragons.findIndex(d => d.id === dragon.id)
  //   if (index !== -1) {
  //     this.dragons[index] = dragon
  //   }
  // }

  // handleDragonDeleted(id) {
  //   this.dragons = this.dragons.filter(d => d.id !== id)
  //   this.totalDragons--
  // }
}

export default new DragonService()