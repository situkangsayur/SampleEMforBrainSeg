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
        start();
    }

    public void setView(CountPanel view) {
        this.view = view;
    }

    public void run() {
        System.out.println("Starting EM iterations");

        view.CalculateExpectation();

        //for ( int iterationNumber = 0; iterationNumber < 400; iterationNumber++ )
        for (int iterationNumber = 0;; iterationNumber++) {
            // Check if we should stop
            if (m_StopRequested == true) {
                view.DrawChangingElements(view.getGraphics());
                return;
            }

            // Save the old cost
            double oldCost = view.getM_Cost();

            // Do one Expectation-Maximization iteration
            System.out.println("iterationNumber: " + iterationNumber);
            view.CalculateMaximization();
            view.CalculateExpectation();

            // Draw result
            if ((iterationNumber % 40) == 0) {
                view.DrawChangingElements(view.getGraphics());
                //getToolkit().sync();
            }

            // Check if we have converged
            if (view.getM_Cost() > oldCost) {
                // Going uphill due to numerical errors
                System.out.println("Going uphill");
                System.out.println("oldCost: " + oldCost);
                System.out.println("m_Cost: " + view.getM_Cost());
                m_StopRequested = true;
            } else if (((oldCost - view.getM_Cost()) / (oldCost + view.getM_Cost()) * 2) <= 1e-9) {
                // Converged
                System.out.println("Converged: " + ((oldCost - view.getM_Cost()) / (oldCost + view.getM_Cost()) * 2));
                System.out.println("oldCost: " + oldCost);
                System.out.println("m_Cost: " + view.getM_Cost());
                m_StopRequested = true;
            }

        } // End looping over iterations

    } // End body run method
}