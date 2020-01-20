package moreredoc.umlgenerator;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.charset.Charset;

import org.apache.commons.io.FileUtils;

import net.sourceforge.plantuml.FileFormat;
import net.sourceforge.plantuml.FileFormatOption;
import net.sourceforge.plantuml.SourceStringReader;

/**
 * This class 
 *
 */
public class ModelGenerator {
	public String drawPng(File file, String umlInput) throws IOException {
		SourceStringReader reader1 = new SourceStringReader(umlInput);
		FileOutputStream png = new FileOutputStream(file);
		return reader1.generateImage(png);
	}

	public String drawSvg(File file, String umlInput) throws IOException {
//		SourceStringReader reader = new SourceStringReader(umlInput);
//		final ByteArrayOutputStream os = new ByteArrayOutputStream();
//		// Write the first image to "os"
//		reader.generateImage(os, new FileFormatOption(FileFormat.SVG));
//		// info in os
//		os.close();
//
//		// The XML is stored into svg
//		final String svg = new String(os.toByteArray(), Charset.forName("UTF-8"));
//		System.out.println(svg);
//
//		FileOutputStream stream = new FileOutputStream(file);
//		return reader.generateImage(os);

		SourceStringReader reader = new SourceStringReader(umlInput);
		final ByteArrayOutputStream os = new ByteArrayOutputStream();
		FileOutputStream out = new FileOutputStream(file);
		// Write the first image to "os"
		reader.generateImage(os, new FileFormatOption(FileFormat.SVG));

		// The XML is stored into svg
		final String svg = new String(os.toByteArray(), Charset.forName("UTF-8"));
		FileUtils.writeStringToFile(file, svg, Charset.forName("UTF-8"));
		
		os.close();
		out.close();
		return null;

	}

	public String generateRawXMI(File file, String umlInput) throws IOException {
		return generateXMI(file, umlInput, XMIToolEnum.RAW);
	}

	public String generateStarXMI(File file, String umlInput) throws IOException {
		return generateXMI(file, umlInput, XMIToolEnum.STARUML);
	}

	public String generateArgoXMI(File file, String umlInput) throws IOException {
		return generateXMI(file, umlInput, XMIToolEnum.ARGOUML);
	}

	private String generateXMI(File file, String umlInput, XMIToolEnum xmiTool) throws IOException {
		SourceStringReader reader = new SourceStringReader(umlInput);
		final ByteArrayOutputStream os = new ByteArrayOutputStream();
		// Write the first image to "os"
		FileFormat fileFormat;
		if(xmiTool == XMIToolEnum.RAW) fileFormat = FileFormat.XMI_STANDARD;
		else if(xmiTool == XMIToolEnum.ARGOUML) fileFormat = FileFormat.XMI_ARGO;
		else if(xmiTool == XMIToolEnum.STARUML) fileFormat = FileFormat.XMI_STAR;
		else fileFormat = FileFormat.XMI_STANDARD;
		
		String desc = reader.generateImage(os, new FileFormatOption(fileFormat));
		FileUtils.writeStringToFile(file, desc, Charset.forName("UTF-8"));
		return null;
	}
}
