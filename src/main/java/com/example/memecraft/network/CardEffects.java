package com.example.memecraft.network;

import com.example.memecraft.entities.FlyingTireEntity;
import com.example.memecraft.entities.ModEntities;
import com.example.memecraft.entities.VoidAnomalyEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Random;

public class CardEffects {
    public static void apply(ServerPlayerEntity p, int cardId) {
        ServerWorld sw = (ServerWorld) p.getWorld();
        Random r = new Random();
        int eff = r.nextInt(3);
        switch (cardId) {
            case 0 -> {
                p.sendMessage(Text.literal("§5Расклад: §dСова на скакалке §7— легендарная предвестница беды!"), false);
                if (eff == 0) {
                    Vec3d t = p.getEyePos().add(p.getRotationVec(1.0f).multiply(8));
                    sw.createExplosion(p, t.x, t.y, t.z, 5.0f, World.ExplosionSourceType.TNT);
                } else if (eff == 1) {
                    for (int i = 0; i < 5; i++) {
                        var l = EntityType.LIGHTNING_BOLT.create(sw);
                        if (l != null) { l.refreshPositionAfterTeleport(p.getPos().add((r.nextDouble()-0.5)*10, 0, (r.nextDouble()-0.5)*10)); sw.spawnEntity(l); }
                    }
                } else {
                    Box box = p.getBoundingBox().expand(10);
                    for (LivingEntity e : sw.getEntitiesByClass(LivingEntity.class, box, e -> e.isAlive() && !((Object) e instanceof ServerPlayerEntity))) {
                        e.addStatusEffect(new StatusEffectInstance(StatusEffects.DARKNESS, 200, 0));
                        e.addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, 200, 1));
                    }
                    VoidAnomalyEntity a = ModEntities.VOID_ANOMALY.create(sw);
                    if (a != null) { a.refreshPositionAndAngles(p.getX(), p.getY() + 15, p.getZ(), 0, 0); sw.spawnEntity(a); }
                }
            }
            case 1 -> { // dog golf
                p.sendMessage(Text.literal("§6Расклад: §eСобака-гольфач!"), false);
                if (eff == 0) {
                    for (int i = 0; i < 2; i++) {
                        FlyingTireEntity t = ModEntities.FLYING_TIRE.create(sw);
                        if (t != null) { t.refreshPositionAndAngles(p.getX(), p.getY(), p.getZ(), 0, 0);
                            t.setVelocity(p.getRotationVec(1.0f).multiply(0.6)); t.setPower(80, true); sw.spawnEntity(t); }
                    }
                } else if (eff == 1) {
                    Box box = p.getBoundingBox().expand(10);
                    for (MobEntity m : sw.getEntitiesByClass(MobEntity.class, box, e -> e.isAlive())) {
                        m.setVelocity(0, 1.8, 0); m.velocityModified = true;
                    }
                } else {
                    var arrow = EntityType.ARROW.create(sw);
                    if (arrow != null) { arrow.setOwner(p);
                        arrow.refreshPositionAndAngles(p.getX(), p.getY() + 1, p.getZ(), 0, 0);
                        arrow.setVelocity(p.getRotationVec(1.0f).multiply(2.0)); sw.spawnEntity(arrow); }
                }
            }
            case 2 -> { // giraffe
                p.sendMessage(Text.literal("§bРасклад: §aЖираф с мыльными пузырями!"), false);
                if (eff == 0) { p.addStatusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, 120, 1)); }
                else if (eff == 1) {
                    Box box = p.getBoundingBox().expand(8);
                    for (MobEntity m : sw.getEntitiesByClass(MobEntity.class, box, e -> e.isAlive())) {
                        m.addStatusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, 100, 0));
                    }
                } else {
                    p.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING, 200, 0));
                    p.addStatusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 400, 4));
                }
            }
            case 3 -> { // snake
                p.sendMessage(Text.literal("§aРасклад: §2Змейка на сёрфе: Всё будет хорошо!"), false);
                if (eff == 0) { p.heal(p.getMaxHealth());
                    p.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 200, 2));
                    p.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 200, 2)); }
                else if (eff == 1) {
                    for (int i = 0; i < 12; i++) {
                        var stack = new ItemStack(i % 3 == 0 ? Items.DIAMOND : i % 3 == 1 ? Items.EMERALD : Items.GOLD_INGOT, 4);
                        net.minecraft.entity.ItemEntity ie = new net.minecraft.entity.ItemEntity(sw, p.getX(), p.getY() + 2, p.getZ(), stack);
                        ie.setVelocity((r.nextDouble()-0.5)*0.5, 0.5, (r.nextDouble()-0.5)*0.5);
                        sw.spawnEntity(ie);
                    }
                } else {
                    p.addStatusEffect(new StatusEffectInstance(StatusEffects.LUCK, 600, 4));
                    p.addStatusEffect(new StatusEffectInstance(StatusEffects.HASTE, 600, 2));
                    p.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 600, 2));
                }
            }
            case 4 -> { // skunk
                p.sendMessage(Text.literal("§2Расклад: §8Скунс-гонщик!"), false);
                if (eff == 0) { p.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 200, 6)); }
                else if (eff == 1) {
                    Box box = p.getBoundingBox().expand(6);
                    for (MobEntity m : sw.getEntitiesByClass(MobEntity.class, box, e -> e.isAlive())) {
                        m.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 200, 1));
                    }
                } else {
                    FlyingTireEntity t = ModEntities.FLYING_TIRE.create(sw);
                    if (t != null) { t.refreshPositionAndAngles(p.getX(), p.getY(), p.getZ(), 0, 0);
                        t.setVelocity(p.getRotationVec(1.0f).multiply(0.9)); t.setPower(100, true); sw.spawnEntity(t); }
                }
            }
            case 5 -> { // dolphin boxer
                p.sendMessage(Text.literal("§3Расклад: §bДельфин-боксёр!"), false);
                if (eff == 0) { p.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 300, 4)); }
                else if (eff == 1) {
                    Box box = p.getBoundingBox().expand(8);
                    for (MobEntity m : sw.getEntitiesByClass(MobEntity.class, box, e -> e.isAlive())) {
                        m.damage(sw.getDamageSources().magic(), 8.0f);
                    }
                } else {
                    p.addStatusEffect(new StatusEffectInstance(StatusEffects.DOLPHINS_GRACE, 1200, 0));
                    p.addStatusEffect(new StatusEffectInstance(StatusEffects.WATER_BREATHING, 1200, 0));
                }
            }
            case 6 -> { // bear skateboard
                p.sendMessage(Text.literal("§6Расклад: §4Медведь на скейтборде!"), false);
                if (eff == 0) { p.setVelocity(p.getRotationVec(1.0f).multiply(2.5).x, 0.3, p.getRotationVec(1.0f).multiply(2.5).z); p.velocityModified = true; }
                else if (eff == 1) {
                    var bear = EntityType.POLAR_BEAR.create(sw);
                    if (bear != null) { bear.refreshPositionAndAngles(p.getX() + 2, p.getY(), p.getZ(), 0, 0); sw.spawnEntity(bear); }
                } else {
                    p.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 400, 2));
                    p.addStatusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 400, 2));
                }
            }
            case 7 -> { // tiger
                p.sendMessage(Text.literal("§6Расклад: §eТигр-фокусник!"), false);
                if (eff == 0) { Vec3d t = p.getPos().add(p.getRotationVec(1.0f).multiply(16)); p.requestTeleport(t.x, t.y, t.z); }
                else if (eff == 1) {
                    for (int i = 0; i < 6; i++) {
                        var m = ModEntities.ANOMALY_MINION.create(sw);
                        if (m != null) { m.refreshPositionAndAngles(p.getX() + (r.nextDouble()-0.5)*4, p.getY(), p.getZ() + (r.nextDouble()-0.5)*4, 0, 0); sw.spawnEntity(m); }
                    }
                } else {
                    p.addStatusEffect(new StatusEffectInstance(StatusEffects.INVISIBILITY, 300, 0));
                    Box box = p.getBoundingBox().expand(6);
                    for (MobEntity m : sw.getEntitiesByClass(MobEntity.class, box, e -> e.isAlive())) {
                        m.discard();
                        var chicken = EntityType.CHICKEN.create(sw);
                        if (chicken != null) { chicken.refreshPositionAndAngles(m.getX(), m.getY(), m.getZ(), 0, 0); sw.spawnEntity(chicken); }
                    }
                }
            }
            case 8 -> { // monkey
                p.sendMessage(Text.literal("§eРасклад: §6Обезьяна с шариками!"), false);
                if (eff == 0) {
                    for (int i = 0; i < 5; i++) sw.createExplosion(p, p.getX() + (r.nextDouble()-0.5)*6, p.getY() + 2, p.getZ() + (r.nextDouble()-0.5)*6, 0.5f, World.ExplosionSourceType.NONE);
                } else if (eff == 1) { p.setVelocity(0, 2.5, 0); p.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING, 200, 0)); }
                else {
                    for (int i = 0; i < 16; i++) {
                        var stack = new ItemStack(i % 2 == 0 ? Items.GOLDEN_CARROT : Items.CAKE);
                        var ie = new net.minecraft.entity.ItemEntity(sw, p.getX(), p.getY() + 3, p.getZ(), stack);
                        ie.setVelocity((r.nextDouble()-0.5)*0.5, 0.3, (r.nextDouble()-0.5)*0.5);
                        sw.spawnEntity(ie);
                    }
                }
            }
            case 9 -> { // flamingo
                p.sendMessage(Text.literal("§dРасклад: §bФламинго в супермаркете!"), false);
                if (eff == 0) {
                    for (int i = 0; i < 6; i++) {
                        var stack = new ItemStack(i % 3 == 0 ? Items.BREAD : i % 3 == 1 ? Items.MELON : Items.CAKE, 4);
                        var ie = new net.minecraft.entity.ItemEntity(sw, p.getX(), p.getY() + 1, p.getZ(), stack);
                        sw.spawnEntity(ie);
                    }
                } else if (eff == 1) {
                    var sword = new ItemStack(Items.DIAMOND_SWORD);
                    sword.addEnchantment(net.minecraft.enchantment.Enchantments.SHARPNESS, 3);
                    net.minecraft.entity.ItemEntity ie = new net.minecraft.entity.ItemEntity(sw, p.getX(), p.getY() + 1, p.getZ(), sword);
                    sw.spawnEntity(ie);
                } else {
                    p.addStatusEffect(new StatusEffectInstance(StatusEffects.SATURATION, 400, 0));
                    p.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 400, 0));
                }
            }
            case 10 -> { // octopus
                p.sendMessage(Text.literal("§3Расклад: §9Осьминог с мороженым!"), false);
                if (eff == 0) {
                    Box box = p.getBoundingBox().expand(8);
                    for (MobEntity m : sw.getEntitiesByClass(MobEntity.class, box, e -> e.isAlive())) {
                        m.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 200, 3));
                    }
                } else if (eff == 1) {
                    p.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 100, 0));
                } else {
                    p.extinguish();
                    p.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 200, 0));
                }
            }
            case 11 -> { // turkey
                p.sendMessage(Text.literal("§4Расклад: §6Индюк-пират!"), false);
                if (eff == 0) {
                    for (int i = 0; i < 3; i++) {
                        var fire = EntityType.SMALL_FIREBALL.create(sw);
                        if (fire != null) { fire.setOwner(p);
                            fire.refreshPositionAndAngles(p.getX(), p.getY() + 1, p.getZ(), 0, 0);
                            fire.setVelocity(p.getRotationVec(1.0f).multiply(1.5)); sw.spawnEntity(fire); }
                    }
                } else if (eff == 1) {
                    for (int i = 0; i < 8; i++) {
                        var stack = new ItemStack(i % 3 == 0 ? Items.GOLD_INGOT : i % 3 == 1 ? Items.DIAMOND : Items.EMERALD, 2);
                        net.minecraft.entity.ItemEntity ie = new net.minecraft.entity.ItemEntity(sw, p.getX(), p.getY() + 2, p.getZ(), stack);
                        sw.spawnEntity(ie);
                    }
                } else {
                    for (int i = 0; i < 4; i++) {
                        var parrot = EntityType.PARROT.create(sw);
                        if (parrot != null) { parrot.refreshPositionAndAngles(p.getX() + i*2 - 3, p.getY() + 2, p.getZ() + i, 0, 0); sw.spawnEntity(parrot); }
                    }
                }
            }
            default -> p.sendMessage(Text.literal("§cНеизвестная карта."), false);
        }
    }
}
