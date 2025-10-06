package com.bread;

import com.bread.feature.PacketDelay;
import com.mojang.brigadier.arguments.BoolArgumentType;
import net.fabricmc.api.ClientModInitializer;

import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.client.option.KeyBinding.Category;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BreadClient implements ClientModInitializer {

    public static final Logger LOGGER = LoggerFactory.getLogger("breadclient");
	// TODO: there is probably a better way to do the namespace
	public static final Category CATEGORY = Category.create(Identifier.of("bread.breadclient")); // key.category.minecraft.bread.breadclient

	@Override
	public void onInitializeClient() {
		registerCommands();
		PacketDelay.init();
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
				.then(ClientCommandManager.literal("packetDelay")
						.then(ClientCommandManager.argument("value", BoolArgumentType.bool())
								.executes(context -> {
									BreadConfig.packetDelay = BoolArgumentType.getBool(context, "value");
									context.getSource().sendFeedback(Text.literal(BreadConfig.packetDelay ? "packetDelay enabled" : "packetDelay disabled"));
									return 1;
								})
						)
				)
		));
	}
}