package de.test.backend.configuration;

import de.test.backend.entities.user.agent.Agent;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Optional;

// TODO Security einfügen
@Configuration
@EnableScheduling
class SpringSecurityAuditorAware implements AuditorAware<Agent> {
    //TODO Remove Static employee
    private Agent agent;

    static {

    }

    SpringSecurityAuditorAware() {
        System.out.println("init");
    }

    @Override
    public Optional<Agent> getCurrentAuditor() {
        return Optional.of(agent);
    }
}
