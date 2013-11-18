/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sampleem.swing.control;

import sampleem.swing.CountPanel;
import sampleem.swing.model.ModelPanel;

/**
 *
 * @author hendri
 */
public class ControlPanel {

    private CountPanel view;
    private ModelPanel model;

    public ControlPanel(CountPanel view) {
        this.view = view;
    }

    public void setModel(ModelPanel model) {
        this.model = model;
    }

    public void startEm() {
    }

    public void initEm() {
    }
}
