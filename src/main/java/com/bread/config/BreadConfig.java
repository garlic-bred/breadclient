package com.bread.config;

import com.bread.BreadClient;
import com.bread.gui.BreadClientGui;
import fi.dy.masa.malilib.config.options.ConfigHotkey;
import net.minecraft.client.MinecraftClient;
import top.hendrixshen.magiclib.malilib.api.annotation.Config;
import top.hendrixshen.magiclib.malilib.api.annotation.Hotkey;
import top.hendrixshen.magiclib.malilib.impl.ConfigHandler;
import top.hendrixshen.magiclib.malilib.impl.ConfigManager;

public class BreadConfig {

    public static final String GENERIC = "generic";
    public static final String KEYBINDS = "keybind";
    public static final String FEATURES = "feature";

    private static ConfigManager cm;
    private static ConfigHandler handler;

    public static void init() {

        cm = ConfigManager.get(BreadClient.MOD_ID);
        cm.parseConfigClass(BreadConfig.class);

        handler = new ConfigHandler(BreadClient.MOD_ID, cm, 1);

        openConfigGui.getKeybind().setCallback((keyAction, iKeybind) -> {
            BreadClientGui gui = BreadClientGui.get();
            gui.setParent(MinecraftClient.getInstance().currentScreen);
            MinecraftClient.getInstance().setScreen(gui);
            return true;
        });

    }

    // GENERIC
    @Hotkey(hotkey = "B,C")
    @Config(category = GENERIC)
    public static ConfigHotkey openConfigGui;

    // Keybinds
    @Hotkey
    @Config(category = KEYBINDS)
    public static ConfigHotkey easyBedrockBreakerActivate;

    // Features
    @Hotkey
    @Config(category = FEATURES)
    public static boolean clickBlockMining = false;

    @Hotkey
    @Config(category = FEATURES)
    public static boolean easyBedrockBreaker = false;

}
