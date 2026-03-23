package com.multiversion.template;

// This file is part of the Fabric Multi-Version Mod Template by shyskyfox
// https://github.com/shyskyfox/fabric-multi-version-mod-template

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.permission.Permission;
import net.minecraft.command.permission.PermissionLevel;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import static net.minecraft.server.command.CommandManager.literal;

public class ExampleModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // Registers the command on client start (also works in singleplayer).
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            registerHelloCommand(dispatcher);
        });
    }

    private void registerHelloCommand(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(literal("hello")
            .executes(context -> {
                // This logic is executed when someone types "/hello".
                if (context.getSource().getPermissions().hasPermission(new Permission.Level(PermissionLevel.GAMEMASTERS))) {
                    context.getSource().sendFeedback(() -> Text.literal("Hello, world!"), false);
                } else {
                    context.getSource().sendFeedback(() -> Text.literal("No permission!"), false);
                }
                return 1; // 1 signals a successful execution
            })
        );
    }
}