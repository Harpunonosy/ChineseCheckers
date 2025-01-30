package config;

import factories.GameFactory;
import factories.StandardGameFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GameConfig {

    @Bean
    public int maxPlayers() {
        return 4; // Możesz to zastąpić wartością z pliku konfiguracyjnego lub skanera
    }

    @Bean
    public GameFactory gameFactory() {
        return  new GameFactory(); // Tutaj możesz przekazać odpowiednie parametry
    }
}