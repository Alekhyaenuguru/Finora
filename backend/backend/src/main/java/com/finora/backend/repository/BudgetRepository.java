
package com.finora.backend.repository;

import com.finora.backend.Budget;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BudgetRepository
        extends JpaRepository<Budget, Long> {

    List<Budget> findByBudgetMonth(String budgetMonth);
}
