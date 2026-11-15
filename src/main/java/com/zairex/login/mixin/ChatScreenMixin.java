package com.zairex.login.mixin;

import com.zairex.login.ZairexLogin;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Style;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChatScreen.class)
public abstract class ChatScreenMixin {
    @Shadow protected TextFieldWidget chatField;

    @Inject(method = "init", at = @At("TAIL"))
    private void zairexlogin$maskPassword(CallbackInfo ci) {
        TextFieldWidget field = this.chatField;
        field.setRenderTextProvider((visible, firstIndex) -> {
            int start = ZairexLogin.passwordStart(field.getText());
            if (start < 0) {
                return OrderedText.styledForwardsVisitedString(visible, Style.EMPTY);
            }
            StringBuilder sb = new StringBuilder(visible.length());
            for (int i = 0; i < visible.length(); i++) {
                char c = visible.charAt(i);
                sb.append((firstIndex + i >= start && c != ' ') ? '*' : c);
            }
            return OrderedText.styledForwardsVisitedString(sb.toString(), Style.EMPTY);
        });
    }
}
