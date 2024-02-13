package it.uninastudents.dietidealsservice.config;

import it.uninastudents.dietidealsservice.model.User;
import lombok.NonNull;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public @NonNull Optional<String> getCurrentAuditor() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
        .map(Authentication::getPrincipal)
        .filter(it -> it.getClass().isAssignableFrom(User.class))
        .map(User.class::cast)
        .map(User::getUid);
    }
}
