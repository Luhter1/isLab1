package org.itmo.isLab1.dragons.policy;

import org.springframework.stereotype.Component;
import org.itmo.isLab1.common.framework.policy.CrudPolicy;
import org.itmo.isLab1.dragons.Dragon;

@Component
public class DragonPolicy extends CrudPolicy<Dragon> {
    @Override
    public String getPolicySubject() {
        return "dragons";
    }
}