package org.itmo.isLab1.coordinates.policy;

import org.springframework.stereotype.Component;
import org.itmo.isLab1.common.framework.policy.CrudPolicy;
import org.itmo.isLab1.coordinates.Coordinate;

@Component
public class CoordinatePolicy extends CrudPolicy<Coordinate> {
    @Override
    public String getPolicySubject() {
        return "coordinates";
    }
}
