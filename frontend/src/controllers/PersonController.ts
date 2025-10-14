import api from '@/controllers/api';
import { AxiosResponse } from 'axios';
import { PersonDto } from '@/interfaces/dto/people/PersonDto';
import { PersonCreateDto } from '@/interfaces/dto/people/PersonCreateDto';
import { PersonUpdateDto } from '@/interfaces/dto/people/PersonUpdateDto';
import CrudController, { staticImplements } from '@/interfaces/crud/CrudController';
import Paged from '@/interfaces/models/Paged';
import { createCrudUri } from '@/utils/uri';

@staticImplements<CrudController<PersonDto, PersonCreateDto, PersonUpdateDto>>()
export default class PersonService {
  static async getAll(page: number, size: number, sort: string[]): Promise<AxiosResponse<Paged<PersonDto>>> {
    return api.get<Paged<PersonDto>>(`/people${createCrudUri(page, size, sort)}`);
  }

  static async get(id: number): Promise<AxiosResponse<PersonDto>> {
    return api.get<PersonDto>(`/people/${id}`);
  }

  static async create(Person: PersonCreateDto): Promise<AxiosResponse<PersonDto>> {
    return api.post<PersonDto>('/people', Person);
  }

  static async update(id: number, Person: PersonUpdateDto): Promise<AxiosResponse<PersonDto>> {
    return api.patch<PersonDto>(`/people/${id}`, Person);
  }

  static async delete(id: number): Promise<AxiosResponse> {
    return api.delete(`/people/${id}`);
  }
}