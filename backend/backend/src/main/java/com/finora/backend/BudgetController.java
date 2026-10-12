
package com.finora.backend;
import com.finora.backend.repository.BudgetRepository;
import com.finora.backend.repository.TransactionRepository;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.YearMonth;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/budgets")

public class BudgetController {

    private final BudgetRepository budgetRepository;
    private final TransactionRepository transactionRepository;

    public BudgetController(
            BudgetRepository budgetRepository,
            TransactionRepository transactionRepository) {

        this.budgetRepository = budgetRepository;
        this.transactionRepository = transactionRepository;
    }

    // GET: Retrieve budgets for a selected month
    // Example: GET /api/budgets?month=2026-10
    @GetMapping
    public List<Map<String, Object>> getBudgets(
            @RequestParam(required = false) String month) {

        String selectedMonth = month == null
                ? YearMonth.now().toString()
                : month;

        validateMonth(selectedMonth);

        return budgetRepository
                .findByBudgetMonth(selectedMonth)
                .stream()
                .map(this::toBudgetResponse)
                .toList();
    }

    // POST: Create a new budget
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, Object> createBudget(
            @Valid @RequestBody Budget budget) {

        validateBudget(budget);

        String category = budget.getCategory().trim();
        String month = budget.getBudgetMonth();

        boolean duplicate = budgetRepository
                .findByBudgetMonth(month)
                .stream()
                .anyMatch(existing ->
                        existing.getCategory()
                                .equalsIgnoreCase(category));

        if (duplicate) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "A budget already exists for this category and month");
        }

        budget.setId(null);
        budget.setCategory(category);

        Budget savedBudget = budgetRepository.save(budget);

        return toBudgetResponse(savedBudget);
    }

    // PUT: Update an existing budget
    @PutMapping("/{id}")
    public Map<String, Object> updateBudget(
            @PathVariable Long id,
            @Valid @RequestBody Budget updated) {

        validateBudget(updated);

        Budget existing = budgetRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Budget not found: " + id));

        String category = updated.getCategory().trim();
        String month = updated.getBudgetMonth();

        boolean duplicate = budgetRepository
                .findByBudgetMonth(month)
                .stream()
                .anyMatch(item ->
                        !item.getId().equals(id)
                                && item.getCategory()
                                        .equalsIgnoreCase(category));

        if (duplicate) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "A budget already exists for this category and month");
        }

        existing.setCategory(category);
        existing.setMonthlyLimit(updated.getMonthlyLimit());
        existing.setBudgetMonth(month);

        Budget savedBudget = budgetRepository.save(existing);

        return toBudgetResponse(savedBudget);
    }

    // DELETE: Delete a budget
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBudget(@PathVariable Long id) {

        if (!budgetRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Budget not found: " + id);
        }

        budgetRepository.deleteById(id);
    }

    // Validate budget fields
    private void validateBudget(Budget budget) {

        if (budget.getCategory() == null
                || budget.getCategory().isBlank()) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Category is required");
        }

        if (budget.getMonthlyLimit() == null
                || budget.getMonthlyLimit()
                        .compareTo(BigDecimal.ZERO) <= 0) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Monthly limit must be greater than zero");
        }

        validateMonth(budget.getBudgetMonth());
    }

    // Validate YYYY-MM month format
    private void validateMonth(String month) {

        try {
            if (month == null || !month.matches("\\d{4}-\\d{2}")) {
                throw new IllegalArgumentException("Invalid month");
            }

            YearMonth.parse(month);

        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Month must use YYYY-MM format");
        }
    }

    // Calculate spending and return budget details
    private Map<String, Object> toBudgetResponse(Budget budget) {

        

BigDecimal spent = transactionRepository.findAll()
        .stream()
        .filter(t -> t.getType() != null
                && "EXPENSE".equalsIgnoreCase(t.getType()))
        .filter(t -> t.getCategory() != null
                && t.getCategory().equalsIgnoreCase(
                        budget.getCategory()))
        .filter(t -> t.getDate() != null
                && t.getDate().toString().startsWith(
                        budget.getBudgetMonth()))
        .map(Transaction::getAmount)
        .filter(amount -> amount != null)
        .reduce(BigDecimal.ZERO, BigDecimal::add);



        BigDecimal remaining =
                budget.getMonthlyLimit().subtract(spent);

        BigDecimal percentage = spent
                .multiply(BigDecimal.valueOf(100))
                .divide(
                        budget.getMonthlyLimit(),
                        2,
                        RoundingMode.HALF_UP);

        Map<String, Object> response = new LinkedHashMap<>();

        response.put("id", budget.getId());
        response.put("category", budget.getCategory());
        response.put("monthlyLimit", budget.getMonthlyLimit());
        response.put("budgetMonth", budget.getBudgetMonth());
        response.put("spent", spent);
        response.put("remaining", remaining);
        response.put("percentage", percentage);
        response.put(
                "exceeded",
                spent.compareTo(budget.getMonthlyLimit()) > 0);

        return response;
    }
}
