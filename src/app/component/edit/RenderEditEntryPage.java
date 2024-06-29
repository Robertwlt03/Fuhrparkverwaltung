package app.component.edit;

import app.component.Header;
import app.services.CreatePage;

import javax.swing.*;
import java.awt.*;

public class RenderEditEntryPage extends JPanel {
    public RenderEditEntryPage(CreatePage createPage) {
        // Set the layout for the main panel
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(0x14181E));

        // Add header
        Header header = new Header();
        JPanel headerPanel = header.createHeaderPanel("Bearbeiten");
        add(headerPanel);

        // Add form panel
        Form form = new Form();
        JScrollPane formPanel = form.createFormPanel(createPage);
        add(formPanel);

        add(Box.createRigidArea(new Dimension(0, 20)));
    }
}
