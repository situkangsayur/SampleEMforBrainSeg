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
        view.showResultMessage("Starting EM iterations");

        view.calculateExpectation();

        //for ( int iterationNumber = 0; iterationNumber < 400; iterationNumber++ )
        for (int iterationNumber = 0;; iterationNumber++) {
            // Check if we should stop
            if (m_StopRequested == true) {
                view.paintGraph();
                return;
            }

            // Save the old cost
            double oldCost = view.getMaxLikelihood();

            // Do one Expectation-Maximization iteration
//            view.DebugMessage("iterationNumber: " + iterationNumber);
//            view.DebugMessage("cost old " + oldCost);

            view.calculateMaximization();
            view.calculateExpectation();

            // Draw result
            if ((iterationNumber % 40) == 0) {
//                view.DrawChangingElements(view.getGraphics());
                view.paintGraph();
                //getToolkit().sync();
            }

            view.debugMessage("cost new " + view.getMaxLikelihood());

            // Check if we have converged
            if (view.getMaxLikelihood() > oldCost) {
                // Going uphill due to numerical errors

                view.showResultMessage("Going uphill");
                view.showResultMessage("oldCost: " + oldCost);
                view.showResultMessage("m_Cost: " + view.getMaxLikelihood());

                m_StopRequested = true;
            } else if (((oldCost - view.getMaxLikelihood()) / (oldCost + view.getMaxLikelihood()) * 2) <= 1e-9) {
                // Converged

                view.showResultMessage("Converged: " + ((oldCost - view.getMaxLikelihood()) / (oldCost + view.getMaxLikelihood()) * 2));
                view.showResultMessage("oldCost: " + oldCost);
                view.showResultMessage("m_Cost: " + view.getMaxLikelihood());

                m_StopRequested = true;
            }

        } // End looping over iterations

    } // End body run method
}