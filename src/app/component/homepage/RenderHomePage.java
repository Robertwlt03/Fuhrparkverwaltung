package app.component.homepage;

import app.component.Header;
import app.component.Search;
import app.services.CreatePage;

import javax.swing.*;
import java.awt.*;

public class RenderHomePage extends JPanel {
    public RenderHomePage(CreatePage createPage) {
        // Set the layout for the main panel
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(0x14181E));

        // Add header
        Header header = new Header();
        JPanel headerPanel = header.createHeaderPanel("Startseite");
        add(headerPanel);

        // Add search panel
        Search search = new Search();
        JPanel searchPanel = search.createSearchGridPanel(createPage);
        add(searchPanel);

        add(Box.createRigidArea(new Dimension(0, 20)));

        // Add content grid
        Choice choice = new Choice();
        JPanel contentGridPanel = choice.createContentGridPanel(createPage);
        add(contentGridPanel);

        add(Box.createRigidArea(new Dimension(0, 20)));
    }
}
