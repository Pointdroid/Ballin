package point.ballin;

import net.fabricmc.api.ModInitializer;


import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Ballin implements ModInitializer {
	public static final String MOD_ID = "ballin";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public class ModItems {
		public static final RegistryKey<Item> BALL_KEY = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(Ballin.MOD_ID, "basketball"));
		public static final Item BASKETBALL = ModItems.register(
				new Item(new Item.Settings().registryKey(BALL_KEY)),
				BALL_KEY
		);

		public static Item register(Item item, RegistryKey<Item> registryKey) {
			// Register the item.
			Item registeredItem = Registry.register(Registries.ITEM, registryKey.getValue(), item);

			// Return the registered item!
			return registeredItem;
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
	}
}

