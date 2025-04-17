package com.company.homeworkloans.view.client;

import com.company.homeworkloans.entity.Client;
import com.company.homeworkloans.view.main.MainView;
import com.company.homeworkloans.view.requestloan.RequestLoan;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.DialogWindows;
import io.jmix.flowui.component.grid.DataGrid;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "clients", layout = MainView.class)
@ViewController("Client.list")
@ViewDescriptor("client-list-view.xml")
@LookupComponent("clientsDataGrid")
@DialogMode(width = "64em")
public class ClientListView extends StandardListView<Client> {

    @Autowired
    private DialogWindows dialogWindows;

    @ViewComponent
    private DataGrid<Client> clientsDataGrid;

    @Subscribe(id = "requestLoan", subject = "clickListener")
    public void onRequestLoanClick(final ClickEvent<JmixButton> event) {

        DialogWindow<RequestLoan> requestLoanView = dialogWindows.view(this, RequestLoan.class).build();

        clientsDataGrid.getSelectedItems().stream().findFirst().ifPresent(client -> {
            requestLoanView.getView().setClient(client);
        });
        requestLoanView.open();
    }
}