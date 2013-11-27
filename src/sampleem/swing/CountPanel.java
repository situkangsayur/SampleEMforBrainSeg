/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 * 
 *  * The algorithm is described in the following paper:
 *         K. Van Leemput, F. Maes, D. Vandermeulen, P. Suetens, 
 *         A unifying framework for partial volume segmentation of brain MR images, 
 *         IEEE transactions on medical imaging, vol. 22, no. 1, pp. 105-119, January 2003
 *
 * The full algorithm is more general than what's implemented here, as it allows for multiple channels and more sophisticated 
 * spatial priors. 
 * 
 * This applet merely serves as a demonstration of the principle; hopefully it also convinces you that uniform 
 * priors on partial volume mixing fractions are not necessarily a good idea. See the paper for a full discussion.
 *
 * Please do send me your comments and suggestions.
 * 
 * Copyright 2007, Koen Van Leemput
 *
 */
package sampleem.swing;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Stroke;
import java.awt.event.ItemEvent;
import java.awt.image.BufferedImage;
import java.awt.image.MemoryImageSource;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JTextField;

import sampleem.swing.listener.Listener;

/**
 *
 * @author hendri
 */
public class CountPanel extends javax.swing.JPanel implements Listener {

    //=======================================================
    private JTextField mean1TextField;
    private MediaTracker media;
    private URL baseUrl;
    private ResultEntity result;
    private EMThread emRunner;
    //=======================================================
    private BufferedImage citraSrc;
    private Vector vectorImages;
    private Vector vectorContrasts;
    private Vector vectorDescriptions;
    private List<DataSetEntity> urlDataset;
    private int[] pixAll;
    //setinisialisasi
    //==============================================================
    private int numberOfGaussiansPerPV;
    private double[] valueMeans;
    private double[] valueVariances;
    private double[] valuePurePriors;
    private double[] valuePVPriors;
    private int valDownsamplingFactor = 2;
    //setImage
    //===============================================================
    String contrast;
    private Image tempImage;
    private int valMinimum;
    private int valMaximum;
    private int valImageWidth;
    private int valImageHeight;
    private int numberOfPixels;
    private int[] nPixelData;
    private int numberOfBins;
    private double[] histogram;
    private int numberOfActivePixels;
    private final int lowThreshold = 25;
    private final int highThreshold = 255;
    private int numberOfPVCluster;
    private final int numberOfCluster = 3;
    //calculate expectation
    //============================================
    private double[] evidenceVal;
    private Vector m_PureLikelihoodTimesPriors;
    private Vector m_PVLikelihoodTimesPriors;
    private double maxLikelihood = 0;
    //DrawPlot curve
    //=============================================
    //     private final int m_PlotWidth = panelHistogram.getPreferredSize().width;
//    private final int m_PlotHeight = panelHistogram.getPreferredSize().height; 494, 210
    private final int valPlotX = 0;
    private final int valPlotY = 0;
//    private final int m_PlotX = panelHistogram.getX();
//    private final int m_PlotY = panelHistogram.getY();
//    private final int valPlotWidth = 494;
//    private final int valPlotHeight = 210;
    private final int valPlotWidth = 660;
    private final int valPlotHeight = 210;
    private Image valuePlotBuffer;
    private Stroke histogramStroke;
    private Color histogramColor;
    private Stroke pureStroke;
    private Color pureColor;
    private Stroke subGaussianStroke;
    private Color subGaussianColor;
    private Stroke histPVStroke;
    private Color histPVColor;
    private Stroke histTotalStroke;
    private Color hitTotalColor;

    /**
     * Creates new form CountPanel
     */
    public CountPanel() {
//        emPanel = new EMAlgo();
        initComponents();
//        this.panelWhiteBorder2.add(emPanel);
        media = new MediaTracker(this);
        try {
            // getDocumentbase gets the applet path.
            baseUrl = new URL("file:" + System.getProperty("user.dir") + "/image");
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        }

        if (urlDataset == null) {
            urlDataset = new ArrayList<DataSetEntity>();
        }
//        loadData();
//        emRunner = new EMThread();
        result = new ResultEntity();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bluePanel1 = new com.widget.karisma.container.BluePanel();
        panelGlass1 = new com.widget.karisma.container.PanelGlass();
        panelWhiteBorder1 = new com.widget.karisma.container.PanelWhiteBorder();
        panelWhiteBorder2 = new com.widget.karisma.container.PanelWhiteBorder();
        textFieldMean0 = new com.widget.karisma.face.OvalTextField();
        textFieldSigma0 = new com.widget.karisma.face.OvalTextField();
        textFieldPure0 = new com.widget.karisma.face.OvalTextField();
        textFieldPvPrior0 = new com.widget.karisma.face.OvalTextField();
        textFieldMean1 = new com.widget.karisma.face.OvalTextField();
        textFieldSigma1 = new com.widget.karisma.face.OvalTextField();
        textFieldPure1 = new com.widget.karisma.face.OvalTextField();
        textFieldPvPrior1 = new com.widget.karisma.face.OvalTextField();
        textFieldMean2 = new com.widget.karisma.face.OvalTextField();
        textFieldSigma2 = new com.widget.karisma.face.OvalTextField();
        textFieldPure2 = new com.widget.karisma.face.OvalTextField();
        textFieldPvPrior2 = new com.widget.karisma.face.OvalTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        textAreaRest = new sampleem.swing.TextAreaGlass();
        panelWhiteBorder3 = new com.widget.karisma.container.PanelWhiteBorder();
        buttonInit = new com.karisma.widget.gradienbutton.ButtonGradient();
        buttonStart = new com.karisma.widget.gradienbutton.ButtonGradient();
        jScrollPane1 = new javax.swing.JScrollPane();
        imageOtak = new javax.swing.JLabel();
        comboImage = new com.widget.karisma.face.OvalCombobox();
        subGaussian = new javax.swing.JCheckBox();
        panelHistogram = new sampleem.swing.HistogramView();
        imageHist = new javax.swing.JLabel();
        buttonStop = new com.karisma.widget.gradienbutton.ButtonGradient();
        textFieldDownSampling = new com.widget.karisma.face.OvalTextField();
        isShowRest = new javax.swing.JCheckBox();
        jLabel5 = new javax.swing.JLabel();
        panelWhiteBorder4 = new com.widget.karisma.container.PanelWhiteBorder();
        panelWhiteBorder6 = new com.widget.karisma.container.PanelWhiteBorder();
        jScrollPane2 = new javax.swing.JScrollPane();
        imgRest1 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        imgRest4 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        imgRest2 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        imgRest5 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        imgRest3 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        imgRest6 = new javax.swing.JLabel();
        panelWhiteBorder5 = new com.widget.karisma.container.PanelWhiteBorder();
        jScrollPane9 = new javax.swing.JScrollPane();
        imgRestAll = new javax.swing.JLabel();

        setLayout(new java.awt.BorderLayout());

        bluePanel1.setLayout(new java.awt.BorderLayout());

        panelGlass1.setLayout(new java.awt.BorderLayout());

        panelWhiteBorder1.setLayout(new java.awt.BorderLayout());

        panelWhiteBorder2.setPreferredSize(new java.awt.Dimension(823, 140));

        textFieldMean0.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        textFieldMean0.setText("0");
        textFieldMean0.setToolTipText("");
        textFieldMean0.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textFieldMean0ActionPerformed(evt);
            }
        });

        textFieldSigma0.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        textFieldSigma0.setText("0");
        textFieldSigma0.setToolTipText("");
        textFieldSigma0.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textFieldSigma0ActionPerformed(evt);
            }
        });

        textFieldPure0.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        textFieldPure0.setText("0");
        textFieldPure0.setToolTipText("");
        textFieldPure0.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textFieldPure0ActionPerformed(evt);
            }
        });

        textFieldPvPrior0.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        textFieldPvPrior0.setText("0");
        textFieldPvPrior0.setToolTipText("");
        textFieldPvPrior0.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textFieldPvPrior0ActionPerformed(evt);
            }
        });

        textFieldMean1.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        textFieldMean1.setText("0");
        textFieldMean1.setToolTipText("");
        textFieldMean1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textFieldMean1ActionPerformed(evt);
            }
        });

        textFieldSigma1.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        textFieldSigma1.setText("0");
        textFieldSigma1.setToolTipText("");
        textFieldSigma1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textFieldSigma1ActionPerformed(evt);
            }
        });

        textFieldPure1.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        textFieldPure1.setText("0");
        textFieldPure1.setToolTipText("");
        textFieldPure1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textFieldPure1ActionPerformed(evt);
            }
        });

        textFieldPvPrior1.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        textFieldPvPrior1.setText("0");
        textFieldPvPrior1.setToolTipText("");
        textFieldPvPrior1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textFieldPvPrior1ActionPerformed(evt);
            }
        });

        textFieldMean2.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        textFieldMean2.setText("0");
        textFieldMean2.setToolTipText("");
        textFieldMean2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textFieldMean2ActionPerformed(evt);
            }
        });

        textFieldSigma2.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        textFieldSigma2.setText("0");
        textFieldSigma2.setToolTipText("");
        textFieldSigma2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textFieldSigma2ActionPerformed(evt);
            }
        });

        textFieldPure2.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        textFieldPure2.setText("0");
        textFieldPure2.setToolTipText("");
        textFieldPure2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textFieldPure2ActionPerformed(evt);
            }
        });

        textFieldPvPrior2.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        textFieldPvPrior2.setText("0");
        textFieldPvPrior2.setToolTipText("");
        textFieldPvPrior2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textFieldPvPrior2ActionPerformed(evt);
            }
        });

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Standard Deviasi");

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel2.setText("Mean");
        jLabel2.setToolTipText("");

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("CSV");

        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("CSF GM");

        javax.swing.GroupLayout panelWhiteBorder2Layout = new javax.swing.GroupLayout(panelWhiteBorder2);
        panelWhiteBorder2.setLayout(panelWhiteBorder2Layout);
        panelWhiteBorder2Layout.setHorizontalGroup(
            panelWhiteBorder2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelWhiteBorder2Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(panelWhiteBorder2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(panelWhiteBorder2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(textFieldMean0, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textFieldSigma0, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textFieldPure0, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textFieldPvPrior0, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addGroup(panelWhiteBorder2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(textFieldSigma1, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textFieldMean1, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textFieldPure1, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textFieldPvPrior1, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(panelWhiteBorder2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(textFieldPvPrior2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(textFieldPure2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(textFieldSigma2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(textFieldMean2, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(39, 39, 39)
                .addComponent(textAreaRest, javax.swing.GroupLayout.PREFERRED_SIZE, 353, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(90, Short.MAX_VALUE))
        );
        panelWhiteBorder2Layout.setVerticalGroup(
            panelWhiteBorder2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelWhiteBorder2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelWhiteBorder2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(textAreaRest, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelWhiteBorder2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(panelWhiteBorder2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelWhiteBorder2Layout.createSequentialGroup()
                                .addGroup(panelWhiteBorder2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(textFieldSigma0, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(textFieldSigma1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panelWhiteBorder2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(textFieldPure0, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(textFieldPure1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panelWhiteBorder2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(textFieldPvPrior0, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(textFieldPvPrior1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(panelWhiteBorder2Layout.createSequentialGroup()
                                .addGap(33, 33, 33)
                                .addComponent(jLabel3)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel4)))
                        .addGroup(panelWhiteBorder2Layout.createSequentialGroup()
                            .addGroup(panelWhiteBorder2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(textFieldMean2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(textFieldMean0, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(textFieldMean1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel2))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(textFieldSigma2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(textFieldPure2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(textFieldPvPrior2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        panelWhiteBorder2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel1, jLabel2, jLabel3, jLabel4});

        panelWhiteBorder1.add(panelWhiteBorder2, java.awt.BorderLayout.CENTER);

        panelWhiteBorder3.setPreferredSize(new java.awt.Dimension(823, 308));

        buttonInit.setText("init");
        buttonInit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonInitActionPerformed(evt);
            }
        });

        buttonStart.setText("Start");
        buttonStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonStartActionPerformed(evt);
            }
        });

        imageOtak.setForeground(new java.awt.Color(255, 255, 255));
        imageOtak.setText("                          IMAGE SATU");
        jScrollPane1.setViewportView(imageOtak);

        comboImage.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboImageItemStateChanged(evt);
            }
        });
        comboImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboImageActionPerformed(evt);
            }
        });

        subGaussian.setForeground(new java.awt.Color(255, 255, 255));
        subGaussian.setSelected(true);
        subGaussian.setText("Sub Gaussian");
        subGaussian.setOpaque(false);

        imageHist.setForeground(new java.awt.Color(255, 255, 255));
        imageHist.setText("Histogram");

        javax.swing.GroupLayout panelHistogramLayout = new javax.swing.GroupLayout(panelHistogram);
        panelHistogram.setLayout(panelHistogramLayout);
        panelHistogramLayout.setHorizontalGroup(
            panelHistogramLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelHistogramLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(imageHist, javax.swing.GroupLayout.DEFAULT_SIZE, 659, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelHistogramLayout.setVerticalGroup(
            panelHistogramLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelHistogramLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(imageHist, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        buttonStop.setText("Stop");
        buttonStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonStopActionPerformed(evt);
            }
        });

        textFieldDownSampling.setText("0");
        textFieldDownSampling.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textFieldDownSamplingActionPerformed(evt);
            }
        });

        isShowRest.setForeground(new java.awt.Color(255, 255, 255));
        isShowRest.setText("Show Result in Text");
        isShowRest.setOpaque(false);
        isShowRest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                isShowRestActionPerformed(evt);
            }
        });

        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel5.setText("down Sampling");
        jLabel5.setToolTipText("");

        javax.swing.GroupLayout panelWhiteBorder3Layout = new javax.swing.GroupLayout(panelWhiteBorder3);
        panelWhiteBorder3.setLayout(panelWhiteBorder3Layout);
        panelWhiteBorder3Layout.setHorizontalGroup(
            panelWhiteBorder3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelWhiteBorder3Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(panelWhiteBorder3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 338, Short.MAX_VALUE)
                    .addComponent(comboImage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelWhiteBorder3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelWhiteBorder3Layout.createSequentialGroup()
                        .addComponent(buttonStart, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonStop, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelWhiteBorder3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(subGaussian)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelWhiteBorder3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(textFieldDownSampling, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(isShowRest))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 6, Short.MAX_VALUE)
                        .addComponent(buttonInit, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(51, 51, 51))
                    .addGroup(panelWhiteBorder3Layout.createSequentialGroup()
                        .addComponent(panelHistogram, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        panelWhiteBorder3Layout.setVerticalGroup(
            panelWhiteBorder3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelWhiteBorder3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelWhiteBorder3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(panelHistogram, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(panelWhiteBorder3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelWhiteBorder3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelWhiteBorder3Layout.createSequentialGroup()
                            .addGap(4, 4, 4)
                            .addGroup(panelWhiteBorder3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(comboImage, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(buttonStart, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(buttonStop, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(panelWhiteBorder3Layout.createSequentialGroup()
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(panelWhiteBorder3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(subGaussian)
                                .addComponent(isShowRest))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(panelWhiteBorder3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(textFieldDownSampling, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel5))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelWhiteBorder3Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(buttonInit, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(14, 14, 14))
        );

        panelWhiteBorder1.add(panelWhiteBorder3, java.awt.BorderLayout.PAGE_START);

        panelWhiteBorder4.setPreferredSize(new java.awt.Dimension(823, 270));
        panelWhiteBorder4.setLayout(new javax.swing.BoxLayout(panelWhiteBorder4, javax.swing.BoxLayout.LINE_AXIS));

        panelWhiteBorder6.setPreferredSize(new java.awt.Dimension(823, 270));

        imgRest1.setForeground(new java.awt.Color(255, 255, 255));
        imgRest1.setText("                          IMAGE SATU");
        jScrollPane2.setViewportView(imgRest1);

        imgRest4.setForeground(new java.awt.Color(255, 255, 255));
        imgRest4.setText("                          IMAGE SATU");
        jScrollPane3.setViewportView(imgRest4);

        imgRest2.setForeground(new java.awt.Color(255, 255, 255));
        imgRest2.setText("                          IMAGE SATU");
        jScrollPane4.setViewportView(imgRest2);

        imgRest5.setForeground(new java.awt.Color(255, 255, 255));
        imgRest5.setText("                          IMAGE SATU");
        jScrollPane5.setViewportView(imgRest5);

        imgRest3.setForeground(new java.awt.Color(255, 255, 255));
        imgRest3.setText("                          IMAGE SATU");
        jScrollPane6.setViewportView(imgRest3);

        imgRest6.setForeground(new java.awt.Color(255, 255, 255));
        imgRest6.setText("                          IMAGE SATU");
        jScrollPane7.setViewportView(imgRest6);

        javax.swing.GroupLayout panelWhiteBorder6Layout = new javax.swing.GroupLayout(panelWhiteBorder6);
        panelWhiteBorder6.setLayout(panelWhiteBorder6Layout);
        panelWhiteBorder6Layout.setHorizontalGroup(
            panelWhiteBorder6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelWhiteBorder6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelWhiteBorder6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 242, Short.MAX_VALUE)
                    .addComponent(jScrollPane3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 45, Short.MAX_VALUE)
                .addGroup(panelWhiteBorder6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36)
                .addGroup(panelWhiteBorder6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 219, Short.MAX_VALUE)
                    .addComponent(jScrollPane7))
                .addGap(29, 29, 29))
        );
        panelWhiteBorder6Layout.setVerticalGroup(
            panelWhiteBorder6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelWhiteBorder6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelWhiteBorder6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE)
                    .addComponent(jScrollPane4)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelWhiteBorder6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane7)
                    .addComponent(jScrollPane5)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelWhiteBorder6Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        panelWhiteBorder4.add(panelWhiteBorder6);

        imgRestAll.setForeground(new java.awt.Color(255, 255, 255));
        imgRestAll.setText("                          IMAGE SATU");
        jScrollPane9.setViewportView(imgRestAll);

        javax.swing.GroupLayout panelWhiteBorder5Layout = new javax.swing.GroupLayout(panelWhiteBorder5);
        panelWhiteBorder5.setLayout(panelWhiteBorder5Layout);
        panelWhiteBorder5Layout.setHorizontalGroup(
            panelWhiteBorder5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelWhiteBorder5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 253, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelWhiteBorder5Layout.setVerticalGroup(
            panelWhiteBorder5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelWhiteBorder5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE)
                .addContainerGap())
        );

        panelWhiteBorder4.add(panelWhiteBorder5);

        panelWhiteBorder1.add(panelWhiteBorder4, java.awt.BorderLayout.PAGE_END);

        panelGlass1.add(panelWhiteBorder1, java.awt.BorderLayout.CENTER);

        bluePanel1.add(panelGlass1, java.awt.BorderLayout.CENTER);

        add(bluePanel1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void textFieldPure0ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textFieldPure0ActionPerformed
        // TODO add your handling code here:
        double newPrior = Double.parseDouble(textFieldPure0.getText()) / 100;
        this.SetPrior(0, newPrior);
    }//GEN-LAST:event_textFieldPure0ActionPerformed

    private void textFieldPure1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textFieldPure1ActionPerformed
        // TODO add your handling code here:
        double newPrior = Double.parseDouble(textFieldPure1.getText()) / 100;
        this.SetPrior(1, newPrior);
    }//GEN-LAST:event_textFieldPure1ActionPerformed

    private void textFieldPure2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textFieldPure2ActionPerformed
        // TODO add your handling code here:
        double newPrior = Double.parseDouble(textFieldPure2.getText()) / 100;
        this.SetPrior(2, newPrior);
    }//GEN-LAST:event_textFieldPure2ActionPerformed

    private void buttonInitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonInitActionPerformed
        // TODO add your handling code here:
        this.stopEM();
        this.setInitialParametersToDefault();
        paintGraph();
    }//GEN-LAST:event_buttonInitActionPerformed

    private void buttonStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonStartActionPerformed
        // TODO add your handling code here:
        this.startEM();
        paintGraph();
    }//GEN-LAST:event_buttonStartActionPerformed

    private void comboImageItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboImageItemStateChanged
        // TODO add your handling code here:
        debugMessage(comboImage.getSelectedIndex() + " : " + comboImage.getSelectedItem());
        pixAll = null;
        try {
            citraSrc = ImageIO.read(new File(urlDataset.get(comboImage.getSelectedIndex()).getUri()));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        imageOtak.setText("");
//            temp = citraDua.getScaledInstance(drawPanel1.getWidth(), drawPanel1.getHeight(), citraDua.SCALE_FAST);
        imageOtak.setIcon(new ImageIcon(citraSrc));
        if (!vectorImages.isEmpty()) {
            SetImage(comboImage.getSelectedIndex());
        }
    }//GEN-LAST:event_comboImageItemStateChanged

    private void textFieldMean0ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textFieldMean0ActionPerformed
        // TODO add your handling code here:
        valueMeans[0] = Double.parseDouble(textFieldMean0.getText());
        this.calculateExpectation();
        paintGraph();
    }//GEN-LAST:event_textFieldMean0ActionPerformed

    private void textFieldMean1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textFieldMean1ActionPerformed
        // TODO add your handling code here:
        valueMeans[1] = Double.parseDouble(textFieldMean1.getText());
        this.calculateExpectation();
        paintGraph();
    }//GEN-LAST:event_textFieldMean1ActionPerformed

    private void textFieldMean2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textFieldMean2ActionPerformed
        // TODO add your handling code here:
        valueMeans[2] = Double.parseDouble(textFieldMean2.getText());
        this.calculateExpectation();
        paintGraph();
    }//GEN-LAST:event_textFieldMean2ActionPerformed

    private void textFieldSigma0ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textFieldSigma0ActionPerformed
        // TODO add your handling code here:
        valueVariances[0] = Math.pow(Double.parseDouble(textFieldSigma0.getText()), 2);
        this.calculateExpectation();
        paintGraph();
    }//GEN-LAST:event_textFieldSigma0ActionPerformed

    private void textFieldSigma1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textFieldSigma1ActionPerformed
        // TODO add your handling code here:
        valueVariances[1] = Math.pow(Double.parseDouble(textFieldSigma1.getText()), 2);
        this.calculateExpectation();
        paintGraph();
    }//GEN-LAST:event_textFieldSigma1ActionPerformed

    private void textFieldSigma2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textFieldSigma2ActionPerformed
        // TODO add your handling code here:
        valueVariances[2] = Math.pow(Double.parseDouble(textFieldSigma2.getText()), 2);
        this.calculateExpectation();
        paintGraph();
    }//GEN-LAST:event_textFieldSigma2ActionPerformed

    private void textFieldPvPrior0ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textFieldPvPrior0ActionPerformed
        // TODO add your handling code here:
        double newPrior = Double.parseDouble(textFieldPvPrior0.getText()) / 100;
        this.SetPrior(3, newPrior);
    }//GEN-LAST:event_textFieldPvPrior0ActionPerformed

    private void textFieldPvPrior1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textFieldPvPrior1ActionPerformed
        // TODO add your handling code here:
        double newPrior = Double.parseDouble(textFieldPvPrior1.getText()) / 100;
        this.SetPrior(4, newPrior);
    }//GEN-LAST:event_textFieldPvPrior1ActionPerformed

    private void textFieldPvPrior2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textFieldPvPrior2ActionPerformed
        // TODO add your handling code here:
        double newPrior = Double.parseDouble(textFieldPvPrior2.getText()) / 100;
        this.SetPrior(5, newPrior);
    }//GEN-LAST:event_textFieldPvPrior2ActionPerformed

    private void comboImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboImageActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboImageActionPerformed

    private void buttonStopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonStopActionPerformed
        // TODO add your handling code here:
        stopEM();
    }//GEN-LAST:event_buttonStopActionPerformed

    private void textFieldDownSamplingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textFieldDownSamplingActionPerformed
        // TODO add your handling code here:
        try {
            valDownsamplingFactor = Integer.parseInt(textFieldDownSampling.getText());
        } catch (NumberFormatException exception) {
            //Use default value.
            valDownsamplingFactor = 2;
        }
        if (valDownsamplingFactor < 1) {
            valDownsamplingFactor = 1;
        } else if (valDownsamplingFactor > 5) // This limitation is not theoretically necessary, but makes the applet fool proof. A factor of 5 already results in 24 PV fractions!
        {
            valDownsamplingFactor = 5;
        }
        textFieldDownSampling.setText("" + valDownsamplingFactor);
        this.setInitialParametersToDefault();
        paintGraph();
    }//GEN-LAST:event_textFieldDownSamplingActionPerformed

    private void isShowRestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_isShowRestActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_isShowRestActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.widget.karisma.container.BluePanel bluePanel1;
    private com.karisma.widget.gradienbutton.ButtonGradient buttonInit;
    private com.karisma.widget.gradienbutton.ButtonGradient buttonStart;
    private com.karisma.widget.gradienbutton.ButtonGradient buttonStop;
    private com.widget.karisma.face.OvalCombobox comboImage;
    private javax.swing.JLabel imageHist;
    private javax.swing.JLabel imageOtak;
    private javax.swing.JLabel imgRest1;
    private javax.swing.JLabel imgRest2;
    private javax.swing.JLabel imgRest3;
    private javax.swing.JLabel imgRest4;
    private javax.swing.JLabel imgRest5;
    private javax.swing.JLabel imgRest6;
    private javax.swing.JLabel imgRestAll;
    private javax.swing.JCheckBox isShowRest;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane9;
    private com.widget.karisma.container.PanelGlass panelGlass1;
    private sampleem.swing.HistogramView panelHistogram;
    private com.widget.karisma.container.PanelWhiteBorder panelWhiteBorder1;
    private com.widget.karisma.container.PanelWhiteBorder panelWhiteBorder2;
    private com.widget.karisma.container.PanelWhiteBorder panelWhiteBorder3;
    private com.widget.karisma.container.PanelWhiteBorder panelWhiteBorder4;
    private com.widget.karisma.container.PanelWhiteBorder panelWhiteBorder5;
    private com.widget.karisma.container.PanelWhiteBorder panelWhiteBorder6;
    private javax.swing.JCheckBox subGaussian;
    private sampleem.swing.TextAreaGlass textAreaRest;
    private com.widget.karisma.face.OvalTextField textFieldDownSampling;
    private com.widget.karisma.face.OvalTextField textFieldMean0;
    private com.widget.karisma.face.OvalTextField textFieldMean1;
    private com.widget.karisma.face.OvalTextField textFieldMean2;
    private com.widget.karisma.face.OvalTextField textFieldPure0;
    private com.widget.karisma.face.OvalTextField textFieldPure1;
    private com.widget.karisma.face.OvalTextField textFieldPure2;
    private com.widget.karisma.face.OvalTextField textFieldPvPrior0;
    private com.widget.karisma.face.OvalTextField textFieldPvPrior1;
    private com.widget.karisma.face.OvalTextField textFieldPvPrior2;
    private com.widget.karisma.face.OvalTextField textFieldSigma0;
    private com.widget.karisma.face.OvalTextField textFieldSigma1;
    private com.widget.karisma.face.OvalTextField textFieldSigma2;
    // End of variables declaration//GEN-END:variables
