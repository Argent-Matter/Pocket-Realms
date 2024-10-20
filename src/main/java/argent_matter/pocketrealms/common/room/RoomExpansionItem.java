package argent_matter.pocketrealms.common.room;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.network.chat.Component;
import java.util.UUID;

public class RoomExpansionItem extends Item {

    private final int targetSize;

    public RoomExpansionItem(Properties properties, int targetSize) {
        super(properties);
        this.targetSize = targetSize;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (!world.isClientSide() && player instanceof ServerPlayer serverPlayer) {
            ItemStack roomKey = findRoomKey(serverPlayer);

            if (roomKey != null) {
                UUID roomID = PocketRealmsKeyItem.getRoomID(roomKey);

                if (roomID != null) {
                    int currentRoomSize = PocketRealmsKeyItem.getRoomSize(roomKey);

                    if (currentRoomSize < targetSize) {
                        RoomExpander.expandRoom((ServerLevel) world, roomID, targetSize, serverPlayer);
                        PocketRealmsKeyItem.setRoomSize(roomKey, targetSize);
                        stack.shrink(1);
                        return InteractionResultHolder.success(stack);
                    } else {
                        player.sendSystemMessage(Component.translatable("message.pocketrealms.room_already_expanded"));
                    }
                } else {
                    player.sendSystemMessage(Component.translatable("message.pocketrealms.no_room_id"));
                }
            } else {
                player.sendSystemMessage(Component.translatable("message.pocketrealms.no_room_key"));
            }
        }

        return InteractionResultHolder.pass(stack);
    }

    private ItemStack findRoomKey(ServerPlayer player) {
        for (ItemStack itemStack : player.getInventory().items) {
            if (itemStack.getItem() instanceof PocketRealmsKeyItem) {
                return itemStack;
            }
        }
        return null;
    }
}
