package openoperator.operators;

import openoperator.OpenOperatorExtensionPackage;
import uk.ac.ed.ph.jqtiplus.exception.QtiLogicException;
import uk.ac.ed.ph.jqtiplus.node.expression.ExpressionParent;
import uk.ac.ed.ph.jqtiplus.node.expression.operator.CustomOperator;
import uk.ac.ed.ph.jqtiplus.running.ProcessingContext;
import uk.ac.ed.ph.jqtiplus.validation.ValidationContext;
import uk.ac.ed.ph.jqtiplus.value.Value;

/**
 * Defines the "distance" custom operator, which calculates the distance
 * between a series of points, and returns it as a single float.
 * 
 * The "distance" accepts one or more child sub-expression values with
 * a base type of "point" and a cardinality of single, multiple, or ordered.
 * 
 * The child point values are flattened, with NULL values being ignored, and 
 * the two-dimensional distance is calculated between each point in succession.   
 * 
 * TODO - review decision to filter/ignore NULLs VS returning NULL when any (grand)child NULL
 * 
 * @author Zack Pierce
 *
 */
public class Distance extends CustomOperator<OpenOperatorExtensionPackage> {
    
    private static final long serialVersionUID = -7111474874525189928L;

    public Distance(ExpressionParent parent) {
        super(parent);
    }

    @Override
    protected void validateThis(ValidationContext context) {
        super.validateThis(context);
        if (getChildren().size() == 0) {
            context.fireValidationWarning(this, "Container should contain some children.");
        }
    }
    
    @Override
    protected Value evaluateSelf(OpenOperatorExtensionPackage jqtiExtensionPackage, ProcessingContext context, Value[] childValues, int depth) {
        return distance(childValues);
    }
    
    public static Value distance(final Value[] childValues) throws QtiLogicException {
        throw new UnsupportedOperationException("Not implemented yet");
    }

}
