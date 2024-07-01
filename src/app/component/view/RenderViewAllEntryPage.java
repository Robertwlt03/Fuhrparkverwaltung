package app.component.view;

import app.component.Header;
import app.component.view.card.AllCards;
import app.services.CreatePage;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class RenderViewAllEntryPage extends JPanel {
    public RenderViewAllEntryPage(CreatePage createPage, Connection conn) {
        // Set the layout for the main panel
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(0x14181E));

        // Add header
        Header header = new Header();
        JPanel headerPanel = header.createHeaderPanel("Ergebnisse");
        add(headerPanel);

        // Add card panel
        AllCards allCards = new AllCards(conn);
        JScrollPane cardPanel = allCards.createAllCardsPanel(createPage);
        add(cardPanel);

        add(Box.createRigidArea(new Dimension(0, 20)));
    }
}
