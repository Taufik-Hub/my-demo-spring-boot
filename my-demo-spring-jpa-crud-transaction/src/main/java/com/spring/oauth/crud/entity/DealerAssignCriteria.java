package com.spring.oauth.crud.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DealerAssignCriteria implements Serializable {
    private static final long serialVersionUID = 3398791983230666548L;
    private Long orderId;
    private String orderTypeCode;
    private String internalDealerCode;
    private String phhDealerCode;
    private Integer makeCode;
    private String make;
    private String productClass;
    private String isoCountryCode;
    private Boolean doeIndicator;
    private Boolean oFlag;
    @JsonProperty("source")
    private DealerAssignSource dealerAssignSource;


    public DealerAssignCriteria() {
		// Empty Constructor
    }

    public Boolean getDoeIndicator() {
        return doeIndicator;
    }

    public String getOrderTypeCode() {
        return orderTypeCode;
    }

    public Integer getMakeCode() {
        return makeCode;
    }

    public String getProductClass() {
        return productClass;
    }

    public String getIsoCountryCode() {
        return isoCountryCode;
    }

    public String getInternalDealerCode() {
        return internalDealerCode;
    }

    public String getPhhDealerCode() {
        return phhDealerCode;
    }

    public Boolean getoFlag() {
        return oFlag;
    }

    public Long getOrderId() {
        return orderId;
    }

    public String getMake() {
        return make;
    }

    
    public DealerAssignSource getDealerAssignSource() {
		return dealerAssignSource;
	}

	 public DealerAssignCriteria setDoeIndicator(Boolean doeIndicator) {
        this.doeIndicator = doeIndicator;
        return this;
    }

    public DealerAssignCriteria setOrderTypeCode(String orderTypeCode) {
        this.orderTypeCode = orderTypeCode;
        return this;
    }

    public DealerAssignCriteria setMakeCode(Integer makeCode) {
        this.makeCode = makeCode;
        return this;
    }

    public DealerAssignCriteria setProductClass(String productClass) {
        this.productClass = productClass;
        return this;
    }

    public DealerAssignCriteria setIsoCountryCode(String isoCountryCode) {
        this.isoCountryCode = isoCountryCode;
        return this;
    }

    public DealerAssignCriteria setInternalDealerCode(String internalDealerCode) {
        this.internalDealerCode = internalDealerCode;
        return this;
    }

    public DealerAssignCriteria setPhhDealerCode(String phhDealerCode) {
        this.phhDealerCode = phhDealerCode;
        return this;
    }

    public DealerAssignCriteria setIsoCountryCodeByCorpCode(String corpCode) {
        this.isoCountryCode = corpCode;
        //this.isoCountryCode = CorpCode.getIsoCountryCode(corpCode);
        return this;
    }

    public DealerAssignCriteria setoFlag(Boolean oFlag) {
        this.oFlag = oFlag;
        return this;
    }

    public DealerAssignCriteria setOrderId(Long orderId) {
        this.orderId = orderId;
        return this;
    }

    public DealerAssignCriteria setMake(String make) {
        this.make = make;
        return this;
    }
    
    
    public DealerAssignCriteria setDealerAssignSource(DealerAssignSource dealerAssignSource) {
		this.dealerAssignSource = dealerAssignSource;
		return this;
	}

	@Override
	public String toString() {
		return "DealerAssignCriteria [orderId=" + orderId + ", orderTypeCode=" + orderTypeCode + ", internalDealerCode="
				+ internalDealerCode + ", phhDealerCode=" + phhDealerCode + ", makeCode=" + makeCode + ", make=" + make
				+ ", productClass=" + productClass + ", isoCountryCode=" + isoCountryCode + ", doeIndicator="
				+ doeIndicator + ", oFlag=" + oFlag + ", dealerAssignSource=" + dealerAssignSource + "]";
	}
    
    
}


