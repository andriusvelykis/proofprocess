package org.ai4fm.proofprocess.provider.edit.util;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.provider.INotifyChangedListener;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.emf.edit.provider.ViewerNotification;


/**
 * A utility class to register for notifications on children EMF element changes.
 * 
 * For any EMF object registered via {@link #reactOnChanges(EObject)}, its parent is notified
 * if the child changes.
 * 
 * @author Andrius Velykis
 */
public class ChildChangeNotifier {

	private Map<EObject, INotifyChangedListener> childListeners = 
			new HashMap<EObject, INotifyChangedListener>();

	private final AdapterFactory adapterFactory;
	private final ItemProviderAdapter provider;
	private final boolean contentRefresh;
	private final boolean labelUpdate;

	public ChildChangeNotifier(AdapterFactory adapterFactory, ItemProviderAdapter provider,
			boolean contentRefresh, boolean labelUpdate) {

		this.adapterFactory = adapterFactory;
		this.provider = provider;
		this.contentRefresh = contentRefresh;
		this.labelUpdate = labelUpdate;
	}

	public void reactOnChanges(EObject childElem) {
		if (!childListeners.containsKey(childElem)) {
			getItemProvider(childElem);
		}
	}

	public ItemProviderAdapter getItemProvider(EObject childElem) {

		ItemProviderAdapter proofInfoItemProvider = itemProvider(childElem);

		if (!childListeners.containsKey(childElem)) {
			INotifyChangedListener listener = new ForwardingNotifyListener(childElem);
			childListeners.put(childElem, listener);
			proofInfoItemProvider.addListener(listener);
		}

		return proofInfoItemProvider;
	}

	private ItemProviderAdapter itemProvider(EObject elem) {
		return (ItemProviderAdapter) adapterFactory.adapt(elem, ItemProviderAdapter.class);
	}

	public void dispose() {
		// disconnect listeners
		for (Map.Entry<EObject, INotifyChangedListener> child : childListeners.entrySet()) {
			itemProvider(child.getKey()).removeListener(child.getValue());
		}
	}


	private class ForwardingNotifyListener implements INotifyChangedListener {

		private final EObject childElem;

		public ForwardingNotifyListener(EObject childElem) {
			this.childElem = childElem;
		}

		@Override
		public void notifyChanged(Notification notification) {
			if (notification.getNotifier() == childElem) {
				EObject parentElem = childElem.eContainer();
				if (parentElem != null) {
					provider.fireNotifyChanged(new ViewerNotification(notification, parentElem,
							contentRefresh, labelUpdate));
				}
			}
		}
	}

}
