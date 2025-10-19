import api from '@/controllers/api';
import { AxiosResponse } from 'axios';
import { DragonResultDto } from '@/interfaces/dto/specialoperations/DragonResultDto';
import { AverageAgeDto } from '@/interfaces/dto/specialoperations/AverageAgeDto';


export default class SpecialOperationController {
  static async deepestCaveDragon(): Promise<AxiosResponse<DragonResultDto>> {
    return api.get<DragonResultDto>('/special-operations/deepest-cave-dragon');
  }

  static async averageAge(): Promise<AxiosResponse<AverageAgeDto>> {
    return api.get<AverageAgeDto>('/special-operations/average-age');
  }
}