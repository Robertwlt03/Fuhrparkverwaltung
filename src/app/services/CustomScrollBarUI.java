package app.services;

import javax.swing.*;
import java.awt.*;

public class CustomScrollBarUI extends javax.swing.plaf.basic.BasicScrollBarUI {
    @Override
    protected void configureScrollBarColors() {
        Color baseColor = new Color(0x1D232C);

        this.thumbColor = baseColor.brighter().brighter();
        this.thumbHighlightColor = baseColor.brighter().brighter().brighter();
        this.thumbDarkShadowColor = baseColor.brighter();
        this.thumbLightShadowColor = baseColor.brighter().brighter().brighter();
        this.trackColor = baseColor;
        this.trackHighlightColor = baseColor.brighter();
    }

    @Override
    protected JButton createDecreaseButton(int orientation) {
        return createZeroButton();
    }

    @Override
    protected JButton createIncreaseButton(int orientation) {
        return createZeroButton();
    }

    private JButton createZeroButton() {
        JButton jbutton = new JButton();
        jbutton.setPreferredSize(new Dimension(0, 0));
        jbutton.setMinimumSize(new Dimension(0, 0));
        jbutton.setMaximumSize(new Dimension(0, 0));
        return jbutton;
    }
}
