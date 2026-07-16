package com.bread;

import com.bread.feature.PacketDelay;
import com.mojang.brigadier.arguments.BoolArgumentType;
import net.fabricmc.api.ClientModInitializer;

import net.fabricmc.fabric.api.client.command.v2.ClientCommands;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.network.chat.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BreadClient implements ClientModInitializer {

    public static final Logger LOGGER = LoggerFactory.getLogger("breadclient");
    public static final String MOD_ID = "breadclient";

	@Override
	public void onInitializeClient() {
		registerCommands();
		PacketDelay.init();
		LOGGER.info("breadclient loaded");
	}

	private void registerCommands() {

		// clickBlockMining
		ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> dispatcher.register(ClientCommands.literal("breadclient")
				.then(ClientCommands.literal("clickBlockMining")
						.then(ClientCommands.argument("value", BoolArgumentType.bool())
								.executes(context -> {
									BreadConfig.clickBlockMining = BoolArgumentType.getBool(context, "value");
									context.getSource().sendFeedback(Component.literal(BreadConfig.clickBlockMining ? "clickBlockMining enabled" : "clickBlockMining disabled"));
									return 1;
								})
						)
				)
		));

		// packetDelay
		ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> dispatcher.register(ClientCommands.literal("breadclient")
				.then(ClientCommands.literal("packetDelay")
						.then(ClientCommands.argument("value", BoolArgumentType.bool())
								.executes(context -> {
									BreadConfig.packetDelay = BoolArgumentType.getBool(context, "value");
									context.getSource().sendFeedback(Component.literal(BreadConfig.packetDelay ? "packetDelay enabled" : "packetDelay disabled"));
									return 1;
								})
						)
				)
		));

	}
}