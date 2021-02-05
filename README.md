# Imaging

The imaging framework provides different functionality in the context of image processing and computer vision. For more
details please visit our [maven site](https://fhooeaist.github.io/imaging/index.html).

## Getting Started

The project is structured into multiple submodules. API builds the base module which contains the domain classes as well
as the interfaces. The core module build the base implementation with functionality all implemented in Java. In addition
to these two modules there are modules for opencv, pdfbox, tesseract and microsoft-cognitive-services. These modules are
wrappers for functionality that is provided in either of these libraries.

To use the code of either of one of these modules, you simply need to include one of them as a dependency:

```xml
<dependency>
    <groupId>science.aist.imaging</groupId>
    <artifactId>api</artifactId> <!-- alternatives core, opencv, pdfbox, tesseract, microsoft-cognitive-services -->
    <version>${imaging.version}</version> <!-- e.g. 1.0.0 -->
</dependency>
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

TODO zenodo.