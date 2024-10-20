package argent_matter.pocketrealms.common.room;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.UUID;

public class PocketRealmsKeyItem extends Item {

    private static final String ROOM_ID_TAG = "RoomID";
    private static final String ROOM_SIZE_TAG = "RoomSize";
    private static final String OVERWORLD_POS_TAG = "OverworldPos";
    private static final String OVERWORLD_DIMENSION_TAG = "OverworldDimension";
    private static final String OWNER_TAG = "Owner";

    public PocketRealmsKeyItem(Properties properties) {
        super(properties);
    }

    public static void storeOverworldPosition(ItemStack stack, BlockPos pos, Level overworld) {
        CompoundTag tag = stack.getOrCreateTag();
        CompoundTag posTag = new CompoundTag();
        posTag.putInt("x", pos.getX());
        posTag.putInt("y", pos.getY());
        posTag.putInt("z", pos.getZ());
        tag.put(OVERWORLD_POS_TAG, posTag);
        tag.putString(OVERWORLD_DIMENSION_TAG, overworld.dimension().location().toString());
    }

    public static BlockPos getOverworldPosition(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        if (tag != null && tag.contains(OVERWORLD_POS_TAG)) {
            CompoundTag posTag = tag.getCompound(OVERWORLD_POS_TAG);
            return new BlockPos(posTag.getInt("x"), posTag.getInt("y"), posTag.getInt("z"));
        }
        return null;
    }

    public static UUID getRoomID(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        if (tag != null && tag.hasUUID(ROOM_ID_TAG)) {
            return tag.getUUID(ROOM_ID_TAG);
        }
        return null;
    }

    public static void setRoomID(ItemStack stack, UUID roomID) {
        CompoundTag tag = stack.getOrCreateTag();
        tag.putUUID(ROOM_ID_TAG, roomID);
    }

    public static int getRoomSize(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        if (tag != null && tag.contains(ROOM_SIZE_TAG)) {
            return tag.getInt(ROOM_SIZE_TAG);
        }
        return 3; // Default room size
    }

    public static void setRoomSize(ItemStack stack, int size) {
        CompoundTag tag = stack.getOrCreateTag();
        tag.putInt(ROOM_SIZE_TAG, size);
    }

    public static void setOwner(ItemStack stack, Player player) {
        CompoundTag tag = stack.getOrCreateTag();
        tag.putString(OWNER_TAG, player.getName().getString());
    }

    public static String getOwner(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        return tag != null && tag.contains(OWNER_TAG) ? tag.getString(OWNER_TAG) : "Unknown";
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);

        UUID roomID = getRoomID(stack);
        String owner = getOwner(stack);
        int roomSize = getRoomSize(stack);

        if (roomID != null) {
            tooltip.add(Component.translatable("item.pocketrealms.room_key.room_id", roomID.toString()));
        } else {
            tooltip.add(Component.translatable("item.pocketrealms.room_key.no_room_id"));
        }

        tooltip.add(Component.translatable("item.pocketrealms.room_key.owner", owner));
        tooltip.add(Component.translatable("item.pocketrealms.room_key.room_size", roomSize, roomSize, roomSize));
    }
}