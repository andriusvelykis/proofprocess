package org.ai4fm.proofprocess.provider.util;

public class ProviderUtils {

	public static String getText(String label, String typeStrId) {
		return label == null || label.length() == 0 ?
				"" : label;
//			getString("_UI_AttemptGroup_type") :
//			getString("_UI_AttemptGroup_type") + " " + label;
	}
	
}
