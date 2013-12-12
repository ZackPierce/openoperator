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
import uk.ac.ed.ph.jqtiplus.value.StringValue;
import uk.ac.ed.ph.jqtiplus.value.Value;

public class ParseIntegerTest {

    @Test
    public void testExampleXml() {
        final String[] examples = new String[] { "parseInteger_NullValueInput_ProducesNull.xml",
                "parseInteger_SingleStringAllZeroesInput_ProducesIntegerValue.xml",
                "parseInteger_SingleStringExplicitNegativeInput_ProducesIntegerValue.xml",
                "parseInteger_SingleStringExplicitNegativeLeadingZeroesInput_ProducesIntegerValue.xml",
                "parseInteger_SingleStringExplicitPositiveInput_ProducesIntegerValue.xml",
                "parseInteger_SingleStringExplicitPositiveLeadingZeroesInput_ProducesIntegerValue.xml",
                "parseInteger_SingleStringImplicitPositiveInput_ProducesIntegerValue.xml",
                "parseInteger_SingleStringImplicitPositiveLeadingZeroesInput_ProducesIntegerValue.xml",
                "parseInteger_SingleStringInterspersedWhitespacePositiveInput_ProducesIntegerValue.xml",
                "parseInteger_SingleStringLeadingWhitespacePositiveInput_ProducesIntegerValue.xml",
                "parseInteger_SingleStringTrailingWhitespacePositiveInput_ProducesIntegerValue.xml",
                "parseInteger_SingleStringZeroInput_ProducesIntegerValue.xml" };
        // "parseInteger_SingleStringWhitespaceInput_ProducesNull.xml" is presently excluded due to a quirk
        // of JQTI+ XML loading that makes it impossible to interpret a whitespace-only or empty baseValue of type
        // string from QTI XML.
        for (final String exampleName : examples) {
            Assert.assertTrue(exampleName + " should produce a true value for HANDLED_CORRECTLY",
                    QtiTestHelper.runItemProcessingAndRetrieveHandledCorrectly("openoperator/operator/" + exampleName));
        }
    }

    @Test
    public void testInstantiation() {
        Assert.assertNotNull(new ParseInteger(null));
    }

    @Test(expected = QtiLogicException.class)
    public void testMultipleInputThrowsQtiLogicException() {
        ParseInteger.parseInteger(new Value[] { MultipleValue.createMultipleValue(new StringValue("1")) });
    }

    @Test(expected = QtiLogicException.class)
    public void testOrderedInputThrowsQtiLogicException() {
        ParseInteger.parseInteger(new Value[] { OrderedValue.createOrderedValue(new StringValue("1")) });
    }

    @Test
    public void testQtiNullInputProducesNull() {
        final Value result = ParseInteger.parseInteger(new Value[] { NullValue.INSTANCE });
        Assert.assertTrue(result instanceof NullValue);
        Assert.assertTrue(result.isNull());
    }

    @Test(expected = QtiLogicException.class)
    public void testRecordInputThrowsQtiLogicException() {
        ParseInteger.parseInteger(new Value[] { RecordValue.createRecordValue("a", new StringValue("1")) });
    }

    @Test(expected = QtiLogicException.class)
    public void testSingleNonStringInputThrowsQtiLogicException() {
        ParseInteger.parseInteger(new Value[] { new PointValue(1, 2) });
    }

    @Test
    public void testSingleStringAllZeroesInputProducesIntegerValue() {
        final Value result = ParseInteger.parseInteger(new Value[] { new StringValue("000") });
        Assert.assertTrue(result instanceof IntegerValue);
        Assert.assertEquals(0, ((IntegerValue) result).intValue());
    }

    @Test
    public void testSingleStringEmptyInputProducesNull() {
        final Value result = ParseInteger.parseInteger(new Value[] { new StringValue("") });
        Assert.assertTrue(result instanceof NullValue);
        Assert.assertTrue(result.isNull());
    }

    @Test
    public void testSingleStringExplicitNegativeInputProducesIntegerValue() {
        final Value result = ParseInteger.parseInteger(new Value[] { new StringValue("-3") });
        Assert.assertTrue(result instanceof IntegerValue);
        Assert.assertEquals(-3, ((IntegerValue) result).intValue());
    }

    @Test
    public void testSingleStringExplicitNegativeLeadingZeroesInputProducesIntegerValue() {
        final Value result = ParseInteger.parseInteger(new Value[] { new StringValue("-003") });
        Assert.assertTrue(result instanceof IntegerValue);
        Assert.assertEquals(-3, ((IntegerValue) result).intValue());
    }

    @Test
    public void testSingleStringExplicitPositiveInputProducesIntegerValue() {
        final Value result = ParseInteger.parseInteger(new Value[] { new StringValue("+3") });
        Assert.assertTrue(result instanceof IntegerValue);
        Assert.assertEquals(3, ((IntegerValue) result).intValue());
    }

    @Test
    public void testSingleStringExplicitPositiveLeadingZeroesInputProducesIntegerValue() {
        final Value result = ParseInteger.parseInteger(new Value[] { new StringValue("+003") });
        Assert.assertTrue(result instanceof IntegerValue);
        Assert.assertEquals(3, ((IntegerValue) result).intValue());
    }

    @Test
    public void testSingleStringImplicitPositiveInputProducesIntegerValue() {
        final Value result = ParseInteger.parseInteger(new Value[] { new StringValue("3") });
        Assert.assertTrue(result instanceof IntegerValue);
        Assert.assertEquals(3, ((IntegerValue) result).intValue());
    }

    @Test
    public void testSingleStringImplicitPositiveLeadingZeroesInputProducesIntegerValue() {
        final Value result = ParseInteger.parseInteger(new Value[] { new StringValue("003") });
        Assert.assertTrue(result instanceof IntegerValue);
        Assert.assertEquals(3, ((IntegerValue) result).intValue());
    }

    @Test
    public void testSingleStringInterspersedWhitespacePositiveInputProducesIntegerValue() {
        final Value result = ParseInteger.parseInteger(new Value[] { new StringValue(" 0 1 2   3") });
        Assert.assertTrue(result instanceof IntegerValue);
        Assert.assertEquals(123, ((IntegerValue) result).intValue());
    }

    @Test
    public void testSingleStringLeadingWhitespacePostiveIntegerInputProducesIntegerValue() {
        final Value result = ParseInteger.parseInteger(new Value[] { new StringValue("  12") });
        Assert.assertTrue(result instanceof IntegerValue);
        Assert.assertEquals(12, ((IntegerValue) result).intValue());
    }

    @Test
    public void testSingleStringTrailingWhitespacePostiveIntegerInputProducesIntegerValue() {
        final Value result = ParseInteger.parseInteger(new Value[] { new StringValue("12  ") });
        Assert.assertTrue(result instanceof IntegerValue);
        Assert.assertEquals(12, ((IntegerValue) result).intValue());
    }

    @Test
    public void testSingleStringWhitespaceInputProducesNull() {
        final Value result = ParseInteger.parseInteger(new Value[] { new StringValue("  ") });
        Assert.assertTrue(result instanceof NullValue);
        Assert.assertTrue(result.isNull());
    }

    @Test
    public void testSingleStringZeroInputProducesIntegerValue() {
        final Value result = ParseInteger.parseInteger(new Value[] { new StringValue("0") });
        Assert.assertTrue(result instanceof IntegerValue);
        Assert.assertEquals(0, ((IntegerValue) result).intValue());
    }

    @Test(expected = NullPointerException.class)
    public void testTrueNullInputThrowsNullPointerException() {
        ParseInteger.parseInteger(null);
    }

    @Test(expected = QtiLogicException.class)
    public void testTwoStringInputThrowsQtiLogicException() {
        ParseInteger.parseInteger(new Value[] { new StringValue("1"), new StringValue("2") });
    }
}
