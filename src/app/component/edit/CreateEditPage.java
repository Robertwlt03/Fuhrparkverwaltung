package app.component.edit;

import app.models.Vehicle;

import app.component.ImagePanel;
import app.modules.VehiclesModule;
import app.services.ComponentEffects;
import app.services.ComponentStyle;
import app.services.CustomScrollBarUI;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class CreateEditPage extends JFrame implements ActionListener {
    private final Vehicle vehicle = new Vehicle();
    private final ComponentStyle componentStyle = new ComponentStyle();
    private final ComponentEffects componentEffects = new ComponentEffects();
    private final JButton removeButton = new JButton("Löschen");
    private final JButton updateButton = new JButton("Daten aktualisieren");
    private final JButton imgButton = new JButton("Bild ändern");
    private final ImagePanel imgPreviewPanel = new ImagePanel();
    private final VehiclesModule vehiclesModule = new VehiclesModule();
    private final Map<String, JComponent> inputComponents = new HashMap<>();
    private final Connection conn;
    private final JPanel innerFormPanel = new JPanel();
    private String imagePath = null;
    private final Integer vehicleId;

    public CreateEditPage(Connection conn, Integer vehicleId) {
        this.vehicleId = vehicleId;
        this.conn = conn;

        this.setTitle("VehicleManager - Bearbeiten");
        this.setSize(1000, 800);
        this.setMinimumSize(new Dimension(800, 600));
        this.setLayout(new BorderLayout());
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        URL logoUrl = getClass().getResource("/app/assets/logo.png");

        if (logoUrl != null) {
            ImageIcon logo = new ImageIcon(logoUrl);
            this.setIconImage(logo.getImage());
        } else {
            System.err.println("App logo not found: app/assets/logo.png");
        }

        add(createFormPanel());
    }

    public JScrollPane createFormPanel() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(0x1D232C));
        formPanel.setBorder(BorderFactory.createLineBorder(new Color(0x1AFFFFFF, true)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(10, 10, 10, 10);

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

        addInput(formGrid, 3, "Marke", true, vehicle.getPopularBrands());
        addInput(formGrid, 4, "Kennzeichen", false, null);
        addInput(formGrid, 5, "Modell", false, null);
        addInput(formGrid, 6, "Farbe", false, null);
        addInput(formGrid, 7, "Baujahr", false, null);
        addInput(formGrid, 8, "Beschreibung", false, null);

        gbc.gridy = 1;
        innerFormPanel.add(formGrid, gbc);

        JPanel ButtonPanel = new JPanel(new GridBagLayout());
        ButtonPanel.setOpaque(false);

        GridBagConstraints ButtonGbc = new GridBagConstraints();
        ButtonGbc.fill = GridBagConstraints.BOTH;
        ButtonGbc.weightx = 1.0;
        ButtonGbc.gridx = 0;
        ButtonGbc.insets = new Insets(0, 10, 0, 10);

        updateButton.setPreferredSize(new Dimension(300, 25));
        componentStyle.styleButton(updateButton);
        updateButton.addActionListener(this);
        ButtonPanel.add(updateButton, ButtonGbc);

        ButtonGbc.gridx = 1;
        ButtonGbc.insets = new Insets(0, 0, 0, 10);

        removeButton.setPreferredSize(new Dimension(300, 25));
        componentStyle.styleButton(removeButton);
        removeButton.setBackground(new Color(141, 13, 13));
        removeButton.addActionListener(this);
        ButtonPanel.add(removeButton, ButtonGbc);

        gbc.fill = GridBagConstraints.CENTER;
        gbc.gridy = 2;
        innerFormPanel.add(ButtonPanel, gbc);

        JScrollPane scrollPane = new JScrollPane(formPanel);
        scrollPane.setBorder(null);
        scrollPane.setBackground(null);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(30);

        // Customize the scrollbars
        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
        verticalScrollBar.setUI(new CustomScrollBarUI());

        loadVehicleData();

        return scrollPane;
    }

    private void addInput(JPanel panel, int gridy, String label, boolean isDropdown, String[] options) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = gridy;
        gbc.weightx = 1.0;
        if (gridy != 9) {
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
        JComponent inputComponent;
        if (isDropdown && options != null) {
            JComboBox<String> comboBox = new JComboBox<>(options);
            comboBox.setBackground(new Color(255, 255, 255));
            comboBox.setForeground(Color.BLACK);
            componentEffects.inputFocus(comboBox);
            comboBox.setPreferredSize(new Dimension(comboBox.getPreferredSize().width, COMPONENT_HEIGHT));
            inputComponent = comboBox;
        } else if (label.equals("Beschreibung")) {
            JTextArea textArea = new JTextArea();
            textArea.setBackground(new Color(255, 255, 255));
            textArea.setForeground(Color.BLACK);
            componentEffects.inputFocus(textArea);
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);
            textArea.setRows(2); // Set the number of rows to 2
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(scrollPane.getPreferredSize().width, 50));
            JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
            verticalScrollBar.setUI(new CustomScrollBarUI());
            panel.add(scrollPane, BorderLayout.CENTER);
            inputComponent = textArea;
        } else {
            JTextField textField = new JTextField();
            textField.setBackground(new Color(255, 255, 255));
            textField.setForeground(Color.BLACK);
            componentEffects.inputFocus(textField);
            textField.setPreferredSize(new Dimension(textField.getPreferredSize().width, COMPONENT_HEIGHT));
            inputComponent = textField;
        }

        panel.add(inputComponent, BorderLayout.CENTER);
        inputComponents.put(label, inputComponent);

        return panel;
    }

    public void loadVehicleData() {
        Vehicle vehicle = vehiclesModule.getVehicleById(conn, vehicleId);

        if (vehicle != null) {
            setTextFieldValue("Kennzeichen", vehicle.getLicensePlate());
            setDropdownValue("Marke", vehicle.getBrand());
            setTextFieldValue("Modell", vehicle.getModel());
            setDropdownValue("Typ", vehicle.getType());
            setTextFieldValue("Farbe", vehicle.getColor());
            setTextFieldValue("Baujahr", String.valueOf(vehicle.getYear()));
            setTextAreaValue(vehicle.getDescription());
            imagePath = vehicle.getImagePath();
            imgPreviewPanel.setImage(imagePath);
            imgPreviewPanel.repaint();
        }
    }

    private void setTextFieldValue(String label, String value) {
        JComponent component = inputComponents.get(label);
        if (component instanceof JTextField) {
            ((JTextField) component).setText(value);
        }
    }

    private void setDropdownValue(String label, String value) {
        JComponent component = inputComponents.get(label);
        if (component instanceof JComboBox) {
            ((JComboBox<String>) component).setSelectedItem(value);
        }
    }

    private void setTextAreaValue(String value) {
        JComponent component = inputComponents.get("Beschreibung");
        if (component instanceof JTextArea) {
            ((JTextArea) component).setText(value);
        }
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
                imagePath = selectedFile.getAbsolutePath();
                imgPreviewPanel.setImage(imagePath);
                imgPreviewPanel.repaint();
            }
        }

        if (e.getSource() == removeButton) {
            boolean isDelete = vehiclesModule.deleteVehicleById(conn, vehicleId);

            if (!isDelete) {
                JOptionPane.showMessageDialog(innerFormPanel, "Bei der Löschung ist ein Fehler aufgetreten.", "Fehler", JOptionPane.ERROR_MESSAGE);
            } else {
                this.dispose();
            }
        }

        if (e.getSource() == updateButton) {
            // Retrieve values from input components
            String licensePlate = getTextFieldValue("Kennzeichen");
            String brand = getDropdownValue();
            String model = getTextFieldValue("Modell");
            String color = getTextFieldValue("Farbe");
            String year = getTextFieldValue("Baujahr");
            String description = getTextAreaValue();

            if (isEmpty(licensePlate) || isEmpty(brand) || isEmpty(model) || isEmpty(color) || isEmpty(year)) {
                JOptionPane.showMessageDialog(innerFormPanel, "Alle Felder außer Beschreibung sind Pflichtfelder.", "Fehler", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validate license plate
            if (!isValidGermanLicensePlate(licensePlate)) {
                JOptionPane.showMessageDialog(innerFormPanel, "Ungültiges Kennzeichen.", "Fehler", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!isValidYear(year)) {
                JOptionPane.showMessageDialog(innerFormPanel, "Das Baujahr muss ein gültiges Jahr sein.", "Fehler", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // If a field is empty, set its value to null
            brand = isEmpty(brand) ? null : brand;
            licensePlate = isEmpty(licensePlate) ? null : licensePlate;
            model = isEmpty(model) ? null : model;
            color = isEmpty(color) ? null : color;
            year = isEmpty(year) ? null : year;
            description = isEmpty(description) ? null : description;

            String type;
            if (vehicle.isTruckBrand(brand)) {
                type = "LKW";
            }else {
                type = "PKW";
            }

            boolean isUpdated = vehiclesModule.updateVehicle(conn, vehicleId, licensePlate, brand, model, type, color, Integer.parseInt(year), description, imagePath);

            if (!isUpdated) {
                JOptionPane.showMessageDialog(innerFormPanel, "Ein Fahrzeug mit diesem Kennzeichen existiert bereits.", "Fehler", JOptionPane.ERROR_MESSAGE);
            } else {
                this.dispose();
            }
        }
    }

    private boolean isValidGermanLicensePlate(String licensePlate) {
        // Gültige Formate: z.B. "B BB 1234" oder "B BB 123" oder "B BB 12" oder "B BB 1"
        String germanPlatePattern = "^([A-ZÄÖÜ]{1,3})\\s([A-Z]{1,2})\\s([0-9]{1,4})$";
        return licensePlate != null && licensePlate.matches(germanPlatePattern);
    }

    private String getTextFieldValue(String label) {
        JComponent component = inputComponents.get(label);
        if (component instanceof JTextField) {
            return ((JTextField) component).getText();
        }
        return null;
    }

    private String getDropdownValue() {
        JComponent component = inputComponents.get("Marke");
        if (component instanceof JComboBox) {
            return (String) ((JComboBox<?>) component).getSelectedItem();
        }
        return null;
    }

    private String getTextAreaValue() {
        JComponent component = inputComponents.get("Beschreibung");
        if (component instanceof JTextArea) {
            return ((JTextArea) component).getText();
        }
        return null;
    }

    private boolean isValidYear(String year) {
        try {
            int yearInt = Integer.parseInt(year);
            int currentYear = Calendar.getInstance().get(Calendar.YEAR);
            // Check if the year is within a reasonable range
            return yearInt > 1885 && yearInt <= currentYear;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

}
