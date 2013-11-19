/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sampleem.swing;

import java.awt.BasicStroke;
import java.awt.Checkbox;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Label;
import java.awt.MediaTracker;
import java.awt.Stroke;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.image.BufferedImage;
import static java.awt.image.ImageObserver.WIDTH;
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
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import sampleem.PVEM;
import sampleem.XYPlot;
import sampleem.swing.listener.Listener;
import sun.nio.cs.MS1250;

/**
 *
 * @author hendri
 */
public class CountPanel extends javax.swing.JPanel implements ActionListener, Listener {

    //=======================================================
    private JTextField m_Mean1TextField;
//    private JTextField m_Mean2TextField;
//    private JTextField m_Sigma0TextField;
//    private JTextField m_Sigma1TextField;
//    private JTextField m_Sigma2TextField;
//    private JTextField m_PurePrior0TextField;
//    private JTextField m_PurePrior1TextField;
//    private JTextField m_PurePrior2TextField;
//    private JTextField m_PVPrior0TextField;
//    private JTextField m_PVPrior1TextField;
//    private JTextField m_PVPrior2TextField;
    //private Label m_CostLabel;
    private JTextField m_DownsamplingFactorTextField;
    private JCheckBox m_ShowSubGaussiansCheckbox;
    private Choice m_ImageChoice;
//    private EMThread m_Solver = null;
    private MediaTracker media;
    private URL baseUrl;
    private ResultEntity result;
    //=======================================================
    private BufferedImage citraSrc;
    private Vector vImages;
    private Vector vContrasts;
    private Vector vDescriptions;
    private List<DataSetEntity> urlDataset;

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
//loadData();
        result = new ResultEntity();
        /*
         for (int i = 1;; i++) {
         //            String imageFileName = getParameter("imageFileName" + i);
         if (imageFileName == null) {
         break;
         }
         DebugMessage("imageFileName" + i + ": " + imageFileName);
         Image image = getImage(m_BaseURL, imageFileName);
         m_Images.add(image);

         // tell the MediaTracker to keep an eye on this image, and give it an ID;
         m_MediaTracker.addImage(image, i);

         // Also read the contrast and the description
         m_Contrasts.add(getParameter("contrast" + i));
         m_Descriptions.add(getParameter("description" + i));
         }

         //     // Check whether or not we do The Real Thing or just the Poor Man's approximation
         //     String algorithmToUse = getParameter( "algorithmToUse" );
         //     if ( algorithmToUse != null )
         //       { 
         //       DebugMessage( "algorithmToUse " + algorithmToUse );
         //       String compareString = "PoorMansAlgorithm";
         //       if ( algorithmToUse.matches( compareString ) )
         //         {
         //         DebugMessage( "Using the poor man's algorithm" );
         //         m_DoTheRealThing = false;
         //         }
         //       }


         // Now tell the mediaTracker to stop the applet execution
         // (in this example don't paint) until the images are fully loaded.
         // Must be in a try catch block.
         try {
         m_MediaTracker.waitForAll();
         } catch (InterruptedException e) {
         }

         this.SetUpGUI();

         this.SetImage(m_ImageChoice.getSelectedIndex());
         */

        System.out.println("" + panelHistogram.getX() + " ; " + panelHistogram.getY());
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
        panelWhiteBorder4 = new com.widget.karisma.container.PanelWhiteBorder();
        panelWhiteBorder6 = new com.widget.karisma.container.PanelWhiteBorder();
        imgRest1 = new javax.swing.JLabel();
        imgRest2 = new javax.swing.JLabel();
        imgRest3 = new javax.swing.JLabel();
        imgRest6 = new javax.swing.JLabel();
        imgRest5 = new javax.swing.JLabel();
        imgRest4 = new javax.swing.JLabel();

        setLayout(new java.awt.BorderLayout());

        bluePanel1.setLayout(new java.awt.BorderLayout());

        panelGlass1.setLayout(new java.awt.BorderLayout());

        panelWhiteBorder1.setLayout(new java.awt.BorderLayout());

        panelWhiteBorder2.setPreferredSize(new java.awt.Dimension(823, 140));

