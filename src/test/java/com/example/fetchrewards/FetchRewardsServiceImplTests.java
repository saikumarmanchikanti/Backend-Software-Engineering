package com.example.fetchrewards;

import com.example.fetchrewards.models.Payer;
import com.example.fetchrewards.models.Points;
import com.example.fetchrewards.models.Transaction;
import com.example.fetchrewards.respository.PayerRepository;
import com.example.fetchrewards.respository.TransactionRepository;
import com.example.fetchrewards.service.FetchRewardsImpl;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class FetchRewardsServiceImplTests {

  @Autowired
  private FetchRewardsImpl fetchRewardsService;
  @Autowired
  private TransactionRepository transactionRepository;
  @Autowired
  private PayerRepository payerRepository;

  @Test
  public void addPoints() {
    Transaction inTransaction = new Transaction(null, "DANNON", 200, new Date());
    Transaction outTransaction = fetchRewardsService
        .addPoints(inTransaction);

    Assertions.assertEquals(inTransaction, outTransaction);
  }

  @Test
  public void getAllPayerBalances() {

    fetchRewardsService.addPoints(new Transaction(null, "DANNON", 200, new Date()));
    List<Payer> allPayerBalances = fetchRewardsService.getAllPayerBalances();
    Assertions.assertEquals(allPayerBalances.get(0), new Payer(1L, "DANNON", 200));
  }

  @Test
  public void getAllTransactions() {

    Transaction inTransaction = new Transaction(null, "DANNON", 200, new Date());
    fetchRewardsService.addPoints(inTransaction);
    List<Transaction> allTransactions = fetchRewardsService.getAllTransactions();
    Assertions.assertEquals(allTransactions.get(0).getPayer(), "DANNON");
    Assertions.assertEquals(allTransactions.get(0).getPoints(), 200);
  }

  @Test
  public void spendPoints() {

    Transaction inTransaction = new Transaction(null, "DANNON", 200, new Date());
    fetchRewardsService.addPoints(inTransaction);
    Payer pointsSpentForPayer = fetchRewardsService.spendPoints(new Points(100)).get(0);
    Assertions.assertEquals(pointsSpentForPayer, new Payer(null, "DANNON", -100));
    Payer balancesByPayer = fetchRewardsService.getAllPayerBalances().get(0);
    Assertions.assertEquals(balancesByPayer, new Payer(1L, "DANNON", 100));
  }
}
