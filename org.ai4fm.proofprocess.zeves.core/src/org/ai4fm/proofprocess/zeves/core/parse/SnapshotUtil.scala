package org.ai4fm.proofprocess.zeves.core.parse

import scala.collection.JavaConverters._

import org.ai4fm.proofprocess.Term

import net.sourceforge.czt.session.SectionInfo
import net.sourceforge.czt.zeves.response.ZEvesOutput
import net.sourceforge.czt.zeves.snapshot.{ISnapshotEntry, ZEvesSnapshot}
import net.sourceforge.czt.zeves.snapshot.ZEvesSnapshot.ResultType


/** @author Andrius Velykis
  */
object SnapshotUtil {

  /** Extractor object to check if the snapshot entry is part of a proof */
  object ProofSnapshotEntry {
    // check if goal is available, otherwise not part of the proof
    def unapply(e: ISnapshotEntry): Option[String] = Option(e.getData.getGoalName)
  }
  
  def isGoal(e: ISnapshotEntry): Boolean = e.getData.getProofStepIndex == ZEvesSnapshot.GOAL_STEP_INDEX

  def isProof(e: ISnapshotEntry): Boolean = ProofSnapshotEntry.unapply(e).isDefined
  
  /** Checks if the snapshot entry is erroneous */
  def isError(e: ISnapshotEntry): Boolean = e.getType == ResultType.ERROR


  /**
   * Finds the proof in the snapshot entries for the given entry
   * (up to and including the given entry, but no more).
   */
  def proofEntries(allEntries: List[ISnapshotEntry],
                   proofEndingWith: ISnapshotEntry): List[ISnapshotEntry] = {
    
    proofEndingWith match {
      case ProofSnapshotEntry(_) => {
        // part of the proof - take everything up to (and including) the entry
        // to locate its full proof

        val endIndex = allEntries.indexOf(proofEndingWith)
        if (endIndex < 0) {
          // the entry is no longer in the snapshot.. ignore
          Nil
        } else {
          val allStartEntries = allEntries.take(endIndex + 1)
          
          // find the last proof in the sublist,
          // which will result in one ending with the required entry
          lastProof(allStartEntries)
        }
      }
      case _ => {
        // entry is not part of the proof, so no proof entries for it
        List()
      }
    }
  }

  /** If the last snapshot entries constitute a proof, retrieves it whole */
  def lastProof(snapshot: ZEvesSnapshot): List[ISnapshotEntry] = {
    lastProof(snapshot.getEntries.asScala)
  }
  
  private def lastProof(entries: Seq[_ <: ISnapshotEntry]): List[ISnapshotEntry] = {
    
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
