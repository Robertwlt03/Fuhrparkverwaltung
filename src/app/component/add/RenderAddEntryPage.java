package app.component.add;

import app.component.Header;
import app.services.CreatePage;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class RenderAddEntryPage extends JPanel {
    public RenderAddEntryPage(CreatePage createPage, Connection conn) {
        // Set the layout for the main panel
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(0x14181E));

        // Add header
        Header header = new Header();
        JPanel headerPanel = header.createHeaderPanel("Eintrag erstellen");
        add(headerPanel);

        // Add form panel
        Form form = new Form();
        JScrollPane formPanel = form.createFormPanel(createPage, conn);
        add(formPanel);

        add(Box.createRigidArea(new Dimension(0, 20)));
    }
}
