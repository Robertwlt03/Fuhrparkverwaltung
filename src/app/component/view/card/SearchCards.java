package app.component.view.card;

import app.models.Vehicle;
import app.modules.VehiclesModule;
import app.services.CreatePage;
import app.services.CustomScrollBarUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.Connection;
import java.util.List;

public class SearchCards {

    private final CreateCard createCard = new CreateCard();
    private final Connection conn;

    public SearchCards(Connection conn) {
        this.conn = conn;
    }

    public JScrollPane createSearchCardsPanel(CreatePage createPage, String licensePlate, String brand, String model, String type, String color, Integer year) {
        JPanel cardPanel = new JPanel(new BorderLayout());
        cardPanel.setOpaque(false);
        cardPanel.setBorder(new EmptyBorder(0, 20, 0, 20));

        JPanel innerCardPanel = new JPanel();
        innerCardPanel.setOpaque(false);
        innerCardPanel.setLayout(new GridBagLayout());
        cardPanel.add(innerCardPanel, BorderLayout.NORTH);

        JPanel cardGrid = new JPanel(new GridBagLayout());
        cardGrid.setOpaque(false);

        updateSearchCards(cardGrid, licensePlate, brand, model, type, color, year);

        JScrollPane scrollPane = new JScrollPane(cardPanel);
        scrollPane.setBorder(null);
        scrollPane.setBackground(null);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(30);

        // Customize the scrollbars
        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
        verticalScrollBar.setUI(new CustomScrollBarUI());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;

        gbc.gridy = 1;
        innerCardPanel.add(cardGrid, gbc);

        return scrollPane;
    }

    private void updateSearchCards(JPanel cardGrid, String licensePlate, String brand, String model, String type, String color, Integer year) {
        cardGrid.removeAll();

        VehiclesModule vehiclesModule = new VehiclesModule();
        List<Vehicle> vehicles = vehiclesModule.searchVehicles(conn, licensePlate, brand, model, type, color, year);

        int gridy = 0;
        int gridx = -1; // -1 so it starts with 0
        int counter = -1; // -1 so it starts with 0
        int max_counter = 3;
        int max_column = 2; // max for gridx starts with 0 | 2 = 3 column

        for (Vehicle vehicle : vehicles) {
            counter++;
            gridx++;

            if (counter == max_counter) {
                counter = -1;
                gridx = -1;
                gridy++;
            }

            createCard.addCard(
                    max_column,
                    cardGrid,
                    vehicle.getId(),
                    vehicle.getImagePath(),
                    vehicle.getBrand(),
                    vehicle.getLicensePlate(),
                    vehicle.getModel(),
                    vehicle.getType(),
                    vehicle.getColor(),
                    String.valueOf(vehicle.getYear()),
                    vehicle.getDescription(),
                    gridx,
                    gridy,
                    conn
            );
        }

        cardGrid.revalidate();
        cardGrid.repaint();
    }
}
