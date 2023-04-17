package com.cs490.shoppingCart.ReportingModule.service;

/**
 * The Class ReportDTO.
 */
public class ReportDTO {
	private int totalRevenue;
    private double rate;
    private int numberOfTransactions;
    private int aov;

    public ReportDTO(int revenue, double rate, int transactions, int aov){
        this.totalRevenue=revenue;
        this.rate=rate;
        this.numberOfTransactions=transactions;
        this.aov=aov;
    }

    public int getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(int totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public int getNumberOfTransactions() {
        return numberOfTransactions;
    }

    public void setNumberOfTransactions(int numberOfTransactions) {
        this.numberOfTransactions = numberOfTransactions;
    }

    public int getAov() {
        return aov;
    }

    public void setAov(int aov) {
        this.aov = aov;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "ReportDTO{" +
                "totalRevenue=" + totalRevenue +
                ", rate=" + rate +
                ", numberOfTransactions=" + numberOfTransactions +
                ", aov=" + aov +
                '}';
    }
}
