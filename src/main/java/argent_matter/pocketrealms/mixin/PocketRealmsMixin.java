package argent_matter.pocketrealms.mixin;

import argent_matter.pocketrealms.PocketRealms;
import net.minecraft.client.gui.screens.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class PocketRealmsMixin {
    @Inject(at = @At("HEAD"), method = "init()V")
    private void init(CallbackInfo info) {
        PocketRealms.LOGGER.info("This line is printed by an example mod mixin!");
    }
}
