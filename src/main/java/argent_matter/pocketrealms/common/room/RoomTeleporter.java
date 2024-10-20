package argent_matter.pocketrealms.common.room;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.network.chat.Component;

import java.util.UUID;

public class RoomTeleporter {

    public static final ResourceKey<Level> POCKET_REALM_DIMENSION = ResourceKey.create(
            Registries.DIMENSION, new ResourceLocation("pocketrealms", "the_pocket_realm")
    );

    public static void teleportToRoom(ServerPlayer player, ItemStack key, ServerLevel pocketRealm) {
        PocketRealmsKeyItem.storeOverworldPosition(key, player.blockPosition(), player.level());

        UUID roomID = PocketRealmsKeyItem.getRoomID(key);
        if (roomID == null) {
            roomID = UUID.randomUUID();
            PocketRealmsKeyItem.setRoomID(key, roomID);
        }

        BlockPos roomOrigin = RoomGenerator.getRoomPositionForKey(roomID);
        if (roomOrigin == null) {
            roomOrigin = generateNewRoom(pocketRealm, roomID, 3);
        }

        player.teleportTo(pocketRealm, roomOrigin.getX() + 1, roomOrigin.getY() + 1, roomOrigin.getZ() + 1, player.getYRot(), player.getXRot());
    }

    public static void teleportBackToOverworld(ServerPlayer player, ItemStack key, ServerLevel overworld) {
        BlockPos overworldPos = PocketRealmsKeyItem.getOverworldPosition(key);

        if (overworldPos != null) {
            player.teleportTo(overworld, overworldPos.getX(), overworldPos.getY(), overworldPos.getZ(), player.getYRot(), player.getXRot());
        } else {
            player.sendSystemMessage(Component.translatable("message.pocketrealms.no_overworld_position"));
        }
    }

    private static BlockPos generateNewRoom(ServerLevel world, UUID roomID, int roomSize) {
        int roomCount = RoomGenerator.getTotalRoomCount();
        BlockPos newRoomOrigin = new BlockPos(500 * roomCount, 100, 500 * roomCount);

        RoomGenerator.generateRoom(world, newRoomOrigin, roomSize);
        RoomGenerator.storeRoomPosition(roomID, newRoomOrigin);

        return newRoomOrigin;
    }
}
