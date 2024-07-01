package app.component.view;

import app.component.Header;
import app.component.view.card.SearchCards;
import app.services.CreatePage;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class RenderViewSearchPage extends JPanel {
    public RenderViewSearchPage(CreatePage createPage, Connection conn) {
        // Set the layout for the main panel
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(0x14181E));

        // Add header
        Header header = new Header();
        JPanel headerPanel = header.createHeaderPanel("Ergebnisse");
        add(headerPanel);

        // Add card panel
        SearchCards searchCards = new SearchCards(conn);
        JScrollPane cardPanel = searchCards.createSearchCardsPanel(createPage, "", "BMW", "", "", "", 2003);
        add(cardPanel);

        add(Box.createRigidArea(new Dimension(0, 20)));
    }
}
