import { PersonCreateDto } from '@/interfaces/dto/people/PersonCreateDto'
import { PersonDto } from '@/interfaces/dto/people/PersonDto'
import { PersonUpdateDto } from '@/interfaces/dto/people/PersonUpdateDto'
import PersonController from '@/controllers/PersonController'
import CrudService from '@/interfaces/crud/CrudService';

class PersonService extends CrudService<PersonDto, PersonCreateDto, PersonUpdateDto> {
    constructor() {
        super("Person", PersonController);
    }

    getTable() {

    }
}

export default new PersonService()