# braille-web

Website with RESTful API to decode Braille.

## Prerequisites

You will need [Leiningen][] 2.0.0 or above installed.

[leiningen]: https://github.com/technomancy/leiningen

## Running

To start a web server for the application you may either run:
```
java -jar /jar/braille-web-standalone.jar
```
Or do so via leinegen:

```
lein ring server
```

It should open a browser pointing to http://localhost:3000, otherwise navigate here and begin.

## Todo

* Javascript to add multiple letters before decoding.
* Add a "about braille" section.
* Allow decoding capitals and numbers.

## License

Copyright © 2017 Dr. Ali Raheem
