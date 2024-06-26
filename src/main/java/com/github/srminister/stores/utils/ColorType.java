package com.github.srminister.stores.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Color;


@Getter
@RequiredArgsConstructor
public enum ColorType {

    AQUA(Color.AQUA),
    BLACK(Color.BLACK),
    BLUE(Color.BLUE),
    FUCHSIA(Color.FUCHSIA),
    GRAY(Color.GRAY),
    GREEN(Color.GREEN),
    LIME(Color.LIME),
    MAROON(Color.MAROON),
    NAVY(Color.NAVY),
    OLIVE(Color.OLIVE),
    ORANGE(Color.ORANGE),
    PURPLE(Color.PURPLE),
    RED(Color.RED),
    SILVER(Color.SILVER),
    TEAL(Color.TEAL),
    WHITE(Color.WHITE),
    YELLOW(Color.YELLOW);


    private final Color color;
}
