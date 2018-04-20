# N26
# Author: Carolyn Hung
# created at : April 15, 2018
require 'rspec'
require 'cucumber'

When (/^I am a non-authenticated user$/) do
  @client = TestClient.new
  puts "[INFO]: Initialize an test client..."
end

And (/^I send PUT request to endpoint to create a new transaction with transaction id (.*), amount (.*), type (.*), parent_id (.*)$/) do |txn_id, amount, type, parent_id|
  @client = TestClient.new
  @response = @client.create_a_transaction_with_parent_id(txn_id, amount, type, parent_id)
  puts "[INFO]: Create a new transaction..."

end

And (/^I send PUT request to endpoint to create a new transaction with transaction id (.*), amount (.*) and type (.*)$/) do |txn_id, amount, type|
  @client = TestClient.new
  @response = @client.create_a_transaction_without_parent_id(txn_id, amount, type)
  puts "[INFO]: Create a new transaction..."

end

And (/^I send GET request to endpoint to get a transaction by id (.*)$/) do |id|
  @client = TestClient.new
  @response = @client.get_a_transaction_by_id(id)
  puts "[INFO]: Retrieve a transaction by id "+ id + "..."

end

And (/^I should get a transaction with id (.*)$/) do |id|
  puts @response.inspect
  @response.should_not be nil
  @response["amount"].should_not be nil
  @response["type"].should_not be nil
end

And (/^I send GET request to endpoint to get transaction by type (.*)$/) do |type|
  @client = TestClient.new
  @response = @client.get_transaction_by_type(type)
  puts "[INFO]: Retrieve transaction(s) by type "+ type + "..."
end

And (/^I should get transaction id with type (.*)$/) do |type|
  puts @response.inspect
  @response.should_not be nil
end

And (/^I send GET request to endpoint to get sum by id (.*)$/) do |id|
  @client = TestClient.new
  @response = @client.get_sum_by_id(id)
  puts "[INFO]: Retrieve sum by transaction id "+ id + "..."
end

And (/^I should get sum with id (.*)$/) do |id|
  puts @response.inspect
  @response.should_not be nil
end

And (/^sum with id (.*) should be (.*)$/) do |id, sum|
  @response["sum"].equal? sum
end

Then (/^I should get OK response$/) do
  @response = JSON.parse(@response.body)
  @response["status"] == "ok"
end

And (/^I send GET request to endpoint to reset a database to initial state$/) do
  @response = @client.reset
  print @response
end