## How to run

To compile and execute this project u nedd to do this steps:

### Compilation 

1. Compile server files:
    ```sh
    javac -d out src/server/GameServer.java src/server/PlayerHandler.java
    ```

2. Compile client files:
    ```sh
    javac -d out src/client/GameClient.java src/client/ClientConnection.java src/client/ClientInputHandler.java src/client/ClientOutputHandler.java
    ```

### Starting

1. Start server:
    ```sh
    java -cp out GameServer
    ```

2. Start Client:
    ```sh
    java -cp out GameClient
    ```
