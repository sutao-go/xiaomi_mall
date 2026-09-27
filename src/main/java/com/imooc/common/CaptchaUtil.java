package com.imooc.common;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * 图形验证码工具（基于 JDK 自带 AWT，零第三方依赖）
 * <p>
 * 替换原 kaptcha：kaptcha 2.3.2 在 Spring Boot 3 下存在 javax/jakarta 兼容风险，
 * 且该库已多年未更新。这里直接用 JDK 能力生成，既没有兼容问题，也能看清验证码的原理。
 */
public final class CaptchaUtil {

    private static final int WIDTH = 120;
    private static final int HEIGHT = 40;

    /** 去掉易混淆字符：0/O、1/l/I */
    private static final String CHARS = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789";

    private static final Random RANDOM = new Random();

    private CaptchaUtil() {
    }

    /**
     * 生成验证码文本
     */
    public static String createText(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(CHARS.charAt(RANDOM.nextInt(CHARS.length())));
        }
        return sb.toString();
    }

    /**
     * 根据文本生成验证码图片（含干扰线与噪点）
     */
    public static BufferedImage createImage(String text) {
        BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // 背景
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        // 干扰线
        g.setColor(new Color(200, 200, 200));
        for (int i = 0; i < 8; i++) {
            g.drawLine(RANDOM.nextInt(WIDTH), RANDOM.nextInt(HEIGHT),
                    RANDOM.nextInt(WIDTH), RANDOM.nextInt(HEIGHT));
        }

        // 字符：随机颜色 + 轻微抖动
        g.setFont(new Font("Arial", Font.BOLD, 28));
        int charWidth = WIDTH / (text.length() + 1);
        for (int i = 0; i < text.length(); i++) {
            g.setColor(new Color(RANDOM.nextInt(120), RANDOM.nextInt(120), RANDOM.nextInt(120)));
            int x = charWidth * i + RANDOM.nextInt(6) + 6;
            int y = HEIGHT - 8 - RANDOM.nextInt(6);
            g.drawString(String.valueOf(text.charAt(i)), x, y);
        }

        // 噪点
        for (int i = 0; i < 60; i++) {
            image.setRGB(RANDOM.nextInt(WIDTH), RANDOM.nextInt(HEIGHT),
                    new Color(RANDOM.nextInt(255), RANDOM.nextInt(255), RANDOM.nextInt(255)).getRGB());
        }

        g.dispose();
        return image;
    }
}
