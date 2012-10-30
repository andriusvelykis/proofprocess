package org.ai4fm.filehistory.core.internal

import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

import org.ai4fm.filehistory.core.FileHistoryCorePlugin.error
import org.ai4fm.filehistory.core.FileHistoryCorePlugin.log
import org.eclipse.core.runtime.Assert

/**
  * @author Andrius Velykis 
  */
object FileHistoryManager {

  def checksumPart(text: String, textPoint: Int, fullChecksum: /*=> */String): String =
    if (textPoint == text.length) {
      fullChecksum
    } else {
      checksum(text, textPoint)
    }

  private def checksum(text: String, textPoint: Int): String = {
    Assert.isLegal(textPoint <= text.length())
    checksum(text.substring(0, textPoint))
  }

  def checksum(text: String): String = {
    try {
      hash(text, "SHA-256")
    } catch {
      case e: NoSuchAlgorithmException => {
        log(error(Some(e)))
        // return empty checksum (should never happen, since SHA-256 is part of Java)
        ""
      }
    }
  }

  @throws(classOf[NoSuchAlgorithmException])
  private def hash(text: String, algorithm: String) = {
    val hash = MessageDigest.getInstance(algorithm).digest(text.getBytes)
    // encode in hex
    val bi = new BigInteger(1, hash)
    val result = bi.toString(16)

    if (result.length % 2 != 0) "0" + result else result
  }
  
}