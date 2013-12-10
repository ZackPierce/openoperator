# Open Operator #


A library of common QTI custom operators and documentation to enable compatible cross-vendor and cross-language scoring implementations.

*Actively seeking recommendations and review of operator design and implementation.*   

## Implemented Operators ##

### pointX ###
Retrieves the horizontal integer sub-value from values of the "point" base type.

### pointY ###
Retrieves the vertical integer sub-value from values of the "point" base type.

Detailed operator documentation can be found in the class descriptions.

## Planned ##
* More Operators:  
 * Advanced substring slicing (e.g. "string contents prior to first instance of substring within target string")
 * Numeric parsing - string to float and string to integer
 * Set manipulation and inspection
 * Geometry-related helpers: distance 
* QTI XML implementations of all custom operators not requiring fundamentally new primitives. 
* Non-Java reference implementations of operations.
* Document guiding practices for operation behavioral patterns
* Expand wiki documentation of operators to reduce the time people have to spend looking through the code-centric documents.
* Document (and automate) code style guidelines
* Document collaboration guidelines 