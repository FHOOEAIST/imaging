package science.aist.imaging.service.imagej.imageprocessing.converter;

import org.testng.Assert;
import org.testng.annotations.Test;
import science.aist.imaging.api.domain.color.RGBColor;

import static org.testng.Assert.*;

/**
 * <p>Test class for {@link ColorConverter}</p>
 *
 * @author Christoph Praschl
 * @since 1.1
 */
public class ColorConverterTest {

    @Test
    public void testConvert() {
        // given
        ColorConverter colorConverter = new ColorConverter();

        // when
        RGBColor convert = colorConverter.convert(1);

        // then
        Assert.assertNotNull(convert);
        assertEquals(convert.getRed(), 0.0);
        assertEquals(convert.getGreen(), 0.0);
        assertEquals(convert.getBlue(), 1.0);
    }

    @Test
    public void testConvert2() {
        // given
        ColorConverter colorConverter = new ColorConverter();
        RGBColor color = new RGBColor(100, 50, 125);

        // when
        int convert = colorConverter.convert(color);

        // then
        assertEquals(convert, 6566525);
    }
}
