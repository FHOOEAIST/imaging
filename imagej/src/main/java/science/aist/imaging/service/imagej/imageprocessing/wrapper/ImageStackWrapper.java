package science.aist.imaging.service.imagej.imageprocessing.wrapper;

import ij.ImageStack;
import science.aist.imaging.api.domain.wrapper.AbstractImageWrapper;
import science.aist.imaging.api.domain.wrapper.ChannelType;

/**
 * <p>Created by Christoph Praschl on 11.02.2021</p>
 * <p>Implementation of a {@link science.aist.imaging.api.domain.wrapper.ImageWrapper} for ImageJ's {@link ImageStack}</p>
 *
 * @author Christoph Praschl christoph.praschl@fh-hagenberg.at
 */
public class ImageStackWrapper extends AbstractImageWrapper<ImageStack> {
    protected ImageStackWrapper(ImageStack image, ChannelType type) {
        super(image);
        this.channelType = type;
    }

    @Override
    public int getWidth() {
        return image.getWidth();
    }

    @Override
    public int getHeight() {
        return image.getHeight();
    }

    @Override
    public int getChannels() {
        if(channelType != ChannelType.UNKNOWN){
            return channelType.getNumberOfChannels();
        } else {
            return image.getSize();
        }
    }

    @Override
    public double getValue(int x, int y, int channel) {
        return image.getVoxel(x,y,channel);
    }

    @Override
    public void setValue(int x, int y, int channel, double val) {
        image.setVoxel(x,y,channel,val);
    }
}
