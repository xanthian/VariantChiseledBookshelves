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
        var exteriorShades = exteriorShades(template, empty, front);
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
                var sampledPalette = sampledPalette(basePlanks, bx, by, baseWidth, baseHeight, exteriorShades);
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
                            : oakIdentity ? original : front && !border ? tint(wood, shade) : sampledPalette.get(original & 0xffffff);
                    int alpha = (original >>> 24) * (source >>> 24) / 255;
                    result.setRGB(fx + x, fy + y, alpha << 24 | colour & 0xffffff);
                }
            }
        }
        return result;
    }

    private static java.util.List<Integer> exteriorShades(BufferedImage template, BufferedImage empty, boolean front) {
        var shades = new java.util.HashSet<Integer>();
        for (int y = 0; y < template.getHeight(); y++) for (int x = 0; x < template.getWidth(); x++) {
            int original = template.getRGB(x, y);
            int reference = empty.getRGB(x * empty.getWidth() / template.getWidth(), y * empty.getHeight() / template.getHeight());
            int ux = x * 16 / template.getWidth(), uy = y * 16 / template.getHeight();
            boolean border = ux == 0 || ux == 15 || uy == 0 || uy == 7 || uy == 8 || uy == 15;
            if ((original >>> 24) != 0 && original == reference && (!front || border)) shades.add(original & 0xffffff);
        }
        return shades.stream().sorted(java.util.Comparator.comparingDouble((Integer colour) -> luminance(colour)).thenComparingInt(Integer::intValue)).toList();
    }

    private static java.util.Map<Integer, Integer> sampledPalette(BufferedImage image, int left, int top,
                                                                 int width, int height, java.util.List<Integer> shades) {
        var weights = new java.util.HashMap<Integer, Long>();
        long total = 0;
        for (int y = top; y < top + height; y++) for (int x = left; x < left + width; x++) {
            int pixel = image.getRGB(x, y), alpha = pixel >>> 24;
            if (alpha == 0) continue;
            weights.merge(pixel & 0xffffff, (long) alpha, Long::sum);
            total += alpha;
        }
        var colours = weights.keySet().stream().sorted(java.util.Comparator.comparingDouble((Integer colour) -> luminance(colour)).thenComparingInt(Integer::intValue)).toList();
        var mapped = new java.util.HashMap<Integer, Integer>();
        for (int index = 0; index < shades.size(); index++) {
            // Frequency-weighted source colours retain real highlights and shadows. Ignore isolated outlier pixels.
            double rank = shades.size() == 1 ? .5 : (double) index / (shades.size() - 1);
            double target = total * (.03 + .94 * rank);
            long cumulative = 0;
            int selected = 0;
            for (int colour : colours) {
                cumulative += weights.get(colour);
                selected = colour;
                if (cumulative >= target) break;
            }
            mapped.put(shades.get(index), selected);
        }
        return mapped;
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
