package com.pi.tobeeb.Utils;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageUtils {

    public static byte[] compressImage(byte[] imageBytes) throws IOException {
        BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageBytes));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(compressImage(image), "jpg", baos);
        } finally {
            baos.close();
        }
        return baos.toByteArray();
    }

    public static byte[] decompressImage(byte[] compressedBytes) throws IOException {
        BufferedImage compressedImage = ImageIO.read(new ByteArrayInputStream(compressedBytes));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(compressedImage, "jpg", baos);
        } finally {
            baos.close();
        }
        return baos.toByteArray();
    }

    private static BufferedImage compressImage(BufferedImage image) {
        BufferedImage compressedImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g = compressedImage.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(image, 0, 0, null);
        g.dispose();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(compressedImage, "jpg", baos);
        } catch (IOException e) {
            // handle exception
        } finally {
            try {
                baos.close();
            } catch (IOException e) {
                // handle exception
            }
        }
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        BufferedImage result = null;
        try {
            result = ImageIO.read(bais);
        } catch (IOException e) {
            // handle exception
        } finally {
            try {
                bais.close();
            } catch (IOException e) {
                // handle exception
            }
        }
        return result;
    }
}

