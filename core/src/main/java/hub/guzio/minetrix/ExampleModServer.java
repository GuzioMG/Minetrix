package hub.guzio.minetrix;

// This file is part of the Fabric Multi-Version Mod Template by shyskyfox
// https://github.com/shyskyfox/fabric-multi-version-mod-template

import net.fabricmc.api.DedicatedServerModInitializer;

public class ExampleModServer implements DedicatedServerModInitializer {
    @Override
    public void onInitializeServer() {
        System.out.println("Server says hello!");
    }
}