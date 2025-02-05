package net.bluemangoo.hypertension.fabric.mixin;

import net.bluemangoo.hypertension.fabric.mixinutils.DropEntity;
import net.minecraft.world.entity.monster.Monster;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Monster.class)
public abstract class MonsterMixin {
    @Inject(method = "shouldDropLoot", at = @At("HEAD"), cancellable = true)
    public void shouldDropLoot(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(((DropEntity)this).hypertension$doDrop());
    }
}
