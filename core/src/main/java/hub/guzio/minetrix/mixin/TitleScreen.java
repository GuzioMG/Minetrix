package hub.guzio.minetrix.mixin;

import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(net.minecraft.client.gui.screens.TitleScreen.class)
public class TitleScreen extends Screen {
    public TitleScreen() {
        super(Component.translatable("narrator.screen.title"));
    }

    @Inject(at = @At("RETURN"), method = "init")
    protected void init(CallbackInfo unused) {
        final var minetrixContentWidth = 60;
        var topEdgeOfBottomRow = this.height/4 + 132; //Y-COORD OF LANGUAGE BUTTON: this.height / 4 + 48 (according to TitleScreen line 106); += 2*24 (according to line 110 and after analyzing what createNormalMenuOptions does); += 36 (line 116)
        var leftEdgeOfMinetrixContent = (this.width-(248+2*minetrixContentWidth))/4; //The most reduced (ie. with only a single division, cuz dividing is expensive) form of: the centerpoint of the X-COORD OF THE LANGUAGE BUTTON (ie. this.width/2 - 124 (var10001 from TitleScreen); divided by 2 because it's the centerpoint), then shifted left by minetrixContentWidth/2 (so that the centerpoints align, instead of having the centerpoint be the left edge)    ===> overall: (this.width/2 - 124)/2 - minetrixContentWidth/2
        this.addRenderableWidget(Button.builder(Component.literal("Minetrix"), (_) -> System.out.println("Clicked!")).bounds(leftEdgeOfMinetrixContent, topEdgeOfBottomRow, minetrixContentWidth, 20).build());
    }
}