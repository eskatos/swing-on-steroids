swing-on-steroids
=================

swing-on-steroids bring Swing development on steroïds with [Supervising Presenters](http://martinfowler.com/eaaDev/SupervisingPresenter.html) and [Passive Views](http://martinfowler.com/eaaDev/PassiveScreen.html) (aka. MVP), a [MessageBus](http://martinfowler.com/eaaDev/EventCollaboration.html) and optionaly [Guice](http://code.google.com/p/google-guice/) or [Qi4j](http://qi4j.org).

A versatile wizard api using the patterns cited above plus a graph model with commodities such as BlockingView easing in and out smoothly thanks to [Trident](http://kenai.com/projects/trident).

The project is hosted in maven central.
[here](http://mavencentral.sonatype.com/#search|ga|1|swing-on-steroids) you'll find a quick copy/paste for the dependency.

**WARNING:** MessageBus API is not fully backward compatible between 1.1 and 1.0, read the Changelog below.

Changelog
---------

### swing-on-steroids-1.1 - Released 2011/02/04

- Removed callbacks on message publication in MessageBus
- Added EventDispatchThreadConcern for Qi4j
- Moved GuiceHelper from java-toolbox in here, now depending on 1.4

### swing-on-steroids-1.0 - Released 2010/11/03

- Initial release


