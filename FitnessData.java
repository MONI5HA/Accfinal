package com.example.androidapp_1;

import java.util.List;


public class FitnessData {
    private String name;
    private String logoUrl;
    private List<String> benefits;
    private List<String> pricePlans;

    public FitnessData() {
    }

    public FitnessData(String name, String logoUrl, List<String> benefits, List<String> pricePlans) {
        this.name = name;
        this.logoUrl = logoUrl;
        this.benefits = benefits;
        this.pricePlans = pricePlans;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public List<String> getBenefits() {
        return benefits;
    }

    public void setBenefits(List<String> benefits) {
        this.benefits = benefits;
    }

    public List<String> getPricePlans() {
        return pricePlans;
    }

    public void setPricePlans(List<String> pricePlans) {
        this.pricePlans = pricePlans;
    }
}
