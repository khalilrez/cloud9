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
=======
import java.io.ByteArrayOutputStream;
import java.util.zip.Deflater;
import java.util.zip.Inflater;
public class ImageUtils {
    public static byte[] compressImage(byte[] data) {

        Deflater deflater = new Deflater();
        deflater.setLevel(Deflater.BEST_COMPRESSION);
        deflater.setInput(data);
        deflater.finish();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] tmp = new byte[4*1024];
        while (!deflater.finished()) {
            int size = deflater.deflate(tmp);
            outputStream.write(tmp, 0, size);
        }
        try {
            outputStream.close();
        } catch (Exception e) {
        }
        return outputStream.toByteArray();
    }

    public static byte[] decompressImage(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] tmp = new byte[4*1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(tmp);
                outputStream.write(tmp, 0, count);
            }
            outputStream.close();
        } catch (Exception exception) {
        }
        return outputStream.toByteArray();
    }
}

