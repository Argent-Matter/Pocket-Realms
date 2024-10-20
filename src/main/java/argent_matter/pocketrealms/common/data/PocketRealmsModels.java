package argent_matter.pocketrealms.common.data;

import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import com.tterrag.registrate.providers.RegistrateItemModelProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CarpetBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.ModelProvider;

public class PocketRealmsModels {

    public static void randomRotatedModel(DataGenContext<Block, ? extends Block> ctx, RegistrateBlockstateProvider prov) {
        Block block = ctx.getEntry();
        ResourceLocation textureLocation = new ResourceLocation("pocketrealms", "block/pocket_wall");

        // Log the texture path to the console
        System.out.println("Generating model for: " + ctx.getName() + ", texture: " + textureLocation);

        ModelFile cubeAll = prov.cubeAll(block);
        ModelFile cubeMirroredAll = prov.models().singleTexture(ctx.getName() + "_mirrored", prov.mcLoc(ModelProvider.BLOCK_FOLDER + "/cube_mirrored_all"), "all", textureLocation);

        ConfiguredModel[] models = ConfiguredModel.builder()
                .modelFile(cubeAll)
                .rotationY(0)
                .nextModel()
                .modelFile(cubeAll)
                .rotationY(180)
                .nextModel()
                .modelFile(cubeMirroredAll)
                .rotationY(0)
                .nextModel()
                .modelFile(cubeMirroredAll)
                .rotationY(180)
                .build();

        prov.simpleBlock(block, models);
    }

    public static void crossModel(DataGenContext<Block, ? extends Block> ctx, RegistrateBlockstateProvider prov) {
        Block block = ctx.getEntry();
        prov.simpleBlock(block, prov.models().cross(ctx.getName(), prov.blockTexture(block)));
    }

    public static void blockTextureGeneratedModel(DataGenContext<Item, ? extends Item> ctx, RegistrateItemModelProvider prov) {
        prov.generated(ctx::getEntry, prov.modLoc("block/" + ctx.getName()));
    }

    private static boolean doesTextureExist(RegistrateBlockstateProvider prov, ResourceLocation texture) {
        return true;
    }
}

