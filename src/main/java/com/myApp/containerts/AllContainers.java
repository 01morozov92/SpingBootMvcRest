package com.myApp.containerts;

import lombok.extern.java.Log;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

@Log
public class AllContainers {

    private static final String postgreSQLImagePath = "postgres:latest";

    private static final Network myNetwork = Network.newNetwork();

    @Container
    public static GenericContainer<?> postgreSQL =
            new GenericContainer<>(DockerImageName.parse(postgreSQLImagePath))
                    .withNetwork(myNetwork)
                    .withEnv("POSTGRES_PASSWORD", "qwer1234")
                    .withExposedPorts(5432)
                    .withCreateContainerCmdModifier(ContainerSettings.name("postgres"))
                    .withCreateContainerCmdModifier(ContainerSettings.usePort(5432));

}
