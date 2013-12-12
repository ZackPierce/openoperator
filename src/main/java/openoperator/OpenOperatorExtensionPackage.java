package openoperator;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import openoperator.operators.Distance;
import openoperator.operators.ParseFloat;
import openoperator.operators.ParseInteger;
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
        final ExtensionNamespaceInfo extensionNamespaceInfo = new ExtensionNamespaceInfo(
                OpenOperatorConstants.OPENOPERATOR_NAMESPACE_URI, OpenOperatorConstants.OPENOPERATOR_SCHEMA_LOCATION,
                OpenOperatorConstants.OPENOPERATOR_DEFAULT_NAMESPACE_PREFIX);
        final Map<String, ExtensionNamespaceInfo> namespaceInfoMapSource = new HashMap<String, ExtensionNamespaceInfo>();
        namespaceInfoMapSource.put(extensionNamespaceInfo.getNamespaceUri(), extensionNamespaceInfo);
        this.namespaceInfoMap = ObjectUtilities.unmodifiableMap(namespaceInfoMapSource);
        this.customOperatorClasses = Collections.unmodifiableSet(new HashSet<String>(Arrays.asList(
                OpenOperatorConstants.OPENOPERATOR_CLASS_DISPLAY_NAME_DISTANCE,
                OpenOperatorConstants.OPENOPERATOR_CLASS_DISPLAY_NAME_PARSEFLOAT,
                OpenOperatorConstants.OPENOPERATOR_CLASS_DISPLAY_NAME_PARSEINTEGER,
                OpenOperatorConstants.OPENOPERATOR_CLASS_DISPLAY_NAME_POINTX,
                OpenOperatorConstants.OPENOPERATOR_CLASS_DISPLAY_NAME_POINTY)));
    }

    @Override
    public void lifecycleEvent(Object source, JqtiLifecycleEventType eventType) {
        return;
    }

    @Override
    public String getDisplayName() {
        return DISPLAY_NAME;
    }

    @Override
    public Map<String, ExtensionNamespaceInfo> getNamespaceInfoMap() {
        return namespaceInfoMap;
    }

    @Override
    public boolean implementsCustomOperator(String operatorClassName) {
        return customOperatorClasses.contains(operatorClassName);
    }

    @Override
    public boolean implementsCustomInteraction(String interactionClassName) {
        return false;
    }

    @Override
    public CustomOperator<OpenOperatorExtensionPackage> createCustomOperator(ExpressionParent expressionParent, String operatorClassName) {
        if (OpenOperatorConstants.OPENOPERATOR_CLASS_DISPLAY_NAME_DISTANCE.equals(operatorClassName)) {
            return new Distance(expressionParent);
        }
        else if (OpenOperatorConstants.OPENOPERATOR_CLASS_DISPLAY_NAME_PARSEFLOAT.equals(operatorClassName)) {
            return new ParseFloat(expressionParent);
        }
        else if (OpenOperatorConstants.OPENOPERATOR_CLASS_DISPLAY_NAME_PARSEINTEGER.equals(operatorClassName)) {
            return new ParseInteger(expressionParent);
        }
        else if (OpenOperatorConstants.OPENOPERATOR_CLASS_DISPLAY_NAME_POINTX.equals(operatorClassName)) {
            return new PointX(expressionParent);
        }
        else if (OpenOperatorConstants.OPENOPERATOR_CLASS_DISPLAY_NAME_POINTY.equals(operatorClassName)) {
            return new PointY(expressionParent);
        }
        return null;
    }

    @Override
    public CustomInteraction<OpenOperatorExtensionPackage> createCustomInteraction(QtiNode parentObject, String interactionClassName) {
        return null;
    }

}
