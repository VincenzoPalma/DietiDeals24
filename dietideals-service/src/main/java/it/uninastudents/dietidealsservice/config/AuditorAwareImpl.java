package it.uninastudents.dietidealsservice.config;

import it.uninastudents.dietidealsservice.model.entity.Userio;
import lombok.NonNull;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public @NonNull Optional<String> getCurrentAuditor() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
        .map(Authentication::getPrincipal)
        .filter(it -> it.getClass().isAssignableFrom(Userio.class))
        .map(Userio.class::cast)
        .map(Userio::getUid);
    }
}
