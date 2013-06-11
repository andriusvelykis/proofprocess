/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.ai4fm.proofprocess.provider;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.ai4fm.proofprocess.Intent;
import org.ai4fm.proofprocess.ProofElem;
import org.ai4fm.proofprocess.ProofInfo;
import org.ai4fm.proofprocess.ProofProcessFactory;
import org.ai4fm.proofprocess.ProofProcessPackage;
import org.ai4fm.proofprocess.provider.edit.util.ChildChangeNotifier;

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
 * This is the item provider adapter for a {@link org.ai4fm.proofprocess.ProofElem} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class ProofElemItemProvider
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
	public ProofElemItemProvider(AdapterFactory adapterFactory) {
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
			childrenFeatures.add(ProofProcessPackage.Literals.PROOF_ELEM__INFO);
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
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public String getText(Object object) {
		return getText(getString("_UI_ProofElem_type"), "Elem", (ProofElem) object);
	}

	private final ChildChangeNotifier childNotifier =
		new ChildChangeNotifier(adapterFactory, this, true, true);

	protected String getText(String emptyLabel, String label, ProofElem elem) {
		return getText(emptyLabel, label, elem, true, true);
	}

	protected String getText(String emptyLabel, String label, ProofElem elem, boolean showIntent,
			boolean showNarrative) {

		ProofInfo proofInfo = elem.getInfo();
		if (proofInfo == null) {
			return emptyLabel;
		} else {
			// register listeners
			childNotifier.reactOnChanges(proofInfo);

			List<String> labelText = new ArrayList<String>();

			if (!label.isEmpty()) {
				labelText.add(label + ":");
			}

			Intent intent = proofInfo.getIntent();
			boolean intentShown = intent != null && showIntent;
			if (intentShown) {
				labelText.add(intent.getName());
			}

			String narrative = proofInfo.getNarrative();
			boolean narrativeShown = !narrative.isEmpty() && showNarrative; 
			if (narrativeShown) {
				String narrStr = excerpt(narrative);
				labelText.add(intentShown ? "- " + narrStr : narrStr);
			}

			if (intentShown || narrativeShown) {
				return concat(labelText, " ");
			} else {
				return emptyLabel;
			}
		}
	}

	private String concat(List<String> texts, String separator) {
		String sep = "";
		StringBuilder out = new StringBuilder();
		for (String text : texts) {
			if (text.isEmpty()) {
				continue;
			} else {
				out.append(sep);
				out.append(text);
				sep = separator;
			}
		}
		
		return out.toString();
	}


	private static int EXCERPT_LENGTH = 50;
	private static String excerpt(String text) {
		if (text.length() <= EXCERPT_LENGTH) {
			return text;
		} else {
			return text.substring(0, EXCERPT_LENGTH - 3) + "...";
		}
	}

	@Override
	public void dispose() {
		childNotifier.dispose();
		super.dispose();
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

		switch (notification.getFeatureID(ProofElem.class)) {
			case ProofProcessPackage.PROOF_ELEM__INFO:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), true, true));
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
				(ProofProcessPackage.Literals.PROOF_ELEM__INFO,
				 ProofProcessFactory.eINSTANCE.createProofInfo()));
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

	@Override
	public Collection<?> getChildren(Object object) {
		Collection<?> children = super.getChildren(object);

		List<Object> filteredChildren = new ArrayList<Object>();
		for (Object child : children) {
			if (child instanceof ProofInfo) {
				ProofInfo proofInfo = (ProofInfo) child;

				// if proof info has no data apart from the intent, do not show it
				boolean emptyInfo = 
						(proofInfo.getNarrative() == null || proofInfo.getNarrative().isEmpty())
						&& proofInfo.getInFeatures().isEmpty()
						&& proofInfo.getOutFeatures().isEmpty();
				if (!emptyInfo) {
					filteredChildren.add(proofInfo);
				} else {
					// show just the intent if available
					Object intent = proofInfo.getIntent();
					if (intent != null) {
						filteredChildren.add(intent);
					}
				}
			} else {
				filteredChildren.add(child);
			}
		}

		return filteredChildren;
	}

}
