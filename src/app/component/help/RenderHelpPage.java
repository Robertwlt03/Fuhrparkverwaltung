package app.component.help;

import app.component.Header;

import javax.swing.*;
import java.awt.*;

public class RenderHelpPage extends JPanel {
    public RenderHelpPage() {
        // Set the layout for the main panel
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(0x14181E));

        // Add header
        Header header = new Header();
        JPanel headerPanel = header.createHeaderPanel("Help");
        add(headerPanel);
    }
}
