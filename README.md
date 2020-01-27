# csv2edn

Simple command line utility to convert CSV files to EDN. Assumes the first row of a CSV file contains column headers.

## Installation

Download from http://example.com/FIXME.

## Usage

FIXME: explanation

    $ java -jar csv2edn-0.1.0-standalone.jar [args]

## Options

Where args are:

    -h, --help                    Print this message and exit.
    -i, --input INPUT             The path name of the CSV file to be read.
      If no input file is specified, input will be read from standard input.
    -o, --output OUTPUT           The path name of the EDN file to be written.
      If no output file is specified, output will be written to standard output.
    -s, --separator SEPARATOR     The character to treat as a separator in the
      CSV file. If not specified, comma will be used by default.


FIXME: listing of options this app accepts.

## Examples

The simplest possible use is to simply use this in a pipeline:

    $ cat path/to/file.csv |\
        java -jar csv2edn-0.1.0-standalone.jar > path/to/file.edn

Exactly the same behaviour can be achieved by specifying input and output
paths:

    $ java -jar csv2edn-0.1.0-standalone.jar \
        -i path/to/file.csv -o path/to/file.edn

or

    $ java -jar csv2edn-0.1.0-standalone.jar \
        --input path/to/file.csv --output path/to/file.edn

### Bugs

...

### Any Other Sections
### That You Think
### Might be Useful

## License

Copyright © 2020 Simon Brooke

This program and the accompanying materials are made available under the
terms of the GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.
