package com.example.memecraft.items;

import com.example.memecraft.entities.ModEntities;
import com.example.memecraft.entities.VoidAnomalyEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class AnomalySpawnerItem extends Item {
    public AnomalySpawnerItem(Settings s) { super(s); }
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient && user instanceof ServerPlayerEntity sp && world instanceof ServerWorld sw) {
            Vec3d pos = sp.getEyePos().add(sp.getRotationVec(1.0f).multiply(6));
            VoidAnomalyEntity a = ModEntities.VOID_ANOMALY.create(sw);
            if (a != null) {
                a.refreshPositionAndAngles(pos.x, pos.y - 2.5, pos.z, 0, 0);
                sw.spawnEntity(a);
            }
            sp.getStackInHand(hand).decrement(1);
        }
        return TypedActionResult.success(user.getStackInHand(hand));
    }
}
