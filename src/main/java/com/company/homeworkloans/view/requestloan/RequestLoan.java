package com.company.homeworkloans.view.requestloan;


import com.company.homeworkloans.entity.Client;
import com.company.homeworkloans.entity.Loan;
import com.company.homeworkloans.entity.LoanStatus;
import com.company.homeworkloans.view.main.MainView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.router.Route;
import io.jmix.core.DataManager;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.component.textfield.TypedTextField;
import io.jmix.flowui.component.valuepicker.EntityPicker;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.model.InstanceContainer;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;

@Route(value = "RequestLoan", layout = MainView.class)
@ViewController(id = "RequestLoan")
@ViewDescriptor(path = "RequestLoan.xml")
@DialogMode(width = "30em")
@EditedEntityContainer("loansDc")
public class RequestLoan extends StandardView {

    @Autowired
    private DataManager dataManager;

    @Autowired
    private Notifications notifications;

    private Loan loan;

    @ViewComponent
    private InstanceContainer<Loan> loansDc;
    @ViewComponent
    private EntityPicker<Client> clientField;
    @ViewComponent
    private TypedTextField<BigDecimal> amountField;

    @Subscribe
    public void onInit(InitEvent event) {
        loan = dataManager.create(Loan.class);
        loansDc.setItem(loan);
    }

    @Subscribe(id = "cancelBtn", subject = "clickListener")
    public void onCancelBtnClick(final ClickEvent<JmixButton> event) {
        close(StandardOutcome.CLOSE);
    }

    @Subscribe(id = "requestBtn", subject = "clickListener")
    public void onRequestBtnClick(final ClickEvent<JmixButton> event) {
        if (!validateLoan()) return;
        loan.setStatus(LoanStatus.REQUESTED);
        loan.setRequestDate(LocalDate.now());

        try {
            dataManager.save(loansDc.getItem());
            close(StandardOutcome.CLOSE);
        } catch (Exception e) {
            notifications.create("Error saving loan")
                    .withPosition(Notification.Position.TOP_END)
                    .withThemeVariant(NotificationVariant.LUMO_ERROR)
                    .show();
        }
    }

    public void setClient(Client client) {
        loan.setClient(client);
    }

    private boolean validateLoan() {
        if (clientField.getValue() == null) {
            notifications.create("Please select a client")
                    .withPosition(Notification.Position.TOP_END)
                    .withThemeVariant(NotificationVariant.LUMO_WARNING)
                    .show();
            return false;
        }

        if (amountField.getValueSource() == null
                || amountField.getValueSource().getValue() == null
                || amountField.getValueSource().getValue().compareTo(BigDecimal.ZERO) <= 0) {
            notifications.create("Amount must be filled and greater than zero")
                    .withThemeVariant(NotificationVariant.LUMO_WARNING)
                    .withPosition(Notification.Position.TOP_END)
                    .show();
            return false;
        }
        return true;
    }
}