/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sampleem.swing;

/**
 *
 * @author hendri
 */
public class EMThread extends Thread {

    private boolean m_StopRequested = false;
    private CountPanel view;

    public boolean isM_StopRequested() {
        return m_StopRequested;
    }

    public void setM_StopRequested(boolean m_StopRequested) {
        this.m_StopRequested = m_StopRequested;
    }

    public EMThread() {
    }

    public void startThread() {
        start();
    }

    public void setView(CountPanel view) {
        this.view = view;
    }

    public void run() {
        view.DebugMessage("Starting EM iterations");

        view.CalculateExpectation();

        //for ( int iterationNumber = 0; iterationNumber < 400; iterationNumber++ )
        for (int iterationNumber = 0;; iterationNumber++) {
            // Check if we should stop
            if (m_StopRequested == true) {
                view.paintGraph();
                return;
            }

            // Save the old cost
            double oldCost = view.getM_Cost();

            // Do one Expectation-Maximization iteration
//            view.DebugMessage("iterationNumber: " + iterationNumber);
//            view.DebugMessage("cost old " + oldCost);

            view.CalculateMaximization();
            view.CalculateExpectation();

            // Draw result
            if ((iterationNumber % 40) == 0) {
//                view.DrawChangingElements(view.getGraphics());
                view.paintGraph();
                //getToolkit().sync();
            }

            view.DebugMessage("cost new " + view.getM_Cost());

            // Check if we have converged
            if (view.getM_Cost() > oldCost) {
                // Going uphill due to numerical errors
                view.DebugMessage("Going uphill");
                view.DebugMessage("oldCost: " + oldCost);
                view.DebugMessage("m_Cost: " + view.getM_Cost());
                m_StopRequested = true;
            } else if (((oldCost - view.getM_Cost()) / (oldCost + view.getM_Cost()) * 2) <= 1e-9) {
                // Converged
                view.DebugMessage("Converged: " + ((oldCost - view.getM_Cost()) / (oldCost + view.getM_Cost()) * 2));
                view.DebugMessage("oldCost: " + oldCost);
                view.DebugMessage("m_Cost: " + view.getM_Cost());
                m_StopRequested = true;
            }

        } // End looping over iterations

    } // End body run method
}