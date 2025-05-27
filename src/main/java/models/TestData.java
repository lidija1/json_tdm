package models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TestData {
    @JsonProperty("CustomerType")
    private String customerType;
    
    @JsonProperty("FirstName")
    private String firstName;
    
    @JsonProperty("LastName")
    private String lastName;
    
    @JsonProperty("DOB")
    private String dob;
    
    @JsonProperty("PhoneNum")
    private String phoneNum;
    
    @JsonProperty("Email")
    private String email;
    
    @JsonProperty("Address")
    private String address;
    
    @JsonProperty("AddressLine")
    private String addressLine;
    
    @JsonProperty("ZIP")
    private String zip;
    
    @JsonProperty("Country")
    private String country;
    
    @JsonProperty("Producer")
    private String producer;
    
    @JsonProperty("Program")
    private String program;

    @JsonProperty("State")
    private String state;

    @JsonProperty("City")
    private String city;

    @JsonProperty("BillingMethod")
    private String billingMethod;

    @JsonProperty("SubmissionType")
    private String submissionType;

    @JsonProperty("ResidenceType")
    private String residenceType;  

    @JsonProperty("ProgramType")
    private String programType;

    @JsonProperty("Policy Coverage Option")
    private String policyCoverageOption;

    @JsonProperty("WindsHailDeductible")
    private String windsHailDeductible;

    @JsonProperty("YearBuilt")
    private String yearBuilt;

    @JsonProperty("Is Child or Day Care run out of the home?")
    private String childDayCare;

    @JsonProperty("Any underground oil or storage tanks?")
    private String undergroundOilTanks;

    @JsonProperty("Is the residence rented more than 10 weeks per year?")
    private String residenceRented;

    @JsonProperty("Is the residence vacant?")
    private String residenceVacant;

    @JsonProperty("Are there any animals or exotic pets kept on the premises?")
    private String exoticPets;

    // Getters
    public String getCustomerType() { return customerType; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getDOB() { return dob; }
    public String getPhoneNum() { return phoneNum; }
    public String getEmail() { return email; }
    public String getAddress() { return address; }
    public String getAddressLine() { return addressLine; }
    public String getZIP() { return zip; }
    public String getCountry() { return country; }
    public String getProducer() { return producer; }
    public String getProgram() { return program; }
    public String getState() { return state; }
    public String getCity() { return city; }
    public String getBillingMethod() { return billingMethod; }
    public String getSubmissionType() { return submissionType; }
    public String getResidenceType() { return residenceType; }
    public String getProgramType() { return programType; }
    public String getPolicyCoverageOption() { return policyCoverageOption; }
    public String getWindsHailDeductible() { return windsHailDeductible; }
    public String getYearBuilt() { return yearBuilt; }
    public String getChildDayCare() { return childDayCare; }
    public String getUndergroundOilTanks() { return undergroundOilTanks; }
    public String getResidenceRented() { return residenceRented; }
    public String getResidenceVacant() { return residenceVacant; }
    public String getExoticPets() { return exoticPets; }

    // Setters
    public void setCustomerType(String customerType) { this.customerType = customerType; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setDOB(String dob) { this.dob = dob; }
    public void setPhoneNum(String phoneNum) { this.phoneNum = phoneNum; }
    public void setEmail(String email) { this.email = email; }
    public void setAddress(String address) { this.address = address; }
    public void setAddressLine(String addressLine) { this.addressLine = addressLine; }
    public void setZIP(String zip) { this.zip = zip; }
    public void setCountry(String country) { this.country = country; }
    public void setProducer(String producer) { this.producer = producer; }
    public void setProgram(String program) { this.program = program; }
    public void setState(String state) { this.state = state; }
    public void setCity(String city) { this.city = city; }
    public void setBillingMethod(String billingMethod) { this.billingMethod = billingMethod; }
    public void setSubmissionType(String submissionType) { this.submissionType = submissionType; }
    public void setResidenceType(String residenceType) { this.residenceType = residenceType; }
    public void setProgramType(String programType) { this.programType = programType; }
    public void setPolicyCoverageOption(String policyCoverageOption) { this.policyCoverageOption = policyCoverageOption; }
    public void setWindsHailDeductible(String windsHailDeductible) { this.windsHailDeductible = windsHailDeductible; }
    public void setYearBuilt(String yearBuilt) { this.yearBuilt = yearBuilt; }
    public void setChildDayCare(String childDayCare) { this.childDayCare = childDayCare; }
    public void setUndergroundOilTanks(String undergroundOilTanks) { this.undergroundOilTanks = undergroundOilTanks; }
    public void setResidenceRented(String residenceRented) { this.residenceRented = residenceRented; }
    public void setResidenceVacant(String residenceVacant) { this.residenceVacant = residenceVacant; }
    public void setExoticPets(String exoticPets) { this.exoticPets = exoticPets; }

    @Override
    public String toString() {
        return "TestData{" +
                "customerType='" + customerType + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dob='" + dob + '\'' +
                ", phoneNum='" + phoneNum + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", addressLine='" + addressLine + '\'' +
                ", zip='" + zip + '\'' +
                ", country='" + country + '\'' +
                ", producer='" + producer + '\'' +
                ", program='" + program + '\'' +
                ", state='" + state + '\'' +
                ", city='" + city + '\'' +
                ", billingMethod='" + billingMethod + '\'' +
                ", submissionType='" + submissionType + '\'' +
                ", residenceType='" + residenceType + '\'' +
                ", programType='" + programType + '\'' +
                ", policyCoverageOption='" + policyCoverageOption + '\'' +
                ", windsHailDeductible='" + windsHailDeductible + '\'' +
                ", yearBuilt='" + yearBuilt + '\'' +
                ", childDayCare='" + childDayCare + '\'' +
                ", undergroundOilTanks='" + undergroundOilTanks + '\'' +
                ", residenceRented='" + residenceRented + '\'' +
                ", residenceVacant='" + residenceVacant + '\'' +
                ", exoticPets='" + exoticPets + '\'' +
                '}';
    }
}