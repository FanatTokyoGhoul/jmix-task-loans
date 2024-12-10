### Prepared project
This project contains the following elements from the “Issuance of loans” subject area:
- Entities Client and Loan
- Views for listing and editing Client, as well as a view with a list of Loans

### Task
This homework requires you to implement a user interface and logic for working with data to create loan requests,
and to review and approve these requests.

#### 1. Request Loan user interface
Create RequestLoan view:
- Create a view based on the "Blank view" template.
- The view should always open in a dialogue mode.
- The view should not be in the menu (will be opened from another view).
- The view must contain two input fields with labels: Client and Amount, and two buttons: Request and Cancel.
- Client field - selects a client from the list, use EntityComboBox component. Required field.
- Amount field is a text field. Mandatory as well.
- Request action logic:
    - Data must be validated: Client (required) and Amount (required; value must be positive).
      Display a notification in case of incorrect values.
    - Creates, fills with attributes and saves a new Loan object in the database.
    - In addition, sets the requestDate attributes using current day date, as well as status - Requested.
    - Closes the current view (dialog) after successful saving.
- Set an appropriate icon to the Request button.
- Cancel action - simply closes the dialog.

Use DataManager for entity saving.

Dialogue layout requirements:
- The Request and Cancel buttons should be located at the bottom of the dialog, next to each other, horizontally.
- The dialogue should be compact in height and width of the content, without empty space.

Add a button to open the RequestLoan dialog view to the ClientListView.
- Name "Request loan"
- When clicked, opens the RequestLoan dialog
- (*) (optional) If an entry is selected in the Clients data grid, then transfer this entry to the RequestLoan dialog,
  to immediately prefill the Client field.

#### 2. Screen for viewing loan applications (requests)
Create a separate LoanApproval view to show information on existed Loan requests.
- Use Entity list view template for Loan entity
- Disable standard CRUD actions.
- Screen title and menu caption - "Loan Approval"
- Only Loans with the Requested status should be loaded into the view.
- Set up the following set of columns:
    - requestDate
    - client (full name)
    - amount
    - client - phoneNumber
    - client - salary
    - client age (age, in years). This is a Generated Column with text. Age is calculated based on client's birthdate.

#### 2.1 (*) (optional) Display of previous customer loans
Add table (DataGrid) to the LoanApproval view to display data about other loans from the client who created the selected request:
- Location - at the bottom of the view.
- This table must have a fixed height, enough to display ~4 rows. Horizontally - stretch to the full width.
- Above the additional table, place the heading: “Previous loans:”.
- The table should display **other** Loans of the same Client, whose application is highlighted in the main table. Do not show the current loan application here.
- When the allocation is changed in the main table, the content of the secondary table must be updated.
- Columns: amount, requestDate, status.

When the view size is changed, the main table should  occupy all available vertical space.

Notes:
- Use the ItemChangeEvent listener of the data container (collection container) to react to the table selection changes.
- Set request parameter values into the data loader from the view controller code.

#### 3. Actions to approve or reject the application.
Add two buttons to the LoanApproval view to approve or reject an application.
- Add buttons to the panel above the main table (DataGrid).
- Set appropriate icons for both buttons.
- Approve action:
    - Changes the status of the selected Loan in the table to Approved
    - Removes Loan from the main table on the view
    - Shows Notification: "Approved"
- Reject action:
    - Changes the status of the selected one in the Loan table to Rejected
    - Removes Loan from the main table on the view
    - Shows Notification: "Rejected"

To save changes use DataManager.