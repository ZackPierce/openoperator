package openoperator.operators;

import java.util.regex.Pattern;

import openoperator.OpenOperatorConstants;
import openoperator.OpenOperatorExtensionPackage;
import uk.ac.ed.ph.jqtiplus.exception.QtiLogicException;
import uk.ac.ed.ph.jqtiplus.exception.QtiParseException;
import uk.ac.ed.ph.jqtiplus.internal.util.StringUtilities;
import uk.ac.ed.ph.jqtiplus.node.expression.ExpressionParent;
import uk.ac.ed.ph.jqtiplus.node.expression.operator.CustomOperator;
import uk.ac.ed.ph.jqtiplus.running.ProcessingContext;
import uk.ac.ed.ph.jqtiplus.types.DataTypeBinder;
import uk.ac.ed.ph.jqtiplus.validation.ValidationContext;
import uk.ac.ed.ph.jqtiplus.value.FloatValue;
import uk.ac.ed.ph.jqtiplus.value.NullValue;
import uk.ac.ed.ph.jqtiplus.value.Signature;
import uk.ac.ed.ph.jqtiplus.value.StringValue;
import uk.ac.ed.ph.jqtiplus.value.Value;

/**
 * 
 * Defines the "parseFloat" operator, which interprets input strings
 * into float results.
 * 
 * "parseFloat" accepts one sub-expression child, which must have a 
 * base type of "string" and a cardinality of "single". If the input
 * has any other cardinality or base type, this operator
 * will throw a QtiLogicException.
 * 
 * The result will have a base type of "float" and a cardinality of "single".
 * 
 * The input string must contain only numeric representations that conform to the lexical
 * representation of a double as defined by "XML Schema Part 2: Datatypes Second Edition",
 * as found at http://www.w3.org/TR/xmlschema-2/#double-lexical-representation
 * 
 * "For example, -1E4, 1267.43233E12, 12.78e-2, 12 , -0, 0 and INF are all legal literals"
 * 
 * If the input string is NULL, or "NaN", or does not contain a numeric representation,
 * then the result will be NULL.
 * 
 * TODO - clarify whitespace handling (probably just trim and ignore it)
 * 
 * @author Zack Pierce
 *
 */
public class ParseFloat extends CustomOperator<OpenOperatorExtensionPackage> {

    private static final long serialVersionUID = -3119980923736984985L;

    private static final Pattern WHITESPACE_PATTERN = Pattern.compile("\\s+");

    public ParseFloat(ExpressionParent parent) {
        super(parent);
    }

    @Override
    protected void validateThis(ValidationContext context) {
        super.validateThis(context);
        if (getChildren().size() != 1) {
            context.fireValidationWarning(this, OpenOperatorConstants.OPENOPERATOR_CLASS_DISPLAY_NAME_PARSEFLOAT
                    + " should contain one child expression.");
        }
    }

    @Override
    protected Value evaluateSelf(OpenOperatorExtensionPackage jqtiExtensionPackage, ProcessingContext context, Value[] childValues, int depth) {
        return parseFloat(childValues);
    }

    public static Value parseFloat(final Value[] childValues) {
        if (childValues.length != 1) {
            throw new QtiLogicException(OpenOperatorConstants.OPENOPERATOR_CLASS_DISPLAY_NAME_PARSEFLOAT
                    + " should contain one child expression.");
        }
        final Value target = childValues[0];
        if (target.isNull()) {
            return NullValue.INSTANCE;
        }
        if (!(target.hasSignature(Signature.SINGLE_STRING) && target instanceof StringValue)) {
            throw new QtiLogicException(OpenOperatorConstants.OPENOPERATOR_CLASS_DISPLAY_NAME_PARSEFLOAT
                    + " expects a single string child, but the provided child had a cardinality of "
                    + target.getCardinality().toQtiString());
        }
        final String raw = ((StringValue) target).stringValue();
        if (StringUtilities.isNullOrEmpty(raw)) {
            return NullValue.INSTANCE;
        }
        try {
            final double parsed = DataTypeBinder.parseFloat(WHITESPACE_PATTERN.matcher(raw).replaceAll(""));
            if (Double.isNaN(parsed)) {
                return NullValue.INSTANCE;
            }
            return new FloatValue(parsed);
        }
        catch (final QtiParseException e) {
            return NullValue.INSTANCE;
        }
    }

}
