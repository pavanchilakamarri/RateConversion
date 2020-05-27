@All
Feature: Conversion Rate for the Past dates

  @pastDate
  Scenario Outline: Validating response status code for Past Foreign Exchange rates
    Given I set endpoint for GET past Rate conversion
    When I send GET HTTP Request with <Date>
    Then I get OK response with status <Status_Code>

    Examples: 
      | Date       | Status_Code |
      | 2010-01-12 |         200 |

  @pastDate
  Scenario Outline: Validating response status code for Latest Foreign Exchange rates
    Given I set endpoint for GET past Rate conversion
    When I send GET HTTP Request with <Date>
    Then I get OK response with status <Status_Code>
    And validating response of <Country> <Currency>

    Examples: 
      | Date       | Status_Code | Country | Currency |
      | 2010-01-12 |         200 | GBP     |   0.8972 |
      
      @pastDate
  Scenario Outline: Validating response status code for Latest Foreign Exchange rates
    Given I set endpoint for GET past Rate conversion
    When I send GET HTTP Request with <Date>
    Then I get OK response with status <Status_Code>
    And validating response of <Country> <Currency>

    Examples: 
      | Date       | Status_Code | Country | Currency |
      | 2030-01-12 |         200 | GBP     |   0.8972 |
      
