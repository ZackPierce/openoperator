package openoperator.operators;

import java.util.List;

import openoperator.OpenOperatorConstants;
import openoperator.OpenOperatorExtensionPackage;
import openoperator.ValueUtilities;
import uk.ac.ed.ph.jqtiplus.exception.QtiLogicException;
import uk.ac.ed.ph.jqtiplus.node.expression.ExpressionParent;
import uk.ac.ed.ph.jqtiplus.node.expression.operator.CustomOperator;
import uk.ac.ed.ph.jqtiplus.running.ProcessingContext;
import uk.ac.ed.ph.jqtiplus.validation.ValidationContext;
import uk.ac.ed.ph.jqtiplus.value.FloatValue;
import uk.ac.ed.ph.jqtiplus.value.PointValue;
import uk.ac.ed.ph.jqtiplus.value.SingleValue;
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
 * If there are 1 or less point values in the flattened inputs from the child
 * expressions, then the result will be 0.0.
 * 
 * Child sub-expressions with a cardinality of "record" or of non-point 
 * base types are not supported and will result in a thrown QtiLogicException.
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
        if (ValueUtilities.isAnyQtiRecord(childValues)) {
            throw new QtiLogicException("Unsupported record cardinality for a child expression of "
                    + OpenOperatorConstants.OPENOPERATOR_DISTANCE_CLASS_DISPLAY_NAME);
        }
        final List<SingleValue> flattened = ValueUtilities.flattenToSinglesIgnoringNull(childValues);
        final int max = flattened.size();
        if (max <= 1) {
            return new FloatValue(0.0);
        }
        double sum = 0.0;
        final Value first = flattened.get(0);
        if (!(first instanceof PointValue)) {
            throw new QtiLogicException("Unsupported base type of " + first.getBaseType()
                    + " for the first child expression of "
                    + OpenOperatorConstants.OPENOPERATOR_DISTANCE_CLASS_DISPLAY_NAME);
        }
        PointValue previous = (PointValue) first;
        for (int i = 1; i < max; i++) {
            final Value currentRaw = flattened.get(i);
            if (!(currentRaw instanceof PointValue)) {
                throw new QtiLogicException("Unsupported base type of " + currentRaw.getBaseType()
                        + " for a child expression of "
                        + OpenOperatorConstants.OPENOPERATOR_DISTANCE_CLASS_DISPLAY_NAME);
            }
            final PointValue current = (PointValue) currentRaw;

            final double horizontalDifference = current.horizontalValue() - previous.horizontalValue();
            final double verticalDifference = current.verticalValue() - previous.verticalValue();
            sum += Math.sqrt(horizontalDifference * horizontalDifference + verticalDifference * verticalDifference);
            previous = current;
        }
        return new FloatValue(sum);
    }

}
