import api from '@/controllers/api';
import { AxiosResponse } from 'axios';
import { DragonHeadDto } from '@/interfaces/dto/dragonheads/DragonHeadDto';
import { DragonHeadCreateDto } from '@/interfaces/dto/dragonheads/DragonHeadCreateDto';
import { DragonHeadUpdateDto } from '@/interfaces/dto/dragonheads/DragonHeadUpdateDto';
import CrudController, { staticImplements } from '@/interfaces/crud/CrudController';
import Paged from '@/interfaces/models/Paged';
import { createCrudUri } from '@/utils/uri';

@staticImplements<CrudController<DragonHeadDto, DragonHeadCreateDto, DragonHeadUpdateDto>>()
export default class DragonHeadController {
  static async getAll(page: number, size: number, sort: string[]): Promise<AxiosResponse<Paged<DragonHeadDto>>> {
    return api.get<Paged<DragonHeadDto>>(`/dragon-heads${createCrudUri(page, size, sort)}`);
  }

  static async get(id: number): Promise<AxiosResponse<DragonHeadDto>> {
    return api.get<DragonHeadDto>(`/dragon-heads/${id}`);
  }

  static async create(dragonHead: DragonHeadCreateDto): Promise<AxiosResponse<DragonHeadDto>> {
    return api.post<DragonHeadDto>('/dragon-heads', dragonHead);
  }

  static async update(id: number, dragonHead: DragonHeadUpdateDto): Promise<AxiosResponse<DragonHeadDto>> {
    return api.patch<DragonHeadDto>(`/dragon-heads/${id}`, dragonHead);
  }

  static async delete(id: number): Promise<AxiosResponse> {
    return api.delete(`/dragon-heads/${id}`);
  }
}