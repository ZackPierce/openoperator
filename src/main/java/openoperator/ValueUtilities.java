package openoperator;

import java.util.ArrayList;
import java.util.List;

import uk.ac.ed.ph.jqtiplus.exception.QtiLogicException;
import uk.ac.ed.ph.jqtiplus.value.Cardinality;
import uk.ac.ed.ph.jqtiplus.value.MultipleValue;
import uk.ac.ed.ph.jqtiplus.value.NullValue;
import uk.ac.ed.ph.jqtiplus.value.OrderedValue;
import uk.ac.ed.ph.jqtiplus.value.SingleValue;
import uk.ac.ed.ph.jqtiplus.value.Value;

/**
 * Static utility functions for dealing with the quirky needs of QTI Values
 * 
 * @author Zack Pierce
 *
 */
public final class ValueUtilities {

    /**
     * Produces a single list of single-cardinality values
     * from single, multiple, or ordered-cardinality inputs.
     * 
     * Record inputs are interpreted as if they were null.
     * 
     * Retains any null values. 
     * 
     * @param Value
     * @return
     */
    public static List<Value> flattenToSingles(final Value[] values) {
        final List<Value> flattenedChildren = new ArrayList<Value>();
        for (final Value childValue : values) {
            if (childValue.isNull()) {
                flattenedChildren.add(NullValue.INSTANCE);
            }
            else {
                Cardinality cardinality = childValue.getCardinality();
                if (cardinality == Cardinality.SINGLE) {
                    flattenedChildren.add(childValue);
                }
                else if ((cardinality == Cardinality.MULTIPLE || cardinality == Cardinality.ORDERED)
                        && childValue instanceof Iterable<?>) {
                    final Iterable<?> childIterable = (Iterable<?>) childValue;
                    for (final Object grandChildValue : childIterable) {
                        if (grandChildValue instanceof SingleValue) {
                            flattenedChildren.add((SingleValue) grandChildValue);
                        }
                        else {
                            flattenedChildren.add(NullValue.INSTANCE);
                        }
                    }
                }
                else if (cardinality == Cardinality.RECORD) {
                    flattenedChildren.add(NullValue.INSTANCE);
                }
                else {
                    flattenedChildren.add(NullValue.INSTANCE);
                }
            }
        }
        return flattenedChildren;
    }

    public static boolean isAnyQtiNull(final Iterable<Value> values) {
        for (final Value value : values) {
            if (value.isNull()) {
                return true;
            }
        }
        return false;
    }

    public static boolean isAnyQtiRecord(final Value[] values) {
        for (final Value value : values) {
            if (value.hasCardinality(Cardinality.RECORD)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 
     * If there is only one member of the inputValue, then the returned value will have the 
     * same cardinality and base type as that input.
     * 
     * There were zero or many members of the inputValues, then the returned value will be
     * of the "ordered" cardinality.
     * 
     * Throws a QtiLogicException if the sole inputValues member is a record.
     * 
     * @param inputValues
     * @param resultValues
     * @param displayName
     * @return
     */
    public static Value coalesceToMatchingCardinalityUnlessSeveralInputs(Value[] inputValues, List<? extends SingleValue> resultValues, String displayName) {
        if (resultValues.isEmpty()) {
            return NullValue.INSTANCE;
        }
        if (inputValues.length == 1) {
            Value first = inputValues[0];
            switch (first.getCardinality()) {
            case SINGLE:
                return resultValues.get(0);
            case MULTIPLE:
                return MultipleValue.createMultipleValue(resultValues);
            case ORDERED:
                return OrderedValue.createOrderedValue(resultValues);
            case RECORD:
                throw new QtiLogicException("Unsupported record cardinality for a result from " + displayName);
            default:
                throw new QtiLogicException("Unsupported missing cardinality for a result from " + displayName);
            }
        }
        else {
            return OrderedValue.createOrderedValue(resultValues);
        }
    }
}
