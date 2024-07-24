package al.library.revenuetracker.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
@Table(name = "monthly_income_summary")
public class MonthlyIncomeSummary {
    @Id
    private Long id;

    private int year;
    private int month;
    private BigDecimal totalIncome;
}
