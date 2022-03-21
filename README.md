![imaging](./src/site/resources/images/bannerRight.png)

# Imaging

[![DOI](https://zenodo.org/badge/335652633.svg)](https://zenodo.org/badge/latestdoi/335652633)
[![License: MPL 2.0](https://img.shields.io/badge/License-MPL%202.0-brightgreen.svg)](https://opensource.org/licenses/MPL-2.0)
[![GitHub release](https://img.shields.io/github/v/release/fhooeaist/imaging.svg)](https://github.com/fhooeaist/imaging/releases)
[![javadoc](https://javadoc.io/badge2/science.aist.imaging/imaging/javadoc.svg)](https://javadoc.io/doc/science.aist.imaging/imaging)
[![Maven Central](https://img.shields.io/maven-central/v/science.aist.imaging/imaging.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:"science.aist.imaging")

The imaging framework provides different functionality in the context of image processing and computer vision. For this there are many image processing related implementations in the project but also connection classes to other frameworks (e.g. OpenCV) to extend its functionality. 
Get a detailed overview on our [maven site](https://fhooeaist.github.io/imaging/index.html).

## Getting Started

The project is structured into multiple submodules. API builds the base module which contains the domain classes as well
as the interfaces. The core module builds the base implementation with pure java functionality. In addition
to these two modules there are modules for connecting to other frameworks for imagej, nd4j, opencv, openimaj, pdfbox, tesseract and microsoft-cognitive-services. These modules are
wrappers for functionality that is provided in either of these libraries.

To use the Imaging project you simply need to add the required dependencies like:

```xml
<dependency>
    <groupId>science.aist.imaging</groupId>
    <artifactId>api</artifactId> <!-- alternatives core, nd4j, openimaj, imagej, opencv, pdfbox, tesseract, microsoft-cognitive-services -->
    <version>${imaging.version}</version> <!-- e.g. 1.0.0 -->
</dependency>
```

### Examples

A simple example using the Core module (1) creates an image using the `ImageFactoryFactory` and (2) draws a circle at a given 
position.

```java
// (1) Create a new image
ImageWrapper<short[][][]> image = ImageFactoryFactory.getImageFactory(short[][][].class).getImage(100, 100, ChannelType.Greyscale);

// (2) Draw on the image
DrawCircle<short[][][]> draw = new DrawCircle<>();
draw.setColor(new double[]{1});
draw.accept(image, new JavaPoint2D(5, 5));
```

A more advanced example is shown in the following code snippet, that shows the module interoperability of the Imaging project based on the `GenericImageFunction`. Note: `GenericImageFunction` casts its input image, and the result of the wrapped function if necessary, which affects its resource requirements.

```java
// (1) Load OpenCV DLLs
AistCVLoader.loadShared();

// (2) Create a random input image for the test
Random rand = new Random(768457);
ImageWrapper<double[][][]> input = ImageFactoryFactory.getImageFactory(double[][][].class).getRandomImage(10, 10, ChannelType.RGB, rand, 0, 255, true);

// (3) Prepare the function to be applied (Note: it is implemented for OpenCV only!)
OpenCVThresholdFunction thresholdFunction = new OpenCVThresholdFunction();
GenericImageFunction<double[][][], double[][][], Mat, Mat> function = new GenericImageFunction<>(thresholdFunction, Mat.class, double[][][].class);

// (4) Apply the function (Note: on a non-OpenCV image)
ImageWrapper<double[][][]> thresholdResult = function.apply(input);
```

## FAQ

If you have any questions, please checkout our [FAQ](https://fhooeaist.github.io/imaging/faq.html) section.

## Contributing

**First make sure to read our [general contribution guidelines](https://fhooeaist.github.io/CONTRIBUTING.html).**

## Licence

Copyright (c) 2020 the original author or authors.
DO NOT ALTER OR REMOVE COPYRIGHT NOTICES.

This Source Code Form is subject to the terms of the Mozilla Public
License, v. 2.0. If a copy of the MPL was not distributed with this
file, You can obtain one at https://mozilla.org/MPL/2.0/.

The following code is under different licence and copyright:

| Licence | Filepaths |
|-|-|
| **MIT**<br>see LICENSE_MIT_JDIEMKE | api/src/main/java/science/aist/imaging/api/domain/twodimensional/JavaTriangle2D |
| **MIT**<br>see LICENSE_MIT_KIERS | core/src/test/java/science/aist/imaging/service/core/pointprocessing/GrahamConvexHull |
| **Apache**<br> see LICENSE_APACHE | tesseract/tessdata/* |

## Research

If you are going to use this project as part of a research paper, we would ask you to reference this project by citing
it.

[![DOI](https://zenodo.org/badge/335652633.svg)](https://zenodo.org/badge/latestdoi/335652633)

Additionally, this work was used in the following publications:
 
 - [Pointner, A., Praschl, C., Krauss, O., Schuler, A., Helm, E., & Zwettler, G. (2021). Line Clustering and Contour Extraction in the Context of 2D Building Plans.](https://doi.org/10.24132/csrn.2021.3101.2)
 - [Praschl, C., Krauss, O., & Zwettler, G. A. (2020). Enabling outdoor MR capabilities for head mounted displays: a case study. International Journal of Simulation and Process Modelling, 15(6), 512-523.](https://doi.org/10.1504/ijspm.2020.112463)
 - [Praschl, C., Pointner, A., Baumgartner, D., & Zwettler, G. A. (2021). Imaging framework: An interoperable and extendable connector for image-related Java frameworks. SoftwareX, 16, 100863.](https://doi.org/10.1016/j.softx.2021.100863)
 - [Baumgartner, D., Zucali, T., & Zwettler, G. A. (2020). Hybrid approach for orientation-estimation of rotating humans in video frames acquired by stationary monocular camera.](https://doi.org/10.24132/csrn.2020.3001.5)
 - [Zwettler, G. A., Praschl, C., Baumgartner, D., Zucali, T., Turk, D., Hanreich, M., & Schuler, A. (2021). Three-step Alignment Approach for Fitting a Normalized Mask of a Person Rotating in A-Pose or T-Pose Essential for 3D Reconstruction based on 2D Images and CGI Derived Reference Target Pose. In VISIGRAPP (5: VISAPP) (pp. 281-292).](https://doi.org/10.5220/0010194102810292)
 - [Pointner A, Krauss O, Freilinger G, Strieder D, Zwettler G. (2018). Model-Based Image Processing Approaches for Automated Person Identification and Authentication in Online Banking. In: 30th European modeling and simulation symposium.](https://www.researchgate.net/publication/336496655_MODEL-BASED_IMAGE_PROCESSING_APPROACHES_FOR_AUTOMATED_PERSON_IDENTIFICATION_AND_AUTHENTIFICATION_IN_ONLINE_BANKING)
