package health;


import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class CommitHealthIndicator implements HealthIndicator {
    @Override
    public Health health() {
        String commit = System.getenv("HEROKU_SLUG_COMMIT");
        return (commit == null) ?
                Health.down().withDetail("Commit", "").build() :
                Health.up().withDetail("Commit", commit).build();
    }
}
