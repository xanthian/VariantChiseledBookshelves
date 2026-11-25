package net.xanthian.variantchiseledbookshelves.client;

import java.awt.image.BufferedImage;

/** Transfers the source wood's colour to the vanilla carving palette, without adding plank grain. */
public final class ShelfTexture {
    private ShelfTexture() {}

    public static BufferedImage compose(BufferedImage template, BufferedImage empty, BufferedImage oakPlanks,
                                        BufferedImage planks, int frameWidth, int frameHeight) {
        if (frameWidth <= 0 || frameHeight <= 0 || planks.getWidth() % frameWidth != 0 || planks.getHeight() % frameHeight != 0)
            throw new IllegalArgumentException("Plank image must contain complete animation frames");
        double[] oak = averageColour(oakPlanks, 0, 0, oakPlanks.getWidth(), oakPlanks.getHeight());
        BufferedImage result = new BufferedImage(planks.getWidth(), planks.getHeight(), BufferedImage.TYPE_INT_ARGB);
        for (int fy = 0; fy < planks.getHeight(); fy += frameHeight) {
            for (int fx = 0; fx < planks.getWidth(); fx += frameWidth) {
                double[] wood = averageColour(planks, fx, fy, frameWidth, frameHeight);
                for (int y = 0; y < frameHeight; y++) for (int x = 0; x < frameWidth; x++) {
                    int tx = x * template.getWidth() / frameWidth;
                    int ty = y * template.getHeight() / frameHeight;
                    int original = template.getRGB(tx, ty);
                    int reference = empty.getRGB(tx * empty.getWidth() / template.getWidth(), ty * empty.getHeight() / template.getHeight());
                    // Pixels changed between the occupied and empty templates are the books.
                    if (original != reference || (original >>> 24) == 0) {
                        result.setRGB(fx + x, fy + y, original);
                        continue;
                    }
                    int r = recolour((original >> 16) & 255, oak[0], wood[0]);
                    int g = recolour((original >> 8) & 255, oak[1], wood[1]);
                    int b = recolour(original & 255, oak[2], wood[2]);
                    int alpha = (original >>> 24) * (planks.getRGB(fx + x, fy + y) >>> 24) / 255;
                    result.setRGB(fx + x, fy + y, alpha << 24 | r << 16 | g << 8 | b);
                }
            }
        }
        return result;
    }

    private static int recolour(int shade, double oak, double wood) {
        // Exact identity for oak; the template's few shades retain their ratios for every wood.
        return Math.min(255, Math.max(0, (int) Math.round(shade * wood / Math.max(1, oak))));
    }

    private static double[] averageColour(BufferedImage image, int left, int top, int width, int height) {
        double[] colour = new double[3];
        double weight = 0;
        for (int y = top; y < top + height; y++) for (int x = left; x < left + width; x++) {
            int pixel = image.getRGB(x, y);
            int alpha = pixel >>> 24;
            colour[0] += ((pixel >> 16) & 255) * alpha;
            colour[1] += ((pixel >> 8) & 255) * alpha;
            colour[2] += (pixel & 255) * alpha;
            weight += alpha;
        }
        if (weight > 0) for (int channel = 0; channel < 3; channel++) colour[channel] /= weight;
        return colour;
    }
}
