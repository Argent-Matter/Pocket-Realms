package argent_matter.pocketrealms.common.data;

import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import static argent_matter.pocketrealms.api.registries.PocketRealmsRegistries.REGISTRATE;

@SuppressWarnings("unused")
public class PocketRealmsBlocks {
    public static final BlockEntry<Block> POCKET_WALL = REGISTRATE
            .block("pocket_wall", Block::new)
            .lang("Pocket Wall")
            .initialProperties(() -> Blocks.BARRIER)
            .blockstate(PocketRealmsModels::randomRotatedModel)
            .tag(BlockTags.PREVENT_MOB_SPAWNING_INSIDE)
            .item()
            .build()
            .register();

    public static void init() {

    }
}
