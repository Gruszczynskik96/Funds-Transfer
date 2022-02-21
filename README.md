# Funds-Transfer
Funds Transfer based on Spring Boot with currency exchange.

# Spring Boot Project
This project is using the Spring Boot as a Framework.  
By default, it is running with Tomcat (the usual way).  
Make sure to import all Maven dependencies.  

# H2 Database
Project is using H2 Database in order to store necessary values.  
Database is temporary, and it's values are not permanent (DB is reset after we shut down application).  
On run, application will load data from `import.sql` file, located in `resources` folder.  
Loaded data are accounts with following settings:  
<i><li>Balance: 150.0, Currency: USD, User ID: 10001</li></i>
<i><li>Balance: 100.0, Currency: PLN, User ID: 10002</li></i>
<i><li>Balance: 50.0, Currency: EUR, User ID: 10003</li></i>  

Necessary IDs are automatically generated and ignored when object is returned through ResponseEntity.

# Used API for Exchange Rates
Project uses the free currency exchange rate from `exchangerate-api.com/docs/free` page.
In order to use it properly, you need to access `https://open.er-api.com/v6/latest/` 
and then provide currency ID (for example, `USD` for dollars).
Then, API should retrieve necessary variables.

<b>BEWARE!</b>  
When using this API, incorrect currency returns 200 status, and body's `result` parameter has `error` value.
Therefore, if order to catch it, you need to check if after body is returned, the `result` parameter has `success` value set.

# REST Endpoints
As this project's role was mainly to allow transfer of funds between two accounts with currency exchange 
between them, we have the following endpoints:
<i><li>`GET` Endpoint used to return all user accounts</li></i>
<i><li>`GET` Endpoint used to return a user account</li></i>
<i><li>`POST` Endpoint to create user account</li></i>
<i><li>`PUT` Endpoint to transfer funds between two accounts</li></i>  
All endpoints are located in rest packages, within their respective classes.  
In the future, it might be wise to allow changing user currency, and edit his pieces of information,
as well as allow deleting user accounts.  
Security layer would be necessary as well.