package com.example.fetchrewards.service;

import com.example.fetchrewards.models.Payer;
import com.example.fetchrewards.models.Points;
import com.example.fetchrewards.models.Transaction;
import com.example.fetchrewards.respository.PayerRepository;
import com.example.fetchrewards.respository.TransactionRepository;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FetchRewardsImpl implements FetchRewardsService {

  @Autowired
  private TransactionRepository transactionRepository;
  @Autowired
  private PayerRepository payerRepository;

  /**
   * @param points
   * @return the amount of points spent for each Fetch Reward participating company
   */
  @Override
  public List<Payer> spendPoints(Points points) {

    List<Transaction> sortedAwardedPointsByDate = transactionRepository.getMostRecentPoints();
    int pointsToSubtract = points.getPoints();
    SpentPointsHelper spentPointsHelper = new SpentPointsHelper();

    for (Transaction transaction : sortedAwardedPointsByDate) {

      if (pointsToSubtract == 0) {
        break;
      } else if (transaction.getPoints() < pointsToSubtract) {
        pointsToSubtract -= transaction.getPoints();
        spentPointsHelper.subtractPoints(transaction.getPayer(), transaction.getPoints());
        transactionRepository.removeTransaction(transaction.getId());
      } else {
        int updatePoints = transaction.getPoints() - pointsToSubtract;
        spentPointsHelper.subtractPoints(transaction.getPayer(), pointsToSubtract);
        transactionRepository.updateTransactionsPoints(updatePoints, transaction.getId());
        pointsToSubtract = 0;
      }
    }

    subtractPointsFromPayerBalances(spentPointsHelper);

    return spentPointsHelper.getSubtractedPointsForPayers();
  }

  /**
   * @param sp contains the spent points for each Fetch Reward participating company when a consumer
   *           spends points.
   */
  private void subtractPointsFromPayerBalances(SpentPointsHelper sp) {
    for (Map.Entry<String, Integer> entry : sp.getSubtractedPoints().entrySet()) {
      payerRepository.updatePayersBalance(entry.getKey(), entry.getValue());
    }
  }

  /**
   * This method will add awarded points to a given Fetch Rewards participating companies balance as
   * well as saving it in the overall transaction repo.
   *
   * @param awardedPoints
   * @return the awarded points that were sent.
   */
  @Override
  public Transaction addPoints(Transaction awardedPoints) {

    if (!payerRepository.findPayer(awardedPoints.getPayer()).isEmpty()) {
      payerRepository.updatePayersBalance(awardedPoints.getPayer(), awardedPoints.getPoints());
    } else {
      payerRepository.save(new Payer(awardedPoints.getPayer(), awardedPoints.getPoints()));
    }

    return transactionRepository.save(awardedPoints);
  }

  @Override
  public List<Transaction> getAllTransactions() {
    return transactionRepository.findAll();
  }

  @Override
  public List<Payer> getAllPayerBalances() {
    return payerRepository.findAll();
  }
}
