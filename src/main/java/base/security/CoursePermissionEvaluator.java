package base.security;

import base.course.Course;
import base.user.User;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class CoursePermissionEvaluator implements PermissionEvaluator {

    @Override
    public boolean hasPermission(Authentication authentication,
                                 Object targetDomainObject, Object permission) {
        /*if(authentication == null) {
            return false;
        }
        Course course = (Course) targetDomainObject;
        if(course == null) {
            return true;
        }
        User currentUser = (User) authentication.getPrincipal();
        //return currentUser.getId().equals(course.getQuarter().getYear().getFlowchart().getUser().getId());*/
        return true;
    }

    @Override
    public boolean hasPermission(Authentication authentication,
                                 Serializable targetId, String targetType, Object permission) {
        throw new UnsupportedOperationException();
    }

}
