package app.component.view.card;

import app.component.ImagePanel;
import app.services.ComponentEffects;
import app.services.ComponentStyle;
import app.component.edit.CreateEditPage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.net.URL;
import java.sql.Connection;

public class CreateCard {
    private final ComponentStyle componentStyle = new ComponentStyle();
    private final ComponentEffects componentEffects = new ComponentEffects();
    Connection conn;

    public void addCard(
            int max_column,
            JPanel panel,
            int vehicleId,
            String img_location,
            String brand,
            String license_plate,
            String model,
            String type,
            String color,
            String construction_year,
            String description,
            int gridx,
            int gridy,
            Connection conn
    ) {
        this.conn = conn;

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

        panel.add(createCard(img_location, brand, license_plate, model, type, color, construction_year, description, vehicleId, conn), gbc);
    }

    private JPanel createCard(
            String img_location,
            String brand,
            String license_plate,
            String model,
            String type,
            String color,
            String construction_year,
            String description,
            int vehicleId,
            Connection conn
    ) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createLineBorder(new Color(0x1AFFFFFF, true)));
        panel.setBackground(new Color(0x1D232C));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(10, 10, 5, 10);

        ImagePanel imgPreviewPanel = new ImagePanel();
        imgPreviewPanel.setOpaque(false);

        if (img_location == null) {
            URL noimage = getClass().getResource("/app/assets/no_image.png");
            imgPreviewPanel.emptyImage(noimage);
        } else {
            imgPreviewPanel.setImage(img_location);
        }

        // Add component listener to maintain 16:9 aspect ratio
        panel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int width = panel.getWidth();
                int height = (int) (width / 16.0 * 9);
                imgPreviewPanel.setPreferredSize(new Dimension(width, height));
                panel.revalidate();
                panel.repaint();
            }
        });

        panel.add(imgPreviewPanel, gbc);

        JPanel gridPanel = new JPanel(new GridBagLayout());
        gridPanel.setOpaque(false);
        gridPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(0x1AFFFFFF, true)));
        gbc.gridy = 1;
        panel.add(gridPanel, gbc);

        JPanel leftPanel = new JPanel(new GridBagLayout());
        leftPanel.setOpaque(false);
        GridBagConstraints leftGbc = new GridBagConstraints();
        leftGbc.fill = GridBagConstraints.BOTH;
        leftGbc.gridx = 0;
        leftGbc.gridy = 0;
        leftGbc.weightx = 1.0;
        leftGbc.insets = new Insets(5, 0, 0, 60);
        gridPanel.add(leftPanel, leftGbc);

        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setOpaque(false);
        GridBagConstraints rightGbc = new GridBagConstraints();
        rightGbc.fill = GridBagConstraints.HORIZONTAL;
        rightGbc.gridx = 1;
        rightGbc.gridy = 0;
        rightGbc.weightx = 1.0;
        rightGbc.anchor = GridBagConstraints.NORTH;
        rightGbc.insets = new Insets(5, 0, 0, 0);
        gridPanel.add(rightPanel, rightGbc);

        // Add description in the rightPanel
        JPanel jPanel_description = new JPanel();
        createContent(rightPanel, jPanel_description, 0, "Beschreibung", description, true);

        // Add other information in the leftPanel
        JPanel jPanel_brand = new JPanel();
        createContent(leftPanel, jPanel_brand, 1, "Marke", brand, false);

        JPanel jPanel_license_plate = new JPanel();
        createContent(leftPanel, jPanel_license_plate, 2, "Kennzeichen", license_plate, false);

        JPanel jPanel_model = new JPanel();
        createContent(leftPanel, jPanel_model, 3, "Modell", model, false);

        JPanel jPanel_type = new JPanel();
        createContent(leftPanel, jPanel_type, 4, "Typ", type, false);


        JPanel jPanel_color = new JPanel();
        createContent(leftPanel, jPanel_color, 5, "Farbe", color, false);

        JPanel jPanel_year = new JPanel();
        createContent(leftPanel, jPanel_year, 6, "Baujahr", construction_year, false);

        JButton editButton = new JButton("Bearbeiten");
        editButton.setPreferredSize(new Dimension(Integer.MAX_VALUE, 25));
        componentStyle.styleButton(editButton);
        componentEffects.buttonHover(editButton);
        gbc.gridy = 2;
        panel.add(editButton, gbc);
        addActionListenerToButton(editButton, vehicleId);

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
            jLabel_header.setPreferredSize(new Dimension(110, jLabel_header.getPreferredSize().height));
        }
        panel.add(jLabel_header, gbcInner);

        gbcInner.gridx = verticalStack ? 0 : 1;
        gbcInner.gridy = verticalStack ? 1 : 0;
        gbcInner.insets = new Insets(verticalStack ? 5 : 0, 0, 0, 0);
        gbcInner.weightx = 1.0;
        gbcInner.weighty = 1.0;

        if (verticalStack) {
            JTextArea jTextArea_content = new JTextArea(content);
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

    private void addActionListenerToButton(JButton button, Integer vehicleId) {
        button.addActionListener(e -> {
            if (e.getSource() == button) {
                if (vehicleId != null) {
                    CreateEditPage createEditPage = new CreateEditPage(conn, vehicleId);
                    createEditPage.setVisible(true);
                }
            }
        });
    }
}
