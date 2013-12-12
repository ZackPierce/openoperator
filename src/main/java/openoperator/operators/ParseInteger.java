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
import uk.ac.ed.ph.jqtiplus.value.IntegerValue;
import uk.ac.ed.ph.jqtiplus.value.NullValue;
import uk.ac.ed.ph.jqtiplus.value.Signature;
import uk.ac.ed.ph.jqtiplus.value.StringValue;
import uk.ac.ed.ph.jqtiplus.value.Value;

/**
 * 
 * Defines the "parseInteger" operator, which interprets an input string
 * into an integer result.
 * 
 * "parseInteger" accepts one sub-expression child, which must have a 
 * base type of "string" and a cardinality of "single". If the input
 * has any other cardinality or base type, this operator
 * will throw a QtiLogicException.
 * 
 * The result will have a base type of "integer" and a cardinality of "single".
 * 
 * The input string must contain only numeric representations that conform to the lexical
 * representation of an integer as defined by "XML Schema Part 2: Datatypes Second Edition",
 * as found at http://www.w3.org/TR/xmlschema-2/#integer-lexical-representation
 * 
 * For example, 0, -2, 2, 002, -002, +2, and +002 are all legal representations.
 * 
 * If the input string is NULL or does not contain a permitted integer representation,
 * then the result will be NULL.  Whitespace in the input string is ignored entirely.
 * 
 * @author Zack Pierce
 *
 */
public class ParseInteger extends CustomOperator<OpenOperatorExtensionPackage> {

    private static final long serialVersionUID = -5390750033145567518L;

    private static final Pattern WHITESPACE_PATTERN = Pattern.compile("\\s+");

    public ParseInteger(ExpressionParent parent) {
        super(parent);
    }

    @Override
    protected void validateThis(ValidationContext context) {
        super.validateThis(context);
        if (getChildren().size() != 1) {
            context.fireValidationWarning(this, OpenOperatorConstants.OPENOPERATOR_CLASS_DISPLAY_NAME_PARSEINTEGER
                    + " should contain one child expression.");
        }
    }

    @Override
    protected Value evaluateSelf(OpenOperatorExtensionPackage jqtiExtensionPackage, ProcessingContext context, Value[] childValues, int depth) {
        return parseInteger(childValues);
    }

    public static Value parseInteger(final Value[] childValues) throws QtiLogicException, NullPointerException {
        if (childValues.length != 1) {
            throw new QtiLogicException(OpenOperatorConstants.OPENOPERATOR_CLASS_DISPLAY_NAME_PARSEINTEGER
                    + " should contain one child expression.");
        }
        final Value target = childValues[0];
        if (target.isNull()) {
            return NullValue.INSTANCE;
        }
        if (!(target.hasSignature(Signature.SINGLE_STRING) && target instanceof StringValue)) {
            throw new QtiLogicException(OpenOperatorConstants.OPENOPERATOR_CLASS_DISPLAY_NAME_PARSEINTEGER
                    + " expects a single string child, but the provided child had a cardinality of "
                    + target.getCardinality().toQtiString());
        }
        final String raw = ((StringValue) target).stringValue();
        if (StringUtilities.isNullOrEmpty(raw)) {
            return NullValue.INSTANCE;
        }
        try {
            return new IntegerValue(DataTypeBinder.parseInteger(WHITESPACE_PATTERN.matcher(raw).replaceAll("")));
        }
        catch (final QtiParseException e) {
            return NullValue.INSTANCE;
        }
    }

}
