package com.myApp.containerts;

import com.github.dockerjava.api.command.CreateContainerCmd;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import lombok.extern.java.Log;

import java.util.List;
import java.util.function.Consumer;

@Log
public class ContainerSettings {

    public static Consumer<CreateContainerCmd> usePort(Integer port) {
        return cmd -> {
            cmd.withPortBindings(new PortBinding(Ports.Binding.bindPort(port), new ExposedPort(port)));
        };
    }

    public static Consumer<CreateContainerCmd> name(String name) {
        return cmd -> cmd.withName(name);
    }

    public static Consumer<CreateContainerCmd> entrypoint(String... command) {
        return cmd -> cmd.withEntrypoint(command);
    }

    public static Consumer<CreateContainerCmd> entrypoint(List<String> command) {
        return cmd -> cmd.withEntrypoint(command);
    }
}
