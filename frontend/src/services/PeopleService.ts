import { PersonCreateDto } from '@/interfaces/dto/people/PersonCreateDto'
import { PersonDto } from '@/interfaces/dto/people/PersonDto'
import { CrudService } from '@/interfaces/crud/CrudService';
import { PersonUpdateDto } from '@/interfaces/dto/people/PersonUpdateDto'
import PersonController from '@/controllers/PersonController'

const Person = CrudService<PersonDto, PersonCreateDto, PersonUpdateDto>(
  'location',
  PersonController
);

export const {
  getAll: getAllPerson,
  getById: getPerson,
  create: createPerson,
  update: updatePerson,
  Delete: deletePerson,
} = Person