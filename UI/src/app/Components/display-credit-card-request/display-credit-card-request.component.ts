import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Subject, takeUntil } from 'rxjs';
import { CreditCardRequest } from 'src/app/Models/creditcardrequest';
import { CardService } from 'src/app/services/card.service';
import { ApproveDialogComponent } from '../approvaldialog/approvedialog.component';
import { ViewApproveDialogComponent } from '../admin/viewapproval/viewapproval.component';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-display-credit-card-request',
  templateUrl: './display-credit-card-request.component.html',
  styleUrls: ['./display-credit-card-request.component.css'],
})
export class DisplayCreditCardRequestComponent implements OnInit {
  private destroy$ = new Subject<void>();

  //pagination
  pageSizeOptions = [5, 10, 20, 25, 30];
  totalItems: number = 0;
  pageSize: number = 10;
  pageIndex: number = 0;
  tome_out: number = 3000;

  enableEdit: boolean = true;
  creditCardRequest: any;
  form!: FormGroup;

  userData: any;
  isCustomerLoggedIn: boolean = true;
  // creditCardRequestData: any[] = [];

  creditCardRequestData: CreditCardRequest[] = [];
  email: string = '';
  isApprovedCardRequests:any;
  isDisableRule: boolean = false;

  statuses: any[] = [
    {
      id: 1,
      value: 'Pending',
    },
    {
      id: 2,
      value: 'Approved',
    },
    {
      id: 3,
      value: 'Rejected',
    },
    {
      id: 4,
      value: 'All',
    },
  ];

  displayedColumns: string[] = [
    'userName',
    'phoneNumber',
    'email',
    'dateOfBirth',
    'income',
    'idProofNumber',
    'cardApprovalStatus',
    'actions',
  ];

  constructor(
    private cardService: CardService,
    public dialog: MatDialog,
    private router: Router,
    private fb: FormBuilder,
    private route: ActivatedRoute
  ) {
    this.form = this.fb.group({
      status: ['All'],
    });
  }

  ngOnInit(): void {
    this.cardService.refreshRequired.subscribe(() => {
      console.log('Refresh signal received');
      this.getCreditCardRequests(); // Refresh data on update
    });

    const user = localStorage.getItem('user');

    if (user) {
      this.userData = JSON.parse(user);
      if (this.userData.roleName === 'Customer') {
        this.isCustomerLoggedIn = true;
        this.email = this.userData.email;
      } else if (this.userData.roleName === 'Admin') {
        this.isCustomerLoggedIn = false;
      }
    }
    // Subscribe to changes in the status form control
    this.form.get('status')?.valueChanges.subscribe((status) => {
      this.onStatusChange(status);
    });
    // Fetch initial data
    this.getCreditCardRequests();
  }

  onStatusChange(status: string): void {
    console.log('Status changed:', status);
    this.getCreditCardRequests();
  }

  getCreditCardRequests() {
    if (this.isCustomerLoggedIn) {
      this.cardService
        .getCardRequest(this.email)
        .pipe(takeUntil(this.destroy$))
        .subscribe({
          next: (response) => {
            console.log(response);
            this.creditCardRequestData = response;
            this.updateEditOptions();
          },
          error: (error: Error) => {
            console.log(error);
          },
        });
    } else {
      const status = this.form.value.status;
      console.log(status);
      this.cardService
        .getAllCreditCardRequests(this.pageIndex, this.pageSize, status)
        .pipe(takeUntil(this.destroy$))
        .subscribe({
          next: (response) => {
            console.log(response);
            this.creditCardRequestData = response.records;
            this.totalItems = response.totalRecords;
          },
          error: (error: Error) => {
            console.log(error);
          },
        });
    }
  }

  // Method to check edit options based on creditApprovalStatus
  updateEditOptions() {
    this.creditCardRequestData.forEach((request) => {
    

      if (request.cardApprovalStatus === 'pending') {
        this.enableEdit = true;
      } else {
        this.enableEdit = false;
      }
    });
  }

  viewDetails(creditcardRequest: any) {
    const dialogRef = this.dialog.open(ViewApproveDialogComponent, {
      width: '60%',
      height: '380px',
      data: { creditcardRequest: creditcardRequest },
    });
  }

  handleApproval(creditcardRequest: any) {
    const dialogRef = this.dialog.open(ApproveDialogComponent, {
      width: '60%',
      height: '250px',
      data: { creditcardRequest: creditcardRequest },
    });
  }

  updateCardRequest(data: any) {
    this.fetchCreditCardRequestdata(data.id);
  }

  fetchCreditCardRequestdata(id: number) {
    console.log(id);
    this.cardService
      .getCreditCardRequestById(id)
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: (response) => {
          console.log(response);

          this.creditCardRequest = response;
          this.router.navigate(['layout/creditcardrequest'], {
            state: {
              creditCardRequest: response,
              isAdding: false,
            },
          });
        },
      });
  }

  onPageChange(event: any) {
    this.pageSize = event.pageSize;
    this.pageIndex = event.pageIndex;
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }
}
