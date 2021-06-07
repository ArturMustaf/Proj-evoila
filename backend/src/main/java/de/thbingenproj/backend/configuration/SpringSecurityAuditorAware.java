package de.thbingenproj.backend.configuration;

import de.thbingenproj.backend.entities.user.agent.Agent;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Optional;

// TODO Security einfügen
@Configuration
@EnableScheduling
@NoArgsConstructor
class SpringSecurityAuditorAware implements AuditorAware<Agent> {
    private Agent agent;

    @Override
    public Optional<Agent> getCurrentAuditor() {
        return Optional.of(agent);
    }
}
