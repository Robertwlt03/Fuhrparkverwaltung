package app.services;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.event.*;

public class ComponentEffects {
    public void buttonHover(JButton button) {
        Color normalColor = new Color(0xFFFFFF);
        Color hoverColor = new Color(0xB3FFFFFF, true);

        button.setForeground(normalColor);

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setForeground(hoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setForeground(normalColor);
            }
        });
    }

    public void inputFocus(JComponent component) {
        // Define the borders
        Border defaultBorder = new LineBorder(new Color(0x4D000000, true), 2);
        Border focusBorder = new LineBorder(new Color(28, 121, 228), 2);

        component.setBorder(defaultBorder);
        component.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                component.setBorder(focusBorder);
            }

            @Override
            public void focusLost(FocusEvent e) {
                component.setBorder(defaultBorder);
            }
        });
    }


}
