package app.component.homepage;

import app.services.ComponentStyle;
import app.services.CreatePage;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Choice implements ActionListener {
    private CreatePage createPage;
    private final ComponentStyle componentStyle = new ComponentStyle();
    private final JButton addEntryButton = new JButton("Jetzt erstellen");
    private final JButton loadAllEntryButton = new JButton("Suchen");

    public JPanel createContentGridPanel(CreatePage createPage) {
        this.createPage = createPage;

        JPanel contentGridPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        contentGridPanel.setOpaque(false);
        contentGridPanel.setPreferredSize(new Dimension(Integer.MAX_VALUE, 200));
        contentGridPanel.setBorder(new EmptyBorder(0, 20, 0, 20));

        JPanel addPanel = createAddPanel();
        contentGridPanel.add(addPanel);

        JPanel loadAllPanel = createLoadAllPanel();
        contentGridPanel.add(loadAllPanel);

        return contentGridPanel;
    }

    private JPanel createAddPanel() {
        JPanel addEntryPanel = new JPanel();
        addEntryPanel.setBackground((new Color(0x1D232C)));
        addEntryPanel.setBorder(BorderFactory.createLineBorder(new Color(0x1AFFFFFF, true)));
        addEntryPanel.setLayout(new GridBagLayout());

        JPanel innerAddEntryPanel = new JPanel();
        innerAddEntryPanel.setOpaque(false);
        innerAddEntryPanel.setLayout(new BoxLayout(innerAddEntryPanel, BoxLayout.Y_AXIS));
        addEntryPanel.add(innerAddEntryPanel);

        JLabel addEntryLabel = new JLabel("Neuer Eintrag erstellen");
        Font currentFont = addEntryLabel.getFont();
        Font newFont = currentFont.deriveFont(Font.BOLD, 20f);
        addEntryLabel.setFont(newFont);
        addEntryLabel.setForeground(new Color(0xFFFFFF));
        addEntryLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        innerAddEntryPanel.add(addEntryLabel);

        innerAddEntryPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        addEntryButton.setPreferredSize(new Dimension(300, 25));
        addEntryButton.setMaximumSize(new Dimension(300, 25));
        addEntryButton.setMinimumSize(new Dimension(300, 25));
        componentStyle.styleButton(addEntryButton);
        addEntryButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        addEntryButton.addActionListener(this);
        innerAddEntryPanel.add(addEntryButton);

        return addEntryPanel;
    }

    private JPanel createLoadAllPanel() {
        JPanel loadAllEntryPanel = new JPanel();
        loadAllEntryPanel.setBackground(new Color(0x1D232C));
        loadAllEntryPanel.setBorder(BorderFactory.createLineBorder(new Color(0x1AFFFFFF, true)));
        loadAllEntryPanel.setLayout(new GridBagLayout());

        JPanel innerLoadAllEntryPanel = new JPanel();
        innerLoadAllEntryPanel.setOpaque(false);
        innerLoadAllEntryPanel.setLayout(new BoxLayout(innerLoadAllEntryPanel, BoxLayout.Y_AXIS));
        loadAllEntryPanel.add(innerLoadAllEntryPanel);

        JLabel loadAllEntryLabel = new JLabel("Alle Eintrage ansehen");
        Font currentFont = loadAllEntryLabel.getFont();
        Font newFont = currentFont.deriveFont(Font.BOLD, 20f);
        loadAllEntryLabel.setFont(newFont);
        loadAllEntryLabel.setForeground(new Color(0xFFFFFF));
        loadAllEntryLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        innerLoadAllEntryPanel.add(loadAllEntryLabel);

        innerLoadAllEntryPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        loadAllEntryButton.setPreferredSize(new Dimension(300, 25));
        loadAllEntryButton.setMaximumSize(new Dimension(300, 25));
        loadAllEntryButton.setMinimumSize(new Dimension(300, 25));
        componentStyle.styleButton(loadAllEntryButton);
        loadAllEntryButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loadAllEntryButton.addActionListener(this);
        innerLoadAllEntryPanel.add(loadAllEntryButton);

        return loadAllEntryPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addEntryButton) {
            createPage.showPage("Hinzufügen");
            createPage.isActiveLink(null);
        } else if (e.getSource() == loadAllEntryButton) {
            createPage.showPage("Ergebnisse");
            createPage.isActiveLink(null);
        }
    }
}
