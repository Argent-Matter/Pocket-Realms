package argent_matter.pocketrealms.common.room;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "pocketrealms", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class KeyItemEventHandler {

    @SubscribeEvent
    public static void onItemRightClick(PlayerInteractEvent.RightClickItem event) {
        ItemStack stack = event.getItemStack();

        if (stack.getItem() instanceof PocketRealmsKeyItem) {
            if (!event.getEntity().level().isClientSide()) {
                ServerPlayer player = (ServerPlayer) event.getEntity();
                ServerLevel currentWorld = (ServerLevel) player.level();

                if (currentWorld.dimension().equals(RoomTeleporter.POCKET_REALM_DIMENSION)) {
                    ServerLevel overworld = player.getServer().getLevel(Level.OVERWORLD);
                    RoomTeleporter.teleportBackToOverworld(player, stack, overworld);
                } else {
                    ServerLevel pocketRealm = player.getServer().getLevel(RoomTeleporter.POCKET_REALM_DIMENSION);
                    RoomTeleporter.teleportToRoom(player, stack, pocketRealm);
                }

                event.setCanceled(true);
            }
        }
    }
}
