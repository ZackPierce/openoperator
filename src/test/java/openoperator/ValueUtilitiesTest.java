package openoperator;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import uk.ac.ed.ph.jqtiplus.value.IntegerValue;
import uk.ac.ed.ph.jqtiplus.value.MultipleValue;
import uk.ac.ed.ph.jqtiplus.value.NullValue;
import uk.ac.ed.ph.jqtiplus.value.OrderedValue;
import uk.ac.ed.ph.jqtiplus.value.Value;

public class ValueUtilitiesTest {
    
    @Test(expected=NullPointerException.class)
    public void testFlattenToSinglesNullThrowsException() {
        ValueUtilities.flattenToSingles(null);
    }
    
    @Test
    public void testFlattenToSinglesEmptyProducesEmptyList() {
        Assert.assertNotNull(ValueUtilities.flattenToSingles(new Value[] {}));
    }
    
    @Test
    public void testFlattenToSinglesProducesSameOrderList() {
        List<Value> result = ValueUtilities.flattenToSingles(new Value[] {new IntegerValue(0), new IntegerValue(1), new IntegerValue(2)});
        Assert.assertNotNull(result);
        Assert.assertEquals(0, ((IntegerValue)result.get(0)).intValue());
        Assert.assertEquals(1, ((IntegerValue)result.get(1)).intValue());
        Assert.assertEquals(2, ((IntegerValue)result.get(2)).intValue());
    }
    
    @Test
    public void testFlattenToSinglesMixedCardinalityProducesSameOrderList() {
        List<Value> result = ValueUtilities.flattenToSingles(new Value[] {new IntegerValue(0), OrderedValue.createOrderedValue(new IntegerValue(1), new IntegerValue(2)), MultipleValue.createMultipleValue(new IntegerValue(3), new IntegerValue(4))});
        Assert.assertNotNull(result);
        Assert.assertEquals(0, ((IntegerValue)result.get(0)).intValue());
        Assert.assertEquals(1, ((IntegerValue)result.get(1)).intValue());
        Assert.assertEquals(2, ((IntegerValue)result.get(2)).intValue());
        Assert.assertEquals(3, ((IntegerValue)result.get(3)).intValue());
        Assert.assertEquals(4, ((IntegerValue)result.get(4)).intValue());
    }
    
    @Test
    public void testFlattenToSinglesPreservesNulls() {
        List<Value> result = ValueUtilities.flattenToSingles(new Value[] {NullValue.INSTANCE, new IntegerValue(1), NullValue.INSTANCE});
        Assert.assertNotNull(result);
        Assert.assertTrue(result.get(0).isNull());
        Assert.assertEquals(1, ((IntegerValue)result.get(1)).intValue());
        Assert.assertTrue(result.get(2).isNull());
    }
}
