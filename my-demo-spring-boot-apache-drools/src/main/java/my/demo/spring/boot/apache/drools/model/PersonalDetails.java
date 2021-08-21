package my.demo.spring.boot.apache.drools.model;

import java.io.Serializable;
import java.util.Optional;

public class PersonalDetails implements Serializable {
	private static final long serialVersionUID = 6983699099801429926L;
	
	enum IncomeType{
		INDIVIDUAL,
		BUSINESS
	}

	private String incomeType;
	private int age;
	private long incomeAmount;
	private int educationCess;
	private float taxDue;
	private int taxPercentage;
	private long calculatedTaxSlab;
	private long calculatedTaxableIncome;
	private String taxSlabMessage;

	public PersonalDetails() {
		super();
	}

	public PersonalDetails(IncomeType incomeType, int age, long incomeAmount, int educationCess, float taxDue,
			int taxPercentage, long calculatedTaxSlab, long calculatedTaxableIncome, String taxSlabMessage) {
		super();
		this.incomeType = Optional.ofNullable(incomeType).map(Enum::toString).orElse("");
		this.age = age;
		this.incomeAmount = incomeAmount;
		this.educationCess = educationCess;
		this.taxDue = taxDue;
		this.taxPercentage = taxPercentage;
		this.calculatedTaxSlab = calculatedTaxSlab;
		this.calculatedTaxableIncome = calculatedTaxableIncome;
		this.taxSlabMessage = taxSlabMessage;
	}

	public String getIncomeType() {
		return incomeType;
	}

	public void setIncomeType(IncomeType incomeType) {
		this.incomeType = Optional.ofNullable(incomeType).map(Enum::toString).orElse("");
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public long getIncomeAmount() {
		return incomeAmount;
	}

	public void setIncomeAmount(long incomeAmount) {
		this.incomeAmount = incomeAmount;
	}

	public int getEducationCess() {
		return educationCess;
	}

	public void setEducationCess(int educationCess) {
		this.educationCess = educationCess;
	}

	public float getTaxDue() {
		return taxDue;
	}

	public void setTaxDue(float taxDue) {
		this.taxDue = taxDue;
	}

	public int getTaxPercentage() {
		return taxPercentage;
	}

	public void setTaxPercentage(int taxPercentage) {
		this.taxPercentage = taxPercentage;
	}

	public long getCalculatedTaxSlab() {
		return calculatedTaxSlab;
	}

	public void setCalculatedTaxSlab(long calculatedTaxSlab) {
		this.calculatedTaxSlab = calculatedTaxSlab;
	}

	public long getCalculatedTaxableIncome() {
		return calculatedTaxableIncome;
	}

	public void setCalculatedTaxableIncome(long calculatedTaxableIncome) {
		this.calculatedTaxableIncome = calculatedTaxableIncome;
	}

	public String getTaxSlabMessage() {
		return taxSlabMessage;
	}

	public void setTaxSlabMessage(String taxSlabMessage) {
		this.taxSlabMessage = taxSlabMessage;
	}

	@Override
	public String toString() {
		return "IncomeDetail [incomeType=" + incomeType + ", age=" + age + ", incomeAmount=" + incomeAmount
				+ ", educationCess=" + educationCess + ", taxDue=" + taxDue + ", taxPercentage=" + taxPercentage
				+ ", calculatedTaxSlab=" + calculatedTaxSlab + ", calculatedTaxableIncome=" + calculatedTaxableIncome
				+ ", taxSlabDetails=" + taxSlabMessage + "]";
	}

}
