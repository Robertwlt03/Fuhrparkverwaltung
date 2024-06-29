package app.component;

import app.services.ComponentEffects;
import app.services.ComponentStyle;
import app.services.CreatePage;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Search implements ActionListener {
    private CreatePage createPage;
    private final ComponentStyle componentStyle = new ComponentStyle();
    private final ComponentEffects componentEffects = new ComponentEffects();
    private final JButton searchButton = new JButton("Suchen");
    private final int COMPONENT_HEIGHT = 25;

    public JPanel createSearchGridPanel(CreatePage createPage) {
        this.createPage = createPage;

        JPanel marginPanel = new JPanel(new BorderLayout());
        marginPanel.setOpaque(false);
        marginPanel.setPreferredSize(new Dimension(Integer.MAX_VALUE, 160));
        marginPanel.setMinimumSize(new Dimension(Integer.MAX_VALUE, 160));
        marginPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 160));
        marginPanel.setBorder(new EmptyBorder(0, 20, 0, 20));

        JPanel searchPanel = new JPanel();
        searchPanel.setBackground(new Color(0x1D232C));
        searchPanel.setBorder(BorderFactory.createLineBorder(new Color(0x1AFFFFFF, true)));
        searchPanel.setLayout(new GridBagLayout());
        marginPanel.add(searchPanel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(10, 10, 10, 10);

        JPanel innerSearchPanel = new JPanel();
        innerSearchPanel.setOpaque(false);
        innerSearchPanel.setLayout(new GridBagLayout());
        searchPanel.add(innerSearchPanel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.weightx = 1.0;
        JLabel searchLabel = new JLabel("Suchoptionen");
        Font currentFont = searchLabel.getFont();
        Font newFont = currentFont.deriveFont(Font.BOLD, 16f);
        searchLabel.setFont(newFont);
        searchLabel.setForeground(new Color(0xFFFFFF));
        innerSearchPanel.add(searchLabel, gbc);

        JPanel searchPanelGrid = new JPanel(new GridBagLayout());
        searchPanelGrid.setOpaque(false);

        addSearchPanel(searchPanelGrid, "Marke", 0, false, null);
        addSearchPanel(searchPanelGrid, "Modell", 1, false, null);
        addSearchPanel(searchPanelGrid, "Typ", 2, true, new String[]{"","PKW", "LKW"});
        addSearchPanel(searchPanelGrid, "Farbe", 3, false, null);
        addSearchPanel(searchPanelGrid, "Baujahr", 4, false, null);

        gbc.gridy = 1;
        innerSearchPanel.add(searchPanelGrid, gbc);

        searchButton.setPreferredSize(new Dimension(300, COMPONENT_HEIGHT));
        componentStyle.styleButton(searchButton);
        searchButton.addActionListener(this);

        gbc.fill = GridBagConstraints.CENTER;
        gbc.gridy = 2;
        innerSearchPanel.add(searchButton, gbc);

        return marginPanel;
    }

    private void addSearchPanel(JPanel panel, String label, int gridx, boolean isDropdown, String[] options) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = gridx;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(0, 5, 0, 5);
        panel.add(createSearchPanel(label, isDropdown, options), gbc);
    }

    private JPanel createSearchPanel(String label, boolean isDropdown, String[] options) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        JLabel jLabel = new JLabel(label);
        jLabel.setForeground(Color.WHITE);
        panel.add(jLabel, BorderLayout.NORTH);

        if (isDropdown && options != null) {
            JComboBox<String> comboBox = new JComboBox<>(options);
            comboBox.setBackground(new Color(255, 255, 255));
            comboBox.setForeground(Color.BLACK);
            componentEffects.inputFocus(comboBox);
            comboBox.setPreferredSize(new Dimension(comboBox.getPreferredSize().width, COMPONENT_HEIGHT));
            panel.add(comboBox, BorderLayout.CENTER);
        } else {
            JTextField textField = new JTextField();
            textField.setBackground(new Color(255, 255, 255));
            textField.setForeground(Color.BLACK);
            componentEffects.inputFocus(textField);
            textField.setPreferredSize(new Dimension(textField.getPreferredSize().width, COMPONENT_HEIGHT));
            panel.add(textField, BorderLayout.CENTER);
        }

        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == searchButton) {
            createPage.showPage("Ergebnisse");
            createPage.isActiveLink(null);
        }
    }
}
