package app.component.view.card;

import app.services.CreatePage;
import app.services.CustomScrollBarUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class Card {
    private CreatePage createPage;
    private final CreateCard createCard = new CreateCard();

    public JScrollPane  createCardPanel(CreatePage createPage) {
        this.createPage = createPage;

        JPanel cardPanel = new JPanel(new BorderLayout());
        cardPanel.setOpaque(false);
        cardPanel.setBorder(new EmptyBorder(0, 20, 0, 20));

        JPanel innerCardPanel = new JPanel();
        innerCardPanel.setOpaque(false);
        innerCardPanel.setLayout(new GridBagLayout());
        cardPanel.add(innerCardPanel, BorderLayout.NORTH);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;

        JPanel cardGrid = new JPanel(new GridBagLayout());
        cardGrid.setOpaque(false);

        // Initial layout setup
        updateCardLayout(this.createPage, cardGrid, createPage.getWidth());


        // Update the layout based on frame width
        createPage.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                updateCardLayout(Card.this.createPage, cardGrid, createPage.getWidth());
            }
        });

        JScrollPane scrollPane = new JScrollPane(cardPanel);
        scrollPane.setBorder(null);
        scrollPane.setBackground(null);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(30);

        // Customize the scrollbars
        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
        verticalScrollBar.setUI(new CustomScrollBarUI());

        gbc.gridy = 1;
        innerCardPanel.add(cardGrid, gbc);

        return scrollPane;
    }

    private void updateCardLayout(CreatePage createPage ,JPanel cardGrid, int pageWidth) {
        cardGrid.removeAll();

        String[] card_brand = {"BMW", "Audi", "Ferrari"};
        int gridy = 0;
        int gridx = -1; // -1 so it starts with 0
        int counter = -1; // -1 so it starts with 0
        int max_counter = 3;
        int max_column = 2; // max for gridx starts with 0 | 2 = 3 column

       if (pageWidth <= 1300 && pageWidth > 900) {
            max_counter = 2;
            max_column = 1;
        } else if (pageWidth <= 900) {
           max_counter = 1;
           max_column = 0;
       }

        for (int i = 0; i < card_brand.length; i++) {
            counter++;
            gridx++;

            if (counter == max_counter) {
                counter = -1;
                gridx = -1;
                gridy++;
            }

            createCard.addCard(
                    createPage,
                    max_column,
                    cardGrid,
                    "test",
                    card_brand[i],
                    "123",
                    "PKW",
                    "Grau",
                    "2010",
                    "Dies ist ein langer Text, der automatisch umbrochen wird, wenn die Grenze erreicht wird.",
                    gridx,
                    gridy
            );
        }

        cardGrid.revalidate();
        cardGrid.repaint();
    }
}
