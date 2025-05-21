# JSON Schema and Data Structure

## Schema Design
The JSON schema (`schema.json`) defines the structure and validation rules for test data:

```json
{
  "$schema": "http://json-schema.org/draft-07/schema#",
  "type": "object",
  "required": ["testData"],
  "properties": {
    "testData": {
      "type": "object",
      "patternProperties": {
        "^TC_ID_\\d+$": {
          "type": "object",
          "required": [...],
          "properties": {
            "PolicyType": {
              "type": "string",
              "enum": ["Home", "Sandbox"]
            },
            // ... other properties
          }
        }
      }
    }
  }
}
```

## Test Data Structure
Test data files (e.g., `HomeData.json`) follow this structure:

```json
{
  "testData": {
    "TC_ID_0001": {
      "PolicyType": "Home",
      "CustomerType": "Individual",
      "FirstName": "John",
      "LastName": "Doe",
      "DOB": "01/15/1980",
      "PhoneNum": "921-555-1234",
      "Email": "john.doe@example.com",
      "Address": "123 Main St",
      "ZIP": "02766",
      "Producer": "Agent Name",
      "EffectiveDate": "01/30/2024",
      "Program": "Homeowner",
      "PolicyCoverageOption": "Gold",
      "ResidenceType": "Homeowner",
      "ReplacementCost": "800,000",
      "Contents": "480,000",
      "LossOfUse": "160,000",
      "AllPerilsDeductible": "1,000",
      "WindsHailDeductible": "10%",
      "Liability": "100,000",
      "MedicalPayments": "1,000",
      "YearBuilt": "2015",
      "RoofType": "Other",
      "ConstructionType": "Frame"
    }
  }
}
```

## Validation Rules
1. **Field Types**
   - String fields with specific formats (email, phone)
   - Currency fields with pattern ^[\d,]+$
   - Date fields in MM/DD/YYYY format
   - Year fields in YYYY format

2. **Required Fields**
   - Policy Type, Customer Type, Name fields
   - Address information
   - PolicyCoverageOption (Gold/Silver/Bronze)
   - ResidenceType
   - Insurance amounts (ReplacementCost, Contents, LossOfUse)
   - Deductibles (AllPerils, WindsHail)
   - Coverage limits (Liability, MedicalPayments)
   - Property details (YearBuilt, ConstructionType)

3. **Enumerations**
   - Policy Type: Home/Sandbox
   - Customer Type: Individual/Business
   - PolicyCoverageOption: Gold/Silver/Bronze

4. **Format Validations**
   - ZIP Code: 12345 or 12345-6789
   - Phone: 999-999-9999
   - Email: valid email format
   - Currency: Numeric with commas (e.g., 100,000)
   - Year: 4-digit format (e.g., 2024)
   - WindsHailDeductible: Percentage or fixed amount

## Best Practices
1. Use consistent test case ID format (TC_ID_XXXX)
2. Include all required fields
3. Follow field format requirements
4. Use appropriate enumerations
5. Keep data realistic and meaningful