//    private EMAlgo emPanel;

    @Override
    public void start(ResultEntity resultEntity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void initSystem() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void loadData() {
        if (result == null) {
            result = new ResultEntity();
        }

        vectorImages = new Vector();
        vectorContrasts = new Vector();
        vectorDescriptions = new Vector();

        for (int i = 1;; i++) {
//            String imageFileName = "imageFileName" + i;
//            if (imageFileName == null) {
//                break;
//            }

            try {
                debugMessage(i + ":" + baseUrl.toString() + "/imageFIleName" + i);
                citraSrc = ImageIO.read(new File(new URI(baseUrl.toString() + "/imageFIleName" + i + ".jpg")));
                DataSetEntity temp = new DataSetEntity();
                temp.setName("Image-" + i);
                temp.setUri(new URI(baseUrl.toString() + "/imageFIleName" + i + ".jpg"));
                urlDataset.add(temp);
                comboImage.addItem(temp.getName());

            } catch (IOException ex) {
                ex.printStackTrace();
                debugMessage(ex.getMessage());
                break;
            } catch (URISyntaxException ex) {
                Logger.getLogger(CountPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
//            DebugMessage("imageFileName" + i + ": " + imageFileName);
//            Image image = getImage(m_BaseURL, imageFileName);
            vectorImages.add(citraSrc);
//            m_Images.add(image);
//            Image image = citraSrc;
            // tell the MediaTracker to keep an eye on this image, and give it an ID;
            media.addImage(citraSrc, i);

            // Also read the contrast and the description
            vectorContrasts.add("imageFileName" + i);
            vectorDescriptions.add("deskripsi " + i);
            imageOtak.setText("");
//            temp = citraDua.getScaledInstance(drawPanel1.getWidth(), drawPanel1.getHeight(), citraDua.SCALE_FAST);
            imageOtak.setIcon(new ImageIcon(citraSrc));
        }

        // Now tell the mediaTracker to stop the applet execution
        // (in this example don't paint) until the images are fully loaded.
        // Must be in a try catch block.
        try {
            media.waitForAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        this.setUpGraphics();
        debugMessage(Integer.toString(comboImage.getSelectedIndex()));
        this.SetImage(comboImage.getSelectedIndex());

    }

    public void SetImage(int imageNumber) {
        contrast = (String) vectorContrasts.get(imageNumber);
        tempImage = (Image) vectorImages.get(imageNumber);

        // Turn into BufferedImage
        BufferedImage bufferedImage = new BufferedImage(tempImage.getWidth(null), tempImage.getHeight(null),
                BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = bufferedImage.createGraphics();
        g2d.drawImage(tempImage, 0, 0, null);

        // Get access to the pixel data
        Raster raster = bufferedImage.getData();
        //DataBuffer  dataBuffer = raster.getDataBuffer();
        valImageWidth = raster.getWidth();
        valImageHeight = raster.getHeight();
        numberOfPixels = valImageWidth * valImageHeight;
        int[] rgbArray = new int[numberOfPixels * 3];
        raster.getPixels(0 /* x */, 0 /* y */, raster.getWidth(), raster.getHeight(), rgbArray);

        // Get only the red component
        nPixelData = new int[numberOfPixels];
        for (int i = 0; i < numberOfPixels; i++) {
//            DebugMessage("rgb array : " + rgbArray[ i * 3]);
            nPixelData[ i] = rgbArray[ i * 3];
        }


        //DebugMessage( "pixel data: " );
        //for ( int i=0; i < m_NumberOfPixels; i++ )
        //  {
        //  DebugMessage( i + ": " + m_PixelData[ i ] );
        //  }


        // Get the minimum and maximum
        valMinimum = 1000000000;
        valMaximum = 0;
//        DebugMessage("jumlah pixel : " + m_NumberOfPixels);
        for (int i = 0; i < numberOfPixels; i++) {
//            DebugMessage("valMinimum: " + nPixelData[i]);
            if (nPixelData[ i] > valMaximum) {
                valMaximum = nPixelData[ i];
            } else if (nPixelData[ i] < valMinimum) {
                valMinimum = nPixelData[ i];
            }
        }
        debugMessage("valMinimum: " + valMinimum);
        debugMessage("valMaximum: " + valMaximum);


        // Build histogram
        numberOfBins = valMaximum - valMinimum + 1;
        histogram = new double[numberOfBins];
        for (int j = 0; j < numberOfBins; j++) {
            histogram[ j] = 0;
        }
        numberOfActivePixels = 0;
        for (int i = 0; i < numberOfPixels; i++) {
            if ((nPixelData[ i] > lowThreshold) && (nPixelData[ i] < highThreshold)) {
                histogram[ nPixelData[ i] - valMinimum] += 1;
                numberOfActivePixels++;
            }
        }
        for (int j = 0; j < numberOfBins; j++) {
            histogram[ j] /= numberOfActivePixels;
        }


        // Calculate number of PV cluster
        numberOfPVCluster = 0;
        for (int clussterNumber = 0; clussterNumber < numberOfCluster; clussterNumber++) {
            for (int clusterNumber2 = clussterNumber + 1; clusterNumber2 < numberOfCluster; clusterNumber2++) {
                numberOfPVCluster++;
            }
        }

        // 
        this.setInitialParametersToDefault();
        paintGraph();
    }

    public void setInitialParametersToDefault() {
        // Calculate number of Gaussians per PV cluster
        numberOfGaussiansPerPV = valDownsamplingFactor * valDownsamplingFactor - 1;
        //DebugMessage( "m_NumberOfGaussiansPerPV: " + m_NumberOfGaussiansPerPV );

        // Initialize parameters    
        valueMeans = new double[numberOfCluster];
        valueVariances = new double[numberOfCluster];
        valuePurePriors = new double[numberOfCluster];
        valuePVPriors = new double[numberOfPVCluster];
        for (int clusterNumber = 0; clusterNumber < numberOfCluster; clusterNumber++) {
            /*      m_Means[ clusterNumber ] = valMinimum + ( ( valMaximum - valMinimum ) / ( ( double )( numberOfCluster + 1 ) ) ) * 
             ( clusterNumber + 1 );*/
            valueMeans[ clusterNumber] = valMinimum + ((valMaximum - valMinimum) / ((double) (numberOfCluster)))
                    * (clusterNumber + 0.5);
            valueVariances[ clusterNumber] = Math.pow((valMaximum - valMinimum) / 3.0, 2);
            if (numberOfGaussiansPerPV != 0) {
                valuePurePriors[ clusterNumber] = 1 / ((double) (numberOfCluster + numberOfPVCluster));
            } else {
                valuePurePriors[ clusterNumber] = 1 / ((double) (numberOfCluster));
            }
        }
        for (int pvClusterNumber = 0; pvClusterNumber < numberOfPVCluster; pvClusterNumber++) {
            if (numberOfGaussiansPerPV != 0) {
                valuePVPriors[ pvClusterNumber] = 1 / ((double) (numberOfCluster + numberOfPVCluster));
            } else {
                valuePVPriors[ pvClusterNumber] = 0;
            }
        }


        // Depending on the contrast, swap initial means to keep the labels in the applet (WM/GM/CSF) relevant
        debugMessage("Adjusting for contrast " + contrast);
        showResultMessage("Adjusting for contrast " + contrast);
        String compareString = "imageFileName2";
        if (contrast.matches(compareString)) {
            debugMessage("swapping");
            double tmp = valueMeans[ 0];
            valueMeans[ 0] = valueMeans[ 2];
            valueMeans[ 2] = tmp;
//            System.out.println("wooowwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww");
        }

        // Recalculate the initial cluster
        calculateExpectation();
    }

    public void calculateExpectation() {
        //
        // Expectation step: cluster
        debugMessage("m number bins :" + numberOfBins);
        showResultMessage("====Expectation Calc=====");
        evidenceVal = new double[numberOfBins];
        for (int i = valMinimum; i < (valMaximum + 1); i++) {
            evidenceVal[ i - valMinimum] = 0;
        }

        // Calculate the contributions of the pure cluster
        m_PureLikelihoodTimesPriors = new Vector();
        for (int clusterNumber = 0; clusterNumber < numberOfCluster; clusterNumber++) {
            // Create the pure Gaussian
            final double mean = valueMeans[ clusterNumber];
            final double variance = valueVariances[ clusterNumber];
            final double prior = valuePurePriors[ clusterNumber];
            double[] likelihoodTimesPrior = new double[numberOfBins];
            for (int i = valMinimum; i < (valMaximum + 1); i++) {
                likelihoodTimesPrior[ i - valMinimum] = 1 / Math.sqrt(2 * Math.PI * variance)
                        * Math.exp(-Math.pow(i - mean, 2) / variance / 2) * prior;
                showResultMessage("Likelihood : " + likelihoodTimesPrior[ i - valMinimum]);
                evidenceVal[ i - valMinimum] += likelihoodTimesPrior[ i - valMinimum];
                showResultMessage("Evidence : " + evidenceVal[ i - valMinimum]);
            }
            // 
            m_PureLikelihoodTimesPriors.add(likelihoodTimesPrior);
            showResultMessage("likelihoodTimesPrior : " + likelihoodTimesPrior);
        }

        showResultMessage("===PV Cluster Calc===");
        // Calculate the contributions of the PV cluster
        m_PVLikelihoodTimesPriors = new Vector();
        int pvClusterNumber = -1;
        for (int clusterNumber1 = 0; clusterNumber1 < numberOfCluster; clusterNumber1++) {
            for (int clusterNumber2 = clusterNumber1 + 1; clusterNumber2 < numberOfCluster; clusterNumber2++) {
                pvClusterNumber++;

                // Retrieve parameters of the pure tissue cluster this PV cluster is mixing for
                final double mean1 = valueMeans[ clusterNumber1];
                final double variance1 = valueVariances[ clusterNumber1];
                final double mean2 = valueMeans[ clusterNumber2];
                final double variance2 = valueVariances[ clusterNumber2];

                final double prior = valuePVPriors[ pvClusterNumber] / numberOfGaussiansPerPV;

                // Loop over all sub-Gaussians of this PV cluster
                Vector likelihoodTimesPriorsForThisPV = new Vector();
                for (int gaussianNumber = 0; gaussianNumber < numberOfGaussiansPerPV; gaussianNumber++) {
                    // Retrieve mean and variance for this sub-Gaussian
                    final double alpha = (gaussianNumber + 1) / ((double) (valDownsamplingFactor * valDownsamplingFactor));
                    final double mean = alpha * mean1 + (1 - alpha) * mean2;
                    final double variance = alpha * variance1 + (1 - alpha) * variance2;

                    double[] likelihoodTimesPrior = new double[numberOfBins];
                    for (int i = valMinimum; i < (valMaximum + 1); i++) {
                        final double tmp = 1 / Math.sqrt(2 * Math.PI * variance) * Math.exp(-Math.pow(i - mean, 2) / variance / 2) * prior;
                        likelihoodTimesPrior[ i - valMinimum] = tmp;
                        evidenceVal[ i - valMinimum] += tmp;
                    }
                    showResultMessage("likelihoodTimesPrior : " + likelihoodTimesPrior);
                    likelihoodTimesPriorsForThisPV.add(likelihoodTimesPrior);
                }
                showResultMessage("===============================");
                m_PVLikelihoodTimesPriors.add(likelihoodTimesPriorsForThisPV);
                showResultMessage("PV likelihoodTimesPrior : " + likelihoodTimesPriorsForThisPV);
            }

        }



        //
        // Show the current value of the objective function
        //
        maxLikelihood = 0;
        for (int i = valMinimum; i < (valMaximum + 1); i++) {
            maxLikelihood -= Math.log(evidenceVal[ i - valMinimum]) * histogram[ i - valMinimum];
            debugMessage(" m minimum : " + valMinimum);
            debugMessage("m eviden :" + evidenceVal[ i - valMinimum] + " --- " + "m histogram :" + histogram[ i - valMinimum]);
            debugMessage("log : " + Math.log(evidenceVal[ i - valMinimum]) * histogram[ i - valMinimum]);
            debugMessage("looping cost: " + maxLikelihood);
        }
        maxLikelihood *= numberOfActivePixels;
        debugMessage("Current cost: " + maxLikelihood);
    }

    public void calculateMaximization() {
        showResultMessage("===Maximization Calc===");
        // 
        // Maximization step: update the parameters
        //
        double[] totalWeights = new double[numberOfCluster];
        double[] means = new double[numberOfCluster];
        double[] variancesTerm1 = new double[numberOfCluster];
        double[] variancesTerm2 = new double[numberOfCluster];
        double[] variancesTerm3 = new double[numberOfCluster];
        double[] purePriors = new double[numberOfCluster];
        for (int clusterNumber = 0; clusterNumber < numberOfCluster; clusterNumber++) {
            totalWeights[ clusterNumber] = 0;
            means[ clusterNumber] = 0;
            variancesTerm1[ clusterNumber] = 0;
            variancesTerm2[ clusterNumber] = 0;
            variancesTerm3[ clusterNumber] = 0;
            purePriors[ clusterNumber] = 0;
        }
        double[] PVPriors = new double[numberOfPVCluster];
        for (int pvClusterNumber = 0; pvClusterNumber < numberOfCluster; pvClusterNumber++) {
            PVPriors[ pvClusterNumber] = 0;
        }

        // Contribution of pure cluster  
        for (int clusterNumber = 0; clusterNumber < numberOfCluster; clusterNumber++) {
            double[] likelihoodTimesPrior = (double[]) m_PureLikelihoodTimesPriors.get(clusterNumber);

            double meanContribution = 0;
            double varianceContributionTerm1 = 0;
            double varianceContributionTerm2 = 0;
            double varianceContributionTerm3 = 0;
            double total = 0;
            final double M = Math.pow(valDownsamplingFactor, 2);
            final double variance = valueVariances[ clusterNumber];
            final double tau = variance * (M - 1) / Math.pow(M, 2);
            //final double tau = 0;
            for (int i = valMinimum; i < (valMaximum + 1); i++) {
                double weight = likelihoodTimesPrior[ i - valMinimum] / evidenceVal[ i - valMinimum] * histogram[ i - valMinimum];
                meanContribution += i / M * weight;
                varianceContributionTerm1 += (tau + Math.pow(i / M, 2)) * weight;
                varianceContributionTerm2 += i / M * weight;
                varianceContributionTerm3 += weight;
                total += weight;
            }

            means[ clusterNumber] += meanContribution;
            variancesTerm1[ clusterNumber] += varianceContributionTerm1;
            variancesTerm2[ clusterNumber] += varianceContributionTerm2;
            variancesTerm3[ clusterNumber] += varianceContributionTerm3;
            purePriors[ clusterNumber] = total;
            totalWeights[ clusterNumber] += total;
        }


        // Contribution of PV cluster
        int pvClusterNumber = -1;
        for (int clusterNumber1 = 0; clusterNumber1 < numberOfCluster; clusterNumber1++) {
            for (int clusterNumber2 = clusterNumber1 + 1; clusterNumber2 < numberOfCluster; clusterNumber2++) {
                pvClusterNumber++;

                // Retrieve parameters of the pure tissue cluster this PV cluster is mixing for
                final double mean1 = valueMeans[ clusterNumber1];
                final double variance1 = valueVariances[ clusterNumber1];
                final double mean2 = valueMeans[ clusterNumber2];
                final double variance2 = valueVariances[ clusterNumber2];

                // Loop over all sub-Gaussians of this PV cluster
                Vector likelihoodTimesPriorsForThisPV = (Vector) m_PVLikelihoodTimesPriors.get(pvClusterNumber);
                for (int gaussianNumber = 0; gaussianNumber < numberOfGaussiansPerPV; gaussianNumber++) {
                    double[] likelihoodTimesPrior = (double[]) likelihoodTimesPriorsForThisPV.get(gaussianNumber);

                    // Retrieve mean and variance for this sub-Gaussian
                    final double alpha = (gaussianNumber + 1) / ((double) (valDownsamplingFactor * valDownsamplingFactor));
                    final double mean = alpha * mean1 + (1 - alpha) * mean2;
                    final double variance = alpha * variance1 + (1 - alpha) * variance2;

                    double mean1Contribution = 0;
                    double variance1ContributionTerm1 = 0;
                    double variance1ContributionTerm2 = 0;
                    double variance1ContributionTerm3 = 0;
                    double total1 = 0;
                    double mean2Contribution = 0;
                    double variance2ContributionTerm1 = 0;
                    double variance2ContributionTerm2 = 0;
                    double variance2ContributionTerm3 = 0;
                    double total2 = 0;
                    for (int i = valMinimum; i < (valMaximum + 1); i++) {
                        double weight = likelihoodTimesPrior[ i - valMinimum] / evidenceVal[ i - valMinimum] * histogram[ i - valMinimum];

                        //DebugMessage( "weight for intensity " + i + " for gaussianNumber " + gaussianNumber + 
                        //                    " of the combination (" + clusterNumber1 + ", " + clusterNumber2 + ") : " + weight );

                        final double M = Math.pow(valDownsamplingFactor, 2);

                        // Contribution to cluster 1
                        final double tau1 = mean1 / M + variance1 / M / variance * (i - mean);
                        final double sigma1 = (variance - variance1 / M) / variance * variance1 / M;
                        mean1Contribution += weight * alpha * tau1;
                        variance1ContributionTerm1 += weight * alpha * (sigma1 + Math.pow(tau1, 2));
                        variance1ContributionTerm2 += weight * alpha * tau1;
                        variance1ContributionTerm3 += weight * alpha;
                        total1 += weight * alpha;

                        // Contribution to cluster 2
                        final double tau2 = mean2 / M + variance2 / M / variance * (i - mean);
                        final double sigma2 = (variance - variance2 / M) / variance * variance2 / M;
                        mean2Contribution += weight * (1 - alpha) * tau2;
                        variance2ContributionTerm1 += weight * (1 - alpha) * (sigma2 + Math.pow(tau2, 2));
                        variance2ContributionTerm2 += weight * (1 - alpha) * tau2;
                        variance2ContributionTerm3 += weight * (1 - alpha);
                        total2 += weight * (1 - alpha);
                    }



                    means[ clusterNumber1] += mean1Contribution;
                    variancesTerm1[ clusterNumber1] += variance1ContributionTerm1;
                    variancesTerm2[ clusterNumber1] += variance1ContributionTerm2;
                    variancesTerm3[ clusterNumber1] += variance1ContributionTerm3;
                    totalWeights[ clusterNumber1] += total1;

                    means[ clusterNumber2] += mean2Contribution;
                    variancesTerm1[ clusterNumber2] += variance2ContributionTerm1;
                    variancesTerm2[ clusterNumber2] += variance2ContributionTerm2;
                    variancesTerm3[ clusterNumber2] += variance2ContributionTerm3;
                    totalWeights[ clusterNumber2] += total2;


                    PVPriors[ pvClusterNumber] += total1 + total2;

                } // End loop over all sub-Gaussians of this PV cluster

            }

        } // End loop over all possible 2-combinations of tissue types


        // Use the collected information to update the parameters   
        final double M = Math.pow(valDownsamplingFactor, 2);
        for (int clusterNumber = 0; clusterNumber < numberOfCluster; clusterNumber++) {
            valueMeans[ clusterNumber] = M * means[ clusterNumber] / totalWeights[ clusterNumber];
            valueVariances[ clusterNumber] = M * (variancesTerm1[ clusterNumber]
                    - 2 * valueMeans[ clusterNumber] / M * variancesTerm2[ clusterNumber]
                    + Math.pow(valueMeans[ clusterNumber] / M, 2) * variancesTerm3[ clusterNumber])
                    / totalWeights[ clusterNumber];
        }

        // Update the weights 
        double sum = 0;
        for (int clusterNumber = 0; clusterNumber < numberOfCluster; clusterNumber++) {
            sum += purePriors[ clusterNumber];
        }
        for (pvClusterNumber = 0; pvClusterNumber < numberOfPVCluster; pvClusterNumber++) {
            sum += PVPriors[ pvClusterNumber];
        }


        for (int clusterNumber = 0; clusterNumber < numberOfCluster; clusterNumber++) {
            valuePurePriors[ clusterNumber] = purePriors[ clusterNumber] / sum;
        }
        for (pvClusterNumber = 0; pvClusterNumber < numberOfPVCluster; pvClusterNumber++) {
            valuePVPriors[ pvClusterNumber] = PVPriors[ pvClusterNumber] / sum;
        }
        showResultMessage("the calculation not show yet");

    }

    public void drawChangingElements(Graphics g) {
        drawClusterResult(g);
        drawPlot(g);
        drawChangingGUI(g);
    }

    public void drawChangingGUI(Graphics g) {
        // Update GUI components
        textFieldMean0.setText("" + valueMeans[ 0]);
        textFieldMean1.setText("" + valueMeans[ 1]);
        textFieldMean2.setText("" + valueMeans[ 2]);

        textFieldSigma0.setText("" + Math.sqrt(valueVariances[ 0]));
        textFieldSigma1.setText("" + Math.sqrt(valueVariances[ 1]));
        textFieldSigma2.setText("" + Math.sqrt(valueVariances[ 2]));

        textFieldPure0.setText("" + 100 * valuePurePriors[ 0]);
        textFieldPure1.setText("" + 100 * valuePurePriors[ 1]);
        textFieldPure2.setText("" + 100 * valuePurePriors[ 2]);

        textFieldPvPrior0.setText("" + 100 * valuePVPriors[ 0]);
        textFieldPvPrior1.setText("" + 100 * valuePVPriors[ 1]);
        textFieldPvPrior2.setText("" + 100 * valuePVPriors[ 2]);

//        m_CostLabel.setText( "- log-likelihood: " + m_Cost );
    }

    public void drawClusteringImage(Graphics g, double[] likelihoodTimesPrior, int x, int y, int width, int height, int label) {
        int[] pix = new int[numberOfPixels];
        if (pixAll == null) {
            pixAll = new int[numberOfPixels];
            for (int i = 0; i < numberOfPixels; i++) {
                pixAll[i] = 0;
            }
        }
        for (int i = 0; i < numberOfPixels; i++) {
            int red;
            if (((nPixelData[ i] > lowThreshold) && (nPixelData[ i] < highThreshold)) == false) {
                //continue;
//                System.out.println("black");
                red = 0;
            } else {

                final int histogramEntry = nPixelData[ i] - valMinimum;
                red = (int) (likelihoodTimesPrior[ histogramEntry] / (evidenceVal[ histogramEntry] + 0.0000001) * 255);
//                System.out.println("diff : " + red);
            }

            final int green = 30 + red;
            final int blue = 50 + red;
            final int alpha = 255;
            pix[ i] = (alpha << 24) | (red << 16) | (green << 8) | blue;

            int greenAll = 0;
            int blueAll = 0;
            int alphaAll = 0;

            if ((red > 0)) {
                switch (label) {
                    case 1:
                        greenAll = 0 + red;
                        blueAll = 100 + red;
                        alphaAll = 255;
                        break;

                    case 2:
                        greenAll = 50 + red;
                        blueAll = 0 + red;
                        alphaAll = 255;
                        break;

                    case 3:
                        greenAll = 50 + red;
                        blueAll = 50 + red;
                        alphaAll = 255;
                        break;

                    case 4:
                        greenAll = 10 + red;
                        blueAll = 10 + red;
                        alphaAll = 255;
                        break;

                    case 5:
                        greenAll = 60 + red;
                        blueAll = 60 + red;
                        alphaAll = 255;
                        break;

                    case 6:
                        greenAll = 100 + red;
                        blueAll = 0 + red;
                        alphaAll = 255;
                        break;
                }

                pixAll[ i] = (alphaAll << 24) | (red << 16) | (greenAll << 8) | blueAll;
            }
        }
        Image img = createImage(new MemoryImageSource(valImageWidth, valImageHeight, pix, 0, valImageWidth));
        Image imgAll = createImage(new MemoryImageSource(valImageWidth, valImageHeight, pixAll, 0, valImageWidth));
//        g.drawImage(img, x, y, width, height, this);

//        BufferedImage bfrImage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_USHORT_GRAY);
        Image bfrImage = img;
        Image bfrImageAll = imgAll;
//        Graphics bg = bfrImage.getGraphics();
//        bg.drawImage(img, x, y, width, height, this);
        switch (label) {
            case 1:
                imgRest1.setText("");
                imgRest1.setIcon(new ImageIcon(bfrImage));
                break;
            case 2:
                imgRest2.setText("");
                imgRest2.setIcon(new ImageIcon(bfrImage));
                break;
            case 3:
                imgRest3.setText("");
                imgRest3.setIcon(new ImageIcon(bfrImage));
                break;
            case 4:
                imgRest4.setText("");
                imgRest4.setIcon(new ImageIcon(bfrImage));
                break;
            case 5:
                imgRest5.setText("");
                imgRest5.setIcon(new ImageIcon(bfrImage));
                break;
            case 6:
                imgRest6.setText("");
                imgRest6.setIcon(new ImageIcon(bfrImage));
                break;
        }

        imgRestAll.setText("");
        imgRestAll.setIcon(new ImageIcon(imgAll));

    }

    public void drawClusterResult(Graphics g) {
        // Draw pure clustering images
        for (int clusterNumber = 0; clusterNumber < numberOfCluster; clusterNumber++) {
            double[] likelihoodTimesPrior = (double[]) m_PureLikelihoodTimesPriors.get(clusterNumber);
            this.drawClusteringImage(g, likelihoodTimesPrior, 60 + clusterNumber * 200, 540, 150, 150, clusterNumber + 1);
        }

        // Draw PV cluster images
        int pvClusterNumber = -1;
        int pos = 1;
        for (int clusterNumber1 = 0; clusterNumber1 < numberOfCluster; clusterNumber1++) {
            for (int clusterNumber2 = clusterNumber1 + 1; clusterNumber2 < numberOfCluster; clusterNumber2++) {
                pvClusterNumber++;
                Vector likelihoodTimesPriorsForThisPV = (Vector) m_PVLikelihoodTimesPriors.get(pvClusterNumber);

                // Retrieve likelihoodTimesPrior for this PV clusster by loop over all sub-Gaussians of this PV cluster, and 
                // adding each Gaussian's contribution
                double[] summedLikelihoodTimesPrior = new double[numberOfBins];
                for (int gaussianNumber = 0; gaussianNumber < numberOfGaussiansPerPV; gaussianNumber++) {
                    double[] likelihoodTimesPrior = (double[]) likelihoodTimesPriorsForThisPV.get(gaussianNumber);
                    for (int i = valMinimum; i < (valMaximum + 1); i++) {
                        summedLikelihoodTimesPrior[ i - valMinimum] += likelihoodTimesPrior[ i - valMinimum];
                    }
                }
//         
                // Now draw 
                this.drawClusteringImage(g, summedLikelihoodTimesPrior, 60 + pvClusterNumber * 200, 720, 150, 150, pos + 3);
                pos++;
            }
        }

    }

    public void drawPlot(Graphics g) {
        // Create plotter and fill it's element in
        //XYPlot  plotter = new XYPlot( plotX, plotY, plotWidth, plotHeight );
        XYPlot plotter = new XYPlot(0, 0, valPlotWidth, valPlotHeight);
        plotter.Add(histogram, histogramColor, histogramStroke);

        double[] totalPlot = new double[numberOfBins];
        for (int i = valMinimum; i < (valMaximum + 1); i++) {
            totalPlot[ i - valMinimum] = 0;
        }

        // Create the pure Gaussian plots
        for (int clusterNumber = 0; clusterNumber < numberOfCluster; clusterNumber++) {
            final double mean = valueMeans[ clusterNumber];
            final double variance = valueVariances[ clusterNumber];
            final double prior = valuePurePriors[ clusterNumber];
            double[] plot = new double[numberOfBins];
            for (int i = valMinimum; i < (valMaximum + 1); i++) {
                plot[ i - valMinimum] = 1 / Math.sqrt(2 * Math.PI * variance) * Math.exp(-Math.pow(i - mean, 2) / variance / 2) * prior;
                totalPlot[ i - valMinimum] += plot[ i - valMinimum];
            }

            plotter.Add(plot, pureColor, pureStroke);
        }

        // Create the PV Gaussian plots
        int pvClusterNumber = -1;
        for (int clusterNumber1 = 0; clusterNumber1 < numberOfCluster; clusterNumber1++) {
            for (int clusterNumber2 = clusterNumber1 + 1; clusterNumber2 < numberOfCluster; clusterNumber2++) {
                pvClusterNumber++;

                // Retrieve parameters of the pure tissue cluster this PV clusteris mixing for
                final double mean1 = valueMeans[ clusterNumber1];
                final double variance1 = valueVariances[ clusterNumber1];
                final double mean2 = valueMeans[ clusterNumber2];
                final double variance2 = valueVariances[ clusterNumber2];

                //DebugMessage( "pvClusterNumber: " + pvClusterNumber );

                final double prior = valuePVPriors[ pvClusterNumber] / numberOfGaussiansPerPV;

                //DebugMessage( "prior: " + prior );



                // Loop over all sub-Gaussians of this PV cluster
                double[] pvPlot = new double[numberOfBins];
                for (int i = valMinimum; i < (valMaximum + 1); i++) {
                    pvPlot[ i - valMinimum] = 0;
                }
                for (int gaussianNumber = 0; gaussianNumber < numberOfGaussiansPerPV; gaussianNumber++) {
                    // Retrieve mean and variance for this sub-Gaussian
                    final double alpha = (gaussianNumber + 1) / ((double) (valDownsamplingFactor * valDownsamplingFactor));
                    final double mean = alpha * mean1 + (1 - alpha) * mean2;
                    final double variance = alpha * variance1 + (1 - alpha) * variance2;

                    double[] plot = new double[numberOfBins];
                    for (int i = valMinimum; i < (valMaximum + 1); i++) {
                        final double tmp = 1 / Math.sqrt(2 * Math.PI * variance) * Math.exp(-Math.pow(i - mean, 2) / variance / 2) * prior;
                        plot[ i - valMinimum] = tmp;
                        pvPlot[ i - valMinimum] += tmp;
                        totalPlot[ i - valMinimum] += tmp;
                    }

                    if (subGaussian.isSelected()) {
                        plotter.Add(plot, subGaussianColor, subGaussianStroke);
                    }
                }

                plotter.Add(pvPlot, histPVColor, histPVStroke);
            }

        }


        plotter.Add(totalPlot, hitTotalColor, histTotalStroke);

        
        plotter.Plot(valuePlotBuffer.getGraphics());

        
        valuePlotBuffer.getGraphics().setColor(Color.black);
       
        valuePlotBuffer.getGraphics().drawString("log likelihood: " + maxLikelihood, 10, 20);

        
//        g.drawImage(m_PlotBuffer, m_PlotX, m_PlotY, this);
        BufferedImage bfrImage = new BufferedImage(valPlotWidth, valPlotHeight, BufferedImage.TYPE_INT_RGB);
        Graphics bg = bfrImage.getGraphics();
        bg.drawImage(valuePlotBuffer, valPlotX, valPlotY, this);

        imageHist.setText("");
        imageHist.setIcon(new ImageIcon(bfrImage));
    }

    public void SetPrior(int number, double newPrior) {
        // Check which cluster we are referring to, and get its current value
        double oldPrior = 0;
        if (number < numberOfCluster) {
            oldPrior = valuePurePriors[ number];
        } else {
            oldPrior = valuePVPriors[ number - numberOfCluster];
        }

        // Make sure the new value makes sense
        if (newPrior > 0.99) {
            newPrior = 0.99;
        } else if (newPrior < 0) {
            newPrior = 0;
        }

        // Take certain fraction off other priors in order to make the required change
        double fraction = (newPrior - oldPrior) / (1 - oldPrior);
        for (int clusterNumber = 0; clusterNumber < numberOfCluster; clusterNumber++) {
            valuePurePriors[ clusterNumber] -= fraction * valuePurePriors[ clusterNumber];
        }
        for (int pvClusterNumber = 0; pvClusterNumber < numberOfCluster; pvClusterNumber++) {
            valuePVPriors[ pvClusterNumber] -= fraction * valuePVPriors[ pvClusterNumber];
        }

        // Set the prior to the specified value
        if (number < numberOfCluster) {
            valuePurePriors[ number] = newPrior;
        } else {
            valuePVPriors[ number - numberOfCluster] = newPrior;
        }

        this.calculateExpectation();
        paintGraph();
    }

    public void setUpGraphics() {
        // Define appearance of the plotted histogram lines
        histogramStroke = new BasicStroke();
        histogramColor = new Color(128, 128, 128);
        histTotalStroke = new BasicStroke(1.0f, // Width
                BasicStroke.CAP_SQUARE, // End cap
                BasicStroke.JOIN_MITER, // Join style
                10.0f, // Miter limit
                new float[]{5.0f, 5.0f}, // Dash pattern
                //new float[] { 8.0f, 4.0f, 2.0f, 4.0f },  // Dash pattern
                0.0f);                     // Dash phase
        hitTotalColor = new Color(64, 64, 64);
        pureStroke = histogramStroke;
        pureColor = new Color(192, 64, 192);
        histPVStroke = histogramStroke;
        histPVColor = new Color(255, 128, 128);
        subGaussianStroke = histogramStroke;
        subGaussianColor = new Color(64, 192, 64);


//        m_PlotBuffer = createImage(m_PlotWidth, m_PlotHeighth);
        valuePlotBuffer = createImage(valPlotWidth, valPlotHeight);
        if (valuePlotBuffer == null) {
            debugMessage("Buffer plot == null");
        }



        textFieldDownSampling.setText("" + valDownsamplingFactor);

        // Mean text fields
        final int parameterFieldsXstart = 10;
        final int parameterFieldXdelta = 210;

        textFieldMean0.setText("100");
        textFieldMean1.setText("100");
        textFieldMean2.setText("100");


        textFieldSigma0.setText("100");
        textFieldSigma1.setText("100");
        textFieldSigma2.setText("100");

        textFieldPure0.setText("100");
        textFieldPure1.setText("100");
        textFieldPure2.setText("100");

        textFieldPvPrior0.setText("100");
        textFieldPvPrior1.setText("100");
        textFieldPvPrior2.setText("100");

    }

    public void startEM() {
        // Make sure we're not running already
        if (emRunner != null) {
            // 
            if (emRunner.isAlive()) {
                debugMessage("Have already a thread doing our work");
                return;
            }
        }
        emRunner = new EMThread();


        emRunner.setView(this);

        emRunner.startThread();
    }

    public double getMaxLikelihood() {
        return maxLikelihood;
    }

    public void stopEM() {
        debugMessage("Trying to stop the solver");

        if (emRunner == null) {
            return;
        }

        // Cause the EMThread to finish up
        emRunner.setM_StopRequested(true);
        while (emRunner.isAlive()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
        }
        emRunner = null;
        pixAll = null;
    }

    public void itemStateChanged(ItemEvent e) {
        this.stopEM();
        this.SetImage(comboImage.getSelectedIndex());

    }
    private Graphics graph1D;
    private Graphics2D graph2D;
    //painted

    public void paintGraph() {

//        graph1D = panelHistogram.getGraph();
        graph1D = new BufferedImage(panelHistogram.getPreferredSize().width, panelHistogram.getPreferredSize().height, BufferedImage.TYPE_INT_RGB).createGraphics();

        if (graph1D == null) {
            debugMessage("graphnya null gan");
        }
        drawChangingElements(graph1D);
//        panelHistogram.paintGraph();
//        panelHistogram.revalidate();
    }

    public void debugMessage(String message) {
        // Comment this out for final release of the Applet
        //DebugMessage( message );
//        System.out.println(message);
//        textAreaRest.getTextArea().append(message);
    }

    public void showResultMessage(String message) {
        // Comment this out for final release of the Applet
        //DebugMessage( message );
//        System.out.println(message);
        if (isShowRest.isSelected()) {
            textAreaRest.getTextArea().append(message + "\n");
        }
    }
}
