package ph.com.project01.webportal.common;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

public class SessionDetailsUtil {

    public static String getAccountId() {
        Authentication authcontext = SecurityContextHolder.getContext().getAuthentication();
        return authcontext != null ? authcontext.getName() : null;
    }

    public static String getRole() {
        Authentication authcontext = SecurityContextHolder.getContext().getAuthentication();
        if (authcontext != null && authcontext.getAuthorities() != null) {
            return authcontext.getAuthorities().stream()
                    .findFirst()
                    .map(GrantedAuthority::getAuthority)
                    .orElse("No role assigned");
        }
        return "No role assigned";
    }
}
