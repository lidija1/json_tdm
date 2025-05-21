package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TestCase {
    @JsonProperty("PolicyType")
    @NotNull(message = "Policy type cannot be null")
    @Pattern(regexp = "Home|Sandbox", message = "Policy type must be either 'Home' or 'Sandbox'")
    private String policyType;

    @JsonProperty("CustomerType")
    @NotNull(message = "Customer type cannot be null")
    @Pattern(regexp = "Individual|Business", message = "Customer type must be either 'Individual' or 'Business'")
    private String customerType;

    @JsonProperty("FirstName")
    @NotBlank(message = "First name cannot be blank")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    private String firstName;

    @JsonProperty("LastName")
    @NotBlank(message = "Last name cannot be blank")
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    private String lastName;

    @JsonProperty("DOB")
    @NotNull(message = "Date of birth cannot be null")
    @Pattern(regexp = "^\\d{2}/\\d{2}/\\d{4}$", message = "Date of birth must be in format MM/DD/YYYY")
    private String dob;

    @JsonProperty("PhoneNum")
    @Pattern(regexp = "^\\d{10}$", message = "Phone number must be exactly 10 digits")
    private String phoneNum;

    @JsonProperty("Email")
    @Email(message = "Email must be valid")
    private String email;

    @JsonProperty("Address")
    @NotBlank(message = "Address cannot be blank")
    private String address;

    @JsonProperty("ZIP")
    @Pattern(regexp = "^\\d{5}(-\\d{4})?$", message = "ZIP code must be in format 12345 or 12345-6789")
    private String zip;

    @JsonProperty("Producer")
    @NotBlank(message = "Producer cannot be blank")
    private String producer;

    @JsonProperty("Program")
    @NotBlank(message = "Program cannot be blank")
    private String program;

    @JsonProperty("CoverageAmount")
    @Pattern(regexp = "^\\d+$", message = "Coverage amount must be a number")
    private String coverageAmount;

    @JsonProperty("Deductible")
    @Pattern(regexp = "^\\d+$", message = "Deductible must be a number")
    private String deductible;

    @JsonProperty("lobName")
    @NotBlank(message = "LOB name cannot be blank")
    private String lobName;

    @JsonProperty("testType")
    @NotBlank(message = "Test type cannot be blank")
    private String testType;

    @JsonProperty("testCaseId")
    @NotBlank(message = "Test case ID cannot be blank")
    @Pattern(regexp = "^TC_ID_\\d+$", message = "Invalid test case ID format")
    private String testCaseId;

    @JsonProperty("Priority")
    private int priority = 1;

    @JsonProperty("DependsOn")
    private String[] dependsOn;

    @JsonProperty("EffectiveDate")
    @Pattern(regexp = "^\\d{2}/\\d{2}/\\d{4}$", message = "Effective date must be in format MM/DD/YYYY")
    private String effectiveDate;

    @JsonProperty("NewTransactionEffDate")
    @Pattern(regexp = "^\\d{2}/\\d{2}/\\d{4}$", message = "New transaction effective date must be in format MM/DD/YYYY")
    private String newTransactionEffDate;

    @JsonProperty("PolicyCoverageOption")
    @Pattern(regexp = "Gold|Silver|Bronze", message = "Policy coverage option must be Gold, Silver, or Bronze")
    private String policyCoverageOption;

    @JsonProperty("ResidenceType")
    @NotBlank(message = "Residence type cannot be blank")
    private String residenceType;

    @JsonProperty("ReplacementCost")
    @Pattern(regexp = "^[\\d,]+$", message = "Replacement cost must be a number")
    private String replacementCost;

    @JsonProperty("Contents")
    @Pattern(regexp = "^[\\d,]+$", message = "Contents value must be a number")
    private String contents;

    @JsonProperty("LossOfUse")
    @Pattern(regexp = "^[\\d,]+$", message = "Loss of use value must be a number")
    private String lossOfUse;

    @JsonProperty("YearBuilt")
    @Pattern(regexp = "^\\d{4}$", message = "Year built must be a 4-digit year")
    private String yearBuilt;

    @JsonProperty("RoofType")
    private String roofType;

    @JsonProperty("ConstructionType")
    private String constructionType;

    @JsonProperty("AllPerilsDeductible")
    @Pattern(regexp = "^[\\d,]+$", message = "All perils deductible must be a number")
    private String allPerilsDeductible;

    @JsonProperty("WindsHailDeductible")
    private String windsHailDeductible;

    @JsonProperty("Liability")
    @Pattern(regexp = "^[\\d,]+$", message = "Liability must be a number")
    private String liability;

    @JsonProperty("MedicalPayments")
    @Pattern(regexp = "^[\\d,]+$", message = "Medical payments must be a number")
    private String medicalPayments;

    // Default constructor for Jackson
    public TestCase() {}

    // Builder pattern
    public static class Builder {
        private TestCase testCase = new TestCase();

        public Builder policyType(String policyType) {
            testCase.setPolicyType(policyType);
            return this;
        }

        public Builder customerType(String customerType) {
            testCase.setCustomerType(customerType);
            return this;
        }

        public Builder firstName(String firstName) {
            testCase.setFirstName(firstName);
            return this;
        }

        public Builder lastName(String lastName) {
            testCase.setLastName(lastName);
            return this;
        }

        public Builder dob(String dob) {
            testCase.setDOB(dob);
            return this;
        }

        public Builder phoneNum(String phoneNum) {
            testCase.setPhoneNum(phoneNum);
            return this;
        }

        public Builder email(String email) {
            testCase.setEmail(email);
            return this;
        }

        public Builder address(String address) {
            testCase.setAddress(address);
            return this;
        }

        public Builder zip(String zip) {
            testCase.setZIP(zip);
            return this;
        }

        public Builder producer(String producer) {
            testCase.setProducer(producer);
            return this;
        }

        public Builder program(String program) {
            testCase.setProgram(program);
            return this;
        }

        public Builder coverageAmount(String coverageAmount) {
            testCase.setCoverageAmount(coverageAmount);
            return this;
        }

        public Builder deductible(String deductible) {
            testCase.setDeductible(deductible);
            return this;
        }

        public Builder lobName(String lobName) {
            testCase.setLobName(lobName);
            return this;
        }

        public Builder testType(String testType) {
            testCase.setTestType(testType);
            return this;
        }

        public Builder testCaseId(String testCaseId) {
            testCase.setTestCaseId(testCaseId);
            return this;
        }

        public Builder priority(int priority) {
            testCase.setPriority(priority);
            return this;
        }

        public Builder dependsOn(String[] dependsOn) {
            testCase.setDependsOn(dependsOn);
            return this;
        }

        public Builder effectiveDate(String effectiveDate) {
            testCase.setEffectiveDate(effectiveDate);
            return this;
        }

        public Builder newTransactionEffDate(String newTransactionEffDate) {
            testCase.setNewTransactionEffDate(newTransactionEffDate);
            return this;
        }

        public Builder policyCoverageOption(String policyCoverageOption) {
            testCase.setPolicyCoverageOption(policyCoverageOption);
            return this;
        }

        public Builder residenceType(String residenceType) {
            testCase.setResidenceType(residenceType);
            return this;
        }

        public Builder replacementCost(String replacementCost) {
            testCase.setReplacementCost(replacementCost);
            return this;
        }

        public Builder contents(String contents) {
            testCase.setContents(contents);
            return this;
        }

        public Builder lossOfUse(String lossOfUse) {
            testCase.setLossOfUse(lossOfUse);
            return this;
        }

        public Builder yearBuilt(String yearBuilt) {
            testCase.setYearBuilt(yearBuilt);
            return this;
        }

        public Builder roofType(String roofType) {
            testCase.setRoofType(roofType);
            return this;
        }

        public Builder constructionType(String constructionType) {
            testCase.setConstructionType(constructionType);
            return this;
        }

        public Builder allPerilsDeductible(String allPerilsDeductible) {
            testCase.setAllPerilsDeductible(allPerilsDeductible);
            return this;
        }

        public Builder windsHailDeductible(String windsHailDeductible) {
            testCase.setWindsHailDeductible(windsHailDeductible);
            return this;
        }

        public Builder liability(String liability) {
            testCase.setLiability(liability);
            return this;
        }

        public Builder medicalPayments(String medicalPayments) {
            testCase.setMedicalPayments(medicalPayments);
            return this;
        }

        public TestCase build() {
            return testCase;
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    // Helper methods
    public LocalDate getDOBAsDate() {
        return LocalDate.parse(dob, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
    }

    public int getCoverageAmountAsInt() {
        return Integer.parseInt(coverageAmount);
    }

    public int getDeductibleAsInt() {
        return Integer.parseInt(deductible);
    }

    // Standard getters and setters
    public String getPolicyType() { return policyType; }
    public void setPolicyType(String policyType) { this.policyType = policyType; }

    // New getters and setters
    public String getCustomerType() { return customerType; }
    public void setCustomerType(String customerType) { this.customerType = customerType; }
    
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    
    public String getDOB() { return dob; }
    public void setDOB(String dob) { this.dob = dob; }
    
    public String getPhoneNum() { return phoneNum; }
    public void setPhoneNum(String phoneNum) { this.phoneNum = phoneNum; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    
    public String getZIP() { return zip; }
    public void setZIP(String zip) { this.zip = zip; }
    
    public String getProducer() { return producer; }
    public void setProducer(String producer) { this.producer = producer; }
    
    public String getProgram() { return program; }
    public void setProgram(String program) { this.program = program; }
    
    public String getCoverageAmount() { return coverageAmount; }
    public void setCoverageAmount(String coverageAmount) { this.coverageAmount = coverageAmount; }
    
    public String getDeductible() { return deductible; }
    public void setDeductible(String deductible) { this.deductible = deductible; }

    public String getLobName() { return lobName; }
    public void setLobName(String lobName) { this.lobName = lobName; }

    public String getTestType() { return testType; }
    public void setTestType(String testType) { this.testType = testType; }

    public String getTestCaseId() { return testCaseId; }
    public void setTestCaseId(String testCaseId) { this.testCaseId = testCaseId; }

    public int getPriority() { return priority; }
    public void setPriority(int priority) { this.priority = priority; }

    public String[] getDependsOn() { return dependsOn != null ? dependsOn.clone() : null; }
    public void setDependsOn(String[] dependsOn) { 
        this.dependsOn = dependsOn != null ? dependsOn.clone() : null; 
    }

    public String getEffectiveDate() { return effectiveDate; }
    public void setEffectiveDate(String effectiveDate) { this.effectiveDate = effectiveDate; }

    public String getNewTransactionEffDate() { return newTransactionEffDate; }
    public void setNewTransactionEffDate(String newTransactionEffDate) { this.newTransactionEffDate = newTransactionEffDate; }

    public String getPolicyCoverageOption() { return policyCoverageOption; }
    public void setPolicyCoverageOption(String policyCoverageOption) { this.policyCoverageOption = policyCoverageOption; }

    public String getResidenceType() { return residenceType; }
    public void setResidenceType(String residenceType) { this.residenceType = residenceType; }

    public String getReplacementCost() { return replacementCost; }
    public void setReplacementCost(String replacementCost) { this.replacementCost = replacementCost; }

    public String getContents() { return contents; }
    public void setContents(String contents) { this.contents = contents; }

    public String getLossOfUse() { return lossOfUse; }
    public void setLossOfUse(String lossOfUse) { this.lossOfUse = lossOfUse; }

    public String getYearBuilt() { return yearBuilt; }
    public void setYearBuilt(String yearBuilt) { this.yearBuilt = yearBuilt; }

    public String getRoofType() { return roofType; }
    public void setRoofType(String roofType) { this.roofType = roofType; }

    public String getConstructionType() { return constructionType; }
    public void setConstructionType(String constructionType) { this.constructionType = constructionType; }

    public String getAllPerilsDeductible() { return allPerilsDeductible; }
    public void setAllPerilsDeductible(String allPerilsDeductible) { this.allPerilsDeductible = allPerilsDeductible; }

    public String getWindsHailDeductible() { return windsHailDeductible; }
    public void setWindsHailDeductible(String windsHailDeductible) { this.windsHailDeductible = windsHailDeductible; }

    public String getLiability() { return liability; }
    public void setLiability(String liability) { this.liability = liability; }

    public String getMedicalPayments() { return medicalPayments; }
    public void setMedicalPayments(String medicalPayments) { this.medicalPayments = medicalPayments; }

    @Override
    public String toString() {
        return String.format("TestCase{policyType='%s', customerType='%s', firstName='%s', lastName='%s', " +
                "dob='%s', phoneNum='%s', email='%s', address='%s', zip='%s', producer='%s', program='%s', " +
                "coverageAmount='%s', deductible='%s', lobName='%s', testType='%s', testCaseId='%s', " +
                "priority='%d', dependsOn='%s', effectiveDate='%s', newTransactionEffDate='%s', " +
                "policyCoverageOption='%s', residenceType='%s', replacementCost='%s', contents='%s', " +
                "lossOfUse='%s', yearBuilt='%s', roofType='%s', constructionType='%s', " +
                "allPerilsDeductible='%s', windsHailDeductible='%s', liability='%s', medicalPayments='%s'}", 
                policyType, customerType, firstName, lastName, dob, phoneNum, email, address, zip, 
                producer, program, coverageAmount, deductible, lobName, testType, testCaseId, priority, 
                dependsOn != null ? String.join(", ", dependsOn) : "null", effectiveDate, newTransactionEffDate, 
                policyCoverageOption, residenceType, replacementCost, contents, lossOfUse, yearBuilt, roofType, 
                constructionType, allPerilsDeductible, windsHailDeductible, liability, medicalPayments);
    }

    // equals and hashCode for proper object comparison
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TestCase testCase = (TestCase) o;
        return java.util.Objects.equals(policyType, testCase.policyType) &&
                java.util.Objects.equals(customerType, testCase.customerType) &&
                java.util.Objects.equals(firstName, testCase.firstName) &&
                java.util.Objects.equals(lastName, testCase.lastName) &&
                java.util.Objects.equals(dob, testCase.dob) &&
                java.util.Objects.equals(phoneNum, testCase.phoneNum) &&
                java.util.Objects.equals(email, testCase.email) &&
                java.util.Objects.equals(address, testCase.address) &&
                java.util.Objects.equals(zip, testCase.zip) &&
                java.util.Objects.equals(producer, testCase.producer) &&
                java.util.Objects.equals(program, testCase.program) &&
                java.util.Objects.equals(coverageAmount, testCase.coverageAmount) &&
                java.util.Objects.equals(deductible, testCase.deductible) &&
                java.util.Objects.equals(lobName, testCase.lobName) &&
                java.util.Objects.equals(testType, testCase.testType) &&
                java.util.Objects.equals(testCaseId, testCase.testCaseId) &&
                java.util.Objects.equals(priority, testCase.priority) &&
                java.util.Arrays.equals(dependsOn, testCase.dependsOn) &&
                java.util.Objects.equals(effectiveDate, testCase.effectiveDate) &&
                java.util.Objects.equals(newTransactionEffDate, testCase.newTransactionEffDate) &&
                java.util.Objects.equals(policyCoverageOption, testCase.policyCoverageOption) &&
                java.util.Objects.equals(residenceType, testCase.residenceType) &&
                java.util.Objects.equals(replacementCost, testCase.replacementCost) &&
                java.util.Objects.equals(contents, testCase.contents) &&
                java.util.Objects.equals(lossOfUse, testCase.lossOfUse) &&
                java.util.Objects.equals(yearBuilt, testCase.yearBuilt) &&
                java.util.Objects.equals(roofType, testCase.roofType) &&
                java.util.Objects.equals(constructionType, testCase.constructionType) &&
                java.util.Objects.equals(allPerilsDeductible, testCase.allPerilsDeductible) &&
                java.util.Objects.equals(windsHailDeductible, testCase.windsHailDeductible) &&
                java.util.Objects.equals(liability, testCase.liability) &&
                java.util.Objects.equals(medicalPayments, testCase.medicalPayments);
    }

    @Override
    public int hashCode() {
        int result = java.util.Objects.hash(policyType, customerType, firstName, lastName, dob, phoneNum, 
                email, address, zip, producer, program, coverageAmount, deductible, lobName, testType, 
                testCaseId, priority, effectiveDate, newTransactionEffDate, policyCoverageOption, 
                residenceType, replacementCost, contents, lossOfUse, yearBuilt, roofType, 
                constructionType, allPerilsDeductible, windsHailDeductible, liability, medicalPayments);
        result = 31 * result + java.util.Arrays.hashCode(dependsOn);
        return result;
    }
}
