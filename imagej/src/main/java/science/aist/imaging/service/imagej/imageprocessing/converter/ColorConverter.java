package science.aist.imaging.service.imagej.imageprocessing.converter;

import science.aist.imaging.api.domain.color.Color;
import science.aist.imaging.api.domain.color.RGBColor;

/**
 * <p>Converts an ImageJ int color representation to a {@link RGBColor} object</p>
 *
 * @author Christoph Praschl
 * @since 1.1
 */
public class ColorConverter {
    /**
     * Converts a color represented as integer to a {@link RGBColor} object
     * @param color to be converted
     * @return {@link RGBColor} representation of the color
     */
    public RGBColor convert(int color) {
        int r = (color & 16711680) >> 16;
        int g = (color & '\uff00') >> 8;
        int b = color & 255;

        return new RGBColor(r, g, b);
    }

    /**
     * Converts a {@link RGBColor} object to a ImageJ integer color representation
     * @param color to be converted
     * @return int representation of the color
     */
    public int convert(Color color){
        return ((int)color.getChannel(0) << 16) + ((int)color.getChannel(1) << 8) + (int)color.getChannel(2);
    }
}
