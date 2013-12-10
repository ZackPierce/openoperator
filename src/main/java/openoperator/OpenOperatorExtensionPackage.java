package openoperator;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import openoperator.operators.Distance;
import openoperator.operators.PointX;
import openoperator.operators.PointY;
import uk.ac.ed.ph.jqtiplus.ExtensionNamespaceInfo;
import uk.ac.ed.ph.jqtiplus.JqtiExtensionPackage;
import uk.ac.ed.ph.jqtiplus.JqtiLifecycleEventType;
import uk.ac.ed.ph.jqtiplus.internal.util.ObjectUtilities;
import uk.ac.ed.ph.jqtiplus.node.QtiNode;
import uk.ac.ed.ph.jqtiplus.node.expression.ExpressionParent;
import uk.ac.ed.ph.jqtiplus.node.expression.operator.CustomOperator;
import uk.ac.ed.ph.jqtiplus.node.item.interaction.CustomInteraction;

public class OpenOperatorExtensionPackage implements JqtiExtensionPackage<OpenOperatorExtensionPackage> {

    public static final String DISPLAY_NAME = "OpenOperator QTI Extension";
    private final Map<String, ExtensionNamespaceInfo> namespaceInfoMap;
    private final Set<String> customOperatorClasses;
    
    public OpenOperatorExtensionPackage() {
        final ExtensionNamespaceInfo extensionNamespaceInfo = new ExtensionNamespaceInfo(OpenOperatorConstants.OPENOPERATOR_NAMESPACE_URI, OpenOperatorConstants.OPENOPERATOR_SCHEMA_LOCATION, OpenOperatorConstants.OPENOPERATOR_DEFAULT_NAMESPACE_PREFIX);
        final Map<String, ExtensionNamespaceInfo> namespaceInfoMapSource = new HashMap<String, ExtensionNamespaceInfo>();
        namespaceInfoMapSource.put(extensionNamespaceInfo.getNamespaceUri(), extensionNamespaceInfo);
        this.namespaceInfoMap = ObjectUtilities.unmodifiableMap(namespaceInfoMapSource);
        this.customOperatorClasses = Collections.unmodifiableSet(new HashSet<String>(Arrays.asList(
                OpenOperatorConstants.OPENOPERATOR_DISTANCE_CLASS,
                OpenOperatorConstants.OPENOPERATOR_POINTX_CLASS,
                OpenOperatorConstants.OPENOPERATOR_POINTY_CLASS
        )));
    }

    public void lifecycleEvent(Object source, JqtiLifecycleEventType eventType) {
        return;
    }

    public String getDisplayName() {
        return DISPLAY_NAME;
    }

    public Map<String, ExtensionNamespaceInfo> getNamespaceInfoMap() {
        return namespaceInfoMap;
    }

    public boolean implementsCustomOperator(String operatorClassName) {
        return customOperatorClasses.contains(operatorClassName);
    }

    public boolean implementsCustomInteraction(String interactionClassName) {
        return false;
    }

    public CustomOperator<OpenOperatorExtensionPackage> createCustomOperator(ExpressionParent expressionParent, String operatorClassName) {
        if (OpenOperatorConstants.OPENOPERATOR_DISTANCE_CLASS.equals(operatorClassName)) {
            return new Distance(expressionParent);
        } else if (OpenOperatorConstants.OPENOPERATOR_POINTX_CLASS.equals(operatorClassName)) {
            return new PointX(expressionParent);
        }
        else if (OpenOperatorConstants.OPENOPERATOR_POINTY_CLASS.equals(operatorClassName)) {
            return new PointY(expressionParent);
        }
        return null;
    }

    public CustomInteraction<OpenOperatorExtensionPackage> createCustomInteraction(QtiNode parentObject, String interactionClassName) {
        return null;
    }

}
