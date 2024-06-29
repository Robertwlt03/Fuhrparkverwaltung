package app.services;

import javax.swing.*;
import java.awt.*;

public class ComponentStyle {
    public void styleButton (JButton button) {
        ComponentEffects componentEffects = new ComponentEffects();

        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setBackground(new Color(28, 121, 228));
        componentEffects.buttonHover(button);

        Font currentFont = button.getFont();
        Font newFont = currentFont.deriveFont(Font.BOLD, 14f);
        button.setFont(newFont);

    }
}
