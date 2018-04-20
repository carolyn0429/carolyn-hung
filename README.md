# n26 

Author: Carolyn Hung
Date: April 18 2018

Folder structure:

src
    main
        Application: start the service
        Transaction: class definition
        TransactionController: transactional APIs controller
        TransactionService: transactional APIs business logic

    test
        java
            n26
                TransactionControllerTest: unit test for controller

n26_integration_test
    features
        helpers
            TestClient: define integration test client
        step_definition:
            api
                transaction.rb: define the step implementation of test description in cucumber
         transaction.feature: feature file for test cases(run this file on ruby client when Application starts)
