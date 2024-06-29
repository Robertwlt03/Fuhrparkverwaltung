package app.component.view.card;

import app.component.ImagePanel;
import app.services.ComponentEffects;
import app.services.ComponentStyle;
import app.services.CreatePage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class CreateCard {
    private CreatePage createPage;
    private final ComponentStyle componentStyle = new ComponentStyle();
    private final ComponentEffects componentEffects = new ComponentEffects();

    public void addCard(
            CreatePage createPage,
            int max_column,
            JPanel panel,
            String img_location,
            String brand,
            String model,
            String type,
            String color,
            String construction_year,
            String description,
            int gridx,
            int gridy
    ) {
        this.createPage = createPage;

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.weightx = 1.0;

        if (gridx < max_column || gridx == 0) {
            gbc.insets = new Insets(0, 0, 10, 10);
        } else {
            gbc.insets = new Insets(0, 0, 10, 0);
        }

        panel.add(createCard(img_location, brand, model, type, color, construction_year, description), gbc);
    }

    private JPanel createCard(
            String img_location,
            String brand,
            String model,
            String type,
            String color,
            String construction_year,
            String description
    ) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createLineBorder(new Color(0x1AFFFFFF, true)));
        panel.setBackground(new Color(0x1D232C));


        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(10, 10, 10, 10);

        ImagePanel imgPreviewPanel = new ImagePanel();
        imgPreviewPanel.setOpaque(false);
        imgPreviewPanel.setBorder(BorderFactory.createLineBorder(new Color(0x1AFFFFFF, true)));
        imgPreviewPanel.setPreferredSize(new Dimension(0, 100));
        imgPreviewPanel.setImage(img_location);
        imgPreviewPanel.repaint();

        // Add component listener to maintain 16:9 aspect ratio
        panel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int width = panel.getWidth();
                int height = (int) (width / 16.0 * 9);
                imgPreviewPanel.setPreferredSize(new Dimension(width, height));
                panel.revalidate();
            }
        });

        panel.add(imgPreviewPanel, gbc);

        JPanel gridPanel = new JPanel(new GridBagLayout());
        gridPanel.setOpaque(false);
        gbc.gridy = 1;
        panel.add(gridPanel, gbc);

        JPanel leftPanel = new JPanel(new GridBagLayout());
        leftPanel.setOpaque(false);
        GridBagConstraints leftGbc = new GridBagConstraints();
        leftGbc.fill = GridBagConstraints.BOTH;
        leftGbc.gridx = 0;
        leftGbc.gridy = 0;
        leftGbc.weightx = 1.0;
        leftGbc.insets = new Insets(0, 0, 0, 60);
        gridPanel.add(leftPanel, leftGbc);

        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setOpaque(false);
        GridBagConstraints rightGbc = new GridBagConstraints();
        rightGbc.fill = GridBagConstraints.HORIZONTAL;
        rightGbc.gridx = 1;
        rightGbc.gridy = 0;
        rightGbc.weightx = 1.0;
        rightGbc.anchor = GridBagConstraints.NORTH;
        gridPanel.add(rightPanel, rightGbc);

        // Add description in the rightPanel
        JPanel jPanel_description = new JPanel();
        createContent(rightPanel, jPanel_description, 0, "Beschreibung", description, true);

        // Add other information in the leftPanel
        JPanel jPanel_brand = new JPanel();
        createContent(leftPanel, jPanel_brand, 1, "Marke", brand, false);

        JPanel jPanel_model = new JPanel();
        createContent(leftPanel, jPanel_model, 2, "Modell", model, false);

        JPanel jPanel_type = new JPanel();
        createContent(leftPanel, jPanel_type, 3, "Typ", type, false);

        JPanel jPanel_color = new JPanel();
        createContent(leftPanel, jPanel_color, 4, "Farbe", color, false);

        JPanel jPanel_year = new JPanel();
        createContent(leftPanel, jPanel_year, 5, "Baujahr", construction_year, false);

        JButton editButton = new JButton("Bearbeiten");
        editButton.setPreferredSize(new Dimension(Integer.MAX_VALUE, 25));
        componentStyle.styleButton(editButton);
        componentEffects.buttonHover(editButton);
        gbc.gridy = 2;
        panel.add(editButton, gbc);
        addActionListenerToButton(editButton);


        return panel;
    }

    private void createContent(JPanel parentPanel, JPanel panel, int gridy, String header, String content, boolean verticalStack) {
        GridBagConstraints gbcOuter = new GridBagConstraints();
        gbcOuter.fill = GridBagConstraints.BOTH;
        gbcOuter.gridx = 0;
        gbcOuter.gridy = gridy;
        gbcOuter.weightx = 1.0;
        gbcOuter.insets = new Insets(5, 5, 5, 5);

        panel.setLayout(new GridBagLayout());
        panel.setOpaque(false);
        parentPanel.add(panel, gbcOuter);

        GridBagConstraints gbcInner = new GridBagConstraints();
        gbcInner.fill = GridBagConstraints.BOTH;
        gbcInner.gridx = 0;
        gbcInner.gridy = 0;

        JLabel jLabel_header = new JLabel(header + ":");
        jLabel_header.setForeground(new Color(0xFFA2A2A2, true));
        if (!verticalStack) {
            jLabel_header.setPreferredSize(new Dimension(60, jLabel_header.getPreferredSize().height));
        }
        panel.add(jLabel_header, gbcInner);

        gbcInner.gridx = verticalStack ? 0 : 1;
        gbcInner.gridy = verticalStack ? 1 : 0;
        gbcInner.insets = new Insets(verticalStack ? 5 : 0, 0, 0, 0);
        gbcInner.weightx = 1.0;
        gbcInner.weighty = 1.0;


        if (verticalStack) {
            JTextArea jTextArea_content = new JTextArea (content);
            jTextArea_content.setLineWrap(true);
            jTextArea_content.setWrapStyleWord(true);
            jTextArea_content.setOpaque(false);
            jTextArea_content.setEditable(false);
            jTextArea_content.setFocusable(false);
            jTextArea_content.setForeground(Color.WHITE);
            panel.add(jTextArea_content, gbcInner);
        } else {
            JLabel jLabel_content = new JLabel(content);
            jLabel_content.setForeground(Color.WHITE);
            panel.add(jLabel_content, gbcInner);
        }
    }

    private void addActionListenerToButton(JButton button) {
        button.addActionListener(e -> {
            if (e.getSource() == button) {
                createPage.showPage("Bearbeiten");
                createPage.isActiveLink(null);
            }
        });
    }
}
