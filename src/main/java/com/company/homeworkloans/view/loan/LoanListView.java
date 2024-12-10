package com.company.homeworkloans.view.loan;

import com.company.homeworkloans.entity.Loan;
import com.company.homeworkloans.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;

@Route(value = "loans", layout = MainView.class)
@ViewController("Loan.list")
@ViewDescriptor("loan-list-view.xml")
@LookupComponent("loansDataGrid")
@DialogMode(width = "64em")
public class LoanListView extends StandardListView<Loan> {
}