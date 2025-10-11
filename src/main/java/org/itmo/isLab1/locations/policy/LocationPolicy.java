package org.itmo.isLab1.locations.policy;

import org.springframework.stereotype.Component;
import org.itmo.isLab1.common.framework.policy.CrudPolicy;
import org.itmo.isLab1.locations.Location;

@Component
public class LocationPolicy extends CrudPolicy<Location> {

    @Override
    public String getPolicySubject() {
        return "locations";
    }
}
