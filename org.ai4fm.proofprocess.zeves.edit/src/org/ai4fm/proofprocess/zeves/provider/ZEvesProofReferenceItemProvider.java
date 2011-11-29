/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.ai4fm.proofprocess.zeves.provider;


import java.util.Collection;
import java.util.List;

import org.ai4fm.proofprocess.zeves.Position;
import org.ai4fm.proofprocess.zeves.ZEvesProofProcessPackage;
import org.ai4fm.proofprocess.zeves.ZEvesProofReference;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.ResourceLocator;

import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.emf.edit.provider.ViewerNotification;

/**
 * This is the item provider adapter for a {@link org.ai4fm.proofprocess.zeves.ZEvesProofReference} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class ZEvesProofReferenceItemProvider
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
	public ZEvesProofReferenceItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
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

			addFilePathPropertyDescriptor(object);
			addPositionPropertyDescriptor(object);
			addMarkupPropertyDescriptor(object);
			addGoalPropertyDescriptor(object);
			addUsedLemmasPropertyDescriptor(object);
			addTextPropertyDescriptor(object);
			addCasePropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the File Path feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addFilePathPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_ZEvesProofReference_filePath_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_ZEvesProofReference_filePath_feature", "_UI_ZEvesProofReference_type"),
				 ZEvesProofProcessPackage.Literals.ZEVES_PROOF_REFERENCE__FILE_PATH,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Position feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addPositionPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_ZEvesProofReference_position_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_ZEvesProofReference_position_feature", "_UI_ZEvesProofReference_type"),
				 ZEvesProofProcessPackage.Literals.ZEVES_PROOF_REFERENCE__POSITION,
				 true,
				 false,
				 true,
				 null,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Markup feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addMarkupPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_ZEvesProofReference_markup_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_ZEvesProofReference_markup_feature", "_UI_ZEvesProofReference_type"),
				 ZEvesProofProcessPackage.Literals.ZEVES_PROOF_REFERENCE__MARKUP,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Goal feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addGoalPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_ZEvesProofReference_goal_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_ZEvesProofReference_goal_feature", "_UI_ZEvesProofReference_type"),
				 ZEvesProofProcessPackage.Literals.ZEVES_PROOF_REFERENCE__GOAL,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Used Lemmas feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addUsedLemmasPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_ZEvesProofReference_usedLemmas_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_ZEvesProofReference_usedLemmas_feature", "_UI_ZEvesProofReference_type"),
				 ZEvesProofProcessPackage.Literals.ZEVES_PROOF_REFERENCE__USED_LEMMAS,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Text feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addTextPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_ZEvesProofReference_text_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_ZEvesProofReference_text_feature", "_UI_ZEvesProofReference_type"),
				 ZEvesProofProcessPackage.Literals.ZEVES_PROOF_REFERENCE__TEXT,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Case feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addCasePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_ZEvesProofReference_case_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_ZEvesProofReference_case_feature", "_UI_ZEvesProofReference_type"),
				 ZEvesProofProcessPackage.Literals.ZEVES_PROOF_REFERENCE__CASE,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This returns ZEvesProofReference.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/ZEvesProofReference"));
	}

	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public String getText(Object object) {
		String label = ((ZEvesProofReference)object).getFilePath();
		String labelText = label != null ? label : "";
		
		Position pos = ((ZEvesProofReference)object).getPosition();
		String posText = pos != null ? "[" + pos.getOffset() + ":" + pos.getLength() + "] @ " : "";
		
		return posText + labelText;
		
//		return label == null || label.length() == 0 ?
//			getString("_UI_ZEvesProofReference_type") :
//			getString("_UI_ZEvesProofReference_type") + " " + label;
	}

	/**
	 * This handles model notifications by calling {@link #updateChildren} to update any cached
	 * children and by creating a viewer notification, which it passes to {@link #fireNotifyChanged}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void notifyChanged(Notification notification) {
		updateChildren(notification);

		switch (notification.getFeatureID(ZEvesProofReference.class)) {
			case ZEvesProofProcessPackage.ZEVES_PROOF_REFERENCE__FILE_PATH:
			case ZEvesProofProcessPackage.ZEVES_PROOF_REFERENCE__MARKUP:
			case ZEvesProofProcessPackage.ZEVES_PROOF_REFERENCE__GOAL:
			case ZEvesProofProcessPackage.ZEVES_PROOF_REFERENCE__USED_LEMMAS:
			case ZEvesProofProcessPackage.ZEVES_PROOF_REFERENCE__TEXT:
			case ZEvesProofProcessPackage.ZEVES_PROOF_REFERENCE__CASE:
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
	}

	/**
	 * Return the resource locator for this item provider's resources.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ResourceLocator getResourceLocator() {
		return ZEvesProofProcessEditPlugin.INSTANCE;
	}

}
