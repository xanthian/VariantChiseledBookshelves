package net.xanthian.variantchiseledbookshelves.client;

import java.awt.image.BufferedImage;

/** Recolours vanilla carvings from clean wood and keeps surface decoration out of front recesses. */
public final class ShelfTexture {
    private ShelfTexture() {}

    public static BufferedImage compose(BufferedImage template, BufferedImage empty, BufferedImage oakPlanks,
                                        BufferedImage planks, int frameWidth, int frameHeight) {
        return compose(template, empty, oakPlanks, planks, frameWidth, frameHeight, planks, frameWidth, frameHeight, false);
    }

    public static BufferedImage compose(BufferedImage template, BufferedImage empty, BufferedImage oakPlanks,
                                        BufferedImage planks, int frameWidth, int frameHeight,
                                        BufferedImage basePlanks, int baseWidth, int baseHeight, boolean front) {
        if (baseWidth <= 0 || baseHeight <= 0 || basePlanks.getWidth() % baseWidth != 0 || basePlanks.getHeight() % baseHeight != 0)
            throw new IllegalArgumentException("Base plank image must contain complete animation frames");
        if (frameWidth <= 0 || frameHeight <= 0 || planks.getWidth() % frameWidth != 0 || planks.getHeight() % frameHeight != 0)
            throw new IllegalArgumentException("Plank image must contain complete animation frames");
        double[] oak = averageColour(oakPlanks, 0, 0, oakPlanks.getWidth(), oakPlanks.getHeight());
        BufferedImage result = new BufferedImage(planks.getWidth(), planks.getHeight(), BufferedImage.TYPE_INT_ARGB);
        for (int fy = 0; fy < planks.getHeight(); fy += frameHeight) {
            for (int fx = 0; fx < planks.getWidth(); fx += frameWidth) {
                int frame = fy / frameHeight * (planks.getWidth() / frameWidth) + fx / frameWidth;
                int baseColumns = basePlanks.getWidth() / baseWidth;
                int baseFrame = frame % (baseColumns * (basePlanks.getHeight() / baseHeight));
                int bx = baseFrame % baseColumns * baseWidth;
                int by = baseFrame / baseColumns * baseHeight;
                double[] wood = averageColour(basePlanks, bx, by, baseWidth, baseHeight);
                boolean oakIdentity = java.util.Arrays.equals(oak, wood);
                var baseColours = new java.util.HashSet<Integer>();
                for (int y = 0; y < baseHeight; y++) for (int x = 0; x < baseWidth; x++)
                    baseColours.add(basePlanks.getRGB(bx + x, by + y) & 0xffffff);
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
                    int source = planks.getRGB(fx + x, fy + y);
                    int ux = tx * 16 / template.getWidth();
                    int uy = ty * 16 / template.getHeight();
                    boolean border = ux == 0 || ux == 15 || uy == 0 || uy == 7 || uy == 8 || uy == 15;
                    boolean overlay = !baseColours.contains(source & 0xffffff) && (!front || border);
                    double shade = luminance(original) / Math.max(1, luminance(oak));
                    int colour = overlay ? tint(new double[]{(source >> 16) & 255, (source >> 8) & 255, source & 255}, shade)
                            : oakIdentity ? original : tint(wood, shade);
                    int alpha = (original >>> 24) * (source >>> 24) / 255;
                    result.setRGB(fx + x, fy + y, alpha << 24 | colour & 0xffffff);
                }
            }
        }
        return result;
    }

    private static double luminance(int colour) {
        return luminance(new double[]{(colour >> 16) & 255, (colour >> 8) & 255, colour & 255});
    }

    private static double luminance(double[] colour) {
        return colour[0] * .2126 + colour[1] * .7152 + colour[2] * .0722;
    }

    private static int tint(double[] colour, double shade) {
        // One multiplier preserves the source wood's hue; channel-wise ratios introduced an oak/brown cast.
        double maximum = Math.max(colour[0], Math.max(colour[1], colour[2]));
        shade = Math.min(shade, 255 / Math.max(1, maximum));
        return (int) Math.round(colour[0] * shade) << 16 | (int) Math.round(colour[1] * shade) << 8 | (int) Math.round(colour[2] * shade);
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
