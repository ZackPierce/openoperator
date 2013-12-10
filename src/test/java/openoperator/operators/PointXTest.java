package openoperator.operators;

import org.junit.Assert;
import org.junit.Test;

import uk.ac.ed.ph.jqtiplus.exception.QtiLogicException;
import uk.ac.ed.ph.jqtiplus.value.IntegerValue;
import uk.ac.ed.ph.jqtiplus.value.MultipleValue;
import uk.ac.ed.ph.jqtiplus.value.NullValue;
import uk.ac.ed.ph.jqtiplus.value.OrderedValue;
import uk.ac.ed.ph.jqtiplus.value.PointValue;
import uk.ac.ed.ph.jqtiplus.value.RecordValue;
import uk.ac.ed.ph.jqtiplus.value.Value;

public class PointXTest {
    @Test(expected = NullPointerException.class)
    public void testNullInputThrowsNullPointerException() {
        PointX.pointX(null);
    }

    @Test(expected = QtiLogicException.class)
    public void testRecordInputThrowsQtiLogicException() {
        PointX.pointX(new Value[] { RecordValue.createRecordValue("a", new PointValue(1, 2)) });
    }

    @Test()
    public void testOneNullInputProducesNull() {
        Value result = PointX.pointX(new Value[] { NullValue.INSTANCE });
        Assert.assertTrue(result instanceof NullValue);
        Assert.assertTrue(result.isNull());
    }

    @Test()
    public void testOneSingleInputProducesSingleInteger() {
        Value result = PointX.pointX(new Value[] { new PointValue(1, 2) });
        Assert.assertTrue(result instanceof IntegerValue);
        Assert.assertEquals(1, ((IntegerValue) result).intValue());
    }

    @Test()
    public void testTwoSingleInputProducesOrderedInteger() {
        Value result = PointX.pointX(new Value[] { new PointValue(1, 2), new PointValue(30, 40) });
        Assert.assertTrue(result instanceof OrderedValue);
        OrderedValue ordered = (OrderedValue) result;
        Assert.assertEquals(1, ((IntegerValue) ordered.get(0)).intValue());
        Assert.assertEquals(30, ((IntegerValue) ordered.get(1)).intValue());
    }

    @Test()
    public void testFiveSingleInputProducesOrderedInteger() {
        Value result = PointX.pointX(new Value[] { new PointValue(1, 2), new PointValue(3, 4), new PointValue(5, 6),
                new PointValue(7, 8), new PointValue(9, 10) });
        Assert.assertTrue(result instanceof OrderedValue);
        OrderedValue ordered = (OrderedValue) result;
        Assert.assertEquals(1, ((IntegerValue) ordered.get(0)).intValue());
        Assert.assertEquals(3, ((IntegerValue) ordered.get(1)).intValue());
        Assert.assertEquals(5, ((IntegerValue) ordered.get(2)).intValue());
        Assert.assertEquals(7, ((IntegerValue) ordered.get(3)).intValue());
        Assert.assertEquals(9, ((IntegerValue) ordered.get(4)).intValue());
    }

    @Test()
    public void testOneMultipleInputProducesMultipleInteger() {
        Value result = PointX.pointX(new Value[] { MultipleValue.createMultipleValue(new PointValue(1, 2)) });
        Assert.assertTrue(result instanceof MultipleValue);
        MultipleValue multiple = (MultipleValue) result;
        Assert.assertEquals(1, ((IntegerValue) multiple.get(0)).intValue());
    }

    @Test()
    public void testTwoMultipleInputProducesOrderedInteger() {
        Value result = PointX.pointX(new Value[] { MultipleValue.createMultipleValue(new PointValue(1, 2)),
                MultipleValue.createMultipleValue(new PointValue(30, 40)) });
        Assert.assertTrue(result instanceof OrderedValue);
        OrderedValue ordered = (OrderedValue) result;
        Assert.assertEquals(1, ((IntegerValue) ordered.get(0)).intValue());
        Assert.assertEquals(30, ((IntegerValue) ordered.get(1)).intValue());
    }

    @Test()
    public void testOneOrderedInputProducesOrderedInteger() {
        Value result = PointX.pointX(new Value[] { OrderedValue.createOrderedValue(new PointValue(1, 2)) });
        Assert.assertTrue(result instanceof OrderedValue);
        OrderedValue ordered = (OrderedValue) result;
        Assert.assertEquals(1, ((IntegerValue) ordered.get(0)).intValue());
    }

    @Test()
    public void testTwoOrderedInputProducesOrderedInteger() {
        Value result = PointX.pointX(new Value[] { OrderedValue.createOrderedValue(new PointValue(1, 2)),
                OrderedValue.createOrderedValue(new PointValue(30, 40)) });
        Assert.assertTrue(result instanceof OrderedValue);
        OrderedValue ordered = (OrderedValue) result;
        Assert.assertEquals(1, ((IntegerValue) ordered.get(0)).intValue());
        Assert.assertEquals(30, ((IntegerValue) ordered.get(1)).intValue());
    }

    @Test()
    public void testMixedWithNullInputProducesNull() {
        Value result = PointX.pointX(new Value[] { NullValue.INSTANCE, new PointValue(1, 2) });
        Assert.assertTrue(result instanceof NullValue);
        Assert.assertTrue(result.isNull());
    }

    @Test()
    public void testMixedInputProducesOrderedInteger() {
        Value result = PointX.pointX(new Value[] { new PointValue(1, 2), OrderedValue.createOrderedValue(new PointValue(3, 4)), 
                MultipleValue.createMultipleValue(new PointValue(5, 6))});
        Assert.assertTrue(result instanceof OrderedValue);
        OrderedValue ordered = (OrderedValue) result;
        Assert.assertEquals(1, ((IntegerValue) ordered.get(0)).intValue());
        Assert.assertEquals(3, ((IntegerValue) ordered.get(1)).intValue());
        Assert.assertEquals(5, ((IntegerValue) ordered.get(2)).intValue());
    }
}
