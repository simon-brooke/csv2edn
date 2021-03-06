# csv2edn

Simple command line utility and library to convert
[comma-separated value (CSV)](https://tools.ietf.org/html/rfc4180) files to
[extensible data notation (EDN)](https://github.com/edn-format/edn) or
[javascript object notation (JSON)](https://www.json.org/json-en.html). Assumes
the first row of a CSV file contains column headers.

## Installation

[![Clojars Project](https://img.shields.io/clojars/v/csv2edn.svg)](https://clojars.org/csv2edn)

**NOTE**: if the version numbers given below differ from the number in the
image above, the image (which is automatically generated) is correct and
this file (which is manually maintained) is in error.

### Leiningen/Boot

[csv2edn "0.1.6"]

### Clojure CLI/deps.edn

csv2edn {:mvn/version "0.1.6"}

### Gradle

compile 'csv2edn:csv2edn:0.1.6'

### Maven

<dependency>
  <groupId>csv2edn</groupId>
  <artifactId>csv2edn</artifactId>
  <version>0.1.6</version>
</dependency>

## Usage: as a standalone commandline tool

To run from the command line:

    $ java -jar csv2edn-0.1.6-standalone.jar [options]

### Options

Where options are:

    -f, --format FORMAT        :edn  Format the ouput as `FORMAT`
      (expected to be either `edn` or `json`, defaults to `edn`)
    -h, --help                       Print this message and exit.
    -i, --input INPUT                The path name of the CSV file to be read.
      If no input file is specified, input will be read from standard input.
    -o, --output OUTPUT              The path name of the EDN file to be written.
      If no output file is specified, output will be written to standard output.
    -s, --separator SEPARATOR  ,     The character to treat as a separator in
      the CSV file

### Examples

The simplest possible use is to simply use this in a pipeline:

    $ cat path/to/file.csv |\
        java -jar csv2edn-0.1.6-standalone.jar > path/to/file.edn

Exactly the same behaviour can be achieved by specifying input and output
paths:

    $ java -jar csv2edn-0.1.6-standalone.jar \
        -i path/to/file.csv -o path/to/file.edn

or

    $ java -jar csv2edn-0.1.6-standalone.jar \
        --input path/to/file.csv --output path/to/file.edn

## Usage: as a library

To use this package as a library in your own code:

    (:require [csv2edn.csv2edn :refer [csv->edn csv->json *options*]])

And then pass either filenames or a reader and writer respectively to the
function `cvs->edn`, thus:

    (cvs->edn *in* *out*)
      ;; reads standard input and writes to standard output

or

    (cvs->edn "path/to/data.csv" "path/to/data.edn")

In either case, if successful, the function returns a possibly-lazy sequence
of maps representing the second and subsequent lines in the file (the first
line being assumed to contain column headers).

If you do not wish to write an EDN file, simply do not pass the second
argument.

If you want to use a non-standard separator charactor, e.g. bar rather than
comma, you should rebind `*options*`:

    (binding [*options* (merge *options* {:separator \|})]
      (cvs->edn "path/to/data.csv" "path/to/data.edn"))

Or else pass the separator character you want to use as a third argument:

    (cvs->edn "path/to/data.csv" "path/to/data.edn" \|)

If you wish to produce JSON, call the function `csv->json` exactly as
`csv->edn` called above.

### Bugs

Not strictly a bug, but this does not yet work in ClojureScript, because it
depends on `clojure.data.csv`, which also doesn't. It would be good to fix
this.

## License

Copyright © 2020 Simon Brooke

This program and the accompanying materials are made available under the
terms of the GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.
