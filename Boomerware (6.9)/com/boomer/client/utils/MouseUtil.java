package com.boomer.client.utils;

/**
 * made by oHare for BoomerWare
 *
 * @since 8/19/2019
 **/
public class MouseUtil {
    public static  boolean mouseWithinBounds(int mouseX, int mouseY, double x, double y, double width, double height) {
        return (mouseX >= x && mouseX <= (x + width)) && (mouseY >= y && mouseY <= (y + height));
    }
}
