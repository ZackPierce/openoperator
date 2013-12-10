package openoperator.operators;

import openoperator.QtiTestHelper;

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

    @Test
    public void testInstantiation() {
        Assert.assertNotNull(new PointX(null));
    }

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
        final Value result = PointX.pointX(new Value[] { NullValue.INSTANCE });
        Assert.assertTrue(result instanceof NullValue);
        Assert.assertTrue(result.isNull());
    }

    @Test()
    public void testOneSingleInputProducesSingleInteger() {
        final Value result = PointX.pointX(new Value[] { new PointValue(1, 2) });
        Assert.assertTrue(result instanceof IntegerValue);
        Assert.assertEquals(1, ((IntegerValue) result).intValue());
    }

    @Test()
    public void testTwoSingleInputProducesOrderedInteger() {
        final Value result = PointX.pointX(new Value[] { new PointValue(1, 2), new PointValue(30, 40) });
        Assert.assertTrue(result instanceof OrderedValue);
        final OrderedValue ordered = (OrderedValue) result;
        Assert.assertEquals(1, ((IntegerValue) ordered.get(0)).intValue());
        Assert.assertEquals(30, ((IntegerValue) ordered.get(1)).intValue());
    }

    @Test()
    public void testFiveSingleInputProducesOrderedInteger() {
        final Value result = PointX.pointX(new Value[] { new PointValue(1, 2), new PointValue(3, 4),
                new PointValue(5, 6), new PointValue(7, 8), new PointValue(9, 10) });
        Assert.assertTrue(result instanceof OrderedValue);
        final OrderedValue ordered = (OrderedValue) result;
        Assert.assertEquals(1, ((IntegerValue) ordered.get(0)).intValue());
        Assert.assertEquals(3, ((IntegerValue) ordered.get(1)).intValue());
        Assert.assertEquals(5, ((IntegerValue) ordered.get(2)).intValue());
        Assert.assertEquals(7, ((IntegerValue) ordered.get(3)).intValue());
        Assert.assertEquals(9, ((IntegerValue) ordered.get(4)).intValue());
    }

    @Test()
    public void testOneMultipleInputProducesMultipleInteger() {
        final Value result = PointX.pointX(new Value[] { MultipleValue.createMultipleValue(new PointValue(1, 2)) });
        Assert.assertTrue(result instanceof MultipleValue);
        final MultipleValue multiple = (MultipleValue) result;
        Assert.assertEquals(1, ((IntegerValue) multiple.get(0)).intValue());
    }

    @Test()
    public void testTwoMultipleInputProducesOrderedInteger() {
        final Value result = PointX.pointX(new Value[] { MultipleValue.createMultipleValue(new PointValue(1, 2)),
                MultipleValue.createMultipleValue(new PointValue(30, 40)) });
        Assert.assertTrue(result instanceof OrderedValue);
        final OrderedValue ordered = (OrderedValue) result;
        Assert.assertEquals(1, ((IntegerValue) ordered.get(0)).intValue());
        Assert.assertEquals(30, ((IntegerValue) ordered.get(1)).intValue());
    }

    @Test()
    public void testOneOrderedInputProducesOrderedInteger() {
        final Value result = PointX.pointX(new Value[] { OrderedValue.createOrderedValue(new PointValue(1, 2)) });
        Assert.assertTrue(result instanceof OrderedValue);
        final OrderedValue ordered = (OrderedValue) result;
        Assert.assertEquals(1, ((IntegerValue) ordered.get(0)).intValue());
    }

    @Test()
    public void testTwoOrderedInputProducesOrderedInteger() {
        final Value result = PointX.pointX(new Value[] { OrderedValue.createOrderedValue(new PointValue(1, 2)),
                OrderedValue.createOrderedValue(new PointValue(30, 40)) });
        Assert.assertTrue(result instanceof OrderedValue);
        final OrderedValue ordered = (OrderedValue) result;
        Assert.assertEquals(1, ((IntegerValue) ordered.get(0)).intValue());
        Assert.assertEquals(30, ((IntegerValue) ordered.get(1)).intValue());
    }

    @Test()
    public void testMixedWithNullInputProducesNull() {
        final Value result = PointX.pointX(new Value[] { NullValue.INSTANCE, new PointValue(1, 2) });
        Assert.assertTrue(result instanceof NullValue);
        Assert.assertTrue(result.isNull());
    }

    @Test()
    public void testMixedInputProducesOrderedInteger() {
        final Value result = PointX.pointX(new Value[] { new PointValue(1, 2),
                OrderedValue.createOrderedValue(new PointValue(3, 4)),
                MultipleValue.createMultipleValue(new PointValue(5, 6)) });
        Assert.assertTrue(result instanceof OrderedValue);
        final OrderedValue ordered = (OrderedValue) result;
        Assert.assertEquals(1, ((IntegerValue) ordered.get(0)).intValue());
        Assert.assertEquals(3, ((IntegerValue) ordered.get(1)).intValue());
        Assert.assertEquals(5, ((IntegerValue) ordered.get(2)).intValue());
    }

    @Test
    public void testExampleXml() {
        final String[] examples = new String[] { "pointX_FiveSingleInput_ProducesOrderedInteger.xml",
                "pointX_MixedIncludingANullInput_ProducesNull.xml", "pointX_MixedInput_ProducesOrderedInteger.xml",
                "pointX_OneMultipleInput_ProducesMultipleInteger.xml", "pointX_OneNullInput_ProducesNull.xml",
                "pointX_OneOrderedInput_ProducesOrderedInteger.xml", "pointX_OneSingleInput_ProducesSingleInteger.xml",
                "pointX_TwoMultipleInput_ProducesOrderedInteger.xml",
                "pointX_TwoOrderedInput_ProducesOrderedInteger.xml", "pointX_TwoSingleInput_ProducesOrderedInteger.xml" };
        for (final String exampleName : examples) {
            Assert.assertTrue(exampleName + " should produce a true value for HANDLED_CORRECTLY",
                    QtiTestHelper.runItemProcessingAndRetrieveHandledCorrectly("openoperator/operator/" + exampleName));
        }
    }
}
