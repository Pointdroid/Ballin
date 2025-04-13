package point.ballin;

import net.fabricmc.api.ModInitializer;


import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.fabricmc.fabric.api.event.player.UseItemCallback;

public class Ballin implements ModInitializer {
	public static final String MOD_ID = "ballin";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	private static ActionResult interact(PlayerEntity player, World world, Hand hand) {
		if (!world.isClient && (player.getMainHandStack().getItem() == Items.DIAMOND)) {
			// Your logic here, e.g., sending a message to the player
			((ServerPlayerEntity) player).sendMessage(Text.literal("Right-click detected!"), false);
		}
		return ActionResult.PASS;
	}

	public static class ModItems {
		public static final RegistryKey<Item> BALL_KEY = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(Ballin.MOD_ID, "basketball"));
		public static final Item BASKETBALL = ModItems.register(
				new Item(new Item.Settings().registryKey(BALL_KEY)),
				BALL_KEY
		);

		public static Item register(Item item, RegistryKey<Item> registryKey) {
			// Register the item.

            // Return the registered item!
			return Registry.register(Registries.ITEM, registryKey.getValue(), item);
		}
		public static void initialize() {
			// Get the event for modifying entries in the ingredients group.
			// And register an event handler that adds our suspicious item to the ingredients group.
			ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS)
					.register((itemGroup) -> itemGroup.add(ModItems.BASKETBALL));
		}

	}


	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		ModItems.initialize();
		LOGGER.info("Hello Fabric world!");
		UseItemCallback.EVENT.register(Ballin::interact);
	}
}

