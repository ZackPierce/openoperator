package openoperator.operators;

import openoperator.QtiTestHelper;

import org.junit.Assert;
import org.junit.Test;

import uk.ac.ed.ph.jqtiplus.exception.QtiLogicException;
import uk.ac.ed.ph.jqtiplus.value.FloatValue;
import uk.ac.ed.ph.jqtiplus.value.MultipleValue;
import uk.ac.ed.ph.jqtiplus.value.NullValue;
import uk.ac.ed.ph.jqtiplus.value.OrderedValue;
import uk.ac.ed.ph.jqtiplus.value.PointValue;
import uk.ac.ed.ph.jqtiplus.value.RecordValue;
import uk.ac.ed.ph.jqtiplus.value.Value;

public class DistanceTest {

    @Test
    public void testInstantiation() {
        Assert.assertNotNull(new Distance(null));
    }

    @Test(expected = NullPointerException.class)
    public void testTrueNullInputThrowsNullPointerException() {
        Distance.distance(null);
    }

    @Test(expected = QtiLogicException.class)
    public void testRecordInputThrowsQtiLogicException() {
        Distance.distance(new Value[] { RecordValue.createRecordValue("a", new PointValue(1, 2)) });
    }

    @Test
    public void testOneQtiNullInputProducesZeroValueFloat() {
        final Value result = Distance.distance(new Value[] { NullValue.INSTANCE });
        Assert.assertNotNull(result);
        Assert.assertTrue(result instanceof FloatValue);
        Assert.assertEquals(0.0, ((FloatValue) result).doubleValue(), Double.MIN_VALUE);
    }

    @Test
    public void testOneSingleInputProducesZeroValueFloat() {
        final Value result = Distance.distance(new Value[] { new PointValue(1, 2) });
        Assert.assertNotNull(result);
        Assert.assertTrue(result instanceof FloatValue);
        Assert.assertEquals(0.0, ((FloatValue) result).doubleValue(), Double.MIN_VALUE);
    }

    @Test
    public void testTwoSingleInputProducesFloat() {
        final Value result = Distance.distance(new Value[] { new PointValue(0, 0), new PointValue(0, 2) });
        Assert.assertNotNull(result);
        Assert.assertTrue(result instanceof FloatValue);
        Assert.assertEquals(2.0, ((FloatValue) result).doubleValue(), Double.MIN_VALUE);
    }

    @Test
    public void testFiveSingleInputProducesFloat() {
        final Value result = Distance.distance(new Value[] { new PointValue(0, 0), new PointValue(1, 1),
                new PointValue(2, 2), new PointValue(3, 3), new PointValue(4, 4) });
        Assert.assertNotNull(result);
        Assert.assertTrue(result instanceof FloatValue);
        Assert.assertEquals(4.0 * Math.sqrt(2.0), ((FloatValue) result).doubleValue(), Double.MIN_VALUE);
    }

    @Test
    public void testEmptyOrderedInputProducesZeroValueFloat() {
        final Value result = Distance.distance(new Value[] { OrderedValue.createOrderedValue() });
        Assert.assertNotNull(result);
        Assert.assertTrue(result instanceof FloatValue);
        Assert.assertEquals(0.0, ((FloatValue) result).doubleValue(), Double.MIN_VALUE);
    }

    @Test
    public void testOrderedInputProducesFloat() {
        final Value result = Distance.distance(new Value[] { OrderedValue.createOrderedValue(new PointValue(0, 0),
                new PointValue(0, 2), new PointValue(2, 2)) });
        Assert.assertNotNull(result);
        Assert.assertTrue(result instanceof FloatValue);
        Assert.assertEquals(4.0, ((FloatValue) result).doubleValue(), Double.MIN_VALUE);
    }

    @Test
    public void testEmptyMultipleInputProducesZeroValueFloat() {
        final Value result = Distance.distance(new Value[] { MultipleValue.createMultipleValue() });
        Assert.assertNotNull(result);
        Assert.assertTrue(result instanceof FloatValue);
        Assert.assertEquals(0.0, ((FloatValue) result).doubleValue(), Double.MIN_VALUE);
    }

    @Test
    public void testMultipleInputProducesFloat() {
        final Value result = Distance.distance(new Value[] { MultipleValue.createMultipleValue(new PointValue(0, 0),
                new PointValue(0, 2), new PointValue(2, 2)) });
        Assert.assertNotNull(result);
        Assert.assertTrue(result instanceof FloatValue);
        Assert.assertEquals(4.0, ((FloatValue) result).doubleValue(), Double.MIN_VALUE);
    }

    @Test
    public void testMixedInputProducesFloat() {
        final Value result = Distance.distance(new Value[] { new PointValue(0, 0),
                MultipleValue.createMultipleValue(new PointValue(0, 2), new PointValue(2, 2)),
                OrderedValue.createOrderedValue(new PointValue(2, 4)) });
        Assert.assertNotNull(result);
        Assert.assertTrue(result instanceof FloatValue);
        Assert.assertEquals(6.0, ((FloatValue) result).doubleValue(), Double.MIN_VALUE);
    }

    @Test
    public void testMixedWithNullsInputProducesFloat() {
        final Value result = Distance.distance(new Value[] { new PointValue(0, 0), NullValue.INSTANCE,
                MultipleValue.createMultipleValue(new PointValue(0, 2), new PointValue(2, 2)),
                OrderedValue.createOrderedValue(new PointValue(2, 4)) });
        Assert.assertNotNull(result);
        Assert.assertTrue(result instanceof FloatValue);
        Assert.assertEquals(6.0, ((FloatValue) result).doubleValue(), Double.MIN_VALUE);
    }

    @Test
    public void testExampleXml() {
        final String[] examples = new String[] { "distance_EmptyMultipleInput_ProducesZeroValueFloat.xml",
                "distance_EmptyOrderedInput_ProducesZeroValueFloat.xml", "distance_FiveSingleInput_ProducesFloat.xml",
                "distance_FullMultipleInput_ProducesFloat.xml", "distance_FullOrderedInput_ProducesFloat.xml",
                "distance_MixedInput_ProducesFloat.xml", "distance_MixedWithNullInput_ProducesFloat.xml",
                "distance_OneNullInput_ProducesZeroValueFloat.xml",
                "distance_OneSingleInput_ProducesZeroValueFloat.xml", "distance_TwoSingleInput_ProducesFloat.xml" };
        for (final String exampleName : examples) {
            Assert.assertTrue(exampleName + " should produce a true value for HANDLED_CORRECTLY",
                    QtiTestHelper.runItemProcessingAndRetrieveHandledCorrectly("openoperator/operator/" + exampleName));
        }
    }
}
