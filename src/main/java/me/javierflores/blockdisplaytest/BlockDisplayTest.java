package me.javierflores.blockdisplaytest;

import org.bukkit.plugin.java.JavaPlugin;

import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIBukkitConfig;

public class BlockDisplayTest extends JavaPlugin {


    private static BlockDisplayTest INSTANCE;

    public BlockDisplayTest() {
        INSTANCE = this;
    }

    @Override
    public void onLoad() {
        CommandAPI.onLoad(new CommandAPIBukkitConfig(this)
                .setNamespace(this.getName())
                .verboseOutput(true));
        CommandAPI.registerCommand(TestCommand.class);
    }

    @Override
    public void onEnable() {
        CommandAPI.onEnable();
    }

    @Override
    public void onDisable() {
        CommandAPI.onDisable();
    }

    public static BlockDisplayTest inst() {
        return INSTANCE;
    }
}