package argent_matter.pocketrealms.common.room;

import argent_matter.pocketrealms.common.data.PocketRealmsBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RoomGenerator {

    private static final Map<UUID, BlockPos> roomPositions = new HashMap<>();

    public static void generateRoom(ServerLevel world, BlockPos origin, int innerSize) {
        int outerSize = innerSize + 2;
        int halfOuterSize = outerSize / 2;

        BlockState wallBlock = PocketRealmsBlocks.POCKET_WALL.getDefaultState();
        BlockState air = Blocks.AIR.defaultBlockState();

        for (int x = -halfOuterSize; x <= halfOuterSize; x++) {
            for (int y = 0; y <= innerSize + 1; y++) {
                for (int z = -halfOuterSize; z <= halfOuterSize; z++) {
                    BlockPos currentPos = origin.offset(x, y, z);

                    boolean isWall = x == -halfOuterSize || x == halfOuterSize || z == -halfOuterSize || z == halfOuterSize;
                    boolean isFloorOrCeiling = y == 0 || y == innerSize + 1;

                    if (isWall || isFloorOrCeiling) {
                        world.setBlockAndUpdate(currentPos, wallBlock);
                    } else {
                        world.setBlockAndUpdate(currentPos, air);
                    }
                }
            }
        }
    }

    public static int getTotalRoomCount() {
        return roomPositions.size();
    }

    public static BlockPos getRoomPositionForKey(UUID roomID) {
        return roomPositions.get(roomID);
    }

    public static void storeRoomPosition(UUID roomID, BlockPos origin) {
        roomPositions.put(roomID, origin);
    }
}
