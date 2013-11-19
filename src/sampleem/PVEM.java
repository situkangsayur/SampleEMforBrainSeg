/*
 *
 * This java applet demonstrates how an Expectation-Maximization algorithm can be used to estimate the 
 * parameters of a Gaussian mixture model in which partial voluming between tissue classes is allowed. 
 *
 * The algorithm is described in the following paper:
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
package sampleem;

import java.awt.*;
import java.applet.*;
import java.net.*;
import java.awt.image.*;
import java.awt.event.*;
import java.util.Vector;


public class PVEM extends Applet implements ActionListener, ItemListener
{
  // Some data members first
  private Image  m_Image;
  private String m_Contrast;
  private Vector  m_Images;
  private Vector  m_Contrasts;
  private Vector  m_Descriptions;
  //private boolean  m_DoTheRealThing = true;  // Should we do real EM as described in the paper, or the often-used pseudo-EM approximation which ignores the contributions of the PV classes in the M step.

  private URL  m_BaseURL;   // The applet base URL
  
  private MediaTracker   m_MediaTracker; // This object controls loading

  private int m_Minimum;
  private int m_Maximum;
  
  private int m_Width;
  private int m_Height;

  private int m_NumberOfPixels;
  
  private int[] m_PixelData;
  
  private int m_NumberOfBins;
  private double[]  m_Histogram;
  private int m_NumberOfActivePixels;
  private final int  m_LowThreshold = 25;
  private final int  m_HighThreshold = 255;
    
  private final int m_NumberOfClasses = 3;
  private int m_DownsamplingFactor = 2;
  private int m_NumberOfPVClasses;
  private int m_NumberOfGaussiansPerPV;
  private double[] m_Means;
  private double[] m_Variances;
  private double[] m_PurePriors;
  private double[] m_PVPriors;

  private Vector m_PureLikelihoodTimesPriors;
  private Vector m_PVLikelihoodTimesPriors;
  private double[]  m_Evidence; 
  
  private double m_Cost = 0;
  
  private final int  m_PlotX = 260;
  private final int  m_PlotY = 20;
  private final int  m_PlotWidth = 400;
  private final int  m_PlotHeight = 230;
  private Image  m_PlotBuffer;

  private Stroke  m_HistogramStroke;
  private Color   m_HistogramColor;  
  private Stroke  m_TotalStroke;
  private Color   m_TotalColor;
  private Stroke  m_PureStroke;
  private Color   m_PureColor;
  private Stroke  m_PVStroke;
  private Color   m_PVColor;
  private Stroke  m_SubGaussianStroke;
  private Color   m_SubGaussianColor;  

  private Button m_StartButton; 
  private Button m_StopButton; 
  private Button m_ReinitializeButton; 
  private TextField m_Mean0TextField;
  private TextField m_Mean1TextField;
  private TextField m_Mean2TextField;
  private TextField m_Sigma0TextField;
  private TextField m_Sigma1TextField;
  private TextField m_Sigma2TextField;
  private TextField m_PurePrior0TextField;
  private TextField m_PurePrior1TextField;
  private TextField m_PurePrior2TextField;
  private TextField m_PVPrior0TextField;
  private TextField m_PVPrior1TextField;
  private TextField m_PVPrior2TextField;
  //private Label m_CostLabel;
  private TextField m_DownsamplingFactorTextField;
  private Checkbox m_ShowSubGaussiansCheckbox;
  private Choice m_ImageChoice;

  private EMThread  m_Solver = null;
  
  // This thread class actually takes care of iterating through the EM equations. Note that, as an inner class, 
  // this thread class has actually transparent access to all the data containers etc of the parent class - in fact, we 
  // leave all the data containers in the parent class
  //
  // Note on the design decision used here: it's modeled after Counter2.java in Bruce Eckel's "Thinking in Java" 
  // (2nd edition, Revision 9), chapter 14. The alternative would be the Runnable-way, but that's more convoluted.
  //
  private class EMThread extends Thread   
    {
    boolean m_StopRequested = false;
    EMThread() 
      {
      start();
      }

    public void run() 
      {
      DebugMessage( "Starting EM iterations" );     
      
      CalculateExpectation();                
  
      //for ( int iterationNumber = 0; iterationNumber < 400; iterationNumber++ )
      for ( int iterationNumber = 0; ; iterationNumber++ )
        {
        // Check if we should stop
        if ( m_StopRequested == true )
          {
          DrawChangingElements( getGraphics() );
          return;
          }

        // Save the old cost
        double  oldCost = m_Cost;

        // Do one Expectation-Maximization iteration
        DebugMessage( "iterationNumber: " + iterationNumber );
        CalculateMaximization();
        CalculateExpectation();                
      
        // Draw result
        if ( ( iterationNumber % 40 ) == 0 )
          {
          DrawChangingElements( getGraphics() );
          //getToolkit().sync();
          }

        // Check if we have converged
        if ( m_Cost > oldCost )
          {
          // Going uphill due to numerical errors
          DebugMessage( "Going uphill" );
          DebugMessage( "oldCost: " + oldCost );
          DebugMessage( "m_Cost: " + m_Cost );
          m_StopRequested = true;
          }
        else if ( ( ( oldCost - m_Cost ) / ( oldCost + m_Cost ) * 2 ) <= 1e-9 )
          {
          // Converged
          DebugMessage( "Converged: " + ( ( oldCost - m_Cost ) / ( oldCost + m_Cost ) * 2 ) );
          DebugMessage( "oldCost: " + oldCost );
          DebugMessage( "m_Cost: " + m_Cost );
          m_StopRequested = true;
          }

        } // End looping over iterations

      } // End body run method

    } 




  public void init() 
    {
    // initialize the MediaTracker
    m_MediaTracker = new MediaTracker( this );

    // The try-catch is necessary when the URL isn't valid
    // Of course this one is valid, since it is generated by
    // Java itself.
    try 
      {
      // getDocumentbase gets the applet path.
      m_BaseURL = getDocumentBase();
      }
    catch ( Exception e ) 
      {}

    // Load the images
    m_Images = new Vector();
    m_Contrasts = new Vector();
    m_Descriptions = new Vector();
    for ( int i = 1; ; i++ )    
      {
      String imageFileName = getParameter( "imageFileName" + i );
      if ( imageFileName == null )
        {
        break;
        }
      DebugMessage( "imageFileName" + i + ": " + imageFileName );
      Image image = getImage( m_BaseURL, imageFileName );
      m_Images.add( image );

      // tell the MediaTracker to keep an eye on this image, and give it an ID;
      m_MediaTracker.addImage( image, i );

      // Also read the contrast and the description
      m_Contrasts.add( getParameter( "contrast" + i ) );
      m_Descriptions.add( getParameter( "description" + i ) );
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
    try 
      {
      m_MediaTracker.waitForAll();
      }
    catch ( InterruptedException e ) 
      {}

    this.SetUpGUI();

    this.SetImage( m_ImageChoice.getSelectedIndex() );
    }

  //
  public void SetImage( int imageNumber )
    {  
    m_Contrast = ( String ) m_Contrasts.get( imageNumber );
    m_Image = ( Image ) m_Images.get( imageNumber );

    // Turn into BufferedImage
    BufferedImage  bufferedImage =  new BufferedImage( m_Image.getWidth( null ), m_Image.getHeight( null ),
                                                       BufferedImage.TYPE_INT_RGB );
    Graphics2D g2d = bufferedImage.createGraphics();
    g2d.drawImage( m_Image, 0, 0, null );
    
    // Get access to the pixel data
    Raster  raster = bufferedImage.getData();
    //DataBuffer  dataBuffer = raster.getDataBuffer();
    m_Width = raster.getWidth();
    m_Height = raster.getHeight();
    m_NumberOfPixels = m_Width * m_Height;
    int[] rgbArray = new int[ m_NumberOfPixels * 3 ];
    raster.getPixels( 0 /* x */, 0 /* y */, raster.getWidth(), raster.getHeight(), rgbArray );
    
    // Get only the red component
    m_PixelData = new int[ m_NumberOfPixels ];
    for ( int i=0; i < m_NumberOfPixels; i++ )
      {
      m_PixelData[ i ] = rgbArray[ i*3 ];
      }
   
     
    //DebugMessage( "pixel data: " );
    //for ( int i=0; i < m_NumberOfPixels; i++ )
    //  {
    //  DebugMessage( i + ": " + m_PixelData[ i ] );
    //  }
    
   
    // Get the minimum and maximum
    m_Minimum = 1000000000;
    m_Maximum = 0;
    for ( int i=0; i < m_NumberOfPixels; i++ )
      {
      if ( m_PixelData[ i ] > m_Maximum )
        {
        m_Maximum = m_PixelData[ i ];
        }
      else if ( m_PixelData[ i ] < m_Minimum )
        {
        m_Minimum = m_PixelData[ i ];
        }
      }
    DebugMessage( "m_Minimum: " + m_Minimum );
    DebugMessage( "m_Maximum: " + m_Maximum );
   
    
    // Build histogram
    m_NumberOfBins = m_Maximum - m_Minimum + 1;
    m_Histogram = new double[ m_NumberOfBins ];
    for ( int j=0; j < m_NumberOfBins; j++ )
      {
      m_Histogram[ j ] = 0;
      }
    m_NumberOfActivePixels = 0;
    for ( int i=0; i < m_NumberOfPixels; i++ )
      {
      if ( ( m_PixelData[ i ] > m_LowThreshold ) && ( m_PixelData[ i ] < m_HighThreshold ) )
        {
        m_Histogram[ m_PixelData[ i ] - m_Minimum ] += 1;
        m_NumberOfActivePixels++;
        }
      }
    for ( int j=0; j < m_NumberOfBins; j++ )
      {
      m_Histogram[ j ] /= m_NumberOfActivePixels;
      }
      
    //DebugMessage( "histogram: " );
    //for ( int j=0; j < m_NumberOfBins; j++ )
    //  {
    //  DebugMessage( j +": " + m_Histogram[ j ] );
    //  }
      
    // Calculate number of PV classes
    m_NumberOfPVClasses = 0;
    for ( int classNumber1 = 0; classNumber1 < m_NumberOfClasses; classNumber1++ )
      {
      for ( int classNumber2 = classNumber1+1; classNumber2 < m_NumberOfClasses; classNumber2++ )
        {
        m_NumberOfPVClasses++;
        }  
      }
    //DebugMessage( "NumberOfPVClasses: " + m_NumberOfPVClasses );

    // 
    this.SetInitialParametersToDefault();
    repaint();        
    }

  // 
  public void SetUpGUI()
    {    
    // Define appearance of the plotted histogram lines
    m_HistogramStroke = new BasicStroke();
    m_HistogramColor = new Color( 128, 128, 128 );
    m_TotalStroke = new BasicStroke( 1.0f,                      // Width
                                      BasicStroke.CAP_SQUARE,    // End cap
                                      BasicStroke.JOIN_MITER,    // Join style
                                      10.0f,                     // Miter limit
                                      new float[] { 5.0f, 5.0f }, // Dash pattern
                                      //new float[] { 8.0f, 4.0f, 2.0f, 4.0f },  // Dash pattern
                                      0.0f );                     // Dash phase
    m_TotalColor = new Color( 64, 64, 64 );
    m_PureStroke = m_HistogramStroke;
    m_PureColor = new Color( 192, 64, 192 );
    m_PVStroke = m_HistogramStroke;
    m_PVColor = new Color( 255, 128, 128 );
    m_SubGaussianStroke = m_HistogramStroke;
    m_SubGaussianColor = new Color( 64, 192, 64 );
             
    // Set up plot buffer. This buffer will be used to draw the plot into until completion, after which
    // the contents of the buffer will be shown on the screen. The motivation is that otherwise annoying
    // flickering will occur, due to the clearing into the background color before the plot's elements 
    // are drawn
    m_PlotBuffer = createImage( m_PlotWidth, m_PlotHeight );

    // GUI components
    setLayout( null );
    
    m_StartButton = new Button( "Start" );
    m_StartButton.setBounds( 20, 310, 100, 30 ); 
    m_StartButton.addActionListener( this );     
    add( m_StartButton );
    
    m_StopButton = new Button( "Stop" );
    m_StopButton.setBounds( 130, 310, 100, 30 ); 
    m_StopButton.addActionListener( this );     
    add( m_StopButton );

    m_ReinitializeButton = new Button( "Re-initialize" );
    m_ReinitializeButton.setBounds( 500, 270, 100, 30 ); 
    m_ReinitializeButton.addActionListener( this );     
    add( m_ReinitializeButton );

    //m_CostLabel = new Label();
    //m_CostLabel.setBounds( 20, 340, 250, 25 );
    //add( m_CostLabel );
    
    //DebugMessage( "" + m_DownsamplingFactor );
    Label  downSamplingFactorLabel = new Label( "Downsampling factor: ", Label.RIGHT );
    downSamplingFactorLabel.setBounds( 280, 270, 150, 30 );
    add( downSamplingFactorLabel );
    m_DownsamplingFactorTextField = new TextField( "" + m_DownsamplingFactor );
    m_DownsamplingFactorTextField.setBounds( 430, 270, 30, 30 ); 
    m_DownsamplingFactorTextField.addActionListener( this );
    add( m_DownsamplingFactorTextField );

    m_ShowSubGaussiansCheckbox = new Checkbox( "Show constituent Gaussians for PV classes", true ); 
    m_ShowSubGaussiansCheckbox.setBounds( 330, 310, 300, 30 );
    m_ShowSubGaussiansCheckbox.addItemListener( this );
    add( m_ShowSubGaussiansCheckbox );

    // Image choice
    m_ImageChoice = new Choice();
    for ( int i = 0; i < m_Images.size(); i++ )
      {
      m_ImageChoice.add( ( String ) m_Descriptions.get( i ) );
      }
    m_ImageChoice.select( 0 );
    m_ImageChoice.setBounds( 20, 270, 230, 30 );
    m_ImageChoice.addItemListener( this );
    add( m_ImageChoice );

    // Mean text fields
    final int  parameterFieldsXstart = 10;
    final int  parameterFieldXdelta = 210;
 
    Label mean0Label = new Label( "Mean CSF: ", Label.RIGHT );
    mean0Label.setBounds( parameterFieldsXstart, 350, 100, 30 );
    add( mean0Label );
    m_Mean0TextField = new TextField( 100 );
    m_Mean0TextField.setBounds( parameterFieldsXstart+100, 350, 100, 30 ); 
    m_Mean0TextField.addActionListener( this );
    add( m_Mean0TextField );
    
    Label mean1Label = new Label( "Mean GM: ", Label.RIGHT );
    mean1Label.setBounds( parameterFieldsXstart+parameterFieldXdelta, 350, 100, 30 );
    add( mean1Label );
    m_Mean1TextField = new TextField( 100 );
    m_Mean1TextField.setBounds( parameterFieldsXstart+parameterFieldXdelta + 100, 350, 100, 30 ); 
    m_Mean1TextField.addActionListener( this );
    add( m_Mean1TextField );
    
    Label mean2Label = new Label( "Mean WM: ", Label.RIGHT );
    mean2Label.setBounds( parameterFieldsXstart+2*parameterFieldXdelta, 350, 100, 30 );
    add( mean2Label );
    m_Mean2TextField = new TextField( 100 );
    m_Mean2TextField.setBounds( parameterFieldsXstart+2*parameterFieldXdelta+100, 350, 100, 30 ); 
    m_Mean2TextField.addActionListener( this );
    add( m_Mean2TextField );


    // Standard deviation
    Label sigma0Label = new Label( "St. dev. CSF: ", Label.RIGHT );
    sigma0Label.setBounds( parameterFieldsXstart, 390, 100, 30 );
    add( sigma0Label );
    m_Sigma0TextField = new TextField( 100 );
    m_Sigma0TextField.setBounds( parameterFieldsXstart+100, 390, 100, 30 ); 
    m_Sigma0TextField.addActionListener( this );
    add( m_Sigma0TextField );
    
    Label sigma1Label = new Label( "St. dev. GM: ", Label.RIGHT );
    sigma1Label.setBounds( parameterFieldsXstart+parameterFieldXdelta, 390, 100, 30 );
    add( sigma1Label );
    m_Sigma1TextField = new TextField( 100 );
    m_Sigma1TextField.setBounds( parameterFieldsXstart+parameterFieldXdelta + 100, 390, 100, 30 ); 
    m_Sigma1TextField.addActionListener( this );
    add( m_Sigma1TextField );
    
    Label sigma2Label = new Label( "St. dev. WM: ", Label.RIGHT );
    sigma2Label.setBounds( parameterFieldsXstart+2*parameterFieldXdelta, 390, 100, 30 );
    add( sigma2Label );
    m_Sigma2TextField = new TextField( 100 );
    m_Sigma2TextField.setBounds( parameterFieldsXstart+2*parameterFieldXdelta+100, 390, 100, 30 ); 
    m_Sigma2TextField.addActionListener( this );
    add( m_Sigma2TextField );
      

    // Pure prior
    Label purePrior0Label = new Label( "Pure CSF %: ", Label.RIGHT );
    purePrior0Label.setBounds( parameterFieldsXstart, 430, 100, 30 );
    add( purePrior0Label );
    m_PurePrior0TextField = new TextField( 100 );
    m_PurePrior0TextField.setBounds( parameterFieldsXstart+100, 430, 100, 30 ); 
    m_PurePrior0TextField.addActionListener( this );
    add( m_PurePrior0TextField );
    
    Label purePrior1Label = new Label( "Pure GM %: ", Label.RIGHT );
    purePrior1Label.setBounds( parameterFieldsXstart+parameterFieldXdelta, 430, 100, 30 );
    add( purePrior1Label );
    m_PurePrior1TextField = new TextField( 100 );
    m_PurePrior1TextField.setBounds( parameterFieldsXstart+parameterFieldXdelta + 100, 430, 100, 30 ); 
    m_PurePrior1TextField.addActionListener( this );
    add( m_PurePrior1TextField );
    
    Label purePrior2Label = new Label( "Pure WM %: ", Label.RIGHT );
    purePrior2Label.setBounds( parameterFieldsXstart+2*parameterFieldXdelta, 430, 100, 30 );
    add( purePrior2Label );
    m_PurePrior2TextField = new TextField( 100 );
    m_PurePrior2TextField.setBounds( parameterFieldsXstart+2*parameterFieldXdelta+100, 430, 100, 30 ); 
    m_PurePrior2TextField.addActionListener( this );
    add( m_PurePrior2TextField );
    
    // PV prior
    Label pvPrior0Label = new Label( "CSF-GM PV %: ", Label.RIGHT );
    pvPrior0Label.setBounds( parameterFieldsXstart, 470, 100, 30 );
    add( pvPrior0Label );
    m_PVPrior0TextField = new TextField( 100 );
    m_PVPrior0TextField.setBounds( parameterFieldsXstart+100, 470, 100, 30 ); 
    m_PVPrior0TextField.addActionListener( this );
    add( m_PVPrior0TextField );
    
    Label pvPrior1Label = new Label( "CSF-WM PV %: ", Label.RIGHT );
    pvPrior1Label.setBounds( parameterFieldsXstart+parameterFieldXdelta, 470, 100, 30 );
    add( pvPrior1Label );
    m_PVPrior1TextField = new TextField( 100 );
    m_PVPrior1TextField.setBounds( parameterFieldsXstart+parameterFieldXdelta + 100, 470, 100, 30 ); 
    m_PVPrior1TextField.addActionListener( this );
    add( m_PVPrior1TextField );
    
    Label pvPrior2Label = new Label( "GM-WM PV %: ", Label.RIGHT );
    pvPrior2Label.setBounds( parameterFieldsXstart+2*parameterFieldXdelta, 470, 100, 30 );
    add( pvPrior2Label );
    m_PVPrior2TextField = new TextField( 100 );
    m_PVPrior2TextField.setBounds( parameterFieldsXstart+2*parameterFieldXdelta+100, 470, 100, 30 ); 
    m_PVPrior2TextField.addActionListener( this );
    add( m_PVPrior2TextField );
    

    // Labels to go with the classification images
    Label  pureClassification0Label = new Label( "Pure CSF classification", Label.CENTER );
    pureClassification0Label.setBounds( 55 + 0 * 200, 520, 160, 20 );
    add( pureClassification0Label );

    Label  pureClassification1Label = new Label( "Pure GM classification", Label.CENTER );
    pureClassification1Label.setBounds( 55 + 1 * 200, 520, 160, 20 );
    add( pureClassification1Label );

    Label  pureClassification2Label = new Label( "Pure WM classification", Label.CENTER );
    pureClassification2Label.setBounds( 55 + 2 * 200, 520, 160, 20 );
    add( pureClassification2Label );

    Label  pvClassification0Label = new Label( "CSF-GM PV classification", Label.CENTER );
    pvClassification0Label.setBounds( 55 + 0 * 200, 700, 160, 20 );
    add( pvClassification0Label );

    Label  pvClassification1Label = new Label( "CSF-WM PV classification", Label.CENTER );
    pvClassification1Label.setBounds( 55 + 1 * 200, 700, 160, 20 );
    add( pvClassification1Label );
    
    Label  pvClassification2Label = new Label( "GM-WM PV classification", Label.CENTER );
    pvClassification2Label.setBounds( 55 + 2 * 200, 700, 160, 20 );
    add( pvClassification2Label );
    }
    
    
  public void start() {}

  public void stop()
    {
    //System.out.println( "Applet's stop function called" );
    StopEM();
    }

  public void itemStateChanged(ItemEvent e)
    { 
    if ( e.getSource() == m_ShowSubGaussiansCheckbox )
      {
      repaint();
      }
    else if ( e.getSource() == m_ImageChoice )
      {
      this.StopEM();
      this.SetImage( m_ImageChoice.getSelectedIndex() );
      }
    }


  public void actionPerformed( ActionEvent e ) 
    {
    if ( e.getSource() == m_StartButton ) 
      {     
      this.StartEM();
      //repaint(); 
      }
    else if ( e.getSource() == m_StopButton )
      {
      this.StopEM();
      }
    else if ( e.getSource() == m_ReinitializeButton )
      {
      this.StopEM();
      this.SetInitialParametersToDefault();
      repaint();
      }
    else if ( e.getSource() == m_DownsamplingFactorTextField )
      {
      //DebugMessage( "Retrieve mean0" );
      try 
        {
        m_DownsamplingFactor = Integer.parseInt( m_DownsamplingFactorTextField.getText() );
        } 
      catch ( NumberFormatException exception ) 
        {
        //Use default value.
        m_DownsamplingFactor = 2;
        }
      if ( m_DownsamplingFactor < 1 )
        {
        m_DownsamplingFactor = 1;
        }
      else if ( m_DownsamplingFactor > 5 ) // This limitation is not theoretically necessary, but makes the applet fool proof. A factor of 5 already results in 24 PV fractions!
        {
        m_DownsamplingFactor = 5;
        }
      m_DownsamplingFactorTextField.setText( "" + m_DownsamplingFactor );
      this.SetInitialParametersToDefault();
      repaint();
      }
    else if ( e.getSource() == m_Mean0TextField )
      {
      m_Means[ 0 ] = Double.parseDouble( m_Mean0TextField.getText() );
      this.CalculateExpectation();
      repaint();
      }
    else if ( e.getSource() == m_Mean1TextField )
      {
      m_Means[ 1 ] = Double.parseDouble( m_Mean1TextField.getText() );
      this.CalculateExpectation();
      repaint();
      }
    else if ( e.getSource() == m_Mean2TextField )
      {
      m_Means[ 2 ] = Double.parseDouble( m_Mean2TextField.getText() );
      this.CalculateExpectation();
      repaint();
      }
    else if ( e.getSource() == m_Sigma0TextField )
      {
      m_Variances[ 0 ] = Math.pow( Double.parseDouble( m_Sigma0TextField.getText() ), 2 );
      this.CalculateExpectation();
      repaint();
      }
    else if ( e.getSource() == m_Sigma1TextField )
      {
      m_Variances[ 1 ] = Math.pow( Double.parseDouble( m_Sigma1TextField.getText() ), 2 );
      this.CalculateExpectation();
      repaint();
      }
    else if ( e.getSource() == m_Sigma2TextField )
      {
      m_Variances[ 2 ] = Math.pow( Double.parseDouble( m_Sigma2TextField.getText() ), 2 );
      this.CalculateExpectation();
      repaint();
      }
    else if ( e.getSource() == m_PurePrior0TextField )
      {
      double newPrior = Double.parseDouble( m_PurePrior0TextField.getText() ) / 100;
      this.SetPrior( 0, newPrior );
      }
    else if ( e.getSource() == m_PurePrior1TextField )
      {
      double newPrior = Double.parseDouble( m_PurePrior1TextField.getText() ) / 100;
      this.SetPrior( 1, newPrior );
      }
    else if ( e.getSource() == m_PurePrior2TextField )
      {
      double newPrior = Double.parseDouble( m_PurePrior2TextField.getText() ) / 100;
      this.SetPrior( 2, newPrior );
      }
    else if ( e.getSource() == m_PVPrior0TextField )
      {
      double newPrior = Double.parseDouble( m_PVPrior0TextField.getText() ) / 100;
      this.SetPrior( 3, newPrior );
      }
    else if ( e.getSource() == m_PVPrior1TextField )
      {
      double newPrior = Double.parseDouble( m_PVPrior1TextField.getText() ) / 100;
      this.SetPrior( 4, newPrior );
      }
    else if ( e.getSource() == m_PVPrior2TextField )
      {
      double newPrior = Double.parseDouble( m_PVPrior2TextField.getText() ) / 100;
      this.SetPrior( 5, newPrior );
      }
    
    
    }   
    
      
  public void SetPrior( int number, double newPrior )
    {
    // Check which class we are referring to, and get its current value
    double oldPrior = 0;
    if ( number < m_NumberOfClasses )
      {
      oldPrior = m_PurePriors[ number ];
      }
    else
      {
      oldPrior = m_PVPriors[ number - m_NumberOfClasses ];
      }

    // Make sure the new value makes sense
    if ( newPrior > 0.99 )
      {
      newPrior = 0.99;
      }
    else if ( newPrior < 0 )
      {
      newPrior = 0;
      }

    // Take certain fraction off other priors in order to make the required change
    double fraction = ( newPrior - oldPrior ) / ( 1 - oldPrior );
    for ( int classNumber = 0; classNumber < m_NumberOfClasses; classNumber++ )
      {
      m_PurePriors[ classNumber ] -= fraction * m_PurePriors[ classNumber ];
      }
    for ( int pvClassNumber = 0; pvClassNumber < m_NumberOfClasses; pvClassNumber++ )
      {
      m_PVPriors[ pvClassNumber ] -= fraction * m_PVPriors[ pvClassNumber ];
      }
      
    // Set the prior to the specified value
    if ( number < m_NumberOfClasses )
      {
      m_PurePriors[ number ] = newPrior;
      }
    else
      {
      m_PVPriors[ number - m_NumberOfClasses ] = newPrior;
      }
  
    this.CalculateExpectation();
    repaint();
    }


  public void paint( Graphics g ) 
    {
    // now we are going to draw the gif on the screen
    // (image name,x,y,observer);
    g.drawImage( m_Image, 20, 20, 230, 230, this );

    DrawChangingElements( g );
    }


  public void DrawChangingElements( Graphics g )
    {
    DrawClassifications( g );
    DrawPlot( g );
    DrawChangingGUI( g );
    }
 
  public void DrawClassifications( Graphics g )
    {
    // Draw pure classification images
    for ( int classNumber = 0; classNumber < m_NumberOfClasses; classNumber++ )
      {
      double[] likelihoodTimesPrior = ( double[] ) m_PureLikelihoodTimesPriors.get( classNumber );
      this.DrawClassification( g, likelihoodTimesPrior,  60 + classNumber * 200, 540, 150, 150 );
      }

    // Draw PV classification images
    int pvClassNumber = -1;
    for ( int classNumber1 = 0; classNumber1 < m_NumberOfClasses; classNumber1++ )
      {
      for ( int classNumber2 = classNumber1+1; classNumber2 < m_NumberOfClasses; classNumber2++ )
        {
        pvClassNumber++;
        Vector likelihoodTimesPriorsForThisPV = ( Vector ) m_PVLikelihoodTimesPriors.get( pvClassNumber );
        
        // Retrieve likelihoodTimesPrior for this PV class by loop over all sub-Gaussians of this PV class, and 
        // adding each Gaussian's contribution
        double[]  summedLikelihoodTimesPrior = new double[ m_NumberOfBins ];
        for ( int gaussianNumber = 0; gaussianNumber < m_NumberOfGaussiansPerPV; gaussianNumber++ )
          {
          double[] likelihoodTimesPrior = ( double[] ) likelihoodTimesPriorsForThisPV.get( gaussianNumber );   
          for ( int i = m_Minimum; i < ( m_Maximum + 1 ); i++ )
            {
            summedLikelihoodTimesPrior[ i - m_Minimum ] += likelihoodTimesPrior[ i - m_Minimum ];
            }
          }
      
        // Now draw 
        this.DrawClassification( g, summedLikelihoodTimesPrior,  60 + pvClassNumber * 200, 720, 150, 150 );
        }
      }

    }

  public void DrawClassification( Graphics g, double[] likelihoodTimesPrior, int x, int y, int width, int height )
    {
    int[] pix = new int[ m_NumberOfPixels ];
    for ( int i=0; i < m_NumberOfPixels; i++ )
      {
      int red;
      if ( ( ( m_PixelData[ i ] > m_LowThreshold ) && ( m_PixelData[ i ] < m_HighThreshold ) ) == false )
        {
        //continue;
        red = 0;
        }
      else
        {
        final int histogramEntry = m_PixelData[ i ] - m_Minimum;
        red = ( int )( likelihoodTimesPrior[ histogramEntry ] / ( m_Evidence[ histogramEntry ] + 0.0000001 ) * 255 );
        }

      final int green = red;
      final int blue = red;
      final int alpha = 255;
      pix[ i ] = ( alpha << 24 ) | ( red << 16 ) | ( green << 8 ) | blue;
      }
    Image img = createImage( new MemoryImageSource( m_Width, m_Height, pix, 0, m_Width ) );
    g.drawImage( img, x, y, width, height, this );
    }


  public void DrawPlot( Graphics g )
    {
    // Create plotter and fill it's element in
    //XYPlot  plotter = new XYPlot( plotX, plotY, plotWidth, plotHeight );
    XYPlot  plotter = new XYPlot( 0, 0, m_PlotWidth, m_PlotHeight );
    plotter.Add( m_Histogram, m_HistogramColor, m_HistogramStroke );

    double[]  totalPlot = new double[ m_NumberOfBins ];
    for ( int i = m_Minimum; i < ( m_Maximum + 1 ); i++ )
      {
      totalPlot[ i - m_Minimum ] = 0;
      }
    
    // Create the pure Gaussian plots
    for ( int classNumber = 0; classNumber < m_NumberOfClasses; classNumber++ )
      {
      final double  mean = m_Means[ classNumber ]; 
      final double  variance = m_Variances[ classNumber ];
      final double  prior = m_PurePriors[ classNumber ];
      double[] plot = new double[ m_NumberOfBins ];
      for ( int i = m_Minimum; i < ( m_Maximum + 1 ); i++ )
        {
        plot[ i - m_Minimum ] = 1 / Math.sqrt( 2 * Math.PI * variance ) * Math.exp( - Math.pow( i - mean, 2 ) / variance / 2 ) * prior;
        totalPlot[ i - m_Minimum ] += plot[ i - m_Minimum ];
        }
      
      plotter.Add( plot, m_PureColor, m_PureStroke );
      }
      
    // Create the PV Gaussian plots
    int pvClassNumber = -1;
    for ( int classNumber1 = 0; classNumber1 < m_NumberOfClasses; classNumber1++ )
      {
      for ( int classNumber2 = classNumber1+1; classNumber2 < m_NumberOfClasses; classNumber2++ )
        {
        pvClassNumber++;
        
        // Retrieve parameters of the pure tissue classes this PV class is mixing for
        final double  mean1 = m_Means[ classNumber1 ]; 
        final double  variance1 = m_Variances[ classNumber1 ];
        final double  mean2 = m_Means[ classNumber2 ]; 
        final double  variance2 = m_Variances[ classNumber2 ];
        
        //DebugMessage( "pvClassNumber: " + pvClassNumber );
        
        final double  prior = m_PVPriors[ pvClassNumber ] / m_NumberOfGaussiansPerPV;
        
        //DebugMessage( "prior: " + prior );
        

        
        // Loop over all sub-Gaussians of this PV class
        double[]  pvPlot = new double[ m_NumberOfBins ];
        for ( int i = m_Minimum; i < ( m_Maximum + 1 ); i++ )
          {
          pvPlot[ i - m_Minimum ] = 0;
          }
        for ( int gaussianNumber = 0; gaussianNumber < m_NumberOfGaussiansPerPV; gaussianNumber++ )
          {
          // Retrieve mean and variance for this sub-Gaussian
          final double  alpha = ( gaussianNumber + 1 ) / ( (double) ( m_DownsamplingFactor * m_DownsamplingFactor ) );
          final double  mean = alpha * mean1 + ( 1 - alpha ) * mean2;
          final double  variance = alpha * variance1 + ( 1 - alpha ) * variance2;
          
          double[] plot = new double[ m_NumberOfBins ];
          for ( int i = m_Minimum; i < ( m_Maximum + 1 ); i++ )
            {
            final double  tmp = 1 / Math.sqrt( 2 * Math.PI * variance ) * Math.exp( - Math.pow( i - mean, 2 ) / variance / 2 ) * prior;
            plot[ i - m_Minimum ] = tmp;
            pvPlot[ i - m_Minimum ] += tmp;
            totalPlot[ i - m_Minimum ] += tmp;
            }

          if ( m_ShowSubGaussiansCheckbox.getState() )
            {
            plotter.Add( plot, m_SubGaussianColor, m_SubGaussianStroke );
            }
          }
                  
        plotter.Add( pvPlot, m_PVColor, m_PVStroke );
        }
      
      }
      
      
    plotter.Add( totalPlot, m_TotalColor, m_TotalStroke );

    // Now we're ready to fill up the buffer. First the plot        
    //plotter.Plot( g );
    plotter.Plot( m_PlotBuffer.getGraphics() );  

    // Now the -logLikelihood
    m_PlotBuffer.getGraphics().setColor( Color.black );
    m_PlotBuffer.getGraphics().drawString(  "- log-likelihood: " + m_Cost, 10, 20 );    

    // Finally, display the buffer
    g.drawImage( m_PlotBuffer, m_PlotX, m_PlotY, this );
    }


  public void DrawChangingGUI( Graphics g )
    {
    // Update GUI components
    m_Mean0TextField.setText( "" + m_Means[ 0 ] );
    m_Mean1TextField.setText( "" + m_Means[ 1 ] );
    m_Mean2TextField.setText( "" + m_Means[ 2 ] );

    m_Sigma0TextField.setText( "" + Math.sqrt( m_Variances[ 0 ] ) );
    m_Sigma1TextField.setText( "" + Math.sqrt( m_Variances[ 1 ] ) );
    m_Sigma2TextField.setText( "" + Math.sqrt( m_Variances[ 2 ] ) );

    m_PurePrior0TextField.setText( "" + 100*m_PurePriors[ 0 ] );
    m_PurePrior1TextField.setText( "" + 100*m_PurePriors[ 1 ] );
    m_PurePrior2TextField.setText( "" + 100*m_PurePriors[ 2 ] );

    m_PVPrior0TextField.setText( "" + 100*m_PVPriors[ 0 ] );
    m_PVPrior1TextField.setText( "" + 100*m_PVPriors[ 1 ] );
    m_PVPrior2TextField.setText( "" + 100*m_PVPriors[ 2 ] );

    //m_CostLabel.setText( "- log-likelihood: " + m_Cost );
    }


  public void CalculateExpectation()
    {
    //
    // Expectation step: classify
    // 
    m_Evidence = new double[ m_NumberOfBins ];
    for ( int i = m_Minimum; i < ( m_Maximum + 1 ); i++ )
      {
      m_Evidence[ i - m_Minimum ] = 0;
      }
      
    // Calculate the contributions of the pure classes
    m_PureLikelihoodTimesPriors = new Vector();
    for ( int classNumber = 0; classNumber < m_NumberOfClasses; classNumber++ )
      {
      // Create the pure Gaussian
      final double  mean = m_Means[ classNumber ]; 
      final double  variance = m_Variances[ classNumber ];
      final double  prior = m_PurePriors[ classNumber ];
      double[] likelihoodTimesPrior = new double[ m_NumberOfBins ];
      for ( int i = m_Minimum; i < ( m_Maximum + 1 ); i++ )
        {
        likelihoodTimesPrior[ i - m_Minimum ] = 1 / Math.sqrt( 2 * Math.PI * variance ) * 
                                                Math.exp( - Math.pow( i - mean, 2 ) / variance / 2 ) * prior;
        m_Evidence[ i - m_Minimum ] += likelihoodTimesPrior[ i - m_Minimum ];
        }
        
      // 
      m_PureLikelihoodTimesPriors.add( likelihoodTimesPrior );
      }
      
      
    // Calculate the contributions of the PV classes
    m_PVLikelihoodTimesPriors = new Vector();
    int pvClassNumber = -1;
    for ( int classNumber1 = 0; classNumber1 < m_NumberOfClasses; classNumber1++ )
      {
      for ( int classNumber2 = classNumber1+1; classNumber2 < m_NumberOfClasses; classNumber2++ )
        {
        pvClassNumber++;
        
        // Retrieve parameters of the pure tissue classes this PV class is mixing for
        final double  mean1 = m_Means[ classNumber1 ]; 
        final double  variance1 = m_Variances[ classNumber1 ];
        final double  mean2 = m_Means[ classNumber2 ]; 
        final double  variance2 = m_Variances[ classNumber2 ];
        
        //DebugMessage( "pvClassNumber: " + pvClassNumber );
        
        final double  prior = m_PVPriors[ pvClassNumber ] / m_NumberOfGaussiansPerPV;
        
        //DebugMessage( "prior: " + prior );
        

        // Loop over all sub-Gaussians of this PV class
        Vector likelihoodTimesPriorsForThisPV = new Vector();
        for ( int gaussianNumber = 0; gaussianNumber < m_NumberOfGaussiansPerPV; gaussianNumber++ )
          {
          // Retrieve mean and variance for this sub-Gaussian
          final double  alpha = ( gaussianNumber + 1 ) / ( (double) ( m_DownsamplingFactor * m_DownsamplingFactor ) );
          final double  mean = alpha * mean1 + ( 1 - alpha ) * mean2;
          final double  variance = alpha * variance1 + ( 1 - alpha ) * variance2;
          
          double[] likelihoodTimesPrior = new double[ m_NumberOfBins ];
          for ( int i = m_Minimum; i < ( m_Maximum + 1 ); i++ )
            {
            final double  tmp = 1 / Math.sqrt( 2 * Math.PI * variance ) * Math.exp( - Math.pow( i - mean, 2 ) / variance / 2 ) * prior;
            likelihoodTimesPrior[ i - m_Minimum ] = tmp;
            m_Evidence[ i - m_Minimum ] += tmp;
            }
          
          likelihoodTimesPriorsForThisPV.add( likelihoodTimesPrior );
          }
                  
        m_PVLikelihoodTimesPriors.add( likelihoodTimesPriorsForThisPV );
        }
      
      }



    //
    // Show the current value of the objective function
    //
    m_Cost = 0;
    for ( int i = m_Minimum; i < ( m_Maximum + 1 ); i++ )
      {
      m_Cost -= Math.log( m_Evidence[ i - m_Minimum ] ) * m_Histogram[ i - m_Minimum ];
      }
    m_Cost *= m_NumberOfActivePixels;
    DebugMessage( "Current cost: " + m_Cost );
    }  
    
    
    
    
    
  public void CalculateMaximization()
    {
    // 
    // Maximization step: update the parameters
    //
    double[] totalWeights = new double[ m_NumberOfClasses ];
    double[] means = new double[ m_NumberOfClasses ];
    double[] variancesTerm1 = new double[ m_NumberOfClasses ];
    double[] variancesTerm2 = new double[ m_NumberOfClasses ];
    double[] variancesTerm3 = new double[ m_NumberOfClasses ];
    double[] purePriors = new double[ m_NumberOfClasses ];
    for ( int classNumber = 0; classNumber < m_NumberOfClasses; classNumber++ )
      {
      totalWeights[ classNumber ] = 0;
      means[ classNumber ] = 0;
      variancesTerm1[ classNumber ] = 0;
      variancesTerm2[ classNumber ] = 0;
      variancesTerm3[ classNumber ] = 0;
      purePriors[ classNumber ] = 0;
      }
    double[] PVPriors = new double[ m_NumberOfPVClasses ];
    for ( int pvClassNumber = 0; pvClassNumber < m_NumberOfClasses; pvClassNumber++ )
      {
      PVPriors[ pvClassNumber ] = 0;
      }
    
    // Contribution of pure classes  
    for ( int classNumber = 0; classNumber < m_NumberOfClasses; classNumber++ )
      {
      double[] likelihoodTimesPrior = ( double[] ) m_PureLikelihoodTimesPriors.get( classNumber );
      
      double  meanContribution = 0;
      double  varianceContributionTerm1 = 0;
      double  varianceContributionTerm2 = 0;
      double  varianceContributionTerm3 = 0;
      double  total = 0;
      final double M = Math.pow( m_DownsamplingFactor, 2 );
      final double  variance = m_Variances[ classNumber ];
      final double  tau = variance * ( M - 1 ) / Math.pow( M, 2 );
      //final double tau = 0;
      for ( int i = m_Minimum; i < ( m_Maximum + 1 ); i++ )
        {
        double weight = likelihoodTimesPrior[ i - m_Minimum ] / m_Evidence[ i - m_Minimum ] * m_Histogram[ i - m_Minimum ];
        meanContribution += i / M * weight;
        varianceContributionTerm1 += ( tau + Math.pow( i / M, 2 ) ) * weight;
        varianceContributionTerm2 += i / M * weight;
        varianceContributionTerm3 += weight;
        total += weight;
        }
        
      means[ classNumber ] += meanContribution;
      variancesTerm1[ classNumber ] += varianceContributionTerm1;
      variancesTerm2[ classNumber ] += varianceContributionTerm2;
      variancesTerm3[ classNumber ] += varianceContributionTerm3;
      purePriors[ classNumber ] = total;
      totalWeights[ classNumber ] += total;
      }
    

    // Contribution of PV classes
    int pvClassNumber = -1;
    for ( int classNumber1 = 0; classNumber1 < m_NumberOfClasses; classNumber1++ )
      {
      for ( int classNumber2 = classNumber1+1; classNumber2 < m_NumberOfClasses; classNumber2++ )
        {
        pvClassNumber++;
        
        // Retrieve parameters of the pure tissue classes this PV class is mixing for
        final double  mean1 = m_Means[ classNumber1 ]; 
        final double  variance1 = m_Variances[ classNumber1 ];
        final double  mean2 = m_Means[ classNumber2 ]; 
        final double  variance2 = m_Variances[ classNumber2 ];
          
        // Loop over all sub-Gaussians of this PV class
        Vector likelihoodTimesPriorsForThisPV = ( Vector ) m_PVLikelihoodTimesPriors.get( pvClassNumber );
        for ( int gaussianNumber = 0; gaussianNumber < m_NumberOfGaussiansPerPV; gaussianNumber++ )
          {
          double[] likelihoodTimesPrior = ( double[] ) likelihoodTimesPriorsForThisPV.get( gaussianNumber );
          
          // Retrieve mean and variance for this sub-Gaussian
          final double  alpha = ( gaussianNumber + 1 ) / ( (double) ( m_DownsamplingFactor * m_DownsamplingFactor ) );
          final double  mean = alpha * mean1 + ( 1 - alpha ) * mean2;
          final double  variance = alpha * variance1 + ( 1 - alpha ) * variance2;
          
          double  mean1Contribution = 0;
          double  variance1ContributionTerm1 = 0;
          double  variance1ContributionTerm2 = 0;
          double  variance1ContributionTerm3 = 0;
          double  total1 = 0;
          double  mean2Contribution = 0;
          double  variance2ContributionTerm1 = 0;
          double  variance2ContributionTerm2 = 0;
          double  variance2ContributionTerm3 = 0;
          double  total2 = 0;
          for ( int i = m_Minimum; i < ( m_Maximum + 1 ); i++ )
            {
            double weight = likelihoodTimesPrior[ i - m_Minimum ] / m_Evidence[ i - m_Minimum ] * m_Histogram[ i - m_Minimum ];
            
            //DebugMessage( "weight for intensity " + i + " for gaussianNumber " + gaussianNumber + 
            //                    " of the combination (" + classNumber1 + ", " + classNumber2 + ") : " + weight );
            
            final double M = Math.pow( m_DownsamplingFactor, 2 );
            
            // Contribution to class 1
            final double tau1 = mean1 / M + variance1 / M / variance * ( i - mean );
            final double sigma1 = ( variance - variance1 / M ) / variance * variance1 / M;
            mean1Contribution += weight * alpha * tau1;
            variance1ContributionTerm1 += weight * alpha * ( sigma1 + Math.pow( tau1, 2 ) );
            variance1ContributionTerm2 += weight * alpha * tau1;
            variance1ContributionTerm3 += weight * alpha;
            total1 += weight * alpha;
                            
            // Contribution to class 2
            final double tau2 = mean2 / M + variance2 / M / variance * ( i - mean );
            final double sigma2 = ( variance - variance2 / M ) / variance * variance2 / M;
            mean2Contribution += weight * ( 1 - alpha ) * tau2;
            variance2ContributionTerm1 += weight * ( 1 - alpha ) * ( sigma2 + Math.pow( tau2, 2 ) );
            variance2ContributionTerm2 += weight * ( 1 - alpha ) * tau2;
            variance2ContributionTerm3 += weight * ( 1 - alpha );
            total2 += weight * ( 1 - alpha );
            }
            
            
              
          means[ classNumber1 ] += mean1Contribution;
          variancesTerm1[ classNumber1 ] += variance1ContributionTerm1;
          variancesTerm2[ classNumber1 ] += variance1ContributionTerm2;
          variancesTerm3[ classNumber1 ] += variance1ContributionTerm3;
          totalWeights[ classNumber1 ] += total1;
            
          means[ classNumber2 ] += mean2Contribution;
          variancesTerm1[ classNumber2 ] += variance2ContributionTerm1;
          variancesTerm2[ classNumber2 ] += variance2ContributionTerm2;
          variancesTerm3[ classNumber2 ] += variance2ContributionTerm3;
          totalWeights[ classNumber2 ] += total2;
           

          PVPriors[ pvClassNumber ] += total1 + total2;

          } // End loop over all sub-Gaussians of this PV class
                  
        }

      } // End loop over all possible 2-combinations of tissue types
    
      
    // Use the collected information to update the parameters   
    final double M = Math.pow( m_DownsamplingFactor, 2 );
    for ( int classNumber = 0; classNumber < m_NumberOfClasses; classNumber++ )
      {
      m_Means[ classNumber ] = M * means[ classNumber ] / totalWeights[ classNumber ];
      m_Variances[ classNumber ] = M * ( variancesTerm1[ classNumber ] 
                                          - 2 * m_Means[ classNumber ] / M * variancesTerm2[ classNumber ] 
                                          + Math.pow( m_Means[ classNumber ] / M, 2 ) * variancesTerm3[ classNumber ] ) 
                                    / totalWeights[ classNumber ];
      }
    
    // Update the weights 
    double  sum = 0;
    for ( int classNumber = 0; classNumber < m_NumberOfClasses; classNumber++ )
      {
      sum += purePriors[ classNumber ];
      }
    for ( pvClassNumber = 0; pvClassNumber < m_NumberOfPVClasses; pvClassNumber++ )
      {
      sum += PVPriors[ pvClassNumber ];
      }
      
      
    for ( int classNumber = 0; classNumber < m_NumberOfClasses; classNumber++ )
      {
      m_PurePriors[ classNumber ] = purePriors[ classNumber ] / sum;
      }
    for ( pvClassNumber = 0; pvClassNumber < m_NumberOfPVClasses; pvClassNumber++ )
      {
      m_PVPriors[ pvClassNumber ] = PVPriors[ pvClassNumber ] / sum;
      }
  
    
    }
    
    
    
    
  public void StartEM()
    {
    // Make sure we're not running already
    if ( m_Solver != null )
      {
      // 
      if ( m_Solver.isAlive() )
        {
        DebugMessage( "Have already a thread doing our work" );     
        return;
        }
      }

    m_Solver = new EMThread();
    }
    

  public void StopEM()
    {
    DebugMessage( "Trying to stop the solver" );     

    if ( m_Solver == null )
      {
      return;
      }

    // Cause the EMThread to finish up
    m_Solver.m_StopRequested = true;
    while ( m_Solver.isAlive() ) 
      {
      try 
        {
        Thread.sleep( 100 );
        } 
      catch ( InterruptedException e ) 
        {}
      }
    m_Solver = null;
    
    }
    

  public void SetInitialParametersToDefault()
    {
    // Calculate number of Gaussians per PV class
    m_NumberOfGaussiansPerPV = m_DownsamplingFactor * m_DownsamplingFactor - 1;
    //DebugMessage( "m_NumberOfGaussiansPerPV: " + m_NumberOfGaussiansPerPV );
      
    // Initialize parameters    
    m_Means = new double[ m_NumberOfClasses ];
    m_Variances = new double[ m_NumberOfClasses ];
    m_PurePriors = new double[ m_NumberOfClasses ];
    m_PVPriors = new double[ m_NumberOfPVClasses ];
    for ( int classNumber = 0; classNumber < m_NumberOfClasses; classNumber++ )
      {
/*      m_Means[ classNumber ] = m_Minimum + ( ( m_Maximum - m_Minimum ) / ( ( double )( m_NumberOfClasses + 1 ) ) ) * 
                                           ( classNumber + 1 );*/
      m_Means[ classNumber ] = m_Minimum + ( ( m_Maximum - m_Minimum ) / ( ( double )( m_NumberOfClasses ) ) ) * 
                                           ( classNumber + 0.5 );
      m_Variances[ classNumber ] = Math.pow( ( m_Maximum - m_Minimum ) / 3.0, 2 );
      if ( m_NumberOfGaussiansPerPV != 0 )
        {
        m_PurePriors[ classNumber ] = 1 / ( ( double )( m_NumberOfClasses + m_NumberOfPVClasses ) );
        }
      else
        {
        m_PurePriors[ classNumber ] = 1 / ( ( double )( m_NumberOfClasses ) );
        }
      }
    for ( int pvClassNumber = 0; pvClassNumber < m_NumberOfPVClasses; pvClassNumber++ )
      {
      if ( m_NumberOfGaussiansPerPV != 0 )
        {
        m_PVPriors[ pvClassNumber ] = 1 / ( ( double )( m_NumberOfClasses + m_NumberOfPVClasses ) );
        }
      else
        {
        m_PVPriors[ pvClassNumber ] = 0;
        }
      }


    // Depending on the contrast, swap initial means to keep the labels in the applet (WM/GM/CSF) relevant
    DebugMessage( "Adjusting for contrast " + m_Contrast );
    String compareString = "T2";
    if ( m_Contrast.matches( compareString ) )
      {
      DebugMessage( "swapping" );
      double tmp = m_Means[ 0 ];
      m_Means[ 0 ] = m_Means[ 2 ];
      m_Means[ 2 ] = tmp;
      }
     
    // Recalculate the initial classification  
    CalculateExpectation();   
    }
    

  public void DebugMessage( String message )
    {
    // Comment this out for final release of the Applet
    //System.out.println( message );
    }

}