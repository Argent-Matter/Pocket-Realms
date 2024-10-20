package argent_matter.pocketrealms.common.room;


import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;

import java.util.UUID;

public class RoomTeleportCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("roomteleport")
                .requires(source -> source.hasPermission(2))
                .then(Commands.argument("roomID", StringArgumentType.string())
                        .executes(context -> {
                            ServerPlayer player = context.getSource().getPlayerOrException();
                            String roomIDStr = StringArgumentType.getString(context, "roomID");

                            UUID roomID;
                            try {
                                roomID = UUID.fromString(roomIDStr);
                            } catch (IllegalArgumentException e) {
                                player.sendSystemMessage(Component.translatable("commands.roomteleport.invalid_room_id"));
                                return 0;
                            }

                            ServerLevel pocketRealm = player.getServer().getLevel(RoomTeleporter.POCKET_REALM_DIMENSION);
                            if (pocketRealm == null) {
                                player.sendSystemMessage(Component.translatable("commands.roomteleport.pocketrealm_not_found"));
                                return 0;
                            }

                            BlockPos roomOrigin = RoomGenerator.getRoomPositionForKey(roomID);
                            if (roomOrigin == null) {
                                player.sendSystemMessage(Component.translatable("commands.roomteleport.room_not_found", roomID.toString()));
                                return 0;
                            }

                            player.teleportTo(pocketRealm, roomOrigin.getX() + 1, roomOrigin.getY() + 1, roomOrigin.getZ() + 1, player.getYRot(), player.getXRot());
                            player.sendSystemMessage(Component.translatable("commands.roomteleport.success", roomID.toString()));

                            return 1;
                        })
                )
        );
    }
}
