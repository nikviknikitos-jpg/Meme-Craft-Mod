package com.example.memecraft.blocks;

import com.example.memecraft.entities.FlyingTireEntity;
import com.example.memecraft.entities.ModEntities;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class IndusMotorBlock extends Block implements BlockEntityProvider {
    public IndusMotorBlock(Settings s) { super(s); }
    @Nullable @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) { return new IndusMotorBlockEntity(pos, state); }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player,
                              Hand hand, BlockHitResult hit) {
        if (world.isClient) return ActionResult.SUCCESS;
        BlockEntity be = world.getBlockEntity(pos);
        if (!(be instanceof IndusMotorBlockEntity motor)) return ActionResult.PASS;
        ItemStack held = player.getStackInHand(hand);
        // Put tire
        if (held.getItem() == com.example.memecraft.items.ModItems.TIRE_ITEM && !motor.hasTire) {
            motor.hasTire = true;
            held.decrement(1);
            world.playSound(null, pos, SoundEvents.BLOCK_ANVIL_USE, SoundCategory.BLOCKS, 1.0f, 1.2f);
            return ActionResult.SUCCESS;
        }
        // Shift + PcM working -> rip off
        if (player.isSneaking() && motor.running && motor.hasTire) {
            motor.running = false;
            motor.hasTire = false;
            float rpm = motor.rpm;
            motor.rpm = 0;
            FlyingTireEntity tire = ModEntities.FLYING_TIRE.create((ServerWorld) world);
            if (tire != null) {
                tire.refreshPositionAndAngles(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 0, 0);
                var dir = player.getRotationVec(1.0f).normalize();
                tire.setVelocity(dir.multiply(0.5 + rpm / 60.0));
                tire.setPower(rpm, rpm > 80);
                world.spawnEntity(tire);
            }
            world.playSound(null, pos, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 1.0f, 1.5f);
            return ActionResult.SUCCESS;
        }
        // Toggle
        if (held.isEmpty()) {
            motor.running = !motor.running;
            world.playSound(null, pos, SoundEvents.BLOCK_LEVER_CLICK, SoundCategory.BLOCKS, 1.0f, motor.running ? 1.2f : 0.8f);
        }
        return ActionResult.SUCCESS;
    }
}
