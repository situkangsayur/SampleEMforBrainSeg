/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sampleem.swing;

import java.util.Vector;

/**
 *
 * @author hendri
 */
public class ResultEntity {

    private Double mean0;
    private Double mean1;
    private Double mean2;
    private Double sigma0;
    private Double sigma1;
    private Double sigma3;
    private Double purePrior0;
    private Double purePrior1;
    private Double purePrior2;
    private Double pvPrior0;
    private Double pvPrior1;
    private Double pvPrior2;
    private double[] m_Means;
    private double[] m_Variances;
    private double[] m_PurePriors;
    private double[] m_PVPriors;
    private Vector m_Images;
    private Vector m_Contrasts;
    private Vector m_Descriptions;

    public ResultEntity() {
        m_Images = new Vector();
        m_Contrasts = new Vector();
        m_Descriptions = new Vector();
    }

    public Vector getM_Images() {
        return m_Images;
    }

    public void setM_Images(Vector m_Images) {
        this.m_Images = m_Images;
    }

    public Vector getM_Contrasts() {
        return m_Contrasts;
    }

    public void setM_Contrasts(Vector m_Contrasts) {
        this.m_Contrasts = m_Contrasts;
    }

    public Vector getM_Descriptions() {
        return m_Descriptions;
    }

    public void setM_Descriptions(Vector m_Descriptions) {
        this.m_Descriptions = m_Descriptions;
    }

    public double[] getM_Means() {
        return m_Means;
    }

    public void setM_Means(double[] m_Means) {
        this.m_Means = m_Means;
    }

    public double[] getM_Variances() {
        return m_Variances;
    }

    public void setM_Variances(double[] m_Variances) {
        this.m_Variances = m_Variances;
    }

    public double[] getM_PurePriors() {
        return m_PurePriors;
    }

    public void setM_PurePriors(double[] m_PurePriors) {
        this.m_PurePriors = m_PurePriors;
    }

    public double[] getM_PVPriors() {
        return m_PVPriors;
    }

    public void setM_PVPriors(double[] m_PVPriors) {
        this.m_PVPriors = m_PVPriors;
    }

    public Double getMean0() {
        return mean0;
    }

    public void setMean0(Double mean0) {
        this.mean0 = mean0;
    }

    public Double getMean1() {
        return mean1;
    }

    public void setMean1(Double mean1) {
        this.mean1 = mean1;
    }

    public Double getMean2() {
        return mean2;
    }

    public void setMean2(Double mean2) {
        this.mean2 = mean2;
    }

    public Double getSigma0() {
        return sigma0;
    }

    public void setSigma0(Double sigma0) {
        this.sigma0 = sigma0;
    }

    public Double getSigma1() {
        return sigma1;
    }

    public void setSigma1(Double sigma1) {
        this.sigma1 = sigma1;
    }

    public Double getSigma3() {
        return sigma3;
    }

    public void setSigma3(Double sigma3) {
        this.sigma3 = sigma3;
    }

    public Double getPurePrior0() {
        return purePrior0;
    }

    public void setPurePrior0(Double purePrior0) {
        this.purePrior0 = purePrior0;
    }

    public Double getPurePrior1() {
        return purePrior1;
    }

    public void setPurePrior1(Double purePrior1) {
        this.purePrior1 = purePrior1;
    }

    public Double getPurePrior2() {
        return purePrior2;
    }

    public void setPurePrior2(Double purePrior2) {
        this.purePrior2 = purePrior2;
    }

    public Double getPvPrior0() {
        return pvPrior0;
    }

    public void setPvPrior0(Double pvPrior0) {
        this.pvPrior0 = pvPrior0;
    }

    public Double getPvPrior1() {
        return pvPrior1;
    }

    public void setPvPrior1(Double pvPrior1) {
        this.pvPrior1 = pvPrior1;
    }

    public Double getPvPrior2() {
        return pvPrior2;
    }

    public void setPvPrior2(Double pvPrior2) {
        this.pvPrior2 = pvPrior2;
    }
}
