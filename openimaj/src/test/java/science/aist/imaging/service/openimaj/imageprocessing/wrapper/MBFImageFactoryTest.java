package science.aist.imaging.service.openimaj.imageprocessing.wrapper;

import org.openimaj.image.MBFImage;
import org.openimaj.image.colour.ColourSpace;
import org.testng.Assert;
import org.testng.annotations.Test;
import science.aist.imaging.api.domain.wrapper.ChannelType;
import science.aist.imaging.api.domain.wrapper.ImageFactory;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;
import science.aist.imaging.api.domain.wrapper.implementation.ImageFactoryFactory;

import java.util.Random;

/**
 * <p>Created by Christoph Praschl  on 12.02.2021</p>
 * <p>Test class for {@link MBFImageFactory}</p>
 *
 * @author Christoph Praschl christoph.praschl@fh-hagenberg.at
 */
public class MBFImageFactoryTest {

    @Test
    public void testGetImage() {
        // given
        ImageFactory<MBFImage> imageProcessorFactory = ImageFactoryFactory.getImageFactory(MBFImage.class);
        int width = 10;
        int height = 15;
        MBFImage img = new MBFImage(width, height, ColourSpace.RGB);

        Random rand = new Random(768457);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Float[] array = new Float[3];
                for (int c = 0; c < 3; c++) {
                    array[c] = rand.nextFloat();
                }
                img.setPixel(x, y, array);
            }
        }

        int[][][] reference = new int[][][]{
                new int[][]{new int[]{72, 67, 172}, new int[]{12, 206, 189}, new int[]{164, 231, 84}, new int[]{228, 45, 70}, new int[]{16, 9, 76}, new int[]{45, 241, 185}, new int[]{179, 193, 167}, new int[]{61, 85, 117}, new int[]{69, 45, 74}, new int[]{75, 37, 169}, new int[]{212, 164, 98}, new int[]{153, 91, 2}, new int[]{74, 36, 14}, new int[]{134, 202, 209}, new int[]{123, 47, 88}},
                new int[][]{new int[]{207, 194, 93}, new int[]{23, 170, 212}, new int[]{13, 168, 72}, new int[]{212, 223, 48}, new int[]{251, 189, 59}, new int[]{14, 144, 242}, new int[]{63, 239, 44}, new int[]{87, 251, 223}, new int[]{184, 25, 101}, new int[]{134, 82, 150}, new int[]{127, 154, 105}, new int[]{8, 48, 161}, new int[]{145, 57, 145}, new int[]{133, 52, 213}, new int[]{144, 91, 105}},
                new int[][]{new int[]{120, 190, 80}, new int[]{201, 210, 146}, new int[]{188, 110, 61}, new int[]{117, 89, 234}, new int[]{196, 21, 11}, new int[]{211, 185, 54}, new int[]{31, 99, 129}, new int[]{159, 48, 39}, new int[]{11, 192, 132}, new int[]{254, 99, 19}, new int[]{58, 147, 142}, new int[]{63, 145, 231}, new int[]{251, 41, 104}, new int[]{10, 238, 13}, new int[]{230, 191, 195}},
                new int[][]{new int[]{160, 50, 170}, new int[]{244, 31, 87}, new int[]{44, 252, 167}, new int[]{113, 238, 92}, new int[]{106, 32, 132}, new int[]{112, 160, 50}, new int[]{18, 123, 139}, new int[]{140, 221, 210}, new int[]{160, 189, 140}, new int[]{225, 221, 79}, new int[]{153, 208, 18}, new int[]{236, 82, 76}, new int[]{166, 120, 72}, new int[]{223, 201, 129}, new int[]{12, 103, 99}},
                new int[][]{new int[]{180, 188, 211}, new int[]{108, 192, 121}, new int[]{252, 131, 119}, new int[]{189, 48, 128}, new int[]{185, 149, 254}, new int[]{132, 183, 4}, new int[]{226, 162, 104}, new int[]{72, 238, 117}, new int[]{68, 190, 210}, new int[]{96, 74, 221}, new int[]{159, 3, 249}, new int[]{60, 75, 223}, new int[]{53, 173, 84}, new int[]{190, 213, 228}, new int[]{184, 44, 219}},
                new int[][]{new int[]{241, 126, 53}, new int[]{113, 17, 172}, new int[]{36, 6, 2}, new int[]{31, 79, 130}, new int[]{38, 91, 67}, new int[]{89, 197, 253}, new int[]{253, 197, 138}, new int[]{26, 70, 146}, new int[]{148, 109, 191}, new int[]{49, 15, 95}, new int[]{92, 45, 60}, new int[]{73, 134, 85}, new int[]{188, 246, 17}, new int[]{189, 119, 59}, new int[]{172, 163, 224}},
                new int[][]{new int[]{240, 62, 183}, new int[]{182, 28, 45}, new int[]{164, 176, 191}, new int[]{107, 7, 160}, new int[]{104, 27, 72}, new int[]{216, 164, 175}, new int[]{131, 106, 96}, new int[]{74, 44, 213}, new int[]{78, 215, 81}, new int[]{202, 102, 151}, new int[]{15, 249, 1}, new int[]{215, 63, 239}, new int[]{135, 173, 178}, new int[]{157, 61, 172}, new int[]{132, 175, 8}},
                new int[][]{new int[]{40, 37, 32}, new int[]{10, 199, 131}, new int[]{223, 207, 21}, new int[]{170, 245, 133}, new int[]{209, 225, 132}, new int[]{94, 79, 165}, new int[]{53, 109, 232}, new int[]{229, 117, 230}, new int[]{226, 80, 208}, new int[]{114, 243, 240}, new int[]{203, 137, 176}, new int[]{158, 215, 49}, new int[]{124, 224, 111}, new int[]{150, 223, 111}, new int[]{249, 139, 103}},
                new int[][]{new int[]{101, 239, 242}, new int[]{33, 233, 192}, new int[]{248, 131, 144}, new int[]{198, 92, 216}, new int[]{21, 201, 16}, new int[]{47, 120, 57}, new int[]{91, 32, 204}, new int[]{190, 64, 153}, new int[]{88, 220, 41}, new int[]{76, 149, 145}, new int[]{28, 239, 114}, new int[]{6, 160, 16}, new int[]{85, 18, 50}, new int[]{176, 218, 70}, new int[]{173, 141, 157}},
                new int[][]{new int[]{197, 160, 174}, new int[]{125, 104, 60}, new int[]{180, 228, 128}, new int[]{234, 179, 235}, new int[]{81, 225, 11}, new int[]{81, 69, 187}, new int[]{182, 237, 126}, new int[]{208, 177, 99}, new int[]{108, 75, 120}, new int[]{94, 224, 180}, new int[]{149, 151, 63}, new int[]{33, 246, 159}, new int[]{59, 66, 49}, new int[]{8, 193, 187}, new int[]{139, 212, 78}},
        };

        // when
        ImageWrapper<MBFImage> image = imageProcessorFactory.getImage(img);

        // then
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                for (int c = 0; c < image.getChannels(); c++) {
                    Assert.assertEquals((int) image.getValue(x,y,c), reference[x][y][c]);
                }
            }
        }
    }

    @Test
    public void testTestGetImage() {
        // given
        MBFImageFactory imageProcessorFactory = new MBFImageFactory();

        // when
        ImageWrapper<MBFImage> image = imageProcessorFactory.getImage(10, 15, 3);

        // then
        Assert.assertEquals(image.getHeight(), 10);
        Assert.assertEquals(image.getWidth(), 15);
        Assert.assertEquals(image.getChannels(), 3);
        Assert.assertEquals(image.getChannelType(), ChannelType.RGB);
    }

}
