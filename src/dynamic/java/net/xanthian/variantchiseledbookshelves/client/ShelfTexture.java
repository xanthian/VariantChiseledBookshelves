package net.xanthian.variantchiseledbookshelves.client;
import java.awt.image.BufferedImage;
public final class ShelfTexture {
    private ShelfTexture() {}
    public static BufferedImage compose(BufferedImage template, BufferedImage empty, BufferedImage planks, int frameWidth, int frameHeight) {
        if (frameWidth <= 0 || frameHeight <= 0 || planks.getWidth() % frameWidth != 0 || planks.getHeight() % frameHeight != 0)
            throw new IllegalArgumentException("Plank image must contain complete animation frames");
        BufferedImage result = new BufferedImage(planks.getWidth(), planks.getHeight(), BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < result.getHeight(); y++) for (int x = 0; x < result.getWidth(); x++) {
            int tx = x % frameWidth * template.getWidth() / frameWidth;
            int ty = y % frameHeight * template.getHeight() / frameHeight;
            int original = template.getRGB(tx, ty);
            int reference = empty.getRGB(tx * empty.getWidth() / template.getWidth(), ty * empty.getHeight() / template.getHeight());
            if (original != reference || (original >>> 24) == 0) { result.setRGB(x, y, original); continue; }
            int plank = planks.getRGB(x, y);
            // Keep the carved recesses and borders while taking the source wood's colour and grain.
            double shade = Math.min(1.15, (((original >> 16) & 255) * .2126 + ((original >> 8) & 255) * .7152 + (original & 255) * .0722) / 145.0);
            int r = Math.min(255, (int) (((plank >> 16) & 255) * shade));
            int g = Math.min(255, (int) (((plank >> 8) & 255) * shade));
            int b = Math.min(255, (int) ((plank & 255) * shade));
            result.setRGB(x, y, (plank & 0xff000000) | r << 16 | g << 8 | b);
        }
        return result;
    }
}
