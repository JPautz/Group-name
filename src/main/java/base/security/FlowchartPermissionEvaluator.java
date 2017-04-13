package base.security;

import base.flowchart.Flowchart;
import base.user.User;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class FlowchartPermissionEvaluator implements PermissionEvaluator {

    @Override
    public boolean hasPermission(Authentication authentication,
                                 Object targetDomainObject, Object permission) {
        if (authentication == null) {
            return false;
        }
        Flowchart flowchart = (Flowchart) targetDomainObject;
        if (flowchart == null) {
            return true;
        }
        User currentUser = (User) authentication.getPrincipal();
        return true; //currentUser.getId().equals(flowchart.getUser().getId());
    }

    @Override
    public boolean hasPermission(Authentication authentication,
                                 Serializable targetId, String targetType, Object permission) {
        throw new UnsupportedOperationException();
    }

}
