package com.bread.gui;

import com.bread.BreadClient;
import top.hendrixshen.magiclib.impl.config.MagicLibConfigs.ConfigCategory;
import top.hendrixshen.magiclib.malilib.impl.ConfigManager;
import top.hendrixshen.magiclib.malilib.impl.gui.ConfigGui;

public class BreadClientGui extends ConfigGui {

    private static BreadClientGui INSTANCE;

    private BreadClientGui(String identifier, String defaultTab) {
        super(identifier, defaultTab, ConfigManager.get(BreadClient.MOD_ID), "Bread Client");
    }

    public static BreadClientGui get() {
        if (INSTANCE == null)
            INSTANCE = new BreadClientGui(BreadClient.MOD_ID, ConfigCategory.GENERIC);
        return INSTANCE;
    }

}
