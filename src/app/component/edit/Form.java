package app.component.edit;

import app.component.ImagePanel;
import app.services.ComponentEffects;
import app.services.ComponentStyle;
import app.services.CreatePage;
import app.services.CustomScrollBarUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class Form implements ActionListener {
    private CreatePage createPage;
    private final ComponentStyle componentStyle = new ComponentStyle();
    private final ComponentEffects componentEffects = new ComponentEffects();
    private final JButton addButton = new JButton("Änderungen Speichern");
    private final JButton imgButton = new JButton("Bild ändern");
    private final ImagePanel imgPreviewPanel = new ImagePanel();

    public JScrollPane createFormPanel(CreatePage createPage) {
        this.createPage = createPage;

        JPanel marginPanel = new JPanel(new GridBagLayout());
        marginPanel.setOpaque(false);
        marginPanel.setBorder(new EmptyBorder(0, 20, 0, 20));

        GridBagConstraints topGbc = new GridBagConstraints();
        topGbc.fill = GridBagConstraints.HORIZONTAL;
        topGbc.gridx = 1;
        topGbc.gridy = 0;
        topGbc.weightx = 1.0;
        topGbc.anchor = GridBagConstraints.NORTH;

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(0x1D232C));
        formPanel.setBorder(BorderFactory.createLineBorder(new Color(0x1AFFFFFF, true)));
        marginPanel.add(formPanel, topGbc);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(10, 10, 10, 10);

        JPanel innerFormPanel = new JPanel();
        innerFormPanel.setOpaque(false);
        innerFormPanel.setLayout(new GridBagLayout());
        formPanel.add(innerFormPanel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.weightx = 1.0;
        JLabel formLabel = new JLabel("Formular");
        Font currentFont = formLabel.getFont();
        Font newFont = currentFont.deriveFont(Font.BOLD, 16f);
        formLabel.setFont(newFont);
        formLabel.setForeground(new Color(0xFFFFFF));
        innerFormPanel.add(formLabel, gbc);

        JPanel formGrid = new JPanel(new GridBagLayout());
        formGrid.setOpaque(false);

        imgPreviewPanel.setOpaque(false);
        imgPreviewPanel.setBorder(BorderFactory.createLineBorder(new Color(0x1AFFFFFF, true)));
        imgPreviewPanel.setPreferredSize(new Dimension(355, 200));
        imgPreviewPanel.setMinimumSize(new Dimension(355, 200));
        imgPreviewPanel.setMaximumSize(new Dimension(355, 200));

        gbc.insets = new Insets(5, 5, 10, 5);
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridy = 0;
        formGrid.add(imgPreviewPanel, gbc);

        imgButton.setPreferredSize(new Dimension(300, 25));
        componentStyle.styleButton(imgButton);
        imgButton.addActionListener(this);
        gbc.gridy = 1;
        formGrid.add(imgButton, gbc);

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.BOTH;

        gbc.gridy = 2;
        formGrid.add(Box.createRigidArea(new Dimension(0, 0)), gbc);

        addInput(formGrid, 3, "Marke", false, null);
        addInput(formGrid, 4, "Modell", false, null);
        addInput(formGrid, 5, "Typ", true, new String[]{"","PKW", "LKW"});
        addInput(formGrid, 6, "Farbe", false, null);
        addInput(formGrid, 7, "Baujahr", false, null);
        addInput(formGrid, 8, "Beschreibung", false, null);

        gbc.gridy = 1;
        innerFormPanel.add(formGrid, gbc);

        addButton.setPreferredSize(new Dimension(300, 25));
        componentStyle.styleButton(addButton);
        addButton.addActionListener(this);

        gbc.fill = GridBagConstraints.CENTER;
        gbc.gridy = 2;
        innerFormPanel.add(addButton, gbc);

        JScrollPane scrollPane = new JScrollPane(marginPanel);
        scrollPane.setBorder(null);
        scrollPane.setBackground(null);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(30);

        // Customize the scrollbars
        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
        verticalScrollBar.setUI(new CustomScrollBarUI());

        return scrollPane;
    }

    private void addInput(JPanel panel, int gridy, String label, boolean isDropdown, String[] options) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = gridy;
        gbc.weightx = 1.0;
        if (gridy != 8) {
            gbc.insets = new Insets(5, 5, 10, 5);
        } else {
            gbc.insets = new Insets(5, 5, 0, 5);
        }
        panel.add(createInput(label, isDropdown, options), gbc);
    }

    private JPanel createInput(String label, boolean isDropdown, String[] options) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);

        JLabel jLabel = new JLabel(label);
        jLabel.setForeground(Color.WHITE);
        panel.add(jLabel, BorderLayout.NORTH);


        int COMPONENT_HEIGHT = 25;
        if (isDropdown && options != null) {
            JComboBox<String> comboBox = new JComboBox<>(options);
            comboBox.setBackground(new Color(255, 255, 255));
            comboBox.setForeground(Color.BLACK);
            componentEffects.inputFocus(comboBox);
            comboBox.setPreferredSize(new Dimension(comboBox.getPreferredSize().width, COMPONENT_HEIGHT));
            panel.add(comboBox, BorderLayout.CENTER);
        } else if (label.equals("Beschreibung")) {
            JTextArea textArea = new JTextArea();
            textArea.setBackground(new Color(255, 255, 255));
            textArea.setForeground(Color.BLACK);
            componentEffects.inputFocus(textArea);
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(scrollPane.getPreferredSize().width, 50));
            JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
            verticalScrollBar.setUI(new CustomScrollBarUI());
            panel.add(scrollPane, BorderLayout.CENTER);
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
        if (e.getSource() == imgButton) {
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Bilddateien", "jpg", "jpeg", "png");
            fileChooser.setFileFilter(filter);

            int result = fileChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                imgPreviewPanel.setImage(selectedFile.getAbsolutePath());
                imgPreviewPanel.repaint();
            }
        }
        if (e.getSource() == addButton) {
            createPage.showPage("Startseite");
            createPage.isActiveLink("home");
        }
    }
}
