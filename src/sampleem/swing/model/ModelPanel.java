/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sampleem.swing.model;

import sampleem.swing.ResultEntity;
import sampleem.swing.listener.Listener;

/**
 *
 * @author hendri
 */
public class ModelPanel {

    private ResultEntity result;
    private String url;
    private Listener listener;
//    private EMAlgo algo;

    public void setResult(ResultEntity result) {
        this.result = result;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    protected void fireOnStart() {
        if (listener != null) {
            listener.start(result);
        }
    }

    protected void fireOnInit() {
        if (listener != null) {
            listener.initSystem();
        }
    }

    public void startAlgorithm() {
//        if (algo == null) {
//            algo = new EMAlgo();
//        }
        fireOnStart();
    }

    public void initAlgorithm() {
        fireOnInit();
    }
}
