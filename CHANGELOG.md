Change Log
==========

Version 1.0 *(2020-02-13)*
----------------------------
 * Library migrated to AndroidX
 * Changed behavior on initialize method. Now it can be called multiple times, in that case the settings will be updated.
 * IllegalStateException is now thrown when the library is not initialized, prior to that it was NullPointerException.

Version 0.9.2 *(2018-01-26)*
----------------------------
 * Improved support for RTL locales on pre-Nougat.
 * Fix activity recreation problems on Oreo.

Version 0.9.1 *(2017-05-21)*
----------------------------
 * Changed internal SharedPreferences file name to make it independent of the package structure.

Version 0.9.0 *(2017-05-14)*
----------------------------
 * Initial release.