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

### parseFloat ###
Parses a single float from a single string.

### parseInteger ###
Parses a single integer from a single string. 

---
Detailed operator documentation can be found in the class descriptions.

Stand-alone assessment item XML files demonstrating use of the custom operators may be found in the src/test/resources/openoperator/operators directory.

## Planned ##
* More Operators:  
 * Advanced substring slicing (e.g. "string contents prior to first instance of substring within target string")
 * Set manipulation and inspection
* QTI XML implementations of all custom operators not requiring fundamentally new primitives.
* Non-Java reference implementations of operations.
* Documentation
 * Guiding practices for operation behavioral patterns
 * Expand wiki documentation of operators to reduce the time people have to spend looking through the code files.
 * Formalize (and automate) code style guidelines
 * Describe collaboration guidelines
 * XML item examples with non-dummy item bodies containing relevant interactions and making advanced use of the operators in nontrivial processing rules..


## Documentation Expectations ##


Operator behavior should be clear.  The documentation should provide guidance on how the operator will behave under a variety of conditions.

Answering the following questions should be a reasonable starting point for ensuring thorough operator design and implementation. 

* How many sub-expression children (inputs) are permitted? Is it a range or a set number? 
* What happens if there are more or fewer children than permitted?
* What cardinality and base type of sub-expression children (inputs) are permitted?
* If the order of child sub-expressions were changed, how would that affect the output? 
* If the child sub-expressions must be in a particular order, do those sub-expressions have different allowed cardinality or base type?
* What is the cardinality and base type of the operator's result output?
* Does the output cardinality and base type depend on the base type or cardinality of the input sub-expression children?
* Does the output cardinality or base type depend on the number of input sub-expression children?
* What is the output if any one of the input sub-expression children is NULL?
* What is the output if all of the input sub-expressions are NULL? 
* For numerical operators, what happens if any one of the input sub-expression children is "NaN"?
* Is it ever possible for the operator to produce a "NaN" result? (NaN is distinct from NULL)
