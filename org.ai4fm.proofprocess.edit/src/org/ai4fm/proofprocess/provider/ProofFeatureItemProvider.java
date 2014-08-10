/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.ai4fm.proofprocess.provider;


import java.util.Collection;
import java.util.List;

import org.ai4fm.proofprocess.ProofFeature;
import org.ai4fm.proofprocess.ProofFeatureDef;
import org.ai4fm.proofprocess.ProofFeatureType;
import org.ai4fm.proofprocess.ProofInfo;
import org.ai4fm.proofprocess.ProofProcessFactory;
import org.ai4fm.proofprocess.ProofProcessPackage;
import org.ai4fm.proofprocess.Term;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.ResourceLocator;

import org.eclipse.emf.ecore.EStructuralFeature;

import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.viewers.ILabelProvider;

/**
 * This is the item provider adapter for a {@link org.ai4fm.proofprocess.ProofFeature} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class ProofFeatureItemProvider
	extends ItemProviderAdapter
	implements
		IEditingDomainItemProvider,
		IStructuredItemContentProvider,
		ITreeItemContentProvider,
		IItemLabelProvider,
		IItemPropertySource {
	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProofFeatureItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	private ILabelProvider labelProvider = null;
	private ComposedAdapterFactory adapterFactory = null;

    private ILabelProvider getLabelProvider() {
    	if (labelProvider == null) {
    		adapterFactory = 
    			new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE);

    		labelProvider = new AdapterFactoryLabelProvider(adapterFactory);
    	}

    	return labelProvider;
    }

	@Override
	public void dispose() {

		if (labelProvider != null) {
			labelProvider.dispose();
			labelProvider = null;
		}
		
		if (adapterFactory != null) {
			adapterFactory.dispose();
			adapterFactory = null;
		}

		super.dispose();
	}

	/**
	 * This returns the property descriptors for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public List<IItemPropertyDescriptor> getPropertyDescriptors(Object object) {
		if (itemPropertyDescriptors == null) {
			super.getPropertyDescriptors(object);

			addNamePropertyDescriptor(object);
			addTypePropertyDescriptor(object);
			addMiscPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Name feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addNamePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_ProofFeature_name_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_ProofFeature_name_feature", "_UI_ProofFeature_type"),
				 ProofProcessPackage.Literals.PROOF_FEATURE__NAME,
				 true,
				 false,
				 true,
				 null,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Type feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addTypePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_ProofFeature_type_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_ProofFeature_type_feature", "_UI_ProofFeature_type"),
				 ProofProcessPackage.Literals.PROOF_FEATURE__TYPE,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Misc feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addMiscPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_ProofFeature_misc_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_ProofFeature_misc_feature", "_UI_ProofFeature_type"),
				 ProofProcessPackage.Literals.PROOF_FEATURE__MISC,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This specifies how to implement {@link #getChildren} and is used to deduce an appropriate feature for an
	 * {@link org.eclipse.emf.edit.command.AddCommand}, {@link org.eclipse.emf.edit.command.RemoveCommand} or
	 * {@link org.eclipse.emf.edit.command.MoveCommand} in {@link #createCommand}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Collection<? extends EStructuralFeature> getChildrenFeatures(Object object) {
		if (childrenFeatures == null) {
			super.getChildrenFeatures(object);
			childrenFeatures.add(ProofProcessPackage.Literals.PROOF_FEATURE__PARAMS);
		}
		return childrenFeatures;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EStructuralFeature getChildFeature(Object object, Object child) {
		// Check the type of the specified child object and return the proper feature to use for
		// adding (see {@link AddCommand}) it as a child.

		return super.getChildFeature(object, child);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public Object getImage(Object object) {
		if (isOutFeature((ProofFeature) object)) {
			return overlayImage(object, getResourceLocator().getImage("full/obj16/ProofFeatureOut"));
		} else {
			return overlayImage(object, getResourceLocator().getImage("full/obj16/ProofFeature"));
		}
	}

	private Boolean isOutFeature(ProofFeature feature) {
		if (feature.eContainer() instanceof ProofInfo) {
			ProofInfo info = (ProofInfo) feature.eContainer();
			if (info.getOutFeatures().contains(feature)) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public String getText(Object object) {
		ProofFeature feature = (ProofFeature) object;

		ProofFeatureDef featureDef = feature.getName();
		String featureDefStr = featureDef != null ? featureDef.getName() : "<?feature>";

		String paramsStr = renderParameters(feature.getParams());

		String misc = feature.getMisc();
		String miscStr = (misc != null && !misc.isEmpty()) ? " - " + misc : "";
		
		return featureDefStr + " (" + paramsStr + ")" + miscStr;
	}

	private String renderParameters(List<Term> params) {
		if (params.isEmpty()) {
			return "";
		} else {
			// render parameters
			ILabelProvider label = getLabelProvider();

			StringBuilder out = new StringBuilder();
			String sep = "";
			for (Term param : params) {
				out.append(sep);
				out.append(label.getText(param));
				sep = ", ";
			}

			return out.toString();
		}
	}

	/**
	 * This handles model notifications by calling {@link #updateChildren} to update any cached
	 * children and by creating a viewer notification, which it passes to {@link #fireNotifyChanged}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public void notifyChanged(Notification notification) {
		updateChildren(notification);

		switch (notification.getFeatureID(ProofFeature.class)) {
			case ProofProcessPackage.PROOF_FEATURE__NAME:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), true, true));
				return;
			case ProofProcessPackage.PROOF_FEATURE__PARAMS:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), true, true));
				return;
			case ProofProcessPackage.PROOF_FEATURE__MISC:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
				return;
		}
		super.notifyChanged(notification);
	}

	/**
	 * This adds {@link org.eclipse.emf.edit.command.CommandParameter}s describing the children
	 * that can be created under this object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object) {
		super.collectNewChildDescriptors(newChildDescriptors, object);

		newChildDescriptors.add
			(createChildParameter
				(ProofProcessPackage.Literals.PROOF_FEATURE__PARAMS,
				 ProofProcessFactory.eINSTANCE.createStringTerm()));
	}

	/**
	 * Return the resource locator for this item provider's resources.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ResourceLocator getResourceLocator() {
		return ProofProcessEditPlugin.INSTANCE;
	}

}
