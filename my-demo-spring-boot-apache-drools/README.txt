 
 POST: http://localhost:8090/personal-data/
 {"incomeType":"BUSINESS","age":26,"incomeAmount":1000000,"educationCess":3}
 
 POST: http://localhost:8090/order-data/
 {"orderName":"Mobile","cardType":"SBI","orderPrice":14999.0,"discountInPercentage":null}
 
 GET: http://localhost:8090/order-data/1
 
 ====================================================================================================================================== 
 String in file must be =>> "" eg. "SUCCESS_INDIVIDUAL_A05"
 DOUBLE value in file must be => 20d (same as in java code)
 IMPORT entity with package as my.demo.spring.boot.apache.drools.model.PersonalDetails;
 NAME column must be UNIQUE
 CONDITION (same as WHEN) column name must be same as field name =>> declare variable with entity type as personalData:PersonalDetails =>> incomeAmount >= $1 && incomeAmount <= $2  (possible to add space, $1 means first param in column separeted by , as 1, 60) =>> incomeType==$param
 ACTION (same as THEN) column name can be anything  =>>  personalData.setTaxSlabMessage($param); (here ; must be present)

 ====================================================================================================================================== 
 for same drl file look like
 
package rules; 
import entity with package as my.demo.spring.boot.apache.drools.model.PersonalDetails;

WHEN
  personalData : PersonalDetails((incomeType == "INDIVIDUAL") && (incomeAmount >= 250001 && incomeAmount <= 500000)	&& (age >= 1 && age < 60))
THEN
	personalData.setTaxPercentage(5);	
	personalData.setCalculatedTaxSlab(0);	
	personalData.setCalculatedTaxableIncome(250000);	
	personalData.setTaxSlabMessage("SUCCESS_INDIVIDUAL_A05");
END	 
 
 ======================================================================================================================================