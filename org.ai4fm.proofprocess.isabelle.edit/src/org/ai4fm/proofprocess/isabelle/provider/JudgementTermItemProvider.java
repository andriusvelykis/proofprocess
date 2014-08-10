/**
 */
package org.ai4fm.proofprocess.isabelle.provider;


import java.util.Collection;
import java.util.List;

import org.ai4fm.proofprocess.ProofProcessFactory;
import org.ai4fm.proofprocess.DisplayTerm;
import org.ai4fm.proofprocess.Term;
import org.ai4fm.proofprocess.isabelle.IsabelleProofProcessFactory;
import org.ai4fm.proofprocess.isabelle.IsabelleProofProcessPackage;
import org.ai4fm.proofprocess.isabelle.JudgementTerm;

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
 * This is the item provider adapter for a {@link org.ai4fm.proofprocess.isabelle.JudgementTerm} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class JudgementTermItemProvider
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
	public JudgementTermItemProvider(AdapterFactory adapterFactory) {
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
			childrenFeatures.add(IsabelleProofProcessPackage.Literals.JUDGEMENT_TERM__ASSMS);
			childrenFeatures.add(IsabelleProofProcessPackage.Literals.JUDGEMENT_TERM__GOAL);
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
	 * This returns JudgementTerm.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/JudgementTerm"));
	}


	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public String getText(Object object) {
		return renderTerm((JudgementTerm) object);
	}

	public static String renderTerm(JudgementTerm term) {
		String goalText = getTermText(term.getGoal());

		List<Term> assms = term.getAssms();
		if (assms.isEmpty()) {
			return goalText;
		} else {

			// output as (with symbols): [| assm; assm |] => goal
			StringBuilder out = new StringBuilder("\u27E6");
			String delimiter = "";

			for (Term assm : assms) {
				out.append(delimiter);
				out.append(getTermText(assm));
				delimiter = "; ";
			}

			out.append("\u27E7 \u27f9 ");
			out.append(goalText);
			return out.toString();
		}
	}

	private static String getTermText(Term term) {
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

		switch (notification.getFeatureID(JudgementTerm.class)) {
			case IsabelleProofProcessPackage.JUDGEMENT_TERM__ASSMS:
			case IsabelleProofProcessPackage.JUDGEMENT_TERM__GOAL:
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
				(IsabelleProofProcessPackage.Literals.JUDGEMENT_TERM__ASSMS,
				 IsabelleProofProcessFactory.eINSTANCE.createMarkupTerm()));

		newChildDescriptors.add
			(createChildParameter
				(IsabelleProofProcessPackage.Literals.JUDGEMENT_TERM__ASSMS,
				 IsabelleProofProcessFactory.eINSTANCE.createIsaTerm()));

		newChildDescriptors.add
			(createChildParameter
				(IsabelleProofProcessPackage.Literals.JUDGEMENT_TERM__ASSMS,
				 IsabelleProofProcessFactory.eINSTANCE.createNameTerm()));

		newChildDescriptors.add
			(createChildParameter
				(IsabelleProofProcessPackage.Literals.JUDGEMENT_TERM__ASSMS,
				 IsabelleProofProcessFactory.eINSTANCE.createNamedTerm()));

		newChildDescriptors.add
			(createChildParameter
				(IsabelleProofProcessPackage.Literals.JUDGEMENT_TERM__ASSMS,
				 IsabelleProofProcessFactory.eINSTANCE.createInstTerm()));

		newChildDescriptors.add
			(createChildParameter
				(IsabelleProofProcessPackage.Literals.JUDGEMENT_TERM__ASSMS,
				 IsabelleProofProcessFactory.eINSTANCE.createAssumptionTerm()));

		newChildDescriptors.add
			(createChildParameter
				(IsabelleProofProcessPackage.Literals.JUDGEMENT_TERM__ASSMS,
				 IsabelleProofProcessFactory.eINSTANCE.createJudgementTerm()));

		newChildDescriptors.add
			(createChildParameter
				(IsabelleProofProcessPackage.Literals.JUDGEMENT_TERM__ASSMS,
				 ProofProcessFactory.eINSTANCE.createStringTerm()));

		newChildDescriptors.add
			(createChildParameter
				(IsabelleProofProcessPackage.Literals.JUDGEMENT_TERM__GOAL,
				 IsabelleProofProcessFactory.eINSTANCE.createMarkupTerm()));

		newChildDescriptors.add
			(createChildParameter
				(IsabelleProofProcessPackage.Literals.JUDGEMENT_TERM__GOAL,
				 IsabelleProofProcessFactory.eINSTANCE.createIsaTerm()));

		newChildDescriptors.add
			(createChildParameter
				(IsabelleProofProcessPackage.Literals.JUDGEMENT_TERM__GOAL,
				 IsabelleProofProcessFactory.eINSTANCE.createNameTerm()));

		newChildDescriptors.add
			(createChildParameter
				(IsabelleProofProcessPackage.Literals.JUDGEMENT_TERM__GOAL,
				 IsabelleProofProcessFactory.eINSTANCE.createNamedTerm()));

		newChildDescriptors.add
			(createChildParameter
				(IsabelleProofProcessPackage.Literals.JUDGEMENT_TERM__GOAL,
				 IsabelleProofProcessFactory.eINSTANCE.createInstTerm()));

		newChildDescriptors.add
			(createChildParameter
				(IsabelleProofProcessPackage.Literals.JUDGEMENT_TERM__GOAL,
				 IsabelleProofProcessFactory.eINSTANCE.createAssumptionTerm()));

		newChildDescriptors.add
			(createChildParameter
				(IsabelleProofProcessPackage.Literals.JUDGEMENT_TERM__GOAL,
				 IsabelleProofProcessFactory.eINSTANCE.createJudgementTerm()));

		newChildDescriptors.add
			(createChildParameter
				(IsabelleProofProcessPackage.Literals.JUDGEMENT_TERM__GOAL,
				 ProofProcessFactory.eINSTANCE.createStringTerm()));
	}

	/**
	 * This returns the label text for {@link org.eclipse.emf.edit.command.CreateChildCommand}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getCreateChildText(Object owner, Object feature, Object child, Collection<?> selection) {
		Object childFeature = feature;
		Object childObject = child;

		boolean qualify =
			childFeature == IsabelleProofProcessPackage.Literals.JUDGEMENT_TERM__ASSMS ||
			childFeature == IsabelleProofProcessPackage.Literals.JUDGEMENT_TERM__GOAL;

		if (qualify) {
			return getString
				("_UI_CreateChild_text2",
				 new Object[] { getTypeText(childObject), getFeatureText(childFeature), getTypeText(owner) });
		}
		return super.getCreateChildText(owner, feature, child, selection);
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
