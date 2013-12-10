# Open Operator #


A library of common QTI custom operators and documentation to enable compatible cross-vendor and cross-language scoring implementations.

*Actively seeking recommendations and review of operator design and implementation.*   

## Implemented Operators ##

### pointX ###
Retrieves the horizontal integer sub-value from values of the "point" base type.

### pointY ###
Retrieves the vertical integer sub-value from values of the "point" base type.

### distance ###
Calculates the total distance from one point to a next for a series of points.

Detailed operator documentation can be found in the class descriptions.

## Planned ##
* More Operators:  
 * Advanced substring slicing (e.g. "string contents prior to first instance of substring within target string")
 * Numeric parsing - string to float and string to integer
 * Set manipulation and inspection
 * Geometry-related helpers: distance 
* QTI XML implementations of all custom operators not requiring fundamentally new primitives.
* Stand-alone example assessmentItem XML demonstrating all custom operator features 
* Non-Java reference implementations of operations.
* Documentation
 * Guiding practices for operation behavioral patterns
 * Expand wiki documentation of operators to reduce the time people have to spend looking through the code files.
 * Formalize (and automate) code style guidelines
 * Describe collaboration guidelines 