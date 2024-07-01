package app.services;

import app.component.add.RenderAddEntryPage;
import app.component.help.RenderHelpPage;
import app.component.homepage.RenderHomePage;
import app.component.search.RenderSearchPage;
import app.component.view.RenderViewAllEntryPage;
import app.component.view.RenderViewSearchPage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.sql.Connection;

public class CreatePage extends JFrame implements ActionListener {
    private JPanel cardPanel;
    private CardLayout cardLayout;
    private final ComponentEffects componentEffects = new ComponentEffects();
    private JButton home = new JButton();
    private JButton search = new JButton();
    private JButton help = new JButton();
    private final Color activeLinkColor = new Color(28, 121, 228);
    private final JButton logoButton = new JButton();


    // prepare frame
    public void createFrame(Connection conn) {
        this.setTitle("VehicleManager - Startseite");
        this.setSize(1600, 800);
        this.setMinimumSize(new Dimension(800, 600));
        this.setLayout(new BorderLayout());
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        URL logoUrl = getClass().getResource("/app/assets/logo.png");

        if (logoUrl != null) {
            ImageIcon logo = new ImageIcon(logoUrl);
            this.setIconImage(logo.getImage());
        } else {
            System.err.println("App logo not found: app/assets/logo.png");
        }

        // CardLayout and main panel
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Various panels (render classes)
        JPanel homepagePanel = new RenderHomePage(this);
        JPanel searchPanel = new RenderSearchPage(this);
        JPanel helpPanel = new RenderHelpPage();
        JPanel viewAllPanel = new RenderViewAllEntryPage(this, conn);
        JPanel viewSearchPanel = new RenderViewSearchPage(this, conn);
        JPanel addPanel = new RenderAddEntryPage(this, conn);

        // Panels zum CreateCard hinzufügen
        cardPanel.add(homepagePanel, "Startseite");
        cardPanel.add(searchPanel, "Suche");
        cardPanel.add(helpPanel, "Help");
        cardPanel.add(viewAllPanel, "Ergebnisse Alle");
        cardPanel.add(viewSearchPanel, "Ergebnisse Suche");
        cardPanel.add(addPanel, "Hinzufügen");

        // Add main panel to JFrame
        add(cardPanel);

        // render navbar
        navbarPanel();
    }

    // Navbar panel
    private void navbarPanel() {
        JPanel navbar = new JPanel(new BorderLayout());
        navbar.setBackground((new Color(0x1D232C)));
        navbar.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, (new Color(0x1AFFFFFF, true))));
        navbar.setPreferredSize(new Dimension(150, 1));
        this.add(navbar, BorderLayout.WEST);


        // navbar sections
        JPanel logoPanel = new JPanel(new GridBagLayout());
        logoPanel.setOpaque(false);
        logoPanel.setPreferredSize(new Dimension(Integer.MAX_VALUE, 150));
        navbar.add(logoPanel, BorderLayout.NORTH);

        JPanel pagesPanel = new JPanel(new BorderLayout(50, 0));
        pagesPanel.setOpaque(false);
        pagesPanel.setPreferredSize(new Dimension(Integer.MAX_VALUE, 100));
        navbar.add(pagesPanel, BorderLayout.CENTER);

        // logo section
        URL logoUrlSmall = getClass().getResource("/app/assets/logo.png");
        this.logoButton.setFocusPainted(false);
        this.logoButton.setContentAreaFilled(false);
        this.logoButton.setBorderPainted(false);

        if (logoUrlSmall != null) {
            this.logoButton.setIcon(new ImageIcon(logoUrlSmall));
            this.logoButton.addActionListener(this);
        } else {
            System.err.println("Navbar logo not found: app/assets/logo.png");
        }

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        logoPanel.add(this.logoButton, gbc);


        // link centering panel
        JPanel LinkCenterPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 10));
        LinkCenterPanel.setOpaque(false);
        LinkCenterPanel.setPreferredSize(new Dimension(1, 200));
        pagesPanel.add(LinkCenterPanel, BorderLayout.NORTH);


        // navbar Links
        this.home = createNavLink("Home");
        this.search = createNavLink("Suche");
        this.help = createNavLink("Help");

        this.home.addActionListener(this);
        this.search.addActionListener(this);
        this.help.addActionListener(this);

        isActiveLink("home");

        LinkCenterPanel.add(this.home);
        LinkCenterPanel.add(this.search);
        LinkCenterPanel.add(this.help);
    }

    // link styles & hover effects
    private JButton createNavLink(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(100, 20));
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        componentEffects.buttonHover(button);

        Font buttonFont = button.getFont().deriveFont(13f);
        button.setFont(buttonFont);

        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.logoButton) {
            isActiveLink("home");
            showPage("Startseite");
        }

        if (e.getSource() == this.home) {
            isActiveLink("home");
            showPage("Startseite");
        } else if (e.getSource() == this.search) {
            isActiveLink("search");
            showPage("Suche");
        } else if (e.getSource() == this.help) {
            isActiveLink("help");
            showPage("Help");
        }
    }

    public void isActiveLink(String name) {
        resetLinks();

        if (name != null) {
            switch (name) {
                case "home":
                    setLinkActive(home);
                    break;
                case "search":
                    setLinkActive(search);
                    break;
                case "help":
                    setLinkActive(help);
                    break;
                default:
                    resetLinks();
                    break;
            }
        }
    }

    private void resetLinks() {
        home.setContentAreaFilled(false);
        search.setContentAreaFilled(false);
        help.setContentAreaFilled(false);
    }

    private void setLinkActive(JButton button) {
        button.setBackground(activeLinkColor);
        button.setContentAreaFilled(true);
    }

    public void showPage(String pageName) {
        cardLayout.show(cardPanel, pageName);
        this.setTitle("VehicleManager - " + pageName);
    }
}