package app.component;

import javax.swing.*;
import java.awt.*;

public class Header {
    public JPanel createHeaderPanel(String header) {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        headerPanel.setPreferredSize(new Dimension(Integer.MAX_VALUE, 100));
        headerPanel.setMinimumSize(new Dimension(Integer.MAX_VALUE, 100));
        headerPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));

        JLabel pageName = new JLabel(header);
        Font currentFont = pageName.getFont();
        Font newFont = currentFont.deriveFont(Font.BOLD, 30f);
        pageName.setFont(newFont);
        pageName.setForeground(Color.WHITE);
        headerPanel.add(pageName, BorderLayout.CENTER);

        return headerPanel;
    }
}
