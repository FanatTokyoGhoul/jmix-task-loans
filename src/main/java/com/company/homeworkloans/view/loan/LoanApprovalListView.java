package com.company.homeworkloans.view.loan;

import com.company.homeworkloans.entity.Loan;
import com.company.homeworkloans.entity.LoanStatus;
import com.company.homeworkloans.view.main.MainView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.router.Route;
import io.jmix.core.DataManager;
import io.jmix.flowui.component.grid.DataGrid;
import io.jmix.flowui.kit.action.ActionPerformedEvent;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.model.CollectionContainer;
import io.jmix.flowui.model.CollectionLoader;
import io.jmix.flowui.model.InstanceContainer;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;


@Route(value = "loans/approval", layout = MainView.class)
@ViewController(id = "LoanApproval.list")
@ViewDescriptor(path = "loan-approval-list-view.xml")
@LookupComponent("loansDataGrid")
@DialogMode(width = "64em")
public class LoanApprovalListView extends StandardListView<Loan> {
    @ViewComponent
    private CollectionContainer<Loan> previousLoansDc;

    @ViewComponent
    private CollectionLoader<Loan> previousLoanDl;

    @ViewComponent
    private DataGrid<Loan> previousLoansDataGrid;

    @ViewComponent
    private CollectionContainer<Loan> loansDc;

    @ViewComponent
    private DataGrid<Loan> loansDataGrid;
    @ViewComponent
    private CollectionLoader<Loan> loansDl;
    @Autowired
    private DataManager dataManager;

    @Subscribe(id = "loansDc", target = Target.DATA_CONTAINER)
    public void onLoansDcItemChange(final InstanceContainer.ItemChangeEvent<Loan> event) {
        Loan selectedLoan = event.getItem();
        if (selectedLoan != null) {
            previousLoanDl.setParameter("clientId", selectedLoan.getClient().getId());
            previousLoanDl.setParameter("currentLoanId", selectedLoan.getId());
            previousLoanDl.load();
        }
    }

    @Subscribe(id = "approveButton", subject = "clickListener")
    public void onApproveButtonClick(final ClickEvent<JmixButton> event) {
        Optional<Loan> selectedLoan = loansDataGrid.getSelectedItems().stream().findFirst();
        selectedLoan.ifPresent(loan -> updateLoanStatus(loan, LoanStatus.APPROVED));
    }

    @Subscribe(id = "rejectButton", subject = "clickListener")
    public void onRejectButtonClick(final ClickEvent<JmixButton> event) {
        Optional<Loan> selectedLoan = loansDataGrid.getSelectedItems().stream().findFirst();
        selectedLoan.ifPresent(loan -> updateLoanStatus(loan, LoanStatus.REJECTED));
    }


    private void updateLoanStatus(Loan loan, LoanStatus status) {
        loan.setStatus(status);
        dataManager.save(loan);
        loansDc.getMutableItems().remove(loan);
        loansDataGrid.getDataProvider().refreshAll();
//        loansDl.load();
    }
}