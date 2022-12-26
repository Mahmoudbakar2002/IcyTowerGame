package com.fsci.games.utills;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class FontLoader {
    public static  Font Broom;
    static {
        InputStream fontBroom=FontLoader.class.getResourceAsStream("/assets/fonts/Broom.ttf");
        try {
            Broom=Font.createFont(Font.TRUETYPE_FONT,fontBroom);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }


    public static Font createFont(int fontType, InputStream input) {
        try {
            Font custom =
                    Font.createFont(
                            fontType,
                            input
                    );
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            //register the font
            ge.registerFont(custom);
            return custom;
        } catch (FontFormatException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }

    }
}
