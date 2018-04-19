require 'rubygems'
require 'selenium-webdriver'
require 'cucumber'
require 'rest-client'

class TestClient
  @@URL = "http://localhost:8080/transactionservice/"


  def create_a_transaction_with_parent_id(txn_id, amount, type, parent_id)
    response = RestClient::Request.execute(
        :method => :put,
        :url => @@URL+"transaction/#{txn_id}",
        :headers => {:accept => :json, :content_type=> :json},
        payload: {'transaction_id': txn_id, 'amount': amount, 'type': type, 'parent_id': parent_id}.to_json
    )
    return response
  end

  def create_a_transaction_without_parent_id(txn_id, amount, type)
    response = RestClient::Request.execute(
        :method => :put,
        :url => @@URL+"transaction/#{txn_id}",
        :headers => {:accept => :json, :content_type=> :json},
        payload: {'transaction_id': txn_id, 'amount': amount, 'type': type}.to_json
    )
    return response
  end

  def get_a_transaction_by_id(txn_id)
    response = RestClient.get(@@URL+"transaction/#{txn_id}")
    return response
  end

  def get_transaction_by_type(type)
    response = RestClient.get(@@URL+"types/#{type}")
    return response
  end

  def get_sum_by_id(id)
    response = RestClient.get(@@URL+"sum/#{id}")
    return response
  end

end