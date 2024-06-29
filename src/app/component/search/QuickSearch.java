package app.component.search;

import app.services.ComponentStyle;
import app.services.CreatePage;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

public class QuickSearch implements ActionListener {
    private CreatePage createPage;
    private final ComponentStyle componentStyle = new ComponentStyle();
    private final JButton searchButton = new JButton("Suchen");

    public JPanel createQuickSearchPanel(CreatePage createPage) {
        this.createPage = createPage;

        JPanel marginPanel = new JPanel(new BorderLayout());
        marginPanel.setOpaque(false);
        marginPanel.setPreferredSize(new Dimension(Integer.MAX_VALUE, 330));
        marginPanel.setMinimumSize(new Dimension(Integer.MAX_VALUE, 330));
        marginPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 330));
        marginPanel.setBorder(new EmptyBorder(0, 20, 0, 20));

        JPanel quickSearchPanel = new JPanel();
        quickSearchPanel.setBackground(new Color(0x1D232C));
        quickSearchPanel.setBorder(BorderFactory.createLineBorder(new Color(0x1AFFFFFF, true)));
        quickSearchPanel.setLayout(new GridBagLayout());
        marginPanel.add(quickSearchPanel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(10, 10, 10, 10);

        JPanel innerQuickSearchPanel = new JPanel();
        innerQuickSearchPanel.setOpaque(false);
        innerQuickSearchPanel.setLayout(new GridBagLayout());
        quickSearchPanel.add(innerQuickSearchPanel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.weightx = 1.0;
        JLabel quickSearchLabel = new JLabel("Schnell - Suche");
        Font currentFont = quickSearchLabel.getFont();
        Font newFont = currentFont.deriveFont(Font.BOLD, 16f);
        quickSearchLabel.setFont(newFont);
        quickSearchLabel.setForeground(new Color(0xFFFFFF));
        innerQuickSearchPanel.add(quickSearchLabel, gbc);

        JPanel quickSearchPanelGrid = new JPanel(new GridBagLayout());
        quickSearchPanelGrid.setOpaque(false);

        addQuickSearchButton(quickSearchPanelGrid, 0,0, "/app/assets/bmw.png");
        addQuickSearchButton(quickSearchPanelGrid, 0,1, "/app/assets/audi.png");
        addQuickSearchButton(quickSearchPanelGrid, 0,2, "/app/assets/ford.png");
        addQuickSearchButton(quickSearchPanelGrid, 0,3, "/app/assets/toyota.png");
        addQuickSearchButton(quickSearchPanelGrid, 1,0, "/app/assets/mercedes-benz.png");
        addQuickSearchButton(quickSearchPanelGrid, 1,1, "/app/assets/ferrari.png");
        addQuickSearchButton(quickSearchPanelGrid, 1,2, "/app/assets/volkswagen.png");
        addQuickSearchButton(quickSearchPanelGrid, 1,3, "/app/assets/porsche.png");

        gbc.gridy = 1;
        innerQuickSearchPanel.add(quickSearchPanelGrid, gbc);

        searchButton.setPreferredSize(new Dimension(300, 25));
        componentStyle.styleButton(searchButton);
        searchButton.addActionListener(this);

        gbc.fill = GridBagConstraints.CENTER;
        gbc.gridy = 2;
        innerQuickSearchPanel.add(searchButton, gbc);

        return marginPanel;
    }

    private void addQuickSearchButton(JPanel panel, int gridy, int gridx, String brand_img) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(5, 5, 5, 5);
        panel.add(createQuickSearchButton(brand_img), gbc);
    }

    private JButton createQuickSearchButton(String brand_img) {
        JButton button = new JButton();
        button.setOpaque(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setBorder(BorderFactory.createLineBorder(new Color(0x1AFFFFFF, true)));
        button.setLayout(new GridBagLayout());

        JLabel brandLabel = new JLabel();
        URL brandURL = getClass().getResource(brand_img);
        if (brandURL != null) {
            brandLabel.setIcon(new ImageIcon(brandURL));
        }

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.CENTER;
        button.add(brandLabel, gbc);

        button.addActionListener(new ActionListener() {
            private boolean isSelected = false;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (isSelected) {
                    button.setBackground(null); // Entfernt die Hintergrundfarbe
                    button.setOpaque(false);
                } else {
                    button.setBackground(new Color(0x2E3B4E)); // Neue Hintergrundfarbe beim Klicken
                    button.setOpaque(true);
                }
                isSelected = !isSelected; // Schaltet den Zustand um
            }
        });

        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == searchButton) {
            createPage.showPage("Ergebnisse");
            createPage.isActiveLink(null);
        }
    }
}
