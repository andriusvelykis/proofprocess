package org.ai4fm.proofprocess.project.core.util;

import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;

public class EmfUtil {

	public static void addValue(EObject owner, Object feature, Object value) {
		EditingDomain editDomain = AdapterFactoryEditingDomain.getEditingDomainFor(owner);
		Assert.isNotNull(editDomain);
		
		Command addCommand = AddCommand.create(editDomain, 
				owner, feature, value);
		editDomain.getCommandStack().execute(addCommand);
	}
	
}
