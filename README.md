swing-on-steroids
=================

swing-on-steroids bring Swing development on steroïds with Swing development on steroïds with Supervising Presenters and Passive Views (aka. MVP), a MessageBus and optionaly Guice or Qi4j.

A versatile wizard api using the patterns cited above plus a graph model with commodities such as BlockingView easing in and out smoothly thanks to Trident.

The project is hosted in maven central.
[here](http://mavencentral.sonatype.com/#search|ga|1|swing-on-steroids) you'll find a quick copy/paste for the dependency.

Changelog
---------

### swing-on-steroids-1.1 - Released 2011/02/04

- Removed callbacks on message publication in MessageBus because it caused some locks in workQueue and was not so usefull
- Added EventDispatchThreadConcern for Qi4j
- Moved GuiceHelper from java-toolbox in here, now depending on 1.4

### swing-on-steroids-1.0 - Released 2010/11/03

- Initial release


