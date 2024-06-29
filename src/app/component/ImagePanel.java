package app.component;

import javax.swing.*;
import java.awt.*;

public class ImagePanel extends JPanel {
    private Image originalImage;
    private Image scaledImage;

    public void setImage(String imagePath) {
        this.originalImage = new ImageIcon(imagePath).getImage();
        scaleImage();
    }

    private void scaleImage() {
        if (originalImage != null) {
            // Maximal erlaubte Größe des Bildes
            int maxWidth = getWidth();
            int maxHeight = getHeight();

            int imageWidth = originalImage.getWidth(this);
            int imageHeight = originalImage.getHeight(this);

            // Überprüfen, ob das Bild größer als die maximale Größe ist
            if (imageWidth > maxWidth || imageHeight > maxHeight) {
                // Berechnung der Skalierungsfaktoren
                double scaleWidth = (double) maxWidth / imageWidth;
                double scaleHeight = (double) maxHeight / imageHeight;

                // Verwende den kleineren Skalierungsfaktor, um das Seitenverhältnis beizubehalten
                double scaleFactor = Math.min(scaleWidth, scaleHeight);

                // Berechnung der neuen Dimensionen
                int newWidth = (int) (imageWidth * scaleFactor);
                int newHeight = (int) (imageHeight * scaleFactor);

                // Skaliert das Bild neu
                scaledImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
            } else {
                // Das Bild ist kleiner als oder gleich der maximalen Größe, keine Skalierung erforderlich
                scaledImage = originalImage;
            }

            repaint(); // Aktualisiere das Panel nach dem Skalieren
        }
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (scaledImage  != null) {
            int x = (getWidth() - scaledImage .getWidth(this)) / 2;
            int y = (getHeight() - scaledImage .getHeight(this)) / 2;
            g.drawImage(scaledImage , x, y, this);
        }
    }
}
