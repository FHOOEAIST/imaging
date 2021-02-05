# OpenCV

This service module is based on our own OpenCV wrapper [AistCV](https://github.com/FHOOEAIST/aistcv) and extends the core functionality via opencv.

## Notes

Before using any of the OpenCV implementations, you have to load the native library with: `science.aist.aistcv.AistCVLoader.loadShared();`. Use this as the first statement of your main method or with `@BeforeClass` (or in a static constructor of an abstract class if using testng) in test classes.

The main domain object for this module is the ```OpenCVImageWrapper```, which encapsulates the OpenCV ```Mat``` class. Use the ```OpenCVFactory``` to create such a wrapper object.

### Warning

Since this is based on a C++ framework, Java does not know anything about allocated native objects (e.g. `Mat`), and it is not able to release that objects automatically. This is the reason for the wrapper class.
Problem with the wrapper, Java still can't see the C++ side, so for Java an `OpenCVImageWrapper` has a reference to a `Mat` which only consists of an id. So Java thinks that e.g. a `ImageWrapper` that contains a 8k-image is circa just as big as the `long` id and for this will only rarely release images.

This is the reason why you have to make sure **manually** to always release not required images!

There are three ways:

0.) Use `close()` manually, but you have to make sure that the method is called **in any case**. (not recommended)

1.) Using Java's try-with-resources feature
```java
try(ImageWrapper<Mat> image = ...){
    // do something with the image
}
``` 

This has the disadvantage for nested blocks if you have multiple images
```java
try(ImageWrapper<Mat> image1 = ...){
    try(ImageWrapper<Mat> image2 = ...){
        try(ImageWrapper<Mat> image3 = ...){
            // do something with the image
        }
    }
}
``` 

2.) Use the `@Cleanup` annotation of Lombok that generates try-finally-blocks in the corresponding .class files. (**recommended version**)
```java
@Cleanup("close") ImageWrapper<Mat> image1 = ...
@Cleanup("close") ImageWrapper<Mat> image2 = ...
@Cleanup("close") ImageWrapper<Mat> image3 = ...
// native images:
@Cleanup("release") Mat image4 = ...
@Cleanup("release") Mat image5 = ...
@Cleanup("release") Mat image6 = ...
```

The same mechanism works for any other native object as `MatOfKeyPoint`.

## Functionality

The OpenCV module consist of following packages:

  * **averaging**: Contains average filters for calculating average pixels of multiple images
  * **compare**: This package consists of different functions to compare two images. This allows you to check if those images are equal or to calculate image shifts and rotations between those.
  * **contour**: This package contains functions to find contours in images or to calculate a morphological skeleton.
  * **contrast**: Contains contrast adapting functions.
  * **conversion**: Contains functions for converting images to grayscale, HSV representation or inverting the image.
  * **distance**: Contains functionality to calculate a distance map of a given image.
  * **domain**: Contains different domain classes wrapping e.g. int enum values of OpenCV.
  * **draw**: Contains ```Consumer<ImageWrapper<Mat>>``` implementations for manipulating a given image by drawing circles, features, lines, polygons or rectangle onto it.
  * **edgedection**: Contains implementations for applying canny or sobel edge detections onto an image.
  * **featureextraction**: This package contains classes to extract features of a given image.
  * **filter**: Contains image filters e.g. for adapting the image brightness
  * **fitnessfunction**: Contains a FitnessFunction for calculating ```SumOfSquareDifferences```
  * **houghspace**: Allows doing calculations in the hough space as finding lines in an image.
  * **lowpassfilter**: This package contains lowpass filters as a gauss blurring filter, as well as sharpen filters.
  * **morphology**: This package contains morphological transformations as thinning.
  * **objectdetection**: Contains different object detector implementations. 
  * **optimization**: Contains implementation of the Optimizer Interface which is used to improve the quality and/or speed of image processing algorithms.
  * **positioning**: Provides functionality to detect the position of an object in the image based on a given grid for calibration, or a given object size.
  * **registration**: Contains functionality for image registration as Implementation Image Alignment (ECC).
  * **storage**: Contains a Function, and a Consumer for loading/saving image from/to disc.
  * **threshold**: Contains a ImageFunction for thresholding an image.
  * **transformation**: Contains image transformation functions for e.g. translating, rotating, cropping, padding or resizing a OpenCV image.
  * **transformers**: The transformers package consists of transformers between OpenCV and  imaging project domain objects. 
  * **wrapper**: Consists of wrappers for OpenCV domain objects as Mat, Line or Point. Also contains the ```OpenCVImageWrapperFactory```.
