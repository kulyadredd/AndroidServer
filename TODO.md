TODO:

* faster image loading
  * increased buffer?
  * advanced caching?
* make variable names consistent
* switch to Jetty v9
* use category selector from v0.0.13
* fix layout: elements of same witdth, spaces and paddings for each element.
* Categories page: edit should happen in-place. Click on category name, input box with category name, edit, hit enter to save.
* Categories page: delete should appear as &times; sign on the right of evey category.
* Refactor packages. All packages should be nested in com.&lt;servicename&gt;.

BUGS:

* position 'x' button on scrolldown
* Sometimes `int wigth = Integer.parseInt(name);` throws this type of error (fix it by fixing the problem, not by yet-another-if-statement)

        Caused by: java.lang.NumberFormatException: For input string: "modules"
            at java.lang.NumberFormatException.forInputString(NumberFormatException.java:65)
            at java.lang.Integer.parseInt(Integer.java:492)
            at java.lang.Integer.parseInt(Integer.java:527)
            at webs.ImageFiles.get(ImageFiles.java:36)
            at mvc.Controller.doGet(Controller.java:41)
            ... 23 more`
    
 * strange symbols 'ï»¿' show up in UI
    


DONE:

* ~~more category manipulation: delete/rename~~
* ~~enable non-latin values for category name and bundle title~~
* ~~warning should appear to confirm the deletion of an element.~~
* ~~placehoders for empty bundle elements~~
* ~~input and text file drop for title~~
* ~~make build-server lazy~~
* ~~web access to logs~~
* ~~delete &times; floating away form it's position. probably it should appear on top of correspodning element.~~

DONE BUGS:

* ~~new bundle placeholder is not removed if category name is cleared manually (using delete/backspace button).~~
* ~~if category name is cleared using red &times; button - current category images stay, but new bundle placeholder disappears.~~
* ~~delete &times; floating away form it's position. probably it should appear on top of * correspodning element.~~
