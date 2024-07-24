package al.library.revenuetracker.controller;

import al.library.revenuetracker.model.Income;
import al.library.revenuetracker.service.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/income")
public class IncomeController {

    private final IncomeService incomeService;

    @Autowired
    public IncomeController(IncomeService incomeService) {
        this.incomeService = incomeService;
    }

    @PostMapping
    public ResponseEntity<Income> addIncome(@RequestBody Income income) {
        Income savedIncome = incomeService.saveIncome(income);
        return ResponseEntity.ok(savedIncome);
    }

    @GetMapping
    public ResponseEntity<List<Income>> getAllIncome() {
        List<Income> incomeList = incomeService.getAllIncome();
        return ResponseEntity.ok(incomeList);
    }

    @GetMapping("/month")
    public ResponseEntity<BigDecimal> getIncomeForMonth(@RequestParam int year, @RequestParam int month) {
        BigDecimal totalIncome = incomeService.calculateTotalIncomeForMonth(year, month);
        return ResponseEntity.ok(totalIncome);
    }

    @GetMapping("/year")
    public ResponseEntity<BigDecimal> getIncomeForYear(@RequestParam int year) {
        BigDecimal totalIncome = incomeService.calculateTotalIncomeForYear(year);
        return ResponseEntity.ok(totalIncome);
    }

    @GetMapping("/month-summary")
    public ResponseEntity<BigDecimal> getMonthlyIncomeSummary(@RequestParam int year, @RequestParam int month) {
        BigDecimal totalIncome = incomeService.getTotalIncomeForMonth(year, month);
        return ResponseEntity.ok(totalIncome);
    }

}
