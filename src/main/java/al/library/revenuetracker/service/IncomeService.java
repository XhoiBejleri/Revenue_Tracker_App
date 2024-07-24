package al.library.revenuetracker.service;

import al.library.revenuetracker.model.Income;
import al.library.revenuetracker.repository.IncomeRepository;
import al.library.revenuetracker.repository.MonthlyIncomeSummaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class IncomeService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private final IncomeRepository incomeRepository;

    @Autowired
    private final MonthlyIncomeSummaryRepository monthlyIncomeSummaryRepository;

    @Autowired
    public IncomeService(IncomeRepository incomeRepository, MonthlyIncomeSummaryRepository monthlyIncomeSummaryRepository) {
        this.incomeRepository = incomeRepository;
        this.monthlyIncomeSummaryRepository = monthlyIncomeSummaryRepository;
    }

    public Income saveIncome(Income income) {
        return incomeRepository.save(income);
    }

    public List<Income> getAllIncome() {
        return incomeRepository.findAll();
    }

    public BigDecimal getTotalIncomeForMonth(int year, int month) {
        return monthlyIncomeSummaryRepository.findTotalIncomeByYearAndMonth(year, month);
    }

    public List<Income> getIncomeByMonth(int year, int month) {
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());
        return incomeRepository.findByLocalDateBetween(startDate, endDate);
    }

    public BigDecimal calculateTotalIncomeForMonth(int year, int month) {
        List<Income> incomeList = getIncomeByMonth(year, month);
        return incomeList.stream()
                .map(Income::getIncomeAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal calculateTotalIncomeForYear(int year) {
        LocalDate startDate = LocalDate.of(year, 1, 1);
        LocalDate endDate = LocalDate.of(year, 12, 31);
        List<Income> incomeList = incomeRepository.findByLocalDateBetween(startDate, endDate);
        return incomeList.stream()
                .map(Income::getIncomeAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
