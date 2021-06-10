package com.example.fetchrewards.respository;

import com.example.fetchrewards.models.Payer;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PayerRepository extends JpaRepository<Payer, Long> {

  @Query("SELECT u FROM Payer u WHERE u.payer = :payer")
  List<Payer> findPayer(@Param("payer") String payer);

  @Transactional
  @Modifying
  @Query("UPDATE Payer u SET u.points = u.points + :points WHERE u.payer = :payer")
  void updatePayersBalance(@Param("payer") String payer, @Param("points") int points);
}
