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
import uk.ac.ed.ph.jqtiplus.value.StringValue;
import uk.ac.ed.ph.jqtiplus.value.Value;

public class ParseFloatTest {

    @Test
    public void testExampleXml() {
        final String[] examples = new String[] {
                "parseFloat_SingleStringDecimalInterspersedWhitespace_ProducesFloatValue.xml",
                "parseFloat_SingleStringDecimalLeadingWhitespace_ProducesFloatValue.xml",
                "parseFloat_SingleStringDecimalMantissaExplicitPositiveExponent_ProducesFloatValue.xml",
                "parseFloat_SingleStringDecimalMantissaFractionalExponent_ProducesNull.xml",
                "parseFloat_SingleStringDecimalMantissaLowerCaseExponent_ProducesFloatValue.xml",
                "parseFloat_SingleStringDecimalMantissaNegativeExponent_ProducesFloatValue.xml",
                "parseFloat_SingleStringDecimalMantissaNoExponent_ProducesFloatValue.xml",
                "parseFloat_SingleStringDecimalMantissaUpperCaseExponent_ProducesFloatValue.xml",
                "parseFloat_SingleStringDecimalTrailingWhitespace_ProducesFloatValue.xml",
                "parseFloat_SingleStringExplicitNegativeDecimalWithExponent_ProducesFloatValue.xml",
                "parseFloat_SingleStringExplicitPositiveDecimalWithExponent_ProducesFloatValue.xml",
                "parseFloat_SingleStringInf_ProducesFloatValue.xml",
                "parseFloat_SingleStringIntegerMantissaNoExponent_ProducesFloatValue.xml",
                "parseFloat_SingleStringNaN_ProducesNull.xml",
                "parseFloat_SingleStringNegativeInf_ProducesFloatValue.xml",
                "parseFloat_SingleStringNegativeZero_ProducesFloatValue.xml",
                "parseFloat_SingleStringUnrelatedWord_ProducesNull.xml",
                "parseFloat_SingleStringWhitespace_ProducesNull.xml" };
        // "parseFloat_SingleStringEmpty_ProducesNull.xml" is presently excluded due to a quirk of JQTI+ XML loading
        // that makes it impossible to interpret an empty single string from QTI XML.
        for (final String exampleName : examples) {
            Assert.assertTrue(exampleName + " should produce a true value for HANDLED_CORRECTLY",
                    QtiTestHelper.runItemProcessingAndRetrieveHandledCorrectly("openoperator/operator/" + exampleName));
        }
    }

    @Test
    public void testInstantiation() {
        Assert.assertNotNull(new ParseFloat(null));
    }

    @Test(expected = QtiLogicException.class)
    public void testMultipleInputThrowsQtiLogicException() {
        ParseFloat.parseFloat(new Value[] { MultipleValue.createMultipleValue(new StringValue("1")) });
    }

    @Test(expected = QtiLogicException.class)
    public void testOrderedInputThrowsQtiLogicException() {
        ParseFloat.parseFloat(new Value[] { OrderedValue.createOrderedValue(new StringValue("1")) });
    }

    @Test(expected = QtiLogicException.class)
    public void testRecordInputThrowsQtiLogicException() {
        ParseFloat.parseFloat(new Value[] { RecordValue.createRecordValue("a", new StringValue("1")) });
    }

    @Test(expected = QtiLogicException.class)
    public void testSingleNonStringInputThrowsQtiLogicException() {
        ParseFloat.parseFloat(new Value[] { new PointValue(1, 2) });
    }

    @Test
    public void testSingleStringDecimalInterspersedWhitespaceProducesFloatValue() {
        final Value result = ParseFloat.parseFloat(new Value[] { new StringValue("1.5 E 1  ") });
        Assert.assertTrue(result instanceof FloatValue);
        Assert.assertEquals(15.0, ((FloatValue) result).doubleValue(), Double.MIN_VALUE);
    }

    @Test
    public void testSingleStringDecimalLeadingWhitespaceProducesFloatValue() {
        final Value result = ParseFloat.parseFloat(new Value[] { new StringValue("   15.0") });
        Assert.assertTrue(result instanceof FloatValue);
        Assert.assertEquals(15.0, ((FloatValue) result).doubleValue(), Double.MIN_VALUE);
    }

    @Test
    public void testSingleStringDecimalMantissaExplicitPositiveExponent() {
        final Value result = ParseFloat.parseFloat(new Value[] { new StringValue("1.5E+1") });
        Assert.assertTrue(result instanceof FloatValue);
        Assert.assertEquals(15.0, ((FloatValue) result).doubleValue(), Double.MIN_VALUE);
    }

    @Test
    public void testSingleStringDecimalMantissaFractionalExponentProducesNull() {
        final Value result = ParseFloat.parseFloat(new Value[] { new StringValue("1.5E1.5") });
        Assert.assertTrue(result instanceof NullValue);
        Assert.assertTrue(result.isNull());
    }

    @Test
    public void testSingleStringDecimalMantissaLowerCaseExponent() {
        final Value result = ParseFloat.parseFloat(new Value[] { new StringValue("1.5e1") });
        Assert.assertTrue(result instanceof FloatValue);
        Assert.assertEquals(15.0, ((FloatValue) result).doubleValue(), Double.MIN_VALUE);
    }

    @Test
    public void testSingleStringDecimalMantissaNegativeExponent() {
        final Value result = ParseFloat.parseFloat(new Value[] { new StringValue("1.5E-2") });
        Assert.assertTrue(result instanceof FloatValue);
        Assert.assertEquals(1.5 * .01, ((FloatValue) result).doubleValue(), Double.MIN_VALUE);
    }

    @Test
    public void testSingleStringDecimalMantissaNoExponent() {
        final Value result = ParseFloat.parseFloat(new Value[] { new StringValue("1.5") });
        Assert.assertTrue(result instanceof FloatValue);
        Assert.assertEquals(1.5, ((FloatValue) result).doubleValue(), Double.MIN_VALUE);
    }

    @Test
    public void testSingleStringDecimalMantissaUpperCaseExponent() {
        final Value result = ParseFloat.parseFloat(new Value[] { new StringValue("1.5E1") });
        Assert.assertTrue(result instanceof FloatValue);
        Assert.assertEquals(15.0, ((FloatValue) result).doubleValue(), Double.MIN_VALUE);
    }

    @Test
    public void testSingleStringDecimalTrailingWhitespaceProducesFloatValue() {
        final Value result = ParseFloat.parseFloat(new Value[] { new StringValue("15.0  ") });
        Assert.assertTrue(result instanceof FloatValue);
        Assert.assertEquals(15.0, ((FloatValue) result).doubleValue(), Double.MIN_VALUE);
    }

    @Test
    public void testSingleStringEmptyProducesNull() {
        final Value result = ParseFloat.parseFloat(new Value[] { new StringValue("") });
        Assert.assertTrue(result instanceof NullValue);
        Assert.assertTrue(result.isNull());
    }

    @Test
    public void testSingleStringExplicitNegativeDecimalWithExponent() {
        final Value result = ParseFloat.parseFloat(new Value[] { new StringValue("-1.5E1") });
        Assert.assertTrue(result instanceof FloatValue);
        Assert.assertEquals(-15.0, ((FloatValue) result).doubleValue(), Double.MIN_VALUE);
    }

    @Test
    public void testSingleStringExplicitPositiveDecimalMantissaWithExponent() {
        final Value result = ParseFloat.parseFloat(new Value[] { new StringValue("+1.5E1") });
        Assert.assertTrue(result instanceof FloatValue);
        Assert.assertEquals(15.0, ((FloatValue) result).doubleValue(), Double.MIN_VALUE);
    }

    @Test
    public void testSingleStringInfProducesFloatValue() {
        final Value result = ParseFloat.parseFloat(new Value[] { new StringValue("INF") });
        Assert.assertTrue(result instanceof FloatValue);
        Assert.assertEquals(Double.POSITIVE_INFINITY, ((FloatValue) result).doubleValue(), Double.MIN_VALUE);
    }

    @Test
    public void testSingleStringIntegerMantissaNoExponent() {
        final Value result = ParseFloat.parseFloat(new Value[] { new StringValue("1") });
        Assert.assertTrue(result instanceof FloatValue);
        Assert.assertEquals(1.0, ((FloatValue) result).doubleValue(), Double.MIN_VALUE);
    }

    @Test
    public void testSingleStringNaNProducesNull() {
        final Value result = ParseFloat.parseFloat(new Value[] { new StringValue("NaN") });
        Assert.assertTrue(result instanceof NullValue);
        Assert.assertTrue(result.isNull());
    }

    @Test
    public void testSingleStringNegativeInfProducesFloatValue() {
        final Value result = ParseFloat.parseFloat(new Value[] { new StringValue("-INF") });
        Assert.assertTrue(result instanceof FloatValue);
        Assert.assertEquals(Double.NEGATIVE_INFINITY, ((FloatValue) result).doubleValue(), Double.MIN_VALUE);
    }

    @Test
    public void testSingleStringNegativeZeroProducesFloatValue() {
        final Value result = ParseFloat.parseFloat(new Value[] { new StringValue("-0") });
        Assert.assertTrue(result instanceof FloatValue);
        Assert.assertEquals(-0, ((FloatValue) result).doubleValue(), Double.MIN_VALUE);
    }

    @Test
    public void testSingleStringUnrelatedWordProducesNull() {
        final Value result = ParseFloat.parseFloat(new Value[] { new StringValue("one") });
        Assert.assertTrue(result instanceof NullValue);
        Assert.assertTrue(result.isNull());
    }

    @Test
    public void testSingleStringWhitespaceProducesNull() {
        final Value result = ParseFloat.parseFloat(new Value[] { new StringValue("  ") });
        Assert.assertTrue(result instanceof NullValue);
        Assert.assertTrue(result.isNull());
    }

    @Test(expected = NullPointerException.class)
    public void testTrueNullInputThrowsNullPointerException() {
        ParseFloat.parseFloat(null);
    }

    @Test(expected = QtiLogicException.class)
    public void testTwoStringInputThrowsQtiLogicException() {
        ParseFloat.parseFloat(new Value[] { new StringValue("1"), new StringValue("2") });
    }
}
