package moreredoc.application;

import moreredoc.analysis.MoreRedocAnalysisConfiguration;
import moreredoc.application.exceptions.InvalidRequirementInputException;
import moreredoc.datainput.tools.SupportedRedocumentationTools;
import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * Hastily developed UI to make application usable without using the API. Code
 * is mostly generated with the help of the WindowBuilder Eclipse Plugin. This
 * UI is just for quickstarting the modeling process, therefore no attention was
 * paid to refactorability.
 */
public class MoreRedocGui extends JFrame {
    /**
     * automatically generated
     */
    private static final long serialVersionUID = 618505241078949953L;
    private static final String APPLICATION_TITLE = "MoreRedoc";

    private static final Logger logger = Logger.getLogger(MoreRedocGui.class);

    // ui elements
    private final JComboBox<SupportedRedocumentationTools> comboBoxToolSelection;
    private final JTextField textfieldCsvKeywords;
    private final JTextField textfieldCsvText;
    private final JTextField textfieldOutputFolder;

    private final JCheckBox cbVerbsMethods;
    private final JCheckBox cbVerbsRelationships;
    private final JCheckBox cbRawXmi;
    private final JCheckBox cbArgoUml;
    private final JCheckBox cbStarUml;
    private final JCheckBox cbPng;
    private final JCheckBox cbSvg;

    private final JButton buttonGenerateModels;

    // preload file chooser, on demand would take noticeably long.
    private final transient FutureTask<JFileChooser> futureFileChooser = new FutureTask<>(JFileChooser::new);
    private final JButton buttonChooseKeywordsCsv;
    private final JButton buttonChooseTextCsv;
    private final JButton buttonChooseOutputFolder;

    private static final String TEXT_CHOOSE_BUTTON = "Select";
    private static final String ERROR_MESSAGE_INVALID_INPUT = "An error occurred while the input files were parsed. Please make sure that both requirement inputs are valid.";
    private static final String ERROR_HEADER_INVALID_INPUT = "Error parsing input files";

    public MoreRedocGui() {
        super(APPLICATION_TITLE);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(700, 400);

        JPanel mainPanel = new JPanel();

        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(futureFileChooser);

        Border border = BorderFactory.createDashedBorder(Color.BLACK);
        Border margin = new EmptyBorder(10, 10, 10, 10);
        mainPanel.setBorder(new CompoundBorder(border, margin));

        this.getContentPane().add(mainPanel, BorderLayout.CENTER);
        GridBagLayout gblPanel = new GridBagLayout();
        gblPanel.columnWidths = new int[] { 0, 0, 0, 0 };
        gblPanel.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
        gblPanel.columnWeights = new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
        gblPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
                Double.MIN_VALUE };
        mainPanel.setLayout(gblPanel);

        JLabel labelToolSelection = new JLabel("Tool:");
        GridBagConstraints gbcLabelToolSelection = new GridBagConstraints();
        gbcLabelToolSelection.anchor = GridBagConstraints.WEST;
        Insets defaultInsets = new Insets(0, 0, 5, 5);
        gbcLabelToolSelection.insets = defaultInsets;
        gbcLabelToolSelection.gridx = 0;
        gbcLabelToolSelection.gridy = 0;
        mainPanel.add(labelToolSelection, gbcLabelToolSelection);

        comboBoxToolSelection = new JComboBox<>(SupportedRedocumentationTools.values());
        GridBagConstraints gbcComboBoxToolSelection = new GridBagConstraints();
        gbcComboBoxToolSelection.insets = defaultInsets;
        gbcComboBoxToolSelection.fill = GridBagConstraints.HORIZONTAL;
        gbcComboBoxToolSelection.gridx = 1;
        gbcComboBoxToolSelection.gridy = 0;
        mainPanel.add(comboBoxToolSelection, gbcComboBoxToolSelection);

        JLabel labelKeywords = new JLabel("Keywords:");
        GridBagConstraints gbcLabelKeywords = new GridBagConstraints();
        gbcLabelKeywords.anchor = GridBagConstraints.WEST;
        gbcLabelKeywords.insets = defaultInsets;
        gbcLabelKeywords.gridx = 0;
        gbcLabelKeywords.gridy = 1;
        mainPanel.add(labelKeywords, gbcLabelKeywords);

        textfieldCsvKeywords = new JTextField();
        textfieldCsvKeywords.setEnabled(false);
        GridBagConstraints gbcTextfieldCsvKeywords = new GridBagConstraints();
        gbcTextfieldCsvKeywords.insets = defaultInsets;
        gbcTextfieldCsvKeywords.fill = GridBagConstraints.HORIZONTAL;
        gbcTextfieldCsvKeywords.gridx = 1;
        gbcTextfieldCsvKeywords.gridy = 1;
        mainPanel.add(textfieldCsvKeywords, gbcTextfieldCsvKeywords);
        textfieldCsvKeywords.setColumns(10);

        buttonChooseKeywordsCsv = new JButton(TEXT_CHOOSE_BUTTON);
        buttonChooseKeywordsCsv.addActionListener(e -> {
            try {
                chooseFileAndUpdateTextfield(textfieldCsvKeywords, mainPanel, JFileChooser.FILES_ONLY);
            } catch (InterruptedException e1) {
                Thread.currentThread().interrupt();
                logger.info(e1);
            } catch (ExecutionException e1) {
                logger.info(e1);
            }
        });
        GridBagConstraints gbcButton = new GridBagConstraints();
        gbcButton.anchor = GridBagConstraints.WEST;
        gbcButton.insets = new Insets(0, 0, 5, 0);
        gbcButton.gridx = 2;
        gbcButton.gridy = 1;
        mainPanel.add(buttonChooseKeywordsCsv, gbcButton);

        JLabel labelCsvText = new JLabel("Text:");
        labelCsvText.setHorizontalAlignment(SwingConstants.CENTER);
        GridBagConstraints gbcLabelCsvText = new GridBagConstraints();
        gbcLabelCsvText.anchor = GridBagConstraints.WEST;
        gbcLabelCsvText.insets = defaultInsets;
        gbcLabelCsvText.gridx = 0;
        gbcLabelCsvText.gridy = 2;
        mainPanel.add(labelCsvText, gbcLabelCsvText);

        textfieldCsvText = new JTextField();
        textfieldCsvText.setEnabled(false);
        GridBagConstraints gbcTextfieldCsvText = new GridBagConstraints();
        gbcTextfieldCsvText.insets = defaultInsets;
        gbcTextfieldCsvText.fill = GridBagConstraints.HORIZONTAL;
        gbcTextfieldCsvText.gridx = 1;
        gbcTextfieldCsvText.gridy = 2;
        mainPanel.add(textfieldCsvText, gbcTextfieldCsvText);
        textfieldCsvText.setColumns(10);

        buttonChooseTextCsv = new JButton(TEXT_CHOOSE_BUTTON);
        buttonChooseTextCsv.addActionListener(e -> {
            try {
                chooseFileAndUpdateTextfield(textfieldCsvText, mainPanel, JFileChooser.FILES_ONLY);
            } catch (InterruptedException e1) {
                Thread.currentThread().interrupt();
                logger.info(e1);
            } catch (ExecutionException e1) {
                logger.info(e1);
            }
        });
        GridBagConstraints gbcButtonChooseTextCsv = new GridBagConstraints();
        gbcButtonChooseTextCsv.anchor = GridBagConstraints.WEST;
        gbcButtonChooseTextCsv.insets = new Insets(0, 0, 5, 0);
        gbcButtonChooseTextCsv.gridx = 2;
        gbcButtonChooseTextCsv.gridy = 2;
        mainPanel.add(buttonChooseTextCsv, gbcButtonChooseTextCsv);

        JLabel labelOutputFolder = new JLabel("Output folder:");
        GridBagConstraints gbcLabelOutputFolder = new GridBagConstraints();
        gbcLabelOutputFolder.anchor = GridBagConstraints.WEST;
        gbcLabelOutputFolder.insets = defaultInsets;
        gbcLabelOutputFolder.gridx = 0;
        gbcLabelOutputFolder.gridy = 3;
        mainPanel.add(labelOutputFolder, gbcLabelOutputFolder);

        textfieldOutputFolder = new JTextField();
        textfieldOutputFolder.setEnabled(false);
        GridBagConstraints gbcTextfieldOutputFolder = new GridBagConstraints();
        gbcTextfieldOutputFolder.insets = defaultInsets;
        gbcTextfieldOutputFolder.fill = GridBagConstraints.HORIZONTAL;
        gbcTextfieldOutputFolder.gridx = 1;
        gbcTextfieldOutputFolder.gridy = 3;
        mainPanel.add(textfieldOutputFolder, gbcTextfieldOutputFolder);
        textfieldOutputFolder.setColumns(10);

        buttonChooseOutputFolder = new JButton(TEXT_CHOOSE_BUTTON);
        buttonChooseOutputFolder.addActionListener(e -> {
            try {
                chooseFileAndUpdateTextfield(textfieldOutputFolder, mainPanel, JFileChooser.DIRECTORIES_ONLY);
            } catch (InterruptedException e1) {
                Thread.currentThread().interrupt();
                logger.info(e1);
            } catch (ExecutionException e1) {
                logger.info(e1);
            }
        });
        GridBagConstraints gbcButtonChooseOutputFolder = new GridBagConstraints();
        gbcButtonChooseOutputFolder.anchor = GridBagConstraints.WEST;
        gbcButtonChooseOutputFolder.insets = new Insets(0, 0, 5, 0);
        gbcButtonChooseOutputFolder.gridx = 2;
        gbcButtonChooseOutputFolder.gridy = 3;
        mainPanel.add(buttonChooseOutputFolder, gbcButtonChooseOutputFolder);

        JLabel labelSettings = new JLabel("Settings:");
        GridBagConstraints gbcLabelSettings = new GridBagConstraints();
        gbcLabelSettings.anchor = GridBagConstraints.WEST;
        gbcLabelSettings.insets = defaultInsets;
        gbcLabelSettings.gridx = 0;
        gbcLabelSettings.gridy = 4;
        mainPanel.add(labelSettings, gbcLabelSettings);

        cbVerbsMethods = new JCheckBox("Model verbs as methods");
        cbVerbsMethods.setSelected(true);
        GridBagConstraints gbcCbVerbsMethods = new GridBagConstraints();
        gbcCbVerbsMethods.anchor = GridBagConstraints.WEST;
        gbcCbVerbsMethods.insets = defaultInsets;
        gbcCbVerbsMethods.gridx = 1;
        gbcCbVerbsMethods.gridy = 4;
        mainPanel.add(cbVerbsMethods, gbcCbVerbsMethods);

        cbVerbsRelationships = new JCheckBox("Model verbs as relationships");
        cbVerbsRelationships.setSelected(true);
        GridBagConstraints gbcCbVerbsRelationships = new GridBagConstraints();
        gbcCbVerbsRelationships.anchor = GridBagConstraints.WEST;
        gbcCbVerbsRelationships.insets = defaultInsets;
        gbcCbVerbsRelationships.gridx = 1;
        gbcCbVerbsRelationships.gridy = 5;
        mainPanel.add(cbVerbsRelationships, gbcCbVerbsRelationships);

        JLabel labelOutput = new JLabel("Output:");
        GridBagConstraints gbcLabelOutput = new GridBagConstraints();
        gbcLabelOutput.anchor = GridBagConstraints.WEST;
        gbcLabelOutput.insets = defaultInsets;
        gbcLabelOutput.gridx = 0;
        gbcLabelOutput.gridy = 7;
        mainPanel.add(labelOutput, gbcLabelOutput);

        cbRawXmi = new JCheckBox("Raw XMI");
        cbRawXmi.setSelected(true);
        GridBagConstraints gbcCbRawXmi = new GridBagConstraints();
        gbcCbRawXmi.anchor = GridBagConstraints.WEST;
        gbcCbRawXmi.insets = defaultInsets;
        gbcCbRawXmi.gridx = 1;
        gbcCbRawXmi.gridy = 7;
        mainPanel.add(cbRawXmi, gbcCbRawXmi);

        cbArgoUml = new JCheckBox("XMI (ArgoUML)");
        cbArgoUml.setSelected(true);
        GridBagConstraints gbcCbArgoUml = new GridBagConstraints();
        gbcCbArgoUml.anchor = GridBagConstraints.WEST;
        gbcCbArgoUml.insets = defaultInsets;
        gbcCbArgoUml.gridx = 1;
        gbcCbArgoUml.gridy = 8;
        mainPanel.add(cbArgoUml, gbcCbArgoUml);

        cbStarUml = new JCheckBox("XMI (StarUML)");
        cbStarUml.setSelected(true);
        GridBagConstraints gbcCbStarUml = new GridBagConstraints();
        gbcCbStarUml.anchor = GridBagConstraints.WEST;
        gbcCbStarUml.insets = defaultInsets;
        gbcCbStarUml.gridx = 1;
        gbcCbStarUml.gridy = 9;
        mainPanel.add(cbStarUml, gbcCbStarUml);

        cbPng = new JCheckBox("PNG");
        cbPng.setSelected(true);
        GridBagConstraints gbcCbPng = new GridBagConstraints();
        gbcCbPng.anchor = GridBagConstraints.WEST;
        gbcCbPng.insets = defaultInsets;
        gbcCbPng.gridx = 1;
        gbcCbPng.gridy = 10;
        mainPanel.add(cbPng, gbcCbPng);

        cbSvg = new JCheckBox("SVG");
        cbSvg.setSelected(true);
        GridBagConstraints gbcCbSvg = new GridBagConstraints();
        gbcCbSvg.anchor = GridBagConstraints.WEST;
        gbcCbSvg.insets = defaultInsets;
        gbcCbSvg.gridx = 1;
        gbcCbSvg.gridy = 11;
        mainPanel.add(cbSvg, gbcCbSvg);

