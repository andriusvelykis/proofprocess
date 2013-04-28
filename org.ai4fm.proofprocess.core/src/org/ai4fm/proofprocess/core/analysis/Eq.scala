package org.ai4fm.proofprocess.core.analysis

import org.ai4fm.proofprocess.Term


/**
 * A trait to signal that the type supports matching using `==`.
 * 
 * This is a type-based solution to ensure that terms are matched correctly.
 * 
 * Note that EMF does not use structural equality itself, so this trait requires
 * bridging from EMF to structural equality.
 * 
 * @author Andrius Velykis
 */
trait Eq {

}
