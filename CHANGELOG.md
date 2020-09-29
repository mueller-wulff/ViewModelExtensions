# Changelog

## Version 1.9.0

- added `observeEvent` function
- updated kotlin and android gradle plugin

## Version 1.8.0

- removed `observe`, `observeRequired`, `map` and `switchMap`, because those functions are all
available through the androidx ktx packages

## Version 1.7.0

- switched to using property delegates
- updated maven publishing

## Version 1.6.3

- added Event class to handle events with LiveData

## Version 1.6.1

- converted to androidX

## Version 1.5.1

- added extension methods on LiveData for transformations

## Version 1.4

- updated kotlin to 1.2.61
- updated android plugin to 3.1.4

## Version 1.2

- updated example project and added new utility method `liveDataOf`

## Version 1.1

- changed dependency group, applicationId and package to mueller-wulff

## Version 1.0

- initial commit adding viewmodel non-null observer/value methods, shortened viewmodel providers and small utility methods