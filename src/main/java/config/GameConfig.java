package config;

import factories.GameFactory;
import factories.StandardGameFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "config")
public class GameConfig {

    private int maxPlayers = 4;
    @Bean
    public GameFactory gameFactory() {
        return new StandardGameFactory(); // Tutaj możesz przekazać odpowiednie parametry
    }
}