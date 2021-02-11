package science.aist.imaging.service.imagej.imageprocessing.wrapper;

import ij.ImageStack;
import ij.process.ColorProcessor;
import science.aist.imaging.api.domain.wrapper.ChannelType;
import science.aist.imaging.api.domain.wrapper.ImageFactory;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;

/**
 * <p>Implementation of a {@link science.aist.imaging.api.domain.wrapper.ImageFactory} for ImageJ's {@link ImageStack}</p>
 * <p>Note that ImageJ saves bytewise as integer. Use {@link science.aist.imaging.service.imagej.imageprocessing.converter.ColorConverter}</p>
 *
 * @author Christoph Praschl
 * @since 1.1
 */
public class ImageStackFactory implements ImageFactory<ImageStack> {
    private static final ImageStackFactory instance = new ImageStackFactory();

    private ImageStackFactory() {
    }

    /**
     * @return a instance of {@link ImageStackFactory}
     */
    public static ImageFactory<ImageStack> getInstance() {
        return instance;
    }

    @Override
    public ImageWrapper<ImageStack> getImage(int height, int width, ChannelType channel) {
        ImageStack imageStack = new ImageStack(width, height, channel.getNumberOfChannels());
        for (int c = 1; c < channel.getNumberOfChannels() + 1; c++) {
            imageStack.setProcessor(new ColorProcessor(width, height), c);
        }

        return getImage(height, width, channel, imageStack);
    }

    @Override
    public ImageWrapper<ImageStack> getImage(int height, int width, ChannelType channel, ImageStack image) {
        if (height != image.getHeight()) {
            throw new IllegalArgumentException("Height does not match the given image processor");
        }

        if (width != image.getWidth()) {
            throw new IllegalArgumentException("Width does not match the given image processor");
        }

        if (channel.getNumberOfChannels() != image.getSize()) {
            throw new IllegalArgumentException("Channeltype does not match stack size");
        }

        return new ImageStackWrapper(image, channel);
    }

    @Override
    public ImageWrapper<ImageStack> getImage(ImageStack image) {
        int size = image.getSize();
        ChannelType c;
        if (size == 3) {
            c = ChannelType.UNKNOWN_4_CHANNEL;
        } else if (size == 2) {
            c = ChannelType.UNKNOWN_3_CHANNEL;
        } else if (size == 1) {
            c = ChannelType.UNKNOWN_2_CHANNEL;
        } else {
            c = ChannelType.UNKNOWN;
        }
        return getImage(image.getHeight(), image.getWidth(), c, image);
    }
}
