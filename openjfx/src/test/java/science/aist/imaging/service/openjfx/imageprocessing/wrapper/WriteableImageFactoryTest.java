package science.aist.imaging.service.openjfx.imageprocessing.wrapper;

import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import org.testng.Assert;
import org.testng.annotations.Test;
import science.aist.imaging.api.domain.wrapper.ImageFactory;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.domain.wrapper.implementation.ImageFactoryFactory;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.net.URL;
import java.util.Random;

/**
 * <p>Created by Christoph Praschl  on 16.06.2021</p>
 * <p>Test class for {@link WriteableImageFactory}</p>
 *
 * @author Christoph Praschl christoph.praschl@fh-hagenberg.at
 */
public class WriteableImageFactoryTest {
    @Test
    public void testGetImage() {
        // given
        ImageFactory<WritableImage> imageProcessorFactory = ImageFactoryFactory.getImageFactory(WritableImage.class);
        int width = 10;
        int height = 15;
        WritableImage img = new WritableImage(width, height);
        PixelWriter pixelWriter = img.getPixelWriter();
        Random rand = new Random(768457);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                float[] array = new float[3];
                for (int c = 0; c < 3; c++) {
                    array[c] = rand.nextFloat();
                }

                pixelWriter.setColor(x, y, Color.color(array[0], array[1], array[2]));
            }
        }

        int[][][] reference = new int[][][]{
                new int[][]{new int[]{73, 68, 173}, new int[]{12, 206, 189}, new int[]{165, 232, 85}, new int[]{228, 46, 70}, new int[]{17, 9, 77}, new int[]{45, 242, 185}, new int[]{179, 194, 168}, new int[]{62, 86, 118}, new int[]{70, 45, 75}, new int[]{76, 38, 170}, new int[]{212, 165, 99}, new int[]{154, 91, 2}, new int[]{75, 37, 15}, new int[]{134, 203, 210}, new int[]{123, 48, 89}},
                new int[][]{new int[]{208, 195, 93}, new int[]{24, 171, 212}, new int[]{13, 169, 73}, new int[]{213, 224, 48}, new int[]{251, 190, 60}, new int[]{14, 145, 243}, new int[]{63, 240, 45}, new int[]{87, 251, 224}, new int[]{184, 26, 101}, new int[]{134, 83, 150}, new int[]{127, 154, 105}, new int[]{8, 48, 162}, new int[]{145, 57, 146}, new int[]{134, 53, 214}, new int[]{145, 91, 105}},
                new int[][]{new int[]{121, 191, 81}, new int[]{201, 210, 146}, new int[]{189, 111, 62}, new int[]{118, 90, 234}, new int[]{197, 21, 11}, new int[]{212, 185, 55}, new int[]{31, 100, 130}, new int[]{159, 49, 39}, new int[]{12, 192, 132}, new int[]{255, 99, 19}, new int[]{58, 147, 143}, new int[]{64, 146, 232}, new int[]{252, 42, 105}, new int[]{10, 238, 13}, new int[]{230, 192, 195}},
                new int[][]{new int[]{161, 50, 171}, new int[]{245, 32, 87}, new int[]{44, 252, 167}, new int[]{113, 238, 93}, new int[]{106, 32, 133}, new int[]{113, 160, 51}, new int[]{19, 124, 140}, new int[]{141, 222, 210}, new int[]{160, 189, 141}, new int[]{225, 222, 79}, new int[]{153, 208, 19}, new int[]{237, 83, 77}, new int[]{167, 120, 72}, new int[]{223, 202, 129}, new int[]{13, 103, 100}},
                new int[][]{new int[]{180, 188, 212}, new int[]{108, 192, 122}, new int[]{252, 131, 120}, new int[]{190, 49, 128}, new int[]{185, 149, 255}, new int[]{132, 184, 4}, new int[]{226, 163, 104}, new int[]{73, 238, 118}, new int[]{68, 190, 210}, new int[]{97, 74, 221}, new int[]{159, 3, 249}, new int[]{61, 75, 224}, new int[]{53, 174, 84}, new int[]{190, 213, 228}, new int[]{185, 44, 220}},
                new int[][]{new int[]{242, 127, 54}, new int[]{114, 18, 172}, new int[]{36, 7, 3}, new int[]{32, 80, 131}, new int[]{39, 91, 68}, new int[]{89, 198, 254}, new int[]{253, 198, 138}, new int[]{26, 71, 146}, new int[]{149, 110, 191}, new int[]{49, 15, 95}, new int[]{92, 46, 60}, new int[]{73, 134, 85}, new int[]{189, 246, 17}, new int[]{189, 120, 59}, new int[]{172, 163, 225}},
                new int[][]{new int[]{241, 63, 184}, new int[]{183, 28, 46}, new int[]{164, 176, 192}, new int[]{108, 8, 160}, new int[]{105, 27, 72}, new int[]{216, 165, 175}, new int[]{132, 106, 97}, new int[]{74, 45, 214}, new int[]{78, 216, 81}, new int[]{202, 102, 152}, new int[]{16, 250, 2}, new int[]{216, 63, 239}, new int[]{136, 174, 179}, new int[]{157, 62, 172}, new int[]{133, 176, 9}},
                new int[][]{new int[]{41, 37, 32}, new int[]{10, 200, 132}, new int[]{223, 207, 21}, new int[]{171, 245, 133}, new int[]{209, 225, 132}, new int[]{94, 79, 166}, new int[]{54, 109, 232}, new int[]{229, 117, 231}, new int[]{226, 81, 209}, new int[]{115, 244, 240}, new int[]{203, 138, 177}, new int[]{158, 215, 50}, new int[]{125, 224, 112}, new int[]{151, 223, 112}, new int[]{250, 140, 103}},
                new int[][]{new int[]{102, 239, 243}, new int[]{34, 233, 193}, new int[]{248, 131, 145}, new int[]{198, 92, 216}, new int[]{22, 202, 17}, new int[]{48, 120, 57}, new int[]{91, 33, 204}, new int[]{190, 64, 153}, new int[]{89, 221, 41}, new int[]{76, 149, 145}, new int[]{28, 239, 115}, new int[]{7, 160, 17}, new int[]{86, 19, 50}, new int[]{176, 219, 71}, new int[]{173, 142, 157}},
                new int[][]{new int[]{197, 160, 175}, new int[]{125, 104, 61}, new int[]{180, 229, 128}, new int[]{234, 180, 236}, new int[]{82, 226, 11}, new int[]{81, 69, 187}, new int[]{182, 238, 126}, new int[]{208, 178, 100}, new int[]{108, 76, 120}, new int[]{95, 224, 180}, new int[]{149, 152, 64}, new int[]{33, 247, 160}, new int[]{60, 67, 49}, new int[]{9, 193, 187}, new int[]{140, 213, 79}}
        };

        // when
        ImageWrapper<WritableImage> image = imageProcessorFactory.getImage(img);

        // then
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                for (int c = 0; c < image.getChannels(); c++) {
                    Assert.assertEquals((int) image.getValue(x, y, c), reference[x][y][c]);
                }
            }
        }
    }

    @Test(expectedExceptions = IndexOutOfBoundsException.class)
    public void testGetImage2() {
        // given
        ImageFactory<WritableImage> imageProcessorFactory = ImageFactoryFactory.getImageFactory(WritableImage.class);
        int width = 10;
        int height = 15;
        WritableImage img = new WritableImage(width, height);
        ImageWrapper<WritableImage> image = imageProcessorFactory.getImage(img);

        // when
        double value = image.getValue(0, 0, -1);

        // then

    }
}
