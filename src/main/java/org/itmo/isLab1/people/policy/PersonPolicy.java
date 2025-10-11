package org.itmo.isLab1.people.policy;

import org.springframework.stereotype.Component;
import org.itmo.isLab1.common.framework.policy.CrudPolicy;
import org.itmo.isLab1.people.Person;


@Component
public class PersonPolicy extends CrudPolicy<Person> {
    @Override
    public String getPolicySubject() {
        return "people";
    }
}