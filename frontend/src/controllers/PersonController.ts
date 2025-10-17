import { PersonDto } from '@/interfaces/dto/people/PersonDto';
import { PersonCreateDto } from '@/interfaces/dto/people/PersonCreateDto';
import { PersonUpdateDto } from '@/interfaces/dto/people/PersonUpdateDto';
import CrudController from '@/interfaces/crud/CrudController';

export default new CrudController<PersonDto, PersonCreateDto, PersonUpdateDto>("people")
