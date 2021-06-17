# Changelog

The changelog should give you a quick overview of what change between the different Seshat versions.

## Version 1.2.0

### New Features

- [5](https://github.com/FHOOEAIST/imaging/issues/5) - Added the mesh module with multiple storage implementations for JavaModel3D class (OBJ, OFF, PLY, STL) as well as a Constrained Delaunay implementation
- [27](https://github.com/FHOOEAIST/imaging/issues/27) - Added `andThen`-method to ImageFunction, to allow chaining of multiple functions.

### Dependencies
- [5](https://github.com/FHOOEAIST/imaging/issues/5) - Added a dependency to [jDelaunay](https://github.com/orbisgis/jdelaunay) for constrained delaunay triangulation

## Version 1.1.0

### New Features

- [6](https://github.com/FHOOEAIST/imaging/issues/6) - Added support for ImageJ based images.
- [7](https://github.com/FHOOEAIST/imaging/issues/7) - Added support for OpenIMAJ based images and added new method `ChannelType.isValidValue()`
- [11](https://github.com/FHOOEAIST/imaging/issues/11) - New `ImageFactoryFactory`, that allows to create an image factory for a given type.
- [12](https://github.com/FHOOEAIST/imaging/issues/12) - Added support for ND4J based images
- [17](https://github.com/FHOOEAIST/imaging/issues/17) - Added the GenericImageFunction

### Dependencies
- [13](https://github.com/FHOOEAIST/imaging/issues/13) - Update from seshat 1.0.1 to 1.1.0

## Version 1.0.0

First release of Imaging.