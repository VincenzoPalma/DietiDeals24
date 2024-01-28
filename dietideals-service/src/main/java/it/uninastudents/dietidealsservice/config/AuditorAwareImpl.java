package it.uninastudents.dietidealsservice.config;

import lombok.NonNull;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public @NonNull Optional<String> getCurrentAuditor() {
        //return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
        //.map(Authentication::getPrincipal)
        //.filter(it -> it.getClass().isAssignableFrom(AppUserDetails.class))
        //.map(AppUserDetails.class::cast)
        //.map(AppUserDetails::getId)
        //.map(UUID::toString)
        return Optional.of("system");
    }
}
