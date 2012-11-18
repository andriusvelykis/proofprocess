package org.ai4fm.proofprocess.zeves.core.parse

import scala.collection.JavaConversions._

import org.ai4fm.proofprocess.Term

import net.sourceforge.czt.eclipse.zeves.ui.core.ZEvesSnapshot
import net.sourceforge.czt.eclipse.zeves.ui.core.ZEvesSnapshot.ISnapshotEntry
import net.sourceforge.czt.eclipse.zeves.ui.core.ZEvesSnapshot.ResultType
import net.sourceforge.czt.session.SectionInfo
import net.sourceforge.czt.zeves.response.ZEvesOutput


/** @author Andrius Velykis
  */
object SnapshotUtil {

  /** Extractor object to check if the snapshot entry is part of a proof */
  object ProofSnapshotEntry {
    // check if goal is available, otherwise not part of the proof
    def unapply(e: ISnapshotEntry): Option[String] = Option(e.getData.getGoalName)
  }
  
  def isGoal(e: ISnapshotEntry): Boolean = e.getData.getProofStepIndex == ZEvesSnapshot.GOAL_STEP_INDEX
  
  /** Checks if the snapshot entry is erroneous */
  def isError(e: ISnapshotEntry): Boolean = e.getType == ResultType.ERROR

  /** Finds the proof in the snapshot for the given entry (up to and including the given entry,
    * but no more).
    */
  def proofEntries(snapshot: ZEvesSnapshot)(proofEndingWith: ISnapshotEntry): List[ISnapshotEntry] = {
    
    proofEndingWith match {
      case ProofSnapshotEntry(_) => {
        // part of the proof - take everything up to (and including) the entry
        // to locate its full proof
        
        val entries = snapshot.getEntries
        val endIndex = entries.lastIndexOf(proofEndingWith)
//        // if the snapshot entry is in the snapshot, take the sublist, otherwise get everything
//        val proofEntries = if (endIndex >= 0) entries.subList(0, endIndex + 1) else entries
        val proofEntries = entries.subList(0, endIndex + 1)
        
        // find the last proof in the sublist,
        // which will result in one ending with the required entry
        lastProof(proofEntries)
      }
      case _ => {
        // entry is not part of the proof, so no proof entries for it
        List()
      }
    }
  }

  /** If the last snapshot entries constitute a proof, retrieves it whole */
  def lastProof(snapshot: ZEvesSnapshot): List[ISnapshotEntry] = {
    lastProof(snapshot.getEntries)
  }
  
  private def lastProof(entries: java.util.List[_ <: ISnapshotEntry]): List[ISnapshotEntry] = {
    
    val revEntries = entries.reverseIterator
    
    if (!revEntries.hasNext) {
      // empty
      List()
    } else {
      
      val entry = revEntries.next
      entry match {
        case ProofSnapshotEntry(goal) => {
          // take everything while the goal matches
          // TODO is goal name enough?
          val proofEntries = revEntries.takeWhile(_ match {
            case ProofSnapshotEntry(testGoal) => goal == testGoal
            case _ => false
          })
          
          // add the first entry, since the iterator continues from the next one in takeWhile()
          val allProofEntries = entry :: proofEntries.toList
          
          // filter out intermediate error entries, which will be ignored
          val nonErrProofEntries = allProofEntries.filterNot(isError);
          
          // reverse the proof, since we iterated backwards
          nonErrProofEntries.reverse
        }
        case _ => {
          // entry is not part of the proof, so no proof entries
          List()
        } 
      }
    }
  }
  
  def parseGoals(sectInfo: SectionInfo, proofEntry: ISnapshotEntry): Option[List[Term]] = {
    zEvesProofResult(proofEntry) map (TermParser.parseGoals(sectInfo, proofEntry.getSectionName, _))
  }
  
  def zEvesProofResult(proofEntry: ISnapshotEntry): Option[ZEvesOutput] = {
    proofEntry.getData.getResult match {
      case ze: ZEvesOutput => Some(ze)
      case _ => None
    }
  }
  
}
