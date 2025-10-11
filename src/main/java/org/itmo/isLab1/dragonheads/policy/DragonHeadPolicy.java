package org.itmo.isLab1.dragonheads.policy;


import org.springframework.stereotype.Component;
import org.itmo.isLab1.common.framework.policy.CrudPolicy;
import org.itmo.isLab1.dragonheads.DragonHead;

@Component
public class DragonHeadPolicy extends CrudPolicy<DragonHead> {

    @Override
    public String getPolicySubject() {
        return "dragon heads";
    }
}
