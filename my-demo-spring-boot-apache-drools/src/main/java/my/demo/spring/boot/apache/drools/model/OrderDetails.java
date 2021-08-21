package my.demo.spring.boot.apache.drools.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Data
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
public class OrderDetails {
	private String orderName;
	private String cardType;
	private Double orderPrice;
	private Double discountInPercentage;

	public String getOrderName() {
		return orderName;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public Double getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(Double orderPrice) {
		this.orderPrice = orderPrice;
	}

	public Double getDiscountInPercentage() {
		return discountInPercentage;
	}

	public void setDiscountInPercentage(Double discountInPercentage) {
		this.discountInPercentage = discountInPercentage;
	}

	@Override
	public String toString() {
		return "OrderDetails [orderName=" + orderName + ", cardType=" + cardType + ", orderPrice=" + orderPrice
				+ ", discountInPercentage=" + discountInPercentage + "]";
	}

	public OrderDetails(String orderName, String cardType, Double orderPrice) {
		super();
		this.orderName = orderName;
		this.cardType = cardType;
		this.orderPrice = orderPrice;
		this.discountInPercentage = 0d;
	}

	public OrderDetails() {
		super();
		this.discountInPercentage = 0d;
	}

}
