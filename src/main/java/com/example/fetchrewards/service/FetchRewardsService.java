package com.example.fetchrewards.service;

import com.example.fetchrewards.models.Payer;
import com.example.fetchrewards.models.Points;
import com.example.fetchrewards.models.Transaction;
import java.util.List;

public interface FetchRewardsService {

  List<Payer> spendPoints(Points points);

  Transaction addPoints(Transaction newTransaction);

  List<Transaction> getAllTransactions();

  List<Payer> getAllPayerBalances();
}
