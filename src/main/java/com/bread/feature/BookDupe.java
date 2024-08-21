package com.bread.feature;

import com.bread.BreadClient;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.BookUpdateC2SPacket;
import net.minecraft.network.packet.c2s.play.ClickSlotC2SPacket;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.Text;

import java.util.List;
import java.util.Optional;

public class BookDupe {

    private static MinecraftClient client = MinecraftClient.getInstance();

    public static boolean shouldSavestate = false;
    public final static String DUPE_TEXT = "bread_bread_bread_bread_bread_bread_bread";

    public static void dupe(int type) {
        ClientPlayerEntity player = client.player;
        if (player.getMainHandStack().isOf(Items.WRITABLE_BOOK)) {
            int bookSlot = player.getInventory().getSlotWithStack(player.getMainHandStack());
            ItemStack bookItem = player.currentScreenHandler.slots.get(bookSlot).getStack();
            writeBook();
            switch (type) {
                case 1:
                    Int2ObjectOpenHashMap itemMap = new Int2ObjectOpenHashMap<>();
                    itemMap.put(0, bookItem.copy());
                    player.networkHandler.sendPacket(new ClickSlotC2SPacket(player.currentScreenHandler.syncId, player.currentScreenHandler.getRevision(), 36 + player.getInventory().selectedSlot, 0, SlotActionType.THROW, bookItem, itemMap));
                    break;
                case 2:
                    Int2ObjectMap<ItemStack> stack = new Int2ObjectArrayMap<>();
                    for (int i = player.currentScreenHandler.slots.size() - 1; i >= 0; i--) {
                        if (player.currentScreenHandler.slots.get(i).getStack().isOf(Items.WRITABLE_BOOK)) {
                            bookSlot = i;
                            break;
                        }
                    }
                    stack.put(bookSlot, bookItem);
                    BreadClient.LOGGER.info("put slot " + bookSlot + " into container");
                    client.getNetworkHandler().sendPacket(new ClickSlotC2SPacket(player.currentScreenHandler.syncId, 0, bookSlot, 0, SlotActionType.QUICK_MOVE, bookItem, stack));
                    break;
            }
        }
        else {
            player.sendMessage(Text.literal("You need a book and quill to do that!"));
        }
    }

    public static void writeBook() {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        player.networkHandler.sendPacket(new BookUpdateC2SPacket(player.getInventory().getSlotWithStack(player.getMainHandStack()), List.of(BookDupe.DUPE_TEXT), Optional.of(BookDupe.DUPE_TEXT)));
        BreadClient.LOGGER.info("writing book");
    }

}
