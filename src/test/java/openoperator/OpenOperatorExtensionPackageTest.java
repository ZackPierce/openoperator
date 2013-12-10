package openoperator;

import openoperator.operators.Distance;
import openoperator.operators.PointX;
import openoperator.operators.PointY;

import org.junit.Assert;
import org.junit.Test;

public class OpenOperatorExtensionPackageTest {

    @Test
    public void testInitialization() {
        try {
            OpenOperatorExtensionPackage ooep = new OpenOperatorExtensionPackage();
            Assert.assertNotNull(ooep);
        }
        catch (Exception e) {
            Assert.fail("OpenOperatorExtensionPackage constructor should not throw any expections");
        }

    }

    @Test
    public void testReportsImplementsPointX() {
        Assert.assertTrue(new OpenOperatorExtensionPackage()
                .implementsCustomOperator(OpenOperatorConstants.OPENOPERATOR_POINTX_CLASS));
    }

    @Test
    public void testReportsImplementsPointY() {
        Assert.assertTrue(new OpenOperatorExtensionPackage()
                .implementsCustomOperator(OpenOperatorConstants.OPENOPERATOR_POINTY_CLASS));
    }

    @Test
    public void testReportsImplementsDistance() {
        Assert.assertTrue(new OpenOperatorExtensionPackage()
                .implementsCustomOperator(OpenOperatorConstants.OPENOPERATOR_DISTANCE_CLASS));
    }

    @Test
    public void testReportsDoesNotImplementsUnrelatedOperator() {
        Assert.assertFalse(new OpenOperatorExtensionPackage()
                .implementsCustomOperator("org.qtitools.mathassess.CasCompare"));
    }

    @Test
    public void testCreatesPointX() {
        Assert.assertTrue(new OpenOperatorExtensionPackage().createCustomOperator(null,
                OpenOperatorConstants.OPENOPERATOR_POINTX_CLASS) instanceof PointX);
    }

    @Test
    public void testCreatesPointY() {
        Assert.assertTrue(new OpenOperatorExtensionPackage().createCustomOperator(null,
                OpenOperatorConstants.OPENOPERATOR_POINTY_CLASS) instanceof PointY);
    }

    @Test
    public void testCreatesDistance() {
        Assert.assertTrue(new OpenOperatorExtensionPackage().createCustomOperator(null,
                OpenOperatorConstants.OPENOPERATOR_DISTANCE_CLASS) instanceof Distance);
    }

    @Test
    public void testReportsDoesNotCreateUnrelatedOperator() {
        Assert.assertNull(new OpenOperatorExtensionPackage().createCustomOperator(null,
                "org.qtitools.mathassess.CasCompare"));
    }
}
