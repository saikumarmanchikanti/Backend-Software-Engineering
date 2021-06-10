package com.example.fetchrewards;

import com.example.fetchrewards.models.Payer;
import com.example.fetchrewards.models.Points;
import com.example.fetchrewards.models.Transaction;
import com.example.fetchrewards.service.FetchRewardsImpl;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FetchRewardsController {

  @Autowired
  private FetchRewardsImpl fetchRewardsService;

  @GetMapping("/payer/balances")
  List<Payer> allPayerBalances() {
    return fetchRewardsService.getAllPayerBalances();
  }

  @PostMapping(path = "/payer/transaction", consumes = "application/json", produces = "application/json")
  Transaction addPoints(@RequestBody Transaction newTransaction) {
    return fetchRewardsService.addPoints(newTransaction);
  }

  @PostMapping(path = "/consumer/points", consumes = "application/json", produces = "application/json")
  List<Payer> spendPoints(@RequestBody Points points) {
    return fetchRewardsService.spendPoints(points);
  }
}
