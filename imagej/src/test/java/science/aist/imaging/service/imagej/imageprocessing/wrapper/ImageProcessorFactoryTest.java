package science.aist.imaging.service.imagej.imageprocessing.wrapper;

import ij.process.ColorProcessor;
import ij.process.ImageProcessor;
import org.testng.Assert;
import org.testng.annotations.Test;
import science.aist.imaging.api.domain.wrapper.ImageFactory;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;

import java.util.Random;

/**
 * <p>Test class for {@link ImageProcessorFactory}</p>
 *
 * @author Christoph Praschl
 * @since 1.1
 */
public class ImageProcessorFactoryTest {

    @Test
    public void testGetImage() {
        // given
        ImageFactory<ImageProcessor> imageProcessorFactory = ImageProcessorFactory.getInstance();
        int width = 10;
        int height = 15;
        ImageProcessor img = new ColorProcessor(width, height);
        Random rand = new Random(768457);

        int[] channelArr = new int[3];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                channelArr[0] = rand.nextInt(255);
                channelArr[1] = 0;
                channelArr[2] = 0;
                img.putPixel(x, y, channelArr);
            }
        }

        int[][] reference = new int[][]{
                new int[]{164,  214,  202,  223,  210,  103,  52,  93,  166,  87,  146,  68,  71,  194,  136},
                new int[]{99,  237,  243,  36,  194,  185,  240,  158,  142,  73,  49,  130,  210,  239,  245},
                new int[]{79,  198,  108,  93,  251,  49,  108,  220,  78,  60,  157,  50,  45,  134,  44},
                new int[]{154,  12,  139,  226,  78,  107,  250,  133,  87,  135,  78,  3,  197,  122,  31},
                new int[]{108,  231,  14,  118,  26,  123,  116,  57,  62,  181,  102,  167,  225,  172,  127},
                new int[]{242,  54,  136,  221,  184,  89,  97,  50,  200,  78,  44,  150,  74,  97,  61},
                new int[]{81,  212,  223,  20,  222,  104,  161,  145,  37,  200,  160,  60,  53,  220,  187},
                new int[]{56,  82,  219,  119,  184,  57,  145,  148,  220,  162,  121,  223,  12,  213,  238},
                new int[]{210,  111,  220,  43,  179,  116,  197,  153,  55,  153,  243,  166,  169,  62,  137},
                new int[]{188,  242,  191,  171,  65,  118,  73,  66,  103,  30,  45,  217,  191,  57,  151}
        };


        // when
        ImageWrapper<ImageProcessor> image = imageProcessorFactory.getImage(img);

        // then
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int c = (int)image.getValue(x, y, 0);
                Assert.assertEquals(c, reference[x][y]);
            }
        }
    }

}
