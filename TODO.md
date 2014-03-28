TODO:

* faster image loading
  * increased buffer?
  * advanced caching?
* make variable names consistent
* switch to Jetty v9

BUGS:

* new bundle placeholder is not removed if category name is cleared manually (using delete/backspace button).
* if category name is cleared using red &times; button - current category images stay, but new bundle placeholder disappears.

DONE:
* ~~more category manipulation: delete/rename~~
* ~~use category selector from v0.0.13~~
* ~~enable non-latin values for category name and bundle title~~
* ~~warning should appear to confirm the deletion of an element.~~
* ~~placehoders for empty bundle elements~~
* ~~input and text file drop for title~~
* ~~make build-server lazy~~
* ~~web access to logs~~
* ~~fix layout: elements of same witdth, spaces and paddings for each element.~~
* ~~delete &times; floating away form it's position. probably it should appear on top of correspodning element.~~