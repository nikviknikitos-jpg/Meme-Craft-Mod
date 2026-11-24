package com.example.memecraft.client;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class MemeEncyclopediaScreen extends Screen {
    private int page = 0;
    private static final String[] PAGES = {
            "§6Индусский Моторчик\n\nЭтот мотор — истинная легенда:\n" +
                    "станина, цилиндр, катушка и вал.\n" +
                    "ПКМ по нему — включить.\n" +
                    "ПКМ Покрышкой — надеть колесо.\n" +
                    "Shift+ПКМ — сорвать колесо.",
            "§6Бешеное Колесо и Перегрев\n\n" +
                    "После 15 секунд работы колесо загорается,\n" +
                    "на 20-й — само срывается в огненный полёт.\n" +
                    "Горящее колесо поджигает путь и взрывается.",
            "§6Фиолетовая Аномалия\n\n" +
                    "Шар диаметром 5 блоков. Спавнится сама\n" +
                    "или через яйцо призыва.\n" +
                    "Бросьте палку — появятся 6 человечков!",
            "§6Кнопка Удачи\n\n" +
                    "Не жми. Ну ладно, жми.\n" +
                    "6 случайных мемных событий:\n" +
                    "наковальня, левитация, курицы, аномалия,\n" +
                    "еда-фонтан или БАБАХ.",
            "§6Колода Коляки: Сова на скакалке\n\n" +
                    "12 уникальных карт:\n" +
                    "Сова, Собака-гольфач, Жираф, Змейка,\n" +
                    "Скунс, Дельфин, Медведь, Тигр, Обезьяна,\n" +
                    "Фламинго, Осьминог, Индюк.\n" +
                    "У каждой карты — 3 случайных эффекта!"
    };

    protected MemeEncyclopediaScreen() { super(Text.literal("Мемная Энциклопедия")); }

    @Override
    protected void init() {
        super.init();
        int cx = this.width / 2;
        int cy = this.height / 2;
        this.addDrawableChild(ButtonWidget.builder(Text.literal("< Назад"), b -> {
            if (page > 0) page--;
        }).dimensions(cx - 120, cy + 100, 80, 20).build());
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Вперед >"), b -> {
            if (page < PAGES.length - 1) page++;
        }).dimensions(cx + 40, cy + 100, 80, 20).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context);
        int w = 260, h = 220;
        int left = (this.width - w) / 2;
        int top = (this.height - h) / 2;
        context.fill(left, top, left + w, top + h, 0xFFF4E2C0);
        context.drawBorder(left, top, w, h, 0xFF603020);
        context.drawCenteredTextWithShadow(this.textRenderer, "Мемная Энциклопедия", left + w/2, top + 12, 0xFF603020);
        String content = PAGES[page];
        String[] lines = content.split("\n");
        int y = top + 40;
        for (String line : lines) {
            context.drawText(this.textRenderer, Text.literal(line), left + 15, y, 0xFF442200, false);
            y += 12;
        }
        context.drawCenteredTextWithShadow(this.textRenderer, "Стр. " + (page+1) + "/" + PAGES.length,
                left + w/2, top + h - 20, 0xFF603020);
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean shouldPause() { return false; }
}
