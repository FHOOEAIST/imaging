# Core

The core module contains the base interfaces for most of the image processing functionality implemented in the different sub modules as the opencv module.

## Software Architecture

The imaging project highly sticks to a functional-like programming style. For this reason most of the functionality is based on Java's `Function` or `Consumer` interface. Functions return new images and don´t touch the given object, while consumers apply changes directly onto the given object.

The core implementations are highly generic so any `ImageWrapper` implementation can be used with most of them. For this reason many function classes require an injected `ImageFactory` to create the result image. The input type can be wildcards most of the time. So if you want to use a `GaussFilter` which takes any input image and results in a `BufferedImage` wrapper use it like this:

```java
GaussFilterFunction<?, BufferedImage> gaussFilterFunction = new GaussFilterFunction<>(ImageFactoryFactory.getImageFactory(BufferedImage.class));
ImageWrapper<BufferedImage> result = gaussFilterFunction.apply(input);
```

## Packages

The main package is the ```imageprocessing``` package which consists different functionalities as well as submodules, as listend below.

 * **imageprocessing**: Contains image processing functionality
     * **analysis**: Contains a function for calculating the ratio of a specific color
     * **contour**: Consists of functions of contour detection and boundary tracing
     * **contrast**: Consists of interfaces for contrast correction (e.q. gamma correction, histogram equalization, ...)
     * **conversion**: Contains functions for converting an image from a given color space to another. Also contains functions for splitting/merging an image to/from channels
     * **creator**: Contains creator interfaces for object creation. e.q. creating a JavaImage based on given JavaLines
     * **distance**: Contains distance metrics and functionality for distance map calculation.
     * **draw**: Contains consumer implementations that draw on images
     * **filter**: Contains filter functionality as AnisotropicDiffusion or different convolution filters
         * **highpass**: Contains high pass filter e.g. for edge detection
         * **lowpass**: Contains low pass filter as mean or gauss filter
         * **pooling**: Contains pooling filters as MaxPooling
     * **fitnessfunction**: provides an interface for calculating fitness metrics as SumOfSquareDifferences
     * **helper**: Contains helper functions for normalizing an image, finding the min/max value in an image or to create a histogram for the image
     * **houghspace**: Contains interfaces and implementations for detecting lines using the hough space
     * **interpolation**: Contains interpolation functionality as BilinearInterpolation or NearestNeighbor
     * **metadata**: Contains functionality for extracting EXIF metadata from images
     * **operator**: Contains multiple base operations for images as adding two images or subtracting one image from another
     * **registration**: Contains an interface for registration methods
     * **segmentation**: Contains a segmentation function for segmenting color parts in an image.
     * **transformation**: Contains image transformation functions (e.g. InvertFunction, Threshold, Transformfunction, Crop, ...)
     * **transformers**: Contains transformers for converting between different image representation (e.g. 2Byte Image, 8Byte Image, Buffered Image)
       * **color**: Contains transformers between different color representations (e.g. RGB to HSV)
 * **objectprocessing**: Contains object recognition interfaces
     * **compare**: Contains interfaces for comparing recognized objects
     * **merge**: Contains interfaces for merging recognized objects
 * **pointprocessing**: Contains functionality for processing points, pointclouds, polygons and similar point structures as convex hull calculation or boundary tracing.
     * **convexhull**:    Contains functionality for calculating a convex hull using the graham convex hull algorithm
 * **storage**: Contains storage services e.g. for saving/loading images or points as/from CSV files