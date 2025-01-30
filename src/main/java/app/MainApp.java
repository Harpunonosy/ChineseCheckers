package app;

import factories.BananJumpFactory;
import factories.GameFactory;
import factories.MultipleJumpsFactory;
import factories.StandardGameFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import server.GameServer;
import server.TargetRegionBotStrategy;

import java.io.IOException;
import java.util.Scanner;
@ComponentScan(basePackages = {"app", "server"})
@SpringBootApplication
public class MainApp {
    public static void main(String[] args) throws IOException {

        ConfigurableApplicationContext context = SpringApplication.run(MainApp.class);

        Scanner scanner = new Scanner(System.in);

        System.out.println("Select game variant:");
        System.out.println("1. Standard");
        System.out.println("2. BananaJump");
        System.out.println("3. Multijump");
        int variant = scanner.nextInt();

        System.out.println("Enter number of players (2, 3, 4, or 6):");
        int maxPlayers = scanner.nextInt();

        GameFactory gameFactory;
        if (variant == 1) {
            gameFactory = new StandardGameFactory();
        } else if (variant == 2) {
            gameFactory = new BananJumpFactory();
        } else if (variant == 3) {
            gameFactory = new MultipleJumpsFactory();
        } else {
            throw new IllegalArgumentException("Invalid game variant selected.");
        }

        GameServer server = new GameServer(maxPlayers, gameFactory);

        System.out.println("Enter number of bots:");
        int botCount = scanner.nextInt();
        for (int i = 0; i < botCount; i++) {
            server.addBot(new TargetRegionBotStrategy());
        }
        GameServer gameServer = context.getBean(GameServer.class);
        gameServer.startServer();
    }
}
