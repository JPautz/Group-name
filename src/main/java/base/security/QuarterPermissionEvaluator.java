package base.security;

import base.quarter.Quarter;
import base.user.User;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import java.io.Serializable;

@Component
public class QuarterPermissionEvaluator implements PermissionEvaluator {


    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        if (authentication == null)
            return false;
        Quarter quarter = (Quarter) targetDomainObject;
        if (quarter == null)
            return true;

        //needs to be implemented to get the User for a given student; fix later
        return ((User) authentication.getPrincipal()).getId().equals(quarter.getYear().getFlowchart().getStudent()
                                                                    .getId());
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType,
                                 Object permission) {
        throw new UnsupportedOperationException();
    }
}
