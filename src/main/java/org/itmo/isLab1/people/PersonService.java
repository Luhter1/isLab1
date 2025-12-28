package org.itmo.isLab1.people;

import org.springframework.stereotype.Service;
import org.itmo.isLab1.common.errors.EntityDuplicateException;
import org.itmo.isLab1.common.framework.CrudService;
import org.itmo.isLab1.events.EventService;
import org.itmo.isLab1.people.dto.*;
import org.itmo.isLab1.people.mapper.PersonMapper;
import org.itmo.isLab1.people.policy.PersonPolicy;
import org.itmo.isLab1.users.UserService;

@Service
public class PersonService
    extends CrudService<
        Person,
        PersonRepository,
        PersonMapper,
        PersonPolicy,
        PersonDto,
        PersonCreateDto,
        PersonUpdateDto> {

    public PersonService(
        PersonRepository repository,
        PersonMapper mapper,
        PersonPolicy policy,
        UserService userService,
        EventService<Person> eventService
    ) {
        super(repository, mapper, policy, userService, eventService);
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    protected void checkUniqueness(Person obj){
        if(repository.existsByPassportIdAndIdNot(obj.getPassportId(), obj.getId())){
            throw new EntityDuplicateException("passportId is not uniq");
        }
    };
}