        textFieldMean0.setText("0");
        textFieldMean0.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textFieldMean0ActionPerformed(evt);
            }
        });

        textFieldSigma0.setText("0");
        textFieldSigma0.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textFieldSigma0ActionPerformed(evt);
            }
        });

        textFieldPure0.setText("0");
        textFieldPure0.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textFieldPure0ActionPerformed(evt);
            }
        });

        textFieldPvPrior0.setText("0");
        textFieldPvPrior0.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textFieldPvPrior0ActionPerformed(evt);
            }
        });

        textFieldMean1.setText("0");
        textFieldMean1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textFieldMean1ActionPerformed(evt);
            }
        });

        textFieldSigma1.setText("0");
        textFieldSigma1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textFieldSigma1ActionPerformed(evt);
            }
        });

        textFieldPure1.setText("0");
        textFieldPure1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textFieldPure1ActionPerformed(evt);
            }
        });

        textFieldPvPrior1.setText("0");
        textFieldPvPrior1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textFieldPvPrior1ActionPerformed(evt);
            }
        });

        textFieldMean2.setText("0");
        textFieldMean2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textFieldMean2ActionPerformed(evt);
            }
        });

        textFieldSigma2.setText("0");
        textFieldSigma2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textFieldSigma2ActionPerformed(evt);
            }
        });

        textFieldPure2.setText("0");
        textFieldPure2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textFieldPure2ActionPerformed(evt);
            }
        });

        textFieldPvPrior2.setText("0");
        textFieldPvPrior2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textFieldPvPrior2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelWhiteBorder2Layout = new javax.swing.GroupLayout(panelWhiteBorder2);
        panelWhiteBorder2.setLayout(panelWhiteBorder2Layout);
        panelWhiteBorder2Layout.setHorizontalGroup(
            panelWhiteBorder2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelWhiteBorder2Layout.createSequentialGroup()
                .addGap(118, 118, 118)
                .addGroup(panelWhiteBorder2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(textFieldPvPrior0, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(textFieldPure0, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(textFieldSigma0, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(textFieldMean0, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(136, 136, 136)
                .addGroup(panelWhiteBorder2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(textFieldPvPrior1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(textFieldPure1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(textFieldSigma1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(textFieldMean1, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 170, Short.MAX_VALUE)
                .addGroup(panelWhiteBorder2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(textFieldPvPrior2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(textFieldPure2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(textFieldSigma2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(textFieldMean2, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(38, 38, 38))
        );
        panelWhiteBorder2Layout.setVerticalGroup(
            panelWhiteBorder2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelWhiteBorder2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelWhiteBorder2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelWhiteBorder2Layout.createSequentialGroup()
                        .addComponent(textFieldMean2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textFieldSigma2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textFieldPure2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textFieldPvPrior2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelWhiteBorder2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelWhiteBorder2Layout.createSequentialGroup()
                            .addComponent(textFieldMean1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(textFieldSigma1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(textFieldPure1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(textFieldPvPrior1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(panelWhiteBorder2Layout.createSequentialGroup()
                            .addComponent(textFieldMean0, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(textFieldSigma0, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(textFieldPure0, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(textFieldPvPrior0, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        panelWhiteBorder1.add(panelWhiteBorder2, java.awt.BorderLayout.CENTER);

        panelWhiteBorder3.setPreferredSize(new java.awt.Dimension(823, 310));

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
                .addComponent(imageHist, javax.swing.GroupLayout.PREFERRED_SIZE, 471, javax.swing.GroupLayout.PREFERRED_SIZE)
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

        javax.swing.GroupLayout panelWhiteBorder3Layout = new javax.swing.GroupLayout(panelWhiteBorder3);
        panelWhiteBorder3.setLayout(panelWhiteBorder3Layout);
        panelWhiteBorder3Layout.setHorizontalGroup(
            panelWhiteBorder3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelWhiteBorder3Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(panelWhiteBorder3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 279, Short.MAX_VALUE)
                    .addComponent(comboImage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelWhiteBorder3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelWhiteBorder3Layout.createSequentialGroup()
                        .addComponent(buttonStart, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonStop, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelWhiteBorder3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(subGaussian)
                            .addComponent(textFieldDownSampling, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)
                        .addComponent(buttonInit, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(panelHistogram, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelWhiteBorder3Layout.setVerticalGroup(
            panelWhiteBorder3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelWhiteBorder3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelWhiteBorder3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(panelHistogram, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(panelWhiteBorder3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelWhiteBorder3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelWhiteBorder3Layout.createSequentialGroup()
                            .addGap(4, 4, 4)
                            .addGroup(panelWhiteBorder3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(comboImage, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(buttonStart, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(buttonStop, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelWhiteBorder3Layout.createSequentialGroup()
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(buttonInit, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panelWhiteBorder3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(subGaussian)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textFieldDownSampling, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(14, 14, 14))
        );

        panelWhiteBorder1.add(panelWhiteBorder3, java.awt.BorderLayout.PAGE_START);

        panelWhiteBorder4.setPreferredSize(new java.awt.Dimension(823, 270));

        panelWhiteBorder6.setPreferredSize(new java.awt.Dimension(823, 270));

        imgRest1.setForeground(new java.awt.Color(255, 255, 255));
        imgRest1.setText("                          IMAGE SATU");

        imgRest2.setForeground(new java.awt.Color(255, 255, 255));
        imgRest2.setText("                          IMAGE SATU");

        imgRest3.setForeground(new java.awt.Color(255, 255, 255));
        imgRest3.setText("                          IMAGE SATU");

        imgRest6.setForeground(new java.awt.Color(255, 255, 255));
        imgRest6.setText("                          IMAGE SATU");

        imgRest5.setForeground(new java.awt.Color(255, 255, 255));
        imgRest5.setText("                          IMAGE SATU");

        imgRest4.setForeground(new java.awt.Color(255, 255, 255));
        imgRest4.setText("                          IMAGE SATU");

        javax.swing.GroupLayout panelWhiteBorder6Layout = new javax.swing.GroupLayout(panelWhiteBorder6);
        panelWhiteBorder6.setLayout(panelWhiteBorder6Layout);
        panelWhiteBorder6Layout.setHorizontalGroup(
            panelWhiteBorder6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelWhiteBorder6Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addGroup(panelWhiteBorder6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(imgRest1, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(imgRest4, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 115, Short.MAX_VALUE)
                .addGroup(panelWhiteBorder6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(imgRest2, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(imgRest5, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(88, 88, 88)
                .addGroup(panelWhiteBorder6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(imgRest6, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(imgRest3, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(66, 66, 66))
        );
        panelWhiteBorder6Layout.setVerticalGroup(
            panelWhiteBorder6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelWhiteBorder6Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(panelWhiteBorder6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(imgRest1, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(imgRest2, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(imgRest3, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelWhiteBorder6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(imgRest4, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(imgRest5, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(imgRest6, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelWhiteBorder4Layout = new javax.swing.GroupLayout(panelWhiteBorder4);
        panelWhiteBorder4.setLayout(panelWhiteBorder4Layout);
        panelWhiteBorder4Layout.setHorizontalGroup(
            panelWhiteBorder4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelWhiteBorder4Layout.createSequentialGroup()
                .addGap(0, 1, Short.MAX_VALUE)
                .addComponent(panelWhiteBorder6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 1, Short.MAX_VALUE))
        );
        panelWhiteBorder4Layout.setVerticalGroup(
            panelWhiteBorder4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelWhiteBorder4Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(panelWhiteBorder6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

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
        this.StopEM();
        this.SetInitialParametersToDefault();
        repaint();
    }//GEN-LAST:event_buttonInitActionPerformed

    private void buttonStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonStartActionPerformed
        // TODO add your handling code here:
        this.StartEM();
        repaint();
    }//GEN-LAST:event_buttonStartActionPerformed

    private void comboImageItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboImageItemStateChanged
        // TODO add your handling code here:
        DebugMessage(comboImage.getSelectedIndex() + " : " + comboImage.getSelectedItem());
        try {
            citraSrc = ImageIO.read(new File(urlDataset.get(comboImage.getSelectedIndex()).getUri()));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        imageOtak.setText("");
//            temp = citraDua.getScaledInstance(drawPanel1.getWidth(), drawPanel1.getHeight(), citraDua.SCALE_FAST);
        imageOtak.setIcon(new ImageIcon(citraSrc));
    }//GEN-LAST:event_comboImageItemStateChanged

    private void textFieldMean0ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textFieldMean0ActionPerformed
        // TODO add your handling code here:
        m_Means[0] = Double.parseDouble(textFieldMean0.getText());
        this.CalculateExpectation();
        repaint();
    }//GEN-LAST:event_textFieldMean0ActionPerformed

    private void textFieldMean1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textFieldMean1ActionPerformed
        // TODO add your handling code here:
        m_Means[1] = Double.parseDouble(textFieldMean1.getText());
        this.CalculateExpectation();
        repaint();
    }//GEN-LAST:event_textFieldMean1ActionPerformed

    private void textFieldMean2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textFieldMean2ActionPerformed
        // TODO add your handling code here:
        m_Means[2] = Double.parseDouble(textFieldMean2.getText());
        this.CalculateExpectation();
        repaint();
    }//GEN-LAST:event_textFieldMean2ActionPerformed

    private void textFieldSigma0ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textFieldSigma0ActionPerformed
        // TODO add your handling code here:
        m_Variances[0] = Math.pow(Double.parseDouble(textFieldSigma0.getText()), 2);
        this.CalculateExpectation();
        repaint();
    }//GEN-LAST:event_textFieldSigma0ActionPerformed

    private void textFieldSigma1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textFieldSigma1ActionPerformed
        // TODO add your handling code here:
        m_Variances[1] = Math.pow(Double.parseDouble(textFieldSigma1.getText()), 2);
        this.CalculateExpectation();
        repaint();
    }//GEN-LAST:event_textFieldSigma1ActionPerformed

    private void textFieldSigma2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textFieldSigma2ActionPerformed
        // TODO add your handling code here:
        m_Variances[2] = Math.pow(Double.parseDouble(textFieldSigma2.getText()), 2);
        this.CalculateExpectation();
        repaint();
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
        StopEM();
    }//GEN-LAST:event_buttonStopActionPerformed

    private void textFieldDownSamplingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textFieldDownSamplingActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textFieldDownSamplingActionPerformed
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
    private javax.swing.JScrollPane jScrollPane1;
    private com.widget.karisma.container.PanelGlass panelGlass1;
    private sampleem.swing.HistogramView panelHistogram;
    private com.widget.karisma.container.PanelWhiteBorder panelWhiteBorder1;
    private com.widget.karisma.container.PanelWhiteBorder panelWhiteBorder2;
    private com.widget.karisma.container.PanelWhiteBorder panelWhiteBorder3;
    private com.widget.karisma.container.PanelWhiteBorder panelWhiteBorder4;
    private com.widget.karisma.container.PanelWhiteBorder panelWhiteBorder6;
    private javax.swing.JCheckBox subGaussian;
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
    public void actionPerformed(ActionEvent e) {
        /*
         if (e.getSource() == m_StartButton) {
         this.StartEM();
         //paintGraph(); 
         } else if (e.getSource() == m_StopButton) {
         this.StopEM();
         } else if (e.getSource() == m_ReinitializeButton) {
         this.StopEM();
         this.SetInitialParametersToDefault();
         paintGraph();
         } else if (e.getSource() == m_DownsamplingFactorTextField) {
         //DebugMessage( "Retrieve mean0" );
         try {
         m_DownsamplingFactor = Integer.parseInt(m_DownsamplingFactorTextField.getText());
         } catch (NumberFormatException exception) {
         //Use default value.
         m_DownsamplingFactor = 2;
         }
         if (m_DownsamplingFactor < 1) {
         m_DownsamplingFactor = 1;
         } else if (m_DownsamplingFactor > 5) // This limitation is not theoretically necessary, but makes the applet fool proof. A factor of 5 already results in 24 PV fractions!
         {
         m_DownsamplingFactor = 5;
         }
         m_DownsamplingFactorTextField.setText("" + m_DownsamplingFactor);
         this.SetInitialParametersToDefault();
         paintGraph();
         } else if (e.getSource() == m_Mean0TextField) {
         m_Means[ 0] = Double.parseDouble(m_Mean0TextField.getText());
         this.CalculateExpectation();
         paintGraph();
         } else if (e.getSource() == m_Mean1TextField) {
         m_Means[ 1] = Double.parseDouble(m_Mean1TextField.getText());
         this.CalculateExpectation();
         paintGraph();
         } else if (e.getSource() == m_Mean2TextField) {
         m_Means[ 2] = Double.parseDouble(m_Mean2TextField.getText());
         this.CalculateExpectation();
         paintGraph();
         } else if (e.getSource() == m_Sigma0TextField) {
         m_Variances[ 0] = Math.pow(Double.parseDouble(m_Sigma0TextField.getText()), 2);
         this.CalculateExpectation();
         paintGraph();
         } else if (e.getSource() == m_Sigma1TextField) {
         m_Variances[ 1] = Math.pow(Double.parseDouble(m_Sigma1TextField.getText()), 2);
         this.CalculateExpectation();
         paintGraph();
         } else if (e.getSource() == m_Sigma2TextField) {
         m_Variances[ 2] = Math.pow(Double.parseDouble(m_Sigma2TextField.getText()), 2);
         this.CalculateExpectation();
         paintGraph();
         } else if (e.getSource() == m_PurePrior0TextField) {
         double newPrior = Double.parseDouble(m_PurePrior0TextField.getText()) / 100;
         this.SetPrior(0, newPrior);
         } else if (e.getSource() == m_PurePrior1TextField) {
         double newPrior = Double.parseDouble(m_PurePrior1TextField.getText()) / 100;
         this.SetPrior(1, newPrior);
         } else if (e.getSource() == m_PurePrior2TextField) {
         double newPrior = Double.parseDouble(m_PurePrior2TextField.getText()) / 100;
         this.SetPrior(2, newPrior);
         } else if (e.getSource() == m_PVPrior0TextField) {
         double newPrior = Double.parseDouble(m_PVPrior0TextField.getText()) / 100;
         this.SetPrior(3, newPrior);
         } else if (e.getSource() == m_PVPrior1TextField) {
         double newPrior = Double.parseDouble(m_PVPrior1TextField.getText()) / 100;
         this.SetPrior(4, newPrior);
         } else if (e.getSource() == m_PVPrior2TextField) {
         double newPrior = Double.parseDouble(m_PVPrior2TextField.getText()) / 100;
         this.SetPrior(5, newPrior);
         }
         */
    }

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
        // The try-catch is necessary when the URL isn't valid
        // Of course this one is valid, since it is generated by
        // Java itself.
//        String baseUrl = null;
//        try {
//            // getDocumentbase gets the applet path.
////      m_BaseURL = System.getProperty("user.dir");
//            baseUrl = System.getProperty("user.dir" + "/image");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        // Load the images
        vImages = new Vector();
        vContrasts = new Vector();
        vDescriptions = new Vector();

        for (int i = 1;; i++) {
//            String imageFileName = "imageFileName" + i;
//            if (imageFileName == null) {
//                break;
//            }

            try {
                DebugMessage(i + ":" + baseUrl.toString() + "/imageFIleName" + i);
                citraSrc = ImageIO.read(new File(new URI(baseUrl.toString() + "/imageFIleName" + i + ".jpg")));
                DataSetEntity temp = new DataSetEntity();
                temp.setName("Image-" + i);
                temp.setUri(new URI(baseUrl.toString() + "/imageFIleName" + i + ".jpg"));
                urlDataset.add(temp);
                comboImage.addItem(temp.getName());

            } catch (IOException ex) {
                ex.printStackTrace();
                DebugMessage(ex.getMessage());
                break;
            } catch (URISyntaxException ex) {
                Logger.getLogger(CountPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
//            DebugMessage("imageFileName" + i + ": " + imageFileName);
//            Image image = getImage(m_BaseURL, imageFileName);
            vImages.add(citraSrc);
//            m_Images.add(image);
            Image image = citraSrc;
            // tell the MediaTracker to keep an eye on this image, and give it an ID;
            media.addImage(image, i);

            // Also read the contrast and the description
            vContrasts.add("imageFileName" + i);
            vDescriptions.add("deskripsi " + i);
            imageOtak.setText("");
//            temp = citraDua.getScaledInstance(drawPanel1.getWidth(), drawPanel1.getHeight(), citraDua.SCALE_FAST);
            imageOtak.setIcon(new ImageIcon(citraSrc));
        }

        //     // Check whether or not we do The Real Thing or just the Poor Man's approximation
//     String algorithmToUse = getParameter( "algorithmToUse" );
//     if ( algorithmToUse != null )
//       { 
//       DebugMessage( "algorithmToUse " + algorithmToUse );
//       String compareString = "PoorMansAlgorithm";
//       if ( algorithmToUse.matches( compareString ) )
//         {
//         DebugMessage( "Using the poor man's algorithm" );
//         m_DoTheRealThing = false;
//         }
//       }


        // Now tell the mediaTracker to stop the applet execution
        // (in this example don't paint) until the images are fully loaded.
        // Must be in a try catch block.
        try {
            media.waitForAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        this.SetUpGUI();

        this.SetImage(comboImage.getSelectedIndex());

    }
    String contrast;
    Image tempImage;
    private int m_Minimum;
    private int m_Maximum;
    private int tempWidth;
    private int tempHeight;
    private int numberOfPixels;
    private int[] nPixelData;
    private int m_NumberOfBins;
    private double[] histogram;
    private int numberOfActivePixels;
    private final int lowThreshold = 25;
    private final int highThreshold = 255;
    private int numberOfPVClasses;
    private final int numberOfClasses = 3;

    public void SetImage(int imageNumber) {
        contrast = (String) vContrasts.get(imageNumber);
        tempImage = (Image) vImages.get(imageNumber);

        // Turn into BufferedImage
        BufferedImage bufferedImage = new BufferedImage(tempImage.getWidth(null), tempImage.getHeight(null),
                BufferedImage.TYPE_INT_RGB);
//        Graphics2D g2d = bufferedImage.createGraphics();
//        g2d.drawImage(tempImage, 0, 0, null);

        // Get access to the pixel data
        Raster raster = bufferedImage.getData();
        //DataBuffer  dataBuffer = raster.getDataBuffer();
        tempWidth = raster.getWidth();
        tempHeight = raster.getHeight();
        numberOfPixels = tempWidth * tempHeight;
        int[] rgbArray = new int[numberOfPixels * 3];
        raster.getPixels(0 /* x */, 0 /* y */, raster.getWidth(), raster.getHeight(), rgbArray);

        // Get only the red component
        nPixelData = new int[numberOfPixels];
        for (int i = 0; i < numberOfPixels; i++) {
            nPixelData[ i] = rgbArray[ i * 3];
        }


        //DebugMessage( "pixel data: " );
        //for ( int i=0; i < m_NumberOfPixels; i++ )
        //  {
        //  DebugMessage( i + ": " + m_PixelData[ i ] );
        //  }


        // Get the minimum and maximum
        m_Minimum = 1000000000;
        m_Maximum = 0;
        for (int i = 0; i < numberOfPixels; i++) {
            if (nPixelData[ i] > m_Maximum) {
                m_Maximum = nPixelData[ i];
            } else if (nPixelData[ i] < m_Minimum) {
                m_Minimum = nPixelData[ i];
            }
        }
        DebugMessage("valMinimum: " + m_Minimum);
        DebugMessage("valMaximum: " + m_Maximum);


        // Build histogram
        m_NumberOfBins = m_Maximum - m_Minimum + 1;
        histogram = new double[m_NumberOfBins];
        for (int j = 0; j < m_NumberOfBins; j++) {
            histogram[ j] = 0;
        }
        numberOfActivePixels = 0;
        for (int i = 0; i < numberOfPixels; i++) {
            if ((nPixelData[ i] > lowThreshold) && (nPixelData[ i] < highThreshold)) {
                histogram[ nPixelData[ i] - m_Minimum] += 1;
                numberOfActivePixels++;
            }
        }
        for (int j = 0; j < m_NumberOfBins; j++) {
            histogram[ j] /= numberOfActivePixels;
        }

        //DebugMessage( "histogram: " );
        //for ( int j=0; j < m_NumberOfBins; j++ )
        //  {
        //  DebugMessage( j +": " + histogram[ j ] );
        //  }

        // Calculate number of PV classes
        numberOfPVClasses = 0;
        for (int classNumber1 = 0; classNumber1 < numberOfClasses; classNumber1++) {
            for (int classNumber2 = classNumber1 + 1; classNumber2 < numberOfClasses; classNumber2++) {
                numberOfPVClasses++;
            }
        }
        //DebugMessage( "NumberOfPVClasses: " + numberOfPVClasses );

        // 
        this.SetInitialParametersToDefault();
        paintGraph();
    }
    private int m_NumberOfGaussiansPerPV;
    private double[] m_Means;
    private double[] m_Variances;
    private double[] m_PurePriors;
    private double[] m_PVPriors;
    private int m_DownsamplingFactor = 2;

    public void SetInitialParametersToDefault() {
        // Calculate number of Gaussians per PV class
        m_NumberOfGaussiansPerPV = m_DownsamplingFactor * m_DownsamplingFactor - 1;
        //DebugMessage( "m_NumberOfGaussiansPerPV: " + m_NumberOfGaussiansPerPV );

        // Initialize parameters    
        m_Means = new double[numberOfClasses];
        m_Variances = new double[numberOfClasses];
        m_PurePriors = new double[numberOfClasses];
        m_PVPriors = new double[numberOfPVClasses];
        for (int classNumber = 0; classNumber < numberOfClasses; classNumber++) {
            /*      m_Means[ classNumber ] = valMinimum + ( ( valMaximum - valMinimum ) / ( ( double )( numberOfClasses + 1 ) ) ) * 
             ( classNumber + 1 );*/
            m_Means[ classNumber] = m_Minimum + ((m_Maximum - m_Minimum) / ((double) (numberOfClasses)))
                    * (classNumber + 0.5);
            m_Variances[ classNumber] = Math.pow((m_Maximum - m_Minimum) / 3.0, 2);
            if (m_NumberOfGaussiansPerPV != 0) {
                m_PurePriors[ classNumber] = 1 / ((double) (numberOfClasses + numberOfPVClasses));
            } else {
                m_PurePriors[ classNumber] = 1 / ((double) (numberOfClasses));
            }
        }
        for (int pvClassNumber = 0; pvClassNumber < numberOfPVClasses; pvClassNumber++) {
            if (m_NumberOfGaussiansPerPV != 0) {
                m_PVPriors[ pvClassNumber] = 1 / ((double) (numberOfClasses + numberOfPVClasses));
            } else {
                m_PVPriors[ pvClassNumber] = 0;
            }
        }


        // Depending on the contrast, swap initial means to keep the labels in the applet (WM/GM/CSF) relevant
        DebugMessage("Adjusting for contrast " + contrast);
        String compareString = "imageFileName2";
        if (contrast.matches(compareString)) {
            DebugMessage("swapping");
            double tmp = m_Means[ 0];
            m_Means[ 0] = m_Means[ 2];
            m_Means[ 2] = tmp;
        }

        // Recalculate the initial classification  
        CalculateExpectation();
    }
    private double[] m_Evidence;
    private Vector m_PureLikelihoodTimesPriors;
    private Vector m_PVLikelihoodTimesPriors;
    private double m_Cost = 0;

    public void CalculateExpectation() {
        //
        // Expectation step: classify
        // 
        m_Evidence = new double[m_NumberOfBins];
        for (int i = m_Minimum; i < (m_Maximum + 1); i++) {
            m_Evidence[ i - m_Minimum] = 0;
        }

        // Calculate the contributions of the pure classes
        m_PureLikelihoodTimesPriors = new Vector();
        for (int classNumber = 0; classNumber < numberOfClasses; classNumber++) {
            // Create the pure Gaussian
            final double mean = m_Means[ classNumber];
            final double variance = m_Variances[ classNumber];
            final double prior = m_PurePriors[ classNumber];
            double[] likelihoodTimesPrior = new double[m_NumberOfBins];
            for (int i = m_Minimum; i < (m_Maximum + 1); i++) {
                likelihoodTimesPrior[ i - m_Minimum] = 1 / Math.sqrt(2 * Math.PI * variance)
                        * Math.exp(-Math.pow(i - mean, 2) / variance / 2) * prior;
                m_Evidence[ i - m_Minimum] += likelihoodTimesPrior[ i - m_Minimum];
            }

            // 
            m_PureLikelihoodTimesPriors.add(likelihoodTimesPrior);
        }


        // Calculate the contributions of the PV classes
        m_PVLikelihoodTimesPriors = new Vector();
        int pvClassNumber = -1;
        for (int classNumber1 = 0; classNumber1 < numberOfClasses; classNumber1++) {
            for (int classNumber2 = classNumber1 + 1; classNumber2 < numberOfClasses; classNumber2++) {
                pvClassNumber++;

                // Retrieve parameters of the pure tissue classes this PV class is mixing for
                final double mean1 = m_Means[ classNumber1];
                final double variance1 = m_Variances[ classNumber1];
                final double mean2 = m_Means[ classNumber2];
                final double variance2 = m_Variances[ classNumber2];

                //DebugMessage( "pvClassNumber: " + pvClassNumber );

                final double prior = m_PVPriors[ pvClassNumber] / m_NumberOfGaussiansPerPV;

                //DebugMessage( "prior: " + prior );


                // Loop over all sub-Gaussians of this PV class
                Vector likelihoodTimesPriorsForThisPV = new Vector();
                for (int gaussianNumber = 0; gaussianNumber < m_NumberOfGaussiansPerPV; gaussianNumber++) {
                    // Retrieve mean and variance for this sub-Gaussian
                    final double alpha = (gaussianNumber + 1) / ((double) (m_DownsamplingFactor * m_DownsamplingFactor));
                    final double mean = alpha * mean1 + (1 - alpha) * mean2;
                    final double variance = alpha * variance1 + (1 - alpha) * variance2;

                    double[] likelihoodTimesPrior = new double[m_NumberOfBins];
                    for (int i = m_Minimum; i < (m_Maximum + 1); i++) {
                        final double tmp = 1 / Math.sqrt(2 * Math.PI * variance) * Math.exp(-Math.pow(i - mean, 2) / variance / 2) * prior;
                        likelihoodTimesPrior[ i - m_Minimum] = tmp;
                        m_Evidence[ i - m_Minimum] += tmp;
                    }

                    likelihoodTimesPriorsForThisPV.add(likelihoodTimesPrior);
                }

                m_PVLikelihoodTimesPriors.add(likelihoodTimesPriorsForThisPV);
            }

        }



        //
        // Show the current value of the objective function
        //
        m_Cost = 0;
        for (int i = m_Minimum; i < (m_Maximum + 1); i++) {
            m_Cost -= Math.log(m_Evidence[ i - m_Minimum]) * histogram[ i - m_Minimum];
        }
        m_Cost *= numberOfActivePixels;
        DebugMessage("Current cost: " + m_Cost);
    }

    public void CalculateMaximization() {
        // 
        // Maximization step: update the parameters
        //
        double[] totalWeights = new double[numberOfClasses];
        double[] means = new double[numberOfClasses];
        double[] variancesTerm1 = new double[numberOfClasses];
        double[] variancesTerm2 = new double[numberOfClasses];
        double[] variancesTerm3 = new double[numberOfClasses];
        double[] purePriors = new double[numberOfClasses];
        for (int classNumber = 0; classNumber < numberOfClasses; classNumber++) {
            totalWeights[ classNumber] = 0;
            means[ classNumber] = 0;
            variancesTerm1[ classNumber] = 0;
            variancesTerm2[ classNumber] = 0;
            variancesTerm3[ classNumber] = 0;
            purePriors[ classNumber] = 0;
        }
        double[] PVPriors = new double[numberOfPVClasses];
        for (int pvClassNumber = 0; pvClassNumber < numberOfClasses; pvClassNumber++) {
            PVPriors[ pvClassNumber] = 0;
        }

        // Contribution of pure classes  
        for (int classNumber = 0; classNumber < numberOfClasses; classNumber++) {
            double[] likelihoodTimesPrior = (double[]) m_PureLikelihoodTimesPriors.get(classNumber);

            double meanContribution = 0;
            double varianceContributionTerm1 = 0;
            double varianceContributionTerm2 = 0;
            double varianceContributionTerm3 = 0;
            double total = 0;
            final double M = Math.pow(m_DownsamplingFactor, 2);
            final double variance = m_Variances[ classNumber];
            final double tau = variance * (M - 1) / Math.pow(M, 2);
            //final double tau = 0;
            for (int i = m_Minimum; i < (m_Maximum + 1); i++) {
                double weight = likelihoodTimesPrior[ i - m_Minimum] / m_Evidence[ i - m_Minimum] * histogram[ i - m_Minimum];
                meanContribution += i / M * weight;
                varianceContributionTerm1 += (tau + Math.pow(i / M, 2)) * weight;
                varianceContributionTerm2 += i / M * weight;
                varianceContributionTerm3 += weight;
                total += weight;
            }

            means[ classNumber] += meanContribution;
            variancesTerm1[ classNumber] += varianceContributionTerm1;
            variancesTerm2[ classNumber] += varianceContributionTerm2;
            variancesTerm3[ classNumber] += varianceContributionTerm3;
            purePriors[ classNumber] = total;
            totalWeights[ classNumber] += total;
        }


        // Contribution of PV classes
        int pvClassNumber = -1;
        for (int classNumber1 = 0; classNumber1 < numberOfClasses; classNumber1++) {
            for (int classNumber2 = classNumber1 + 1; classNumber2 < numberOfClasses; classNumber2++) {
                pvClassNumber++;

                // Retrieve parameters of the pure tissue classes this PV class is mixing for
                final double mean1 = m_Means[ classNumber1];
                final double variance1 = m_Variances[ classNumber1];
                final double mean2 = m_Means[ classNumber2];
                final double variance2 = m_Variances[ classNumber2];

                // Loop over all sub-Gaussians of this PV class
                Vector likelihoodTimesPriorsForThisPV = (Vector) m_PVLikelihoodTimesPriors.get(pvClassNumber);
                for (int gaussianNumber = 0; gaussianNumber < m_NumberOfGaussiansPerPV; gaussianNumber++) {
                    double[] likelihoodTimesPrior = (double[]) likelihoodTimesPriorsForThisPV.get(gaussianNumber);

                    // Retrieve mean and variance for this sub-Gaussian
                    final double alpha = (gaussianNumber + 1) / ((double) (m_DownsamplingFactor * m_DownsamplingFactor));
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
                    for (int i = m_Minimum; i < (m_Maximum + 1); i++) {
                        double weight = likelihoodTimesPrior[ i - m_Minimum] / m_Evidence[ i - m_Minimum] * histogram[ i - m_Minimum];

                        //DebugMessage( "weight for intensity " + i + " for gaussianNumber " + gaussianNumber + 
                        //                    " of the combination (" + classNumber1 + ", " + classNumber2 + ") : " + weight );

                        final double M = Math.pow(m_DownsamplingFactor, 2);

                        // Contribution to class 1
                        final double tau1 = mean1 / M + variance1 / M / variance * (i - mean);
                        final double sigma1 = (variance - variance1 / M) / variance * variance1 / M;
                        mean1Contribution += weight * alpha * tau1;
                        variance1ContributionTerm1 += weight * alpha * (sigma1 + Math.pow(tau1, 2));
                        variance1ContributionTerm2 += weight * alpha * tau1;
                        variance1ContributionTerm3 += weight * alpha;
                        total1 += weight * alpha;

                        // Contribution to class 2
                        final double tau2 = mean2 / M + variance2 / M / variance * (i - mean);
                        final double sigma2 = (variance - variance2 / M) / variance * variance2 / M;
                        mean2Contribution += weight * (1 - alpha) * tau2;
                        variance2ContributionTerm1 += weight * (1 - alpha) * (sigma2 + Math.pow(tau2, 2));
                        variance2ContributionTerm2 += weight * (1 - alpha) * tau2;
                        variance2ContributionTerm3 += weight * (1 - alpha);
                        total2 += weight * (1 - alpha);
                    }



                    means[ classNumber1] += mean1Contribution;
                    variancesTerm1[ classNumber1] += variance1ContributionTerm1;
                    variancesTerm2[ classNumber1] += variance1ContributionTerm2;
                    variancesTerm3[ classNumber1] += variance1ContributionTerm3;
                    totalWeights[ classNumber1] += total1;

                    means[ classNumber2] += mean2Contribution;
                    variancesTerm1[ classNumber2] += variance2ContributionTerm1;
                    variancesTerm2[ classNumber2] += variance2ContributionTerm2;
                    variancesTerm3[ classNumber2] += variance2ContributionTerm3;
                    totalWeights[ classNumber2] += total2;


                    PVPriors[ pvClassNumber] += total1 + total2;

                } // End loop over all sub-Gaussians of this PV class

            }

        } // End loop over all possible 2-combinations of tissue types


        // Use the collected information to update the parameters   
        final double M = Math.pow(m_DownsamplingFactor, 2);
        for (int classNumber = 0; classNumber < numberOfClasses; classNumber++) {
            m_Means[ classNumber] = M * means[ classNumber] / totalWeights[ classNumber];
            m_Variances[ classNumber] = M * (variancesTerm1[ classNumber]
                    - 2 * m_Means[ classNumber] / M * variancesTerm2[ classNumber]
                    + Math.pow(m_Means[ classNumber] / M, 2) * variancesTerm3[ classNumber])
                    / totalWeights[ classNumber];
        }

        // Update the weights 
        double sum = 0;
        for (int classNumber = 0; classNumber < numberOfClasses; classNumber++) {
            sum += purePriors[ classNumber];
        }
        for (pvClassNumber = 0; pvClassNumber < numberOfPVClasses; pvClassNumber++) {
            sum += PVPriors[ pvClassNumber];
        }


        for (int classNumber = 0; classNumber < numberOfClasses; classNumber++) {
            m_PurePriors[ classNumber] = purePriors[ classNumber] / sum;
        }
        for (pvClassNumber = 0; pvClassNumber < numberOfPVClasses; pvClassNumber++) {
            m_PVPriors[ pvClassNumber] = PVPriors[ pvClassNumber] / sum;
        }


    }

    public void DrawChangingElements(Graphics g) {
        DrawClassifications(g);
        DrawPlot(g);
        DrawChangingGUI(g);
    }

    public void DrawChangingGUI(Graphics g) {
        // Update GUI components
        textFieldMean0.setText("" + m_Means[ 0]);
        textFieldMean1.setText("" + m_Means[ 1]);
        textFieldMean2.setText("" + m_Means[ 2]);

        textFieldSigma0.setText("" + Math.sqrt(m_Variances[ 0]));
        textFieldSigma1.setText("" + Math.sqrt(m_Variances[ 1]));
        textFieldSigma2.setText("" + Math.sqrt(m_Variances[ 2]));

        textFieldPure0.setText("" + 100 * m_PurePriors[ 0]);
        textFieldPure1.setText("" + 100 * m_PurePriors[ 1]);
        textFieldPure2.setText("" + 100 * m_PurePriors[ 2]);

        textFieldPvPrior0.setText("" + 100 * m_PVPriors[ 0]);
        textFieldPvPrior1.setText("" + 100 * m_PVPriors[ 1]);
        textFieldPvPrior2.setText("" + 100 * m_PVPriors[ 2]);

        //m_CostLabel.setText( "- log-likelihood: " + m_Cost );
    }

    public void DrawClassification(Graphics g, double[] likelihoodTimesPrior, int x, int y, int width, int height, int label) {
        int[] pix = new int[numberOfPixels];
        for (int i = 0; i < numberOfPixels; i++) {
            int red;
            if (((nPixelData[ i] > lowThreshold) && (nPixelData[ i] < highThreshold)) == false) {
                //continue;
                red = 0;
            } else {
                final int histogramEntry = nPixelData[ i] - m_Minimum;
                red = (int) (likelihoodTimesPrior[ histogramEntry] / (m_Evidence[ histogramEntry] + 0.0000001) * 255);
            }

            final int green = red;
            final int blue = red;
            final int alpha = 255;
            pix[ i] = (alpha << 24) | (red << 16) | (green << 8) | blue;
        }
        Image img = createImage(new MemoryImageSource(tempWidth, tempHeight, pix, 0, tempWidth));
//        g.drawImage(img, x, y, width, height, this);

        BufferedImage bfrImage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_RGB);
        Graphics bg = bfrImage.getGraphics();
        bg.drawImage(img, x, y, width, height, this);
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
    }

    public void DrawClassifications(Graphics g) {
        // Draw pure classification images
        for (int classNumber = 0; classNumber < numberOfClasses; classNumber++) {
            double[] likelihoodTimesPrior = (double[]) m_PureLikelihoodTimesPriors.get(classNumber);
            this.DrawClassification(g, likelihoodTimesPrior, 60 + classNumber * 200, 540, 150, 150, classNumber + 1);
        }

        // Draw PV classification images
        int pvClassNumber = -1;
        int pos = 1;
        for (int classNumber1 = 0; classNumber1 < numberOfClasses; classNumber1++) {
            for (int classNumber2 = classNumber1 + 1; classNumber2 < numberOfClasses; classNumber2++) {
                pvClassNumber++;
                Vector likelihoodTimesPriorsForThisPV = (Vector) m_PVLikelihoodTimesPriors.get(pvClassNumber);

                // Retrieve likelihoodTimesPrior for this PV class by loop over all sub-Gaussians of this PV class, and 
                // adding each Gaussian's contribution
                double[] summedLikelihoodTimesPrior = new double[m_NumberOfBins];
                for (int gaussianNumber = 0; gaussianNumber < m_NumberOfGaussiansPerPV; gaussianNumber++) {
                    double[] likelihoodTimesPrior = (double[]) likelihoodTimesPriorsForThisPV.get(gaussianNumber);
                    for (int i = m_Minimum; i < (m_Maximum + 1); i++) {
                        summedLikelihoodTimesPrior[ i - m_Minimum] += likelihoodTimesPrior[ i - m_Minimum];
                    }
                }
//                System.out.println("class number 2 : " + classNumber2 + " " + pos);
                // Now draw 
                this.DrawClassification(g, summedLikelihoodTimesPrior, 60 + pvClassNumber * 200, 720, 150, 150, pos + 3);
                pos++;
            }
        }

    }
//     private final int m_PlotWidth = panelHistogram.getPreferredSize().width;
//    private final int m_PlotHeight = panelHistogram.getPreferredSize().height; 494, 210
    private final int m_PlotX = 0;
    private final int m_PlotY = 0;
//    private final int m_PlotX = panelHistogram.getX();
//    private final int m_PlotY = panelHistogram.getY();
    private final int m_PlotWidth = 494;
    private final int m_PlotHeight = 210;
    private Image m_PlotBuffer;
    private Stroke m_HistogramStroke;
    private Color m_HistogramColor;
    private Stroke m_PureStroke;
    private Color m_PureColor;
    private Stroke m_SubGaussianStroke;
    private Color m_SubGaussianColor;
    private Stroke m_PVStroke;
    private Color m_PVColor;
    private Stroke m_TotalStroke;
    private Color m_TotalColor;

    public void DrawPlot(Graphics g) {
        // Create plotter and fill it's element in
        //XYPlot  plotter = new XYPlot( plotX, plotY, plotWidth, plotHeight );
        XYPlot plotter = new XYPlot(0, 0, m_PlotWidth, m_PlotHeight);
        plotter.Add(histogram, m_HistogramColor, m_HistogramStroke);

        double[] totalPlot = new double[m_NumberOfBins];
        for (int i = m_Minimum; i < (m_Maximum + 1); i++) {
            totalPlot[ i - m_Minimum] = 0;
        }

        // Create the pure Gaussian plots
        for (int classNumber = 0; classNumber < numberOfClasses; classNumber++) {
            final double mean = m_Means[ classNumber];
            final double variance = m_Variances[ classNumber];
            final double prior = m_PurePriors[ classNumber];
            double[] plot = new double[m_NumberOfBins];
            for (int i = m_Minimum; i < (m_Maximum + 1); i++) {
                plot[ i - m_Minimum] = 1 / Math.sqrt(2 * Math.PI * variance) * Math.exp(-Math.pow(i - mean, 2) / variance / 2) * prior;
                totalPlot[ i - m_Minimum] += plot[ i - m_Minimum];
            }

            plotter.Add(plot, m_PureColor, m_PureStroke);
        }

        // Create the PV Gaussian plots
        int pvClassNumber = -1;
        for (int classNumber1 = 0; classNumber1 < numberOfClasses; classNumber1++) {
            for (int classNumber2 = classNumber1 + 1; classNumber2 < numberOfClasses; classNumber2++) {
                pvClassNumber++;

                // Retrieve parameters of the pure tissue classes this PV class is mixing for
                final double mean1 = m_Means[ classNumber1];
                final double variance1 = m_Variances[ classNumber1];
                final double mean2 = m_Means[ classNumber2];
                final double variance2 = m_Variances[ classNumber2];

                //DebugMessage( "pvClassNumber: " + pvClassNumber );

                final double prior = m_PVPriors[ pvClassNumber] / m_NumberOfGaussiansPerPV;

                //DebugMessage( "prior: " + prior );



                // Loop over all sub-Gaussians of this PV class
                double[] pvPlot = new double[m_NumberOfBins];
                for (int i = m_Minimum; i < (m_Maximum + 1); i++) {
                    pvPlot[ i - m_Minimum] = 0;
                }
                for (int gaussianNumber = 0; gaussianNumber < m_NumberOfGaussiansPerPV; gaussianNumber++) {
                    // Retrieve mean and variance for this sub-Gaussian
                    final double alpha = (gaussianNumber + 1) / ((double) (m_DownsamplingFactor * m_DownsamplingFactor));
                    final double mean = alpha * mean1 + (1 - alpha) * mean2;
                    final double variance = alpha * variance1 + (1 - alpha) * variance2;

                    double[] plot = new double[m_NumberOfBins];
                    for (int i = m_Minimum; i < (m_Maximum + 1); i++) {
                        final double tmp = 1 / Math.sqrt(2 * Math.PI * variance) * Math.exp(-Math.pow(i - mean, 2) / variance / 2) * prior;
                        plot[ i - m_Minimum] = tmp;
                        pvPlot[ i - m_Minimum] += tmp;
                        totalPlot[ i - m_Minimum] += tmp;
                    }

                    if (subGaussian.isSelected()) {
                        plotter.Add(plot, m_SubGaussianColor, m_SubGaussianStroke);
                    }
                }

                plotter.Add(pvPlot, m_PVColor, m_PVStroke);
            }

        }


        plotter.Add(totalPlot, m_TotalColor, m_TotalStroke);

        // Now we're ready to fill up the buffer. First the plot        
        //plotter.Plot( g );
        plotter.Plot(m_PlotBuffer.getGraphics());

        // Now the -logLikelihood
        m_PlotBuffer.getGraphics().setColor(Color.black);
        m_PlotBuffer.getGraphics().drawString("- log-likelihood: " + m_Cost, 10, 20);

        // Finally, display the buffer
//        g.drawImage(m_PlotBuffer, m_PlotX, m_PlotY, this);
        BufferedImage bfrImage = new BufferedImage(m_PlotWidth, m_PlotHeight, BufferedImage.TYPE_INT_RGB);
        Graphics bg = bfrImage.getGraphics();
        bg.drawImage(m_PlotBuffer, m_PlotX, m_PlotY, this);

        imageHist.setText("");
        imageHist.setIcon(new ImageIcon(bfrImage));
    }

    public void SetPrior(int number, double newPrior) {
        // Check which class we are referring to, and get its current value
        double oldPrior = 0;
        if (number < numberOfClasses) {
            oldPrior = m_PurePriors[ number];
        } else {
            oldPrior = m_PVPriors[ number - numberOfClasses];
        }

        // Make sure the new value makes sense
        if (newPrior > 0.99) {
            newPrior = 0.99;
        } else if (newPrior < 0) {
            newPrior = 0;
        }

        // Take certain fraction off other priors in order to make the required change
        double fraction = (newPrior - oldPrior) / (1 - oldPrior);
        for (int classNumber = 0; classNumber < numberOfClasses; classNumber++) {
            m_PurePriors[ classNumber] -= fraction * m_PurePriors[ classNumber];
        }
        for (int pvClassNumber = 0; pvClassNumber < numberOfClasses; pvClassNumber++) {
            m_PVPriors[ pvClassNumber] -= fraction * m_PVPriors[ pvClassNumber];
        }

        // Set the prior to the specified value
        if (number < numberOfClasses) {
            m_PurePriors[ number] = newPrior;
        } else {
            m_PVPriors[ number - numberOfClasses] = newPrior;
        }

        this.CalculateExpectation();
        paintGraph();
    }

    public void SetUpGUI() {
        // Define appearance of the plotted histogram lines
        m_HistogramStroke = new BasicStroke();
        m_HistogramColor = new Color(128, 128, 128);
        m_TotalStroke = new BasicStroke(1.0f, // Width
                BasicStroke.CAP_SQUARE, // End cap
                BasicStroke.JOIN_MITER, // Join style
                10.0f, // Miter limit
                new float[]{5.0f, 5.0f}, // Dash pattern
                //new float[] { 8.0f, 4.0f, 2.0f, 4.0f },  // Dash pattern
                0.0f);                     // Dash phase
        m_TotalColor = new Color(64, 64, 64);
        m_PureStroke = m_HistogramStroke;
        m_PureColor = new Color(192, 64, 192);
        m_PVStroke = m_HistogramStroke;
        m_PVColor = new Color(255, 128, 128);
        m_SubGaussianStroke = m_HistogramStroke;
        m_SubGaussianColor = new Color(64, 192, 64);


//        m_PlotBuffer = createImage(m_PlotWidth, m_PlotHeighth);
        m_PlotBuffer = createImage(m_PlotWidth, m_PlotHeight);
        if (m_PlotBuffer == null) {
            DebugMessage("Buffer plot == null");
        }



        textFieldDownSampling.setText("" + m_DownsamplingFactor);

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
    private EMThread m_Solver;

    public void StartEM() {
        // Make sure we're not running already
        if (m_Solver != null) {
            // 
            if (m_Solver.isAlive()) {
                DebugMessage("Have already a thread doing our work");
                return;
            }
        }

        m_Solver = new EMThread();
        m_Solver.setView(this);
    }

    public double getM_Cost() {
        return m_Cost;
    }

    public void StopEM() {
        DebugMessage("Trying to stop the solver");

        if (m_Solver == null) {
            return;
        }

        // Cause the EMThread to finish up
        m_Solver.setM_StopRequested(true);
        while (m_Solver.isAlive()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
        }
        m_Solver = null;

    }

    public void itemStateChanged(ItemEvent e) {
        if (e.getSource() == m_ShowSubGaussiansCheckbox) {
            paintGraph();
        } else if (e.getSource() == m_ImageChoice) {
            this.StopEM();
            this.SetImage(m_ImageChoice.getSelectedIndex());
        }
    }
    private Graphics graph1D;
    private Graphics2D graph2D;
    //painted

    public void paintGraph() {
        // now we are going to draw the gif on the screen
        // (image name,x,y,observer);
//        g.drawImage(tempImage, 20, 20, 230, 230, this);

//        graph1D = panelHistogram.getGraph();
        graph1D = new BufferedImage(panelHistogram.getPreferredSize().width, panelHistogram.getPreferredSize().height, BufferedImage.TYPE_INT_RGB).createGraphics();
        if (graph1D == null) {
            DebugMessage("graphnya null gan");
        }
        DrawChangingElements(graph1D);
        panelHistogram.repaint();
//        panelHistogram.revalidate();
    }

    public void DebugMessage(String message) {
        // Comment this out for final release of the Applet
        //DebugMessage( message );
    }
}
