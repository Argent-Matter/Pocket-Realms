package argent_matter.pocketrealms.common.room;

import argent_matter.pocketrealms.common.data.PocketRealmsBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.particles.ParticleTypes;
import java.util.UUID;

public class RoomExpander {

    public static void expandRoom(ServerLevel world, UUID roomID, int targetSize, ServerPlayer player) {
        ItemStack key = getKeyForRoom(roomID, player);
        if (key.isEmpty()) {
            player.sendSystemMessage(Component.translatable("message.pocketrealms.no_room_key"));
            return;
        }

        int currentSize = PocketRealmsKeyItem.getRoomSize(key);
        BlockPos roomOrigin = RoomGenerator.getRoomPositionForKey(roomID);

        if (roomOrigin == null) {
            player.sendSystemMessage(Component.translatable("message.pocketrealms.room_not_found"));
            return;
        }

        if (currentSize >= targetSize) {
            player.sendSystemMessage(Component.translatable("message.pocketrealms.room_already_expanded"));
            return;
        }

        removeCurrentWalls(world, roomOrigin, currentSize);
        RoomGenerator.generateRoom(world, roomOrigin, targetSize);
        PocketRealmsKeyItem.setRoomSize(key, targetSize);
        showExpansionEffect(world, roomOrigin, targetSize);
        player.sendSystemMessage(Component.translatable("message.pocketrealms.room_expanded", targetSize));
    }

    private static void removeCurrentWalls(ServerLevel world, BlockPos origin, int currentSize) {
        int outerSize = currentSize + 2;
        BlockState air = Blocks.AIR.defaultBlockState();

        for (int x = -(outerSize / 2); x <= (outerSize / 2); x++) {
            for (int y = 0; y <= currentSize + 1; y++) {
                for (int z = -(outerSize / 2); z <= (outerSize / 2); z++) {
                    BlockPos currentPos = origin.offset(x, y, z);
                    if (world.getBlockState(currentPos).getBlock() == PocketRealmsBlocks.POCKET_WALL.get()) {
                        world.setBlock(currentPos, air, 2);
                    }
                }
            }
        }
    }

    private static ItemStack getKeyForRoom(UUID roomID, ServerPlayer player) {
        for (ItemStack itemStack : player.getInventory().items) {
            if (itemStack.getItem() instanceof PocketRealmsKeyItem) {
                UUID keyRoomID = PocketRealmsKeyItem.getRoomID(itemStack);
                if (roomID.equals(keyRoomID)) {
                    return itemStack;
                }
            }
        }
        return ItemStack.EMPTY;
    }

    private static void showExpansionEffect(ServerLevel world, BlockPos origin, int newSize) {
        int outerSize = newSize + 2;
        int halfOuterSize = outerSize / 2;

        for (int x = -halfOuterSize; x <= halfOuterSize; x++) {
            for (int z = -halfOuterSize; z <= halfOuterSize; z++) {
                BlockPos particlePos = origin.offset(x, 0, z);
                world.sendParticles(ParticleTypes.HAPPY_VILLAGER, particlePos.getX(), particlePos.getY(), particlePos.getZ(), 10, 0.5, 0.5, 0.5, 0.01);
            }
        }
    }
}
