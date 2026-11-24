package com.example.memecraft.client;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class MemeCardScreen extends Screen {
    private boolean flipped = false;
    private int selectedCard = 0;
    private long clickTime = 0;
    private static final String[] CARD_NAMES = {
            "Сова на скакалке", "Собака-гольфач", "Жираф с мыльными пузырями",
            "Змейка на сёрфе", "Скунс-гонщик", "Дельфин-боксёр",
            "Медведь на скейтборде", "Тигр-фокусник", "Обезьяна с шариками",
            "Фламинго в супермаркете", "Осьминог с мороженым", "Индюк-пират"
    };
    private static final String[] CARD_TEXTURES = {
            "card_owl", "card_dog_golf", "card_giraffe", "card_snake", "card_skunk",
            "card_dolphin", "card_bear", "card_tiger", "card_monkey", "card_flamingo",
            "card_octopus", "card_turkey"
    };
    private static final String[] CARD_EFFECT_TEXT = {
            "Ядерная бомбардировка / Молнии / Тьма и Аномалия",
            "Горящие колёса / Мобы в небо / Взрывной снаряд",
            "Левитация / Мобы в пузырях / Плавное падение",
            "Полное исцеление / Фонтан алмазов / Везение V",
            "Скорость VII / Ядовитое облако / Бешеное колесо",
            "Сила V / Ударная волна / Дыхание дельфина",
            "Рывок с искрами / Полярный медведь / Сопротивление III",
            "Телепорт на 16 / 6 человечков / Невидимость",
            "Фейерверки / Подбрасывание / Дождь из морковок",
            "Продукты / Алмазный меч / Насыщение",
            "Заморозка / Чернильное облако / Лёд и снежки",
            "Пушечный залп / Пиратский сундук / Попугаи"
    };

    protected MemeCardScreen() { super(Text.literal("Колода Коляки")); }

    @Override
    protected void init() {
        super.init();
        int cx = this.width / 2;
        int cy = this.height / 2;
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Открыть карту"), b -> {
            if (!flipped) {
                flipped = true;
                selectedCard = (int) (Math.random() * 12);
                clickTime = System.currentTimeMillis();
                PacketByteBuf buf = PacketByteBufs.create();
                buf.writeInt(selectedCard);
                ClientPlayNetworking.send(new Identifier("memecraft", "card_effect"), buf);
            }
        }).dimensions(cx - 60, this.height - 40, 120, 20).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context);
        int cx = this.width / 2;
        int cy = this.height / 2;
        int w = 135, h = 195;
        int x = cx - w/2, y = cy - h/2;
        if (!flipped) {
            // blue card back
            context.fill(x, y, x + w, y + h, 0xFF1A2A80);
            context.drawBorder(x, y, w, h, 0xFFFFC030);
            context.drawCenteredTextWithShadow(this.textRenderer, "Нажми на карту, чтобы сделать расклад!",
                    cx, y - 20, 0xFFFFD060);
            context.drawCenteredTextWithShadow(this.textRenderer, "★", cx, cy - 4, 0xFFFFD060);
        } else {
            // face up card
            context.fill(x, y, x + w, y + h, 0xFFF5F0DC);
            context.drawBorder(x, y, w, h, 0xFF603020);
            // draw animal icon
            context.fill(x + 25, y + 20, x + 110, y + 105, 0xFF303030);
            context.drawCenteredTextWithShadow(this.textRenderer, CARD_NAMES[selectedCard],
                    cx, y + 120, 0xFF603020);
            context.drawCenteredTextWithShadow(this.textRenderer, CARD_EFFECT_TEXT[selectedCard],
                    cx, y + 145, 0xFF603020);
            context.drawCenteredTextWithShadow(this.textRenderer, "Судьба выбрана",
                    cx, y + 165, 0xFF803020);
        }
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean shouldPause() { return false; }
}
