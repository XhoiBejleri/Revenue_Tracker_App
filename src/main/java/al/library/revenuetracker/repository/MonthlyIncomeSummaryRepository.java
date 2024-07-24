package al.library.revenuetracker.repository;

import al.library.revenuetracker.model.MonthlyIncomeSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface MonthlyIncomeSummaryRepository extends JpaRepository<MonthlyIncomeSummary, Long> {
    @Query("SELECT m.totalIncome FROM MonthlyIncomeSummary m WHERE m.year = :year AND m.month = :month")
    BigDecimal findTotalIncomeByYearAndMonth(@Param("year") int year, @Param("month") int month);
}
