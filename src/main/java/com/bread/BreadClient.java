package com.bread;

import com.bread.feature.BookDupe;
import com.bread.feature.PacketDelay;
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

		// inventory savestate
		ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
			dispatcher.register(ClientCommandManager.literal("dupe").executes(context -> {
				BookDupe.dupe(0);
				return 1;
			}));
		});

		// chunk ban
		ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
			dispatcher.register(ClientCommandManager.literal("chunkban").executes(context -> {
				BookDupe.dupe(1);
				return 1;
			}));
		});

		// chunk savestate
		ClientCommandRegistrationCallback.EVENT.register(((dispatcher, registryAccess) -> {
			dispatcher.register(ClientCommandManager.literal("savestate").executes(context -> {
				if (!BookDupe.shouldSavestate) {
					context.getSource().getPlayer().sendMessage(Text.literal("Ready to savestate!"));
					BookDupe.shouldSavestate = true;
				} else {
					context.getSource().getPlayer().sendMessage(Text.literal("Savestate canceled!"));
					BookDupe.shouldSavestate = false;
				}
				return 1;
			}));
		}));
	}
}