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

    public String getBillingMethod() {
        return billingMethod;
    }

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

    public void setBillingMethod(String billingMethod) {
        this.billingMethod = billingMethod;
    }

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
                '}';
    }
} 