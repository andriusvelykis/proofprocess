/**
 */
package org.ai4fm.proofprocess.isabelle.provider;


import java.util.Collection;
import java.util.List;

import org.ai4fm.proofprocess.Term;
import org.ai4fm.proofprocess.isabelle.AssumptionTerm;
import org.ai4fm.proofprocess.isabelle.DisplayTerm;
import org.ai4fm.proofprocess.isabelle.IsabelleProofProcessFactory;
import org.ai4fm.proofprocess.isabelle.IsabelleProofProcessPackage;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.ResourceLocator;

import org.eclipse.emf.ecore.EStructuralFeature;

import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.emf.edit.provider.ViewerNotification;

/**
 * This is the item provider adapter for a {@link org.ai4fm.proofprocess.isabelle.AssumptionTerm} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class AssumptionTermItemProvider
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
	public AssumptionTermItemProvider(AdapterFactory adapterFactory) {
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

		}
		return itemPropertyDescriptors;
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
			childrenFeatures.add(IsabelleProofProcessPackage.Literals.ASSUMPTION_TERM__TERM);
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
	 * This returns AssumptionTerm.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/AssumptionTerm"));
	}

	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public String getText(Object object) {
		AssumptionTerm term = (AssumptionTerm) object;
		return "Assumption: " + getTermText(term.getTerm());
	}

	private String getTermText(Term term) {
		if (term == null) {
			return String.valueOf(term);
		} else if (term instanceof DisplayTerm) {
			return ((DisplayTerm) term).getDisplay();
		} else {
			return term.getClass().getSimpleName();
		}
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

		switch (notification.getFeatureID(AssumptionTerm.class)) {
			case IsabelleProofProcessPackage.ASSUMPTION_TERM__TERM:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), true, false));
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
				(IsabelleProofProcessPackage.Literals.ASSUMPTION_TERM__TERM,
				 IsabelleProofProcessFactory.eINSTANCE.createMarkupTerm()));

		newChildDescriptors.add
			(createChildParameter
				(IsabelleProofProcessPackage.Literals.ASSUMPTION_TERM__TERM,
				 IsabelleProofProcessFactory.eINSTANCE.createIsaTerm()));

		newChildDescriptors.add
			(createChildParameter
				(IsabelleProofProcessPackage.Literals.ASSUMPTION_TERM__TERM,
				 IsabelleProofProcessFactory.eINSTANCE.createNameTerm()));

		newChildDescriptors.add
			(createChildParameter
				(IsabelleProofProcessPackage.Literals.ASSUMPTION_TERM__TERM,
				 IsabelleProofProcessFactory.eINSTANCE.createNamedTerm()));

		newChildDescriptors.add
			(createChildParameter
				(IsabelleProofProcessPackage.Literals.ASSUMPTION_TERM__TERM,
				 IsabelleProofProcessFactory.eINSTANCE.createInstTerm()));

		newChildDescriptors.add
			(createChildParameter
				(IsabelleProofProcessPackage.Literals.ASSUMPTION_TERM__TERM,
				 IsabelleProofProcessFactory.eINSTANCE.createAssumptionTerm()));

		newChildDescriptors.add
			(createChildParameter
				(IsabelleProofProcessPackage.Literals.ASSUMPTION_TERM__TERM,
				 IsabelleProofProcessFactory.eINSTANCE.createJudgementTerm()));
	}

	/**
	 * Return the resource locator for this item provider's resources.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ResourceLocator getResourceLocator() {
		return IsabelleProofProcessEditPlugin.INSTANCE;
	}

}
