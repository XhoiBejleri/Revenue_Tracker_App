package al.library.revenuetracker.repository;

import al.library.revenuetracker.model.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface IncomeRepository extends JpaRepository<Income, Long> {
    List<Income> findByLocalDateBetween(LocalDate startDate, LocalDate endDate);
}
