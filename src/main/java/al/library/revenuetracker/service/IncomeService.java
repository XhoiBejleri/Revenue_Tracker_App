package al.library.revenuetracker.service;

import al.library.revenuetracker.model.Income;
import al.library.revenuetracker.repository.IncomeRepository;
import al.library.revenuetracker.repository.MonthlyIncomeSummaryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@SuppressWarnings("ALL")
@Service
public class IncomeService {

    private static final Logger logger = LoggerFactory.getLogger(IncomeService.class);


    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private final IncomeRepository incomeRepository;

    @Autowired
    private final MonthlyIncomeSummaryRepository monthlyIncomeSummaryRepository;

    @Autowired
    private final EmailService emailService;

    @Autowired
    public IncomeService(IncomeRepository incomeRepository, MonthlyIncomeSummaryRepository monthlyIncomeSummaryRepository, EmailService emailService) {
        this.incomeRepository = incomeRepository;
        this.monthlyIncomeSummaryRepository = monthlyIncomeSummaryRepository;
        this.emailService = emailService;
    }

    public Income saveIncome(Income income){
        Income savedIncome = incomeRepository.save(income);
        String recipientEmail = "xbejleri@gmail.com";
        String subject = "New Income Entry Added";
        String body = String.format(
                """
                        A new income entry was added:

                        ID: %d
                        Item Type: %s
                        Income Amount: %s
                        Transaction Date: %s
                        """,
                savedIncome.getId(),
                savedIncome.getItemType(),
                savedIncome.getIncomeAmount(),
                savedIncome.getLocalDate()
        );
        try {
            emailService.sendSimpleMessage(recipientEmail, subject, body);
            logger.info("Email send successfully to: {}", recipientEmail);
        } catch (MailException e) {
            logger.error("Failed to send email notification to recipient", e);
        }
        return savedIncome;

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
