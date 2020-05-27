@All
Feature: Login page

  @login
  Scenario Outline: Validating response status code for Latest Foreign Exchange rates
    Given I set endpoint for GET Latest Rate conversion
    When I send GET HTTP Request
    Then I get a OK response with status <Status_Code>

    Examples: 
      | Status_Code |
      |         200 |

  @login
  Scenario Outline: Validating response for Latest Foreign Exchange rates
    Given I set endpoint for GET Latest Rate conversion
    When I send GET HTTP Request
    Then I get a OK response with status <Status_Code>
    And validating response for <Country> <Currency>

    Examples: 
      | Status_Code | Country | Currency |
      |         200 | GBP     |  0.88878 |

  @login
  Scenario: Validating currency for multiple countries through extenal data source (Excel)
    Given I set endpoint for GET Latest Rate conversion
    When I send GET HTTP Request
    Then validate response for multiple countries currency
    
    
  