package moreredoc.application;

import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
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
    private static final String TEXT_CHOOSE_BUTTON = "Choose";
    /**
     * automatically generated
     */
    private static final long serialVersionUID = 618505241078949953L;
    private static final String APPLICATION_TITLE = "MoreRedoc";

    private static Logger logger = Logger.getLogger(MoreRedocGui.class);

    // ui elements
    private JTextField textfieldCsvKeywords;
    private JTextField textfieldCsvText;
    private JTextField textfieldOutputFolder;
    private JCheckBox cbRawXmi;
    private JCheckBox cbArgoUml;
    private JCheckBox cbStarUml;
    private JButton buttonGenerateModels;
    // private JButton buttonCancel;

    private Thread modelingThread;

    // preload file chooser, on demand would take noticeably long.
    private FutureTask<JFileChooser> futureFileChooser = new FutureTask<>(JFileChooser::new);

    public MoreRedocGui() {
        super(APPLICATION_TITLE);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(700, 400);

        JPanel mainPanel = new JPanel();

        Border border = BorderFactory.createDashedBorder(Color.BLACK);
        Border margin = new EmptyBorder(10, 10, 10, 10);
        mainPanel.setBorder(new CompoundBorder(border, margin));

        this.getContentPane().add(mainPanel, BorderLayout.CENTER);
        GridBagLayout gblPanel = new GridBagLayout();
        gblPanel.columnWidths = new int[]{0, 0, 0, 0};
        gblPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        gblPanel.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
        gblPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
                Double.MIN_VALUE};
        mainPanel.setLayout(gblPanel);

        JLabel labelKeywords = new JLabel("Keywords:");
        GridBagConstraints gbcLabelKeywords = new GridBagConstraints();
        gbcLabelKeywords.anchor = GridBagConstraints.WEST;
        gbcLabelKeywords.insets = new Insets(0, 0, 5, 5);
        gbcLabelKeywords.gridx = 0;
        gbcLabelKeywords.gridy = 0;
        mainPanel.add(labelKeywords, gbcLabelKeywords);

        textfieldCsvKeywords = new JTextField();
        textfieldCsvKeywords.setEnabled(false);
        GridBagConstraints gbcTextfieldCsvKeywords = new GridBagConstraints();
        gbcTextfieldCsvKeywords.insets = new Insets(0, 0, 5, 5);
        gbcTextfieldCsvKeywords.fill = GridBagConstraints.HORIZONTAL;
        gbcTextfieldCsvKeywords.gridx = 1;
        gbcTextfieldCsvKeywords.gridy = 0;
        mainPanel.add(textfieldCsvKeywords, gbcTextfieldCsvKeywords);
        textfieldCsvKeywords.setColumns(10);

        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(futureFileChooser);

        JButton buttonChooseKeywordsCsv = new JButton(TEXT_CHOOSE_BUTTON);
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
        gbcButton.gridy = 0;
        mainPanel.add(buttonChooseKeywordsCsv, gbcButton);

        JLabel labelCsvText = new JLabel("Text:");
        labelCsvText.setHorizontalAlignment(SwingConstants.CENTER);
        GridBagConstraints gbcLabelCsvText = new GridBagConstraints();
        gbcLabelCsvText.anchor = GridBagConstraints.WEST;
        gbcLabelCsvText.insets = new Insets(0, 0, 5, 5);
        gbcLabelCsvText.gridx = 0;
        gbcLabelCsvText.gridy = 1;
        mainPanel.add(labelCsvText, gbcLabelCsvText);

        textfieldCsvText = new JTextField();
        textfieldCsvText.setEnabled(false);
        GridBagConstraints gbcTextfieldCsvText = new GridBagConstraints();
        gbcTextfieldCsvText.insets = new Insets(0, 0, 5, 5);
        gbcTextfieldCsvText.fill = GridBagConstraints.HORIZONTAL;
        gbcTextfieldCsvText.gridx = 1;
        gbcTextfieldCsvText.gridy = 1;
        mainPanel.add(textfieldCsvText, gbcTextfieldCsvText);
        textfieldCsvText.setColumns(10);

        JButton buttonChooseTextCsv = new JButton(TEXT_CHOOSE_BUTTON);
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
        gbcButtonChooseTextCsv.gridy = 1;
        mainPanel.add(buttonChooseTextCsv, gbcButtonChooseTextCsv);

        JLabel labelOutputFolder = new JLabel("Output folder:");
        GridBagConstraints gbcLabelOutputFolder = new GridBagConstraints();
        gbcLabelOutputFolder.anchor = GridBagConstraints.WEST;
        gbcLabelOutputFolder.insets = new Insets(0, 0, 5, 5);
        gbcLabelOutputFolder.gridx = 0;
        gbcLabelOutputFolder.gridy = 2;
        mainPanel.add(labelOutputFolder, gbcLabelOutputFolder);

        textfieldOutputFolder = new JTextField();
        textfieldOutputFolder.setEnabled(false);
        GridBagConstraints gbcTextfieldOutputFolder = new GridBagConstraints();
        gbcTextfieldOutputFolder.insets = new Insets(0, 0, 5, 5);
        gbcTextfieldOutputFolder.fill = GridBagConstraints.HORIZONTAL;
        gbcTextfieldOutputFolder.gridx = 1;
        gbcTextfieldOutputFolder.gridy = 2;
        mainPanel.add(textfieldOutputFolder, gbcTextfieldOutputFolder);
        textfieldOutputFolder.setColumns(10);

        JButton buttonChooseOutputFolder = new JButton(TEXT_CHOOSE_BUTTON);
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
        gbcButtonChooseOutputFolder.gridy = 2;
        mainPanel.add(buttonChooseOutputFolder, gbcButtonChooseOutputFolder);

        JLabel labelSettings = new JLabel("Settings:");
        GridBagConstraints gbcLabelSettings = new GridBagConstraints();
        gbcLabelSettings.anchor = GridBagConstraints.WEST;
        gbcLabelSettings.insets = new Insets(0, 0, 5, 5);
        gbcLabelSettings.gridx = 0;
        gbcLabelSettings.gridy = 4;
        mainPanel.add(labelSettings, gbcLabelSettings);

     /*   JCheckBox chckbxNewCheckBox_3 = new JCheckBox("New check box");
        GridBagConstraints gbc_chckbxNewCheckBox_3 = new GridBagConstraints();
        gbc_chckbxNewCheckBox_3.anchor = GridBagConstraints.WEST;
        gbc_chckbxNewCheckBox_3.insets = new Insets(0, 0, 5, 5);
        gbc_chckbxNewCheckBox_3.gridx = 1;
        gbc_chckbxNewCheckBox_3.gridy = 4;
        mainPanel.add(chckbxNewCheckBox_3, gbc_chckbxNewCheckBox_3);

        JCheckBox chckbxNewCheckBox_4 = new JCheckBox("New check box");
        GridBagConstraints gbc_chckbxNewCheckBox_4 = new GridBagConstraints();
        gbc_chckbxNewCheckBox_4.anchor = GridBagConstraints.WEST;
        gbc_chckbxNewCheckBox_4.insets = new Insets(0, 0, 5, 5);
        gbc_chckbxNewCheckBox_4.gridx = 1;
        gbc_chckbxNewCheckBox_4.gridy = 5;
        mainPanel.add(chckbxNewCheckBox_4, gbc_chckbxNewCheckBox_4);
*/
        JLabel labelOutput = new JLabel("Output:");
        GridBagConstraints gbcLabelOutput = new GridBagConstraints();
        gbcLabelOutput.anchor = GridBagConstraints.WEST;
        gbcLabelOutput.insets = new Insets(0, 0, 5, 5);
        gbcLabelOutput.gridx = 0;
        gbcLabelOutput.gridy = 7;
        mainPanel.add(labelOutput, gbcLabelOutput);

        cbRawXmi = new JCheckBox("Raw XMI");
        GridBagConstraints gbcCbRawXmi = new GridBagConstraints();
        gbcCbRawXmi.anchor = GridBagConstraints.WEST;
        gbcCbRawXmi.insets = new Insets(0, 0, 5, 5);
        gbcCbRawXmi.gridx = 1;
        gbcCbRawXmi.gridy = 7;
        mainPanel.add(cbRawXmi, gbcCbRawXmi);

        cbArgoUml = new JCheckBox("XMI (ArgoUML)");
        GridBagConstraints gbcCbArgoUml = new GridBagConstraints();
        gbcCbArgoUml.anchor = GridBagConstraints.WEST;
        gbcCbArgoUml.insets = new Insets(0, 0, 5, 5);
        gbcCbArgoUml.gridx = 1;
        gbcCbArgoUml.gridy = 8;
        mainPanel.add(cbArgoUml, gbcCbArgoUml);

        cbStarUml = new JCheckBox("XMI (StarUML)");
        GridBagConstraints gbcCbStarUml = new GridBagConstraints();
        gbcCbStarUml.anchor = GridBagConstraints.WEST;
        gbcCbStarUml.insets = new Insets(0, 0, 5, 5);
        gbcCbStarUml.gridx = 1;
        gbcCbStarUml.gridy = 9;
        mainPanel.add(cbStarUml, gbcCbStarUml);

        buttonGenerateModels = new JButton("Run");
        buttonGenerateModels.setVerticalAlignment(SwingConstants.BOTTOM);
        buttonGenerateModels.addActionListener(e -> {
            try {
                buttonGenerateModels.setEnabled(false);
                runGenerateModel();
            } catch (Exception e1) {
                logger.error(e1);
            }
        });
        GridBagConstraints gbcButtonGenerateModels = new GridBagConstraints();
        gbcButtonGenerateModels.insets = new Insets(0, 0, 5, 0);
        gbcButtonGenerateModels.gridx = 2;
        gbcButtonGenerateModels.gridy = 11;
        mainPanel.add(buttonGenerateModels, gbcButtonGenerateModels);

        // buttonCancel = new JButton("Cancel");
        // buttonCancel.addActionListener( e -> {
        // if(modelingThread != null) {
        // modelingThread.interrupt();
        // }
        // });
        // GridBagConstraints gbcButtonCancel = new GridBagConstraints();
        // gbcButtonCancel.gridx = 2;
        // gbcButtonCancel.gridy = 12;
        // mainPanel.add(buttonCancel, gbcButtonCancel);
        // buttonCancel.setEnabled(false);
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

    private void runGenerateModel() throws InterruptedException {
        if (necessaryFieldsHaveLegalArguments()) {

            modelingThread = new Thread(() -> {
				try {
					MoreRedocLogicStarter.runMoreRedocLogic(textfieldCsvKeywords.getText(),
							textfieldCsvText.getText(), textfieldOutputFolder.getText());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					logger.error(e);
				}
			});

            modelingThread.start();

            // deactivate ui
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

            while (modelingThread.isAlive()) {
                Thread.sleep(1000L);
            }


            // activate ui again

        }
        buttonGenerateModels.setEnabled(true);

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
        return true;
    }

}
