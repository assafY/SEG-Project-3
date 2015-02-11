package Data;

import Core.Settings;
import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PictureLabel extends JLabel {

    private Picture picture;
    private BufferedImage thumbnail;
    private int currentSize;
    private boolean isSelected;

    public PictureLabel(Picture picture) {
        this.picture = picture;
        thumbnail = null;
        isSelected = false;

        try {
            thumbnail = ImageIO.read(new File(picture.getImagePath()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (thumbnail != null) {
            thumbnail = Scalr.resize(thumbnail, Settings.THUMBNAIL_SIZES[0]);
        }

        this.addMouseListener(new ThumbnailClickListener());
    }

    public void createThumbnail(int size) {
        currentSize = size;
        setIcon(new ImageIcon(Scalr.resize(thumbnail, currentSize)));
    }

    public void removeThumbnail() {
        setIcon(null);
    }

    public void toggleSelection() {
        if (!isSelected) {
            isSelected = true;
            repaint();
        }
        else {
            isSelected = false;
            repaint();
        }
    }

    public String getPath() {
        return picture.getImagePath();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (isSelected) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setStroke(new BasicStroke(3));
            g2.setColor(new Color(0xD3, 0xD3, 0xD3, 0x50));
            g2.fillRect(2, 2, currentSize-2, getHeight() - 3);
            g2.setColor(Color.GRAY);
            g2.drawRect(0, 0, currentSize-2, getHeight() - 2);

        }
    }

    public class ThumbnailClickListener extends MouseAdapter {

        /**
         * Incomlete picture selection method. Ideally a 2D array should store all labels and enable moving between
         * thumbnails using arrow keys and viewing a single image using spacebar. Additionally only one picture should be
         * selected at one time unless mouse is dragged across pictures or ctrl key is held. Good luck with that.
         */
        @Override
        public void mouseClicked(MouseEvent e) {
            int clickCount = e.getClickCount();
            if (clickCount == 1) {
                toggleSelection();
            }
            else if (clickCount == 2) {
                //TODO: Open single image view
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

    }
}
