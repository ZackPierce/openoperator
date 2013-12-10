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
 * Defines the "pointX" customOperator, which retrieves the X, or "horizontal" property 
 * value of input child subexpressions.
 * 
 * "pointX" accepts one or more sub-expressions with a base-type of "point" and
 * a cardinality of single, multiple, or ordered.
 * 
 * The result will have a base-type of integer, and a cardinality that depends on
 * the input sub-expressions.
 * 
 * If there is only one sub-expression child, then the result will have the same 
 * cardinality as that input.
 * 
 * There there are 2 or more sub-expression children, then the result will have an
 * "ordered" cardinality that contains the flattened and same-ordered X values from
 * all of the input children points.
 * 
 * If any of the child sub-expressions are NULL or contain NULL values, 
 * then the result of "pointX" will be NULL. Child sub-expressions with a
 * cardinality of "record" or of non-point base types are not supported and 
 * will result in a thrown QtiLogicException.
 * 
 * @author Zack Pierce
 *
 */
public class PointX extends CustomOperator<OpenOperatorExtensionPackage> {

    private static final long serialVersionUID = 5898159436362957565L;

    public PointX(ExpressionParent parent) {
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
        return pointX(childValues);
    }
    
    public static Value pointX(final Value[] childValues) throws QtiLogicException {
        if (ValueUtilities.isAnyQtiRecord(childValues)) {
            throw new QtiLogicException("Unsupported record cardinality for a child expression of " + OpenOperatorConstants.OPENOPERATOR_POINTX_CLASS);
        }
        List<Value> flattened = ValueUtilities.flattenToSingles(childValues);
        if (ValueUtilities.isAnyQtiNull(flattened)) {
            return NullValue.INSTANCE;
        }
        int count = flattened.size();
        List<IntegerValue> horizontals = new ArrayList<IntegerValue>(count);
        for (int i = 0; i < count; i++) {
            Value child = flattened.get(i);
            if (!(child instanceof PointValue)) {
                throw new QtiLogicException("Unsupported base type of " + child.getBaseType()
                        + " for a child expression of " + OpenOperatorConstants.OPENOPERATOR_POINTX_CLASS);
            }
            horizontals.add(new IntegerValue(((PointValue) child).horizontalValue()));
        }

        return ValueUtilities.coalesceToMatchingCardinalityUnlessSeveralInputs(childValues, horizontals, OpenOperatorConstants.OPENOPERATOR_POINTX_CLASS);
    }

}
