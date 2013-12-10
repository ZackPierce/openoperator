package openoperator.operators;

import java.util.ArrayList;
import java.util.List;

import openoperator.OpenOperatorConstants;
import openoperator.OpenOperatorExtensionPackage;
import openoperator.ValueUtilities;
import uk.ac.ed.ph.jqtiplus.exception.QtiLogicException;
import uk.ac.ed.ph.jqtiplus.node.expression.ExpressionParent;
import uk.ac.ed.ph.jqtiplus.node.expression.operator.CustomOperator;
import uk.ac.ed.ph.jqtiplus.running.ProcessingContext;
import uk.ac.ed.ph.jqtiplus.validation.ValidationContext;
import uk.ac.ed.ph.jqtiplus.value.IntegerValue;
import uk.ac.ed.ph.jqtiplus.value.NullValue;
import uk.ac.ed.ph.jqtiplus.value.PointValue;
import uk.ac.ed.ph.jqtiplus.value.Value;

/**
 * Defines the "pointY" customOperator, which retrieves the Y, or "vertical" property 
 * value of input child subexpressions.
 * 
 * "pointY" accepts one or more sub-expressions with a base-type of "point" and
 * a cardinality of single, multiple, or ordered.
 * 
 * The result will have a base-type of integer, and a cardinality that depends on
 * the input sub-expressions.
 * 
 * If there is only one sub-expression child, then the result will have the same 
 * cardinality as that input.
 * 
 * There there are 2 or more sub-expression children, then the result will have an
 * "ordered" cardinality that contains the flattened and same-ordered Y values from
 * all of the input children points.
 * 
 * If any of the child sub-expressions are NULL or contain NULL values, 
 * then the result of "pointY" will be NULL. Child sub-expressions with a
 * cardinality of "record" or of non-point base types are not supported and 
 * will result in a thrown QtiLogicException.
 * 
 * 
 * @author Zack Pierce
 */
public class PointY extends CustomOperator<OpenOperatorExtensionPackage> {

    private static final long serialVersionUID = -2084012647999966966L;

    public PointY(ExpressionParent parent) {
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
        return pointY(childValues);
    }

    public static Value pointY(Value[] childValues) throws QtiLogicException {
        if (ValueUtilities.isAnyQtiRecord(childValues)) {
            throw new QtiLogicException("Unsupported record cardinality for a child expression of "
                    + OpenOperatorConstants.OPENOPERATOR_POINTY_CLASS_DISPLAY_NAME);
        }
        final List<Value> flattened = ValueUtilities.flattenToSingles(childValues);
        if (ValueUtilities.isAnyQtiNull(flattened)) {
            return NullValue.INSTANCE;
        }
        final int count = flattened.size();
        final List<IntegerValue> verticals = new ArrayList<IntegerValue>(count);
        for (int i = 0; i < count; i++) {
            final Value child = flattened.get(i);
            if (!(child instanceof PointValue)) {
                throw new QtiLogicException("Unsupported base type of " + child.getBaseType()
                        + " for a child expression of " + OpenOperatorConstants.OPENOPERATOR_POINTY_CLASS_DISPLAY_NAME);
            }
            verticals.add(new IntegerValue(((PointValue) child).verticalValue()));
        }

        return ValueUtilities.coalesceToMatchingCardinalityUnlessSeveralInputs(childValues, verticals,
                OpenOperatorConstants.OPENOPERATOR_POINTY_CLASS_DISPLAY_NAME);
    }

}
