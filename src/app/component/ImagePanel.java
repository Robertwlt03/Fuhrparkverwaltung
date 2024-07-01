package app.component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.net.URL;

public class ImagePanel extends JPanel {
    private Image originalImage;
    private Image scaledImage;
    private boolean imageLoaded;

    public ImagePanel() {
        super();
        setOpaque(false);

        // Initialize without an image loaded
        imageLoaded = false;

        // Add component listener to maintain aspect ratio
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                if (imageLoaded) {
                    scaleImage();
                    repaint();
                }
            }
        });
    }

    public void emptyImage(URL imagePath) {
        this.originalImage = new ImageIcon(imagePath).getImage();
        imageLoaded = true;

        // Only scale the image if the panel has a size
        if (getWidth() > 0 && getHeight() > 0) {
            scaleImage();
        }
    }

    public void setImage(String imagePath) {
        this.originalImage = new ImageIcon(imagePath).getImage();
        imageLoaded = true;

        // Only scale the image if the panel has a size
        if (getWidth() > 0 && getHeight() > 0) {
            scaleImage();
        }
    }

    private void scaleImage() {
        if (originalImage != null && getWidth() > 0 && getHeight() > 0) {
            int maxWidth = getWidth();
            int maxHeight = getHeight();

            int imageWidth = originalImage.getWidth(this);
            int imageHeight = originalImage.getHeight(this);

            double scaleWidth = (double) maxWidth / imageWidth;
            double scaleHeight = (double) maxHeight / imageHeight;
            double scaleFactor = Math.min(scaleWidth, scaleHeight);

            int newWidth = (int) (imageWidth * scaleFactor);
            int newHeight = (int) (imageHeight * scaleFactor);

            scaledImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (scaledImage != null) {
            int x = (getWidth() - scaledImage.getWidth(this)) / 2;
            int y = (getHeight() - scaledImage.getHeight(this)) / 2;
            g.drawImage(scaledImage, x, y, this);
        }
    }
}
