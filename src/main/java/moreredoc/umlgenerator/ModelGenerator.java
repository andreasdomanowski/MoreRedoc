package moreredoc.umlgenerator;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.FileUtils;

import net.sourceforge.plantuml.FileFormat;
import net.sourceforge.plantuml.FileFormatOption;
import net.sourceforge.plantuml.SourceStringReader;

public class ModelGenerator {
	public void drawPng(File file, String umlInput) throws IOException {
		SourceStringReader reader = new SourceStringReader(umlInput);
		FileOutputStream outStream = new FileOutputStream(file);
		reader.generateImage(outStream);
	}

	public void drawSvg(File file, String umlInput) throws IOException {
		SourceStringReader reader = new SourceStringReader(umlInput);
		try(ByteArrayOutputStream os = new ByteArrayOutputStream()){
			reader.generateImage(os, new FileFormatOption(FileFormat.SVG));
			final String svg = new String(os.toByteArray(), StandardCharsets.UTF_8);
			FileUtils.writeStringToFile(file, svg, StandardCharsets.UTF_8);
		}
	}

	public void generateRawXMI(File file, String umlInput) throws IOException {
		generateXMI(file, umlInput, XMITools.RAW);
	}

	public void generateStarXMI(File file, String umlInput) throws IOException {
		generateXMI(file, umlInput, XMITools.STAR_UML);
	}

	public void generateArgoXMI(File file, String umlInput) throws IOException {
		generateXMI(file, umlInput, XMITools.ARGO_UML);
	}

	private void generateXMI(File file, String umlInput, XMITools xmiTool) throws IOException {
		SourceStringReader reader = new SourceStringReader(umlInput);
		final ByteArrayOutputStream os = new ByteArrayOutputStream();
		FileFormat fileFormat;
		if(xmiTool == XMITools.RAW) fileFormat = FileFormat.XMI_STANDARD;
		else if(xmiTool == XMITools.ARGO_UML) fileFormat = FileFormat.XMI_ARGO;
		else if(xmiTool == XMITools.STAR_UML) fileFormat = FileFormat.XMI_STAR;
		else fileFormat = FileFormat.XMI_STANDARD;
		
		String desc = reader.generateImage(os, new FileFormatOption(fileFormat));
		FileUtils.writeStringToFile(file, desc, StandardCharsets.UTF_8);
	}
}