        buttonGenerateModels = new JButton("Run");
        buttonGenerateModels.setVerticalAlignment(SwingConstants.BOTTOM);
        buttonGenerateModels.addActionListener(e -> runGenerateModel(cbVerbsMethods.isSelected(), cbVerbsRelationships.isSelected(), cbRawXmi.isSelected(), cbArgoUml.isSelected(), cbStarUml.isSelected(), cbPng.isSelected(), cbSvg.isSelected()));
        GridBagConstraints gbcButtonGenerateModels = new GridBagConstraints();
        gbcButtonGenerateModels.insets = new Insets(0, 0, 5, 0);
        gbcButtonGenerateModels.gridx = 2;
        gbcButtonGenerateModels.gridy = 12;
        mainPanel.add(buttonGenerateModels, gbcButtonGenerateModels);
    }

    private void chooseFileAndUpdateTextfield(JTextField textfield, Component parent, int selectionMode)
            throws InterruptedException, ExecutionException {
        if (selectionMode != JFileChooser.FILES_ONLY && selectionMode != JFileChooser.DIRECTORIES_ONLY) {
            throw new IllegalArgumentException();
        }

        JFileChooser fileChooser;
        fileChooser = futureFileChooser.get();
        fileChooser.setFileSelectionMode(selectionMode);
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        int result = fileChooser.showOpenDialog(parent);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            textfield.setText(selectedFile.getAbsolutePath());
        }
    }

    private void runGenerateModel(boolean verbsMethods, boolean verbsRelationships, boolean outputRawXmi, boolean outputArgoXmi, boolean outputStarUml, boolean outputPng, boolean outputSvg) {
        Component parentComponentForDialog = this;

        SwingWorker<Void, Void> backgroundWorker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                logger.info("Start model generation");
                setUiActive(false);

                MoreRedocAnalysisConfiguration analysisConfiguration = new MoreRedocAnalysisConfiguration(verbsMethods, verbsRelationships);
                MoreRedocOutputConfiguration outputConfiguration = new MoreRedocOutputConfiguration(outputRawXmi, outputArgoXmi, outputStarUml, outputPng, outputSvg);

                SupportedRedocumentationTools tool = comboBoxToolSelection.getItemAt(comboBoxToolSelection.getSelectedIndex());
                Objects.requireNonNull(tool);

                MoreRedocStarter.generateModel(textfieldCsvText.getText(), textfieldOutputFolder.getText(), textfieldCsvKeywords.getText(), outputConfiguration, analysisConfiguration, tool.getDataHandler());
                JOptionPane.showMessageDialog(parentComponentForDialog, "Models were successfully generated.");
                setUiActive(true);
                logger.info("Model generation done");
                return null;
            }

            @Override
            protected void done() {
                try {
                    get();
                } catch (ExecutionException e) {
                    // working around getting into swing too deeply..
                    if (e.getCause() instanceof InvalidRequirementInputException) {
                        logger.error("InvalidRequirementInput");
                        JOptionPane.showMessageDialog(parentComponentForDialog, ERROR_MESSAGE_INVALID_INPUT, ERROR_HEADER_INVALID_INPUT, JOptionPane.ERROR_MESSAGE);
                    } else {
                        logger.error(e);
                    }
                } catch (InterruptedException e) {
                    logger.error(e);
                    Thread.currentThread().interrupt();
                } finally {
                    setUiActive(true);
                }
            }
        };

        if (necessaryFieldsHaveLegalArguments()) {
            backgroundWorker.execute();
        }
    }

    private void setUiActive(boolean activeState) {
        this.setCursor(activeState ? Cursor.getDefaultCursor() : Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        this.cbVerbsMethods.setEnabled(activeState);
        this.cbVerbsRelationships.setEnabled(activeState);
        this.cbArgoUml.setEnabled(activeState);
        this.cbRawXmi.setEnabled(activeState);
        this.cbStarUml.setEnabled(activeState);
        this.cbPng.setEnabled(activeState);
        this.cbSvg.setEnabled(activeState);

        this.buttonChooseKeywordsCsv.setEnabled(activeState);
        this.buttonChooseOutputFolder.setEnabled(activeState);
        this.buttonChooseTextCsv.setEnabled(activeState);
        this.buttonGenerateModels.setEnabled(activeState);
    }

    private boolean necessaryFieldsHaveLegalArguments() {
        if (textfieldCsvKeywords.getText() == null || !new File(textfieldCsvKeywords.getText()).isFile()) {
            JOptionPane.showMessageDialog(this, "Specify a valid csv input file containing the keywords");
            return false;
        }

        if (textfieldCsvText.getText() == null || !new File(textfieldCsvText.getText()).isFile()) {
            JOptionPane.showMessageDialog(this, "Specify a valid csv input file containing the text");
            return false;
        }

        if (textfieldOutputFolder.getText() == null || !new File(textfieldOutputFolder.getText()).isDirectory()) {
            JOptionPane.showMessageDialog(this, "Specify a valid folder for the generated models");
            return false;
        }

        if (!cbArgoUml.isSelected() && !cbRawXmi.isSelected() && !cbStarUml.isSelected() && !cbPng.isSelected() && !cbSvg.isSelected()) {
            JOptionPane.showMessageDialog(this, "Please select at least one output file type.");
            return false;
        }
        return true;
    }

}
