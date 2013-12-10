package openoperator;

import java.net.URI;
import java.util.Date;

import org.junit.Assert;

import uk.ac.ed.ph.jqtiplus.JqtiExtensionManager;
import uk.ac.ed.ph.jqtiplus.internal.util.ObjectUtilities;
import uk.ac.ed.ph.jqtiplus.reading.AssessmentObjectXmlLoader;
import uk.ac.ed.ph.jqtiplus.reading.QtiXmlReader;
import uk.ac.ed.ph.jqtiplus.resolution.ResolvedAssessmentItem;
import uk.ac.ed.ph.jqtiplus.resolution.ResolvedAssessmentObject;
import uk.ac.ed.ph.jqtiplus.running.ItemProcessingInitializer;
import uk.ac.ed.ph.jqtiplus.running.ItemSessionController;
import uk.ac.ed.ph.jqtiplus.running.ItemSessionControllerSettings;
import uk.ac.ed.ph.jqtiplus.state.ItemProcessingMap;
import uk.ac.ed.ph.jqtiplus.state.ItemSessionState;
import uk.ac.ed.ph.jqtiplus.types.Identifier;
import uk.ac.ed.ph.jqtiplus.value.BooleanValue;
import uk.ac.ed.ph.jqtiplus.xmlutils.locators.ClassPathResourceLocator;
import uk.ac.ed.ph.jqtiplus.xmlutils.locators.ResourceLocator;

public class QtiTestHelper {

    public static URI createTestResourceUri(final String testFilePath) {
        return URI.create("classpath:/" + testFilePath);
    }
    
    public static JqtiExtensionManager createJqtiExtensionManager() {
        return new JqtiExtensionManager(new OpenOperatorExtensionPackage());
    }
    
    private static ItemSessionController loadUnitTestAssessmentItemForControl(final String testFilePath, final boolean isValid) {
        final ResolvedAssessmentItem resolvedAssessmentItem = resolveUnitTestAssessmentItem(testFilePath);
        assertSuccessfulResolution(resolvedAssessmentItem);

        final ItemSessionControllerSettings itemSessionControllerSettings = new ItemSessionControllerSettings();
        final ItemProcessingMap itemProcessingMap = new ItemProcessingInitializer(resolvedAssessmentItem, isValid).initialize();
        final ItemSessionState itemSessionState = new ItemSessionState();
        return new ItemSessionController(createJqtiExtensionManager(), itemSessionControllerSettings,
                itemProcessingMap, itemSessionState);
    }
    
    private static ResolvedAssessmentItem resolveUnitTestAssessmentItem(final String testFilePath) {
        final AssessmentObjectXmlLoader assessmentObjectXmlLoader = createUnitTestAssessmentObjectXmlLoader();
        final URI testFileUri = createTestResourceUri(testFilePath);
        return assessmentObjectXmlLoader.loadAndResolveAssessmentItem(testFileUri);
    }
    
    private static AssessmentObjectXmlLoader createUnitTestAssessmentObjectXmlLoader() {
        final QtiXmlReader qtiXmlReader = createUnitTestQtiXmlReader();
        return new AssessmentObjectXmlLoader(qtiXmlReader, createTestFileResourceLocator());
    }
    
    public static QtiXmlReader createUnitTestQtiXmlReader() {
        return new QtiXmlReader(createJqtiExtensionManager());
    }

    public static ResourceLocator createTestFileResourceLocator() {
        return new ClassPathResourceLocator();
    }
    
    private static void assertSuccessfulResolution(final ResolvedAssessmentObject<?> resolvedAssessmentObject) {
        if (!resolvedAssessmentObject.getRootNodeLookup().wasSuccessful()) {
            Assert.fail("Failed to load and resolve unit test resource " + resolvedAssessmentObject.getRootNodeLookup().getSystemId());
        }
    }
    
    public static ItemSessionState runAssessmentItemProcessing(final String itemFilePath) {
        ItemSessionController controller = loadUnitTestAssessmentItemForControl(itemFilePath, true);
        Date initTimestamp = new Date();
        Date entryTimestamp = ObjectUtilities.addToTime(initTimestamp, 2000L);
        Date commitTimestamp = ObjectUtilities.addToTime(initTimestamp, 8000L);
        Date rpTimestamp = ObjectUtilities.addToTime(commitTimestamp, 16000L);
        Date endTimestamp = ObjectUtilities.addToTime(rpTimestamp, 32000L);
        controller.initialize(initTimestamp);
        controller.enterItem(entryTimestamp);
        controller.performResponseProcessing(rpTimestamp);
        controller.endItem(endTimestamp);
        return controller.getItemSessionState();
    }

    public static boolean runItemProcessingAndRetrieveHandledCorrectly(final String itemFilePath) {
        return ((BooleanValue) runAssessmentItemProcessing(itemFilePath).getOutcomeValue(
                Identifier.parseString("HANDLED_CORRECTLY"))).booleanValue();
    }
}
