package science.aist.imaging.service.imagej.imageprocessing.wrapper;

import ij.process.ColorProcessor;
import ij.process.ImageProcessor;
import science.aist.imaging.api.domain.wrapper.ChannelType;
import science.aist.imaging.api.domain.wrapper.ImageFactory;
import science.aist.imaging.api.domain.wrapper.ImageWrapper;

/**
 * <p>Implementation of a {@link science.aist.imaging.api.domain.wrapper.ImageFactory} for ImageJ's {@link ImageProcessor}</p>
 * <p>Note that ImageJ saves pixels as integers using bit shifting. So if you need more than 3 channels take {@link ImageStackFactory}.</p>
 *
 * @author Christoph Praschl
 * @since 1.1
 */
public class ImageProcessorFactory implements ImageFactory<ImageProcessor> {

    private static final ImageProcessorFactory instance = new ImageProcessorFactory();

    private ImageProcessorFactory() {
    }

    /**
     * @return a instance of {@link ImageStackFactory}
     */
    public static ImageFactory<ImageProcessor> getInstance() {
        return instance;
    }

    @Override
    public ImageWrapper<ImageProcessor> getImage(int height, int width, ChannelType channel) {
        return getImage(height, width, channel, new ColorProcessor(width, height));
    }

    @Override
    public ImageWrapper<ImageProcessor> getImage(int height, int width, ChannelType channel, ImageProcessor image) {
        if (height != image.getHeight()) {
            throw new IllegalArgumentException("Height does not match the given image processor");
        }

        if (width != image.getWidth()) {
            throw new IllegalArgumentException("Width does not match the given image processor");
        }

        if(image.getNChannels() != channel.getNumberOfChannels()){
            throw new IllegalArgumentException("Channeltype does not match the given image processor");
        }

        return new ImageProcessorWrapper(image, channel);
    }

    @Override
    public ImageWrapper<ImageProcessor> getImage(ImageProcessor image) {
        return getImage(image.getHeight(), image.getWidth(), ChannelType.RGB, image);
    }
}
