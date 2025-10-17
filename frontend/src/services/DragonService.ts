import { DragonCreateDto } from '@/interfaces/dto/dragons/DragonCreateDto'
import { DragonDto } from '@/interfaces/dto/dragons/DragonDto'
import { DragonUpdateDto } from '@/interfaces/dto/dragons/DragonUpdateDto'
import DragonController from '@/controllers/DragonController'
import CrudService from '@/interfaces/crud/CrudService';

class DragonService extends CrudService<DragonDto, DragonCreateDto, DragonUpdateDto> {
    constructor() {
        super("Dragon", DragonController);
    }

    getTable() {

    }
}

export default new DragonService()

// class DragonService extends CrudService2<DragonDto, DragonCreateDto, DragonUpdateDto> {
//     constructor() {
//         super("Dragon", DragonController);
//     }

//     getTable() {

//     }
// }

// class DragonTable {
//   dragons: DragonDto[]
//   totalDragons: number;
//   sortBy: boolean;
//   loading: boolean;
//   isLast: boolean;
//   isFirst: boolean;
//   isEmpty: boolean;
//   sortOrder: string;
//   currentPage: number;
//   pageSize: number;

//   constructor() {
//     this.pageSize = 10;
//     this.currentPage = 1;
//   }

//   async fetchDragons() {
//     this.loading = true

//     const response = await getAllDragon(
//       this.currentPage - 1,
//       this.pageSize,
//       []
//     )
//     this.dragons = response.content
//     this.totalDragons = response.totalElements
//     this.isLast = response.last
//     this.isFirst = response.first
//     this.isEmpty = response.empty

//     this.loading = false
//   }
// }

// export default new DragonService()