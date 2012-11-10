package org.ai4fm.proofprocess.parse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import javax.xml.bind.DatatypeConverter;

/**
 * Provides utility methods to compress/decompress Strings.
 * <p>
 * Uses ZLIB compression and then encodes the resulting bytes as ASCII String (base64). This allows
 * storing the compressed String in XML files, etc.
 * </p>
 * <p>
 * The compression can be up to 95% efficient for large Strings.
 * </p>
 * 
 * @author Andrius Velykis
 */
public class StringCompression {

	private static final String ENCODING = "UTF-8";
	
	/**
	 * Compresses the given String using ZLIB and encodes resulting bytes as ASCII String (base64)
	 * 
	 * @param inputString
	 *            text to compress
	 * @return compressed and base64-encoded text
	 */
	public static String compress(String inputString) {
//		long start = System.currentTimeMillis();
		String compressed = encodeBytes(compressString(inputString));

//		System.out.println("Compressed from " + inputString.length() + " to " + compressed.length()
//				+ " in " + (System.currentTimeMillis() - start) + "ms");

		return compressed;
	}

	/**
	 * Decompresses the given ZLIB-compressed String. Expects the input to be base64-encoded.
	 * 
	 * @param compressed
	 *            compressed and base64-encoded text
	 * @return uncompressed text
	 */
	public static String decompress(String compressed) {
//		long start = System.currentTimeMillis();
		String decompressed = decompressString(decodeBytes(compressed));
//		System.out.println("Decompressed from " + compressed.length() + " to " + decompressed.length()
//				+ " in " + (System.currentTimeMillis() - start) + "ms");

		return decompressed;
	}

	private static byte[] compressString(String inputString) {

		byte[] input;
		try {
			// Encode a String into bytes
			input = inputString.getBytes(ENCODING);
		} catch (UnsupportedEncodingException ex) {
			// should never happen
			throw new IllegalStateException("Unsupported encoding", ex);
		}

		// Compress the bytes
		Deflater def = new Deflater();
		def.setLevel(Deflater.BEST_COMPRESSION);
		def.setInput(input);
		def.finish();

		ByteArrayOutputStream byteArray = new ByteArrayOutputStream(input.length);

		// copy to a byte array using a buffer
		byte[] buf = new byte[1024];
		while (!def.finished()) {
			int compByte = def.deflate(buf);
			byteArray.write(buf, 0, compByte);
		}
		try {
			byteArray.close();
		} catch (IOException ioe) {
			throw new IllegalStateException("Cannot close byte stream", ioe);
		}

		byte[] comData = byteArray.toByteArray();

		return comData;
	}

	private static String decompressString(byte[] compressed) {

		// Decompress the bytes
		Inflater inf = new Inflater();
		inf.setInput(compressed, 0, compressed.length);

		ByteArrayOutputStream byteArray = new ByteArrayOutputStream(compressed.length * 2);

		// copy to a byte array using a buffer
		try {
			byte[] buf = new byte[1024];
			while (!inf.finished()) {
				int compByte = inf.inflate(buf);
				byteArray.write(buf, 0, compByte);
			}
		} catch (DataFormatException ex) {
			throw new IllegalArgumentException("Compressed data format is invalid", ex);
		} finally {
			try {
				byteArray.close();
			} catch (IOException ioe) {
				throw new IllegalStateException("Cannot close byte stream", ioe);
			}
		}

		inf.end();

		byte[] comData = byteArray.toByteArray();
		try {
			// Decode the bytes into a String
			return new String(comData, 0, comData.length, ENCODING);
		} catch (UnsupportedEncodingException ex) {
			// should never happen
			throw new IllegalStateException("Unsupported encoding", ex);
		}
	}

	private static String encodeBytes(byte[] bytes) {
		return DatatypeConverter.printBase64Binary(bytes);
	}

	private static byte[] decodeBytes(String encoded) {
		return DatatypeConverter.parseBase64Binary(encoded);
	}

}
