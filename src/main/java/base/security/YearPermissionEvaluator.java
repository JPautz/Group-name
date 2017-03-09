package base.security;

import base.year.Year;
import base.user.User;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import java.io.Serializable;

@Component
public class YearPermissionEvaluator implements PermissionEvaluator {


    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        if (authentication == null)
            return false;
        Year year = (Year) targetDomainObject;
        if (year == null)
            return true;

        //needs to be implemented to get the User for a given student; fix later
        return ((User) authentication.getPrincipal()).getId().equals(year.getFlowchart().getStudent());
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType,
                                 Object permission) {
        throw new UnsupportedOperationException();
    }
}
