package com.bread;

import com.bread.feature.EasyBedrockBreaker;
import com.mojang.brigadier.arguments.BoolArgumentType;
import net.fabricmc.api.ClientModInitializer;

import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BreadClient implements ClientModInitializer {

    public static final Logger LOGGER = LoggerFactory.getLogger("breadclient");

	@Override
	public void onInitializeClient() {
		registerCommands();
		EasyBedrockBreaker.init();
		LOGGER.info("breadclient loaded");
	}

	private void registerCommands() {

		// clickBlockMining
		ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> dispatcher.register(ClientCommandManager.literal("breadclient")
				.then(ClientCommandManager.literal("clickBlockMining")
						.then(ClientCommandManager.argument("value", BoolArgumentType.bool())
								.executes(context -> {
									BreadConfig.clickBlockMining = BoolArgumentType.getBool(context, "value");
									context.getSource().sendFeedback(Text.literal(BreadConfig.clickBlockMining ? "clickBlockMining enabled" : "clickBlockMining disabled"));
									return 1;
								})
						)
				)
		));

		// easyBedrockBreaker
		ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> dispatcher.register(ClientCommandManager.literal("breadclient")
				.then(ClientCommandManager.literal("easyBedrockBreaker")
						.then(ClientCommandManager.argument("value", BoolArgumentType.bool())
								.executes(context -> {
									BreadConfig.easyBedrockBreaker = BoolArgumentType.getBool(context, "value");
									context.getSource().sendFeedback(Text.literal(BreadConfig.easyBedrockBreaker ? "easyBedrockBreaker enabled" : "easyBedrockBreaker disabled"));
									return 1;
								})
						)
				)
		));
	}
}