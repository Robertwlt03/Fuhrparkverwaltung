package app;

import app.services.CreatePage;
import app.services.FontManager;
import app.services.PrepareDatabase;

public class Main {
    public static void main(String[] args) {
        PrepareDatabase prepareDatabase = new PrepareDatabase();
        prepareDatabase.initializeDB();

        FontManager fontManager = new FontManager();
        fontManager.setDefaultFont(fontManager.DEFAULT_FONT);

        CreatePage frame = new CreatePage();
        frame.createFrame();
        frame.setVisible(true);
    }
}
