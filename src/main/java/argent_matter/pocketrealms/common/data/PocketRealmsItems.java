package argent_matter.pocketrealms.common.data;

import argent_matter.pocketrealms.common.room.PocketRealmsKeyItem;
import argent_matter.pocketrealms.common.room.RoomExpansionItem;
import com.tterrag.registrate.util.entry.ItemEntry;

import static argent_matter.pocketrealms.api.registries.PocketRealmsRegistries.REGISTRATE;

public class PocketRealmsItems {

    public static final ItemEntry<PocketRealmsKeyItem> ROOM_KEY = REGISTRATE
            .item("room_key", PocketRealmsKeyItem::new)
            .properties(p -> p.stacksTo(1))
            .register();

    public static final ItemEntry<RoomExpansionItem> ROOM_EXPANSION_TIER_1 = REGISTRATE
            .item("room_expansion_tier_1", p -> new RoomExpansionItem(p, 5))  // Expands to 5x5x5
            .properties(p -> p.stacksTo(1))
            .register();

    public static final ItemEntry<RoomExpansionItem> ROOM_EXPANSION_TIER_2 = REGISTRATE
            .item("room_expansion_tier_2", p -> new RoomExpansionItem(p, 7))  // Expands to 7x7x7
            .properties(p -> p.stacksTo(1))
            .register();

    public static final ItemEntry<RoomExpansionItem> ROOM_EXPANSION_TIER_3 = REGISTRATE
            .item("room_expansion_tier_3", p -> new RoomExpansionItem(p, 9))  // Expands to 9x9x9
            .properties(p -> p.stacksTo(1))
            .register();

    public static final ItemEntry<RoomExpansionItem> ROOM_EXPANSION_TIER_4 = REGISTRATE
            .item("room_expansion_tier_4", p -> new RoomExpansionItem(p, 11))  // Expands to 11x11x11
            .properties(p -> p.stacksTo(1))
            .register();

    public static final ItemEntry<RoomExpansionItem> ROOM_EXPANSION_TIER_5 = REGISTRATE
            .item("room_expansion_tier_5", p -> new RoomExpansionItem(p, 13))  // Expands to 13x13x13
            .properties(p -> p.stacksTo(1))
            .register();


    public static void init() {
    }
}
