package org.itmo.isLab1.dragoncaves.policy;

import org.springframework.stereotype.Component;
import org.itmo.isLab1.common.framework.policy.CrudPolicy;
import org.itmo.isLab1.dragoncaves.DragonCave;


@Component
public class DragonCavePolicy extends CrudPolicy<DragonCave> {
    @Override
    public String getPolicySubject() {
        return "dragon caves";
    }
}
