package hub.guzio.minetrix;

// This file is part of the Fabric Multi-Version Mod Template by shyskyfox
// https://github.com/shyskyfox/fabric-multi-version-mod-template

import net.fabricmc.api.ClientModInitializer;

public class ExampleModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        System.out.println("Client says hello!");
    }
}
