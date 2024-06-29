package app.component.view;

import app.component.Header;
import app.component.view.card.Card;
import app.services.CreatePage;

import javax.swing.*;
import java.awt.*;

public class RenderViewEntryPage extends JPanel {
    public RenderViewEntryPage(CreatePage createPage) {
        // Set the layout for the main panel
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(0x14181E));

        // Add header
        Header header = new Header();
        JPanel headerPanel = header.createHeaderPanel("Ergebnisse");
        add(headerPanel);

        // Add card panel
        Card card = new Card();
        JScrollPane cardPanel = card.createCardPanel(createPage);
        add(cardPanel);

        add(Box.createRigidArea(new Dimension(0, 20)));
    }
}
