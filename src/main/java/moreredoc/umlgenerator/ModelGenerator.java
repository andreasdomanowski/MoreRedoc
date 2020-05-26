package moreredoc.umlgenerator;

import moreredoc.application.MoreRedocOutputConfiguration;
import net.sourceforge.plantuml.FileFormat;
import net.sourceforge.plantuml.FileFormatOption;
import net.sourceforge.plantuml.SourceStringReader;
import org.apache.commons.io.FileUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ModelGenerator {
	private final String plantUmlModel;
	private final MoreRedocOutputConfiguration outputConfiguration;

	public ModelGenerator(String plantUmlModel, MoreRedocOutputConfiguration outputConfiguration) {
		this.plantUmlModel = plantUmlModel;
		this.outputConfiguration = outputConfiguration;
	}

	public void generateModels(String pathOutputFolder) throws IOException {
		if (outputConfiguration.isOutputPng()) {
			drawPng(new File(pathOutputFolder + File.separator + "/model.png"), plantUmlModel);
		}

		if (outputConfiguration.isOutputSvg()) {
			drawSvg(new File(pathOutputFolder + File.separator + "model.svg"), plantUmlModel);
		}

		if (outputConfiguration.isXmiRaw()) {
			generateRawXMI(new File(pathOutputFolder + File.separator + "xmiRaw.xmi"), plantUmlModel);
		}

		if (outputConfiguration.isXmiArgo()) {
			generateArgoXMI(new File(pathOutputFolder + File.separator + "xmiArgo.xmi"), plantUmlModel);
		}

		if (outputConfiguration.isXmiStar()) {
			generateStarXMI(new File(pathOutputFolder + File.separator + "xmiStar.xmi"), plantUmlModel);
		}
	}

	private void drawPng(File file, String umlInput) throws IOException {
		SourceStringReader reader = new SourceStringReader(umlInput);
		FileOutputStream outStream = new FileOutputStream(file);
		reader.generateImage(outStream);
	}

	private void drawSvg(File file, String umlInput) throws IOException {
		SourceStringReader reader = new SourceStringReader(umlInput);
		try(ByteArrayOutputStream os = new ByteArrayOutputStream()){
			reader.generateImage(os, new FileFormatOption(FileFormat.SVG));
			final String svg = new String(os.toByteArray(), StandardCharsets.UTF_8);
			FileUtils.writeStringToFile(file, svg, StandardCharsets.UTF_8);
		}
	}

	private void generateRawXMI(File file, String umlInput) throws IOException {
		generateXMI(file, umlInput, XMITools.RAW);
	}

	private void generateStarXMI(File file, String umlInput) throws IOException {
		generateXMI(file, umlInput, XMITools.STAR_UML);
	}

	private void generateArgoXMI(File file, String umlInput) throws IOException {
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
		
		reader.generateImage(os, new FileFormatOption(fileFormat));
		final String outString = new String(os.toByteArray(), StandardCharsets.UTF_8);
		FileUtils.writeStringToFile(file, outString, StandardCharsets.UTF_8);
	}
}
