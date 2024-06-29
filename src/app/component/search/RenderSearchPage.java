package app.component.search;

import app.component.Header;
import app.component.Search;
import app.services.CreatePage;

import javax.swing.*;
import java.awt.*;

public class RenderSearchPage extends JPanel {
    public RenderSearchPage(CreatePage createPage) {
        // Set the layout for the main panel
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(0x14181E));

        // Add header
        Header header = new Header();
        JPanel headerPanel = header.createHeaderPanel("Suche");
        add(headerPanel);

        // Add search panel
        Search search = new Search();
        JPanel searchPanel = search.createSearchGridPanel(createPage);
        add(searchPanel);

        add(Box.createRigidArea(new Dimension(0, 20)));

        QuickSearch quickSearch = new QuickSearch();
        JPanel quickSearchPanel = quickSearch.createQuickSearchPanel(createPage);
        add(quickSearchPanel);

        add(Box.createRigidArea(new Dimension(0, 20)));
    }
}
