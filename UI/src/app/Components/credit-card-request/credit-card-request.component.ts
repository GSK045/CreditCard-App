import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Subject } from 'rxjs';
import {
  CreditCardRequest,
  ICreditCardRequest,
} from 'src/app/Models/creditcardrequest';
import { CardService } from 'src/app/services/card.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-login',
  templateUrl: './credit-card-request.component.html',
  styleUrls: ['./credit-card-request.component.css'],
})
export class CreditCardRequestComponent implements OnInit {
  isAdding: boolean = true;
  private destroy$ = new Subject<void>();
  formData!: FormGroup;
  cardData: ICreditCardRequest = new CreditCardRequest();
  creditCardRequest: ICreditCardRequest = new CreditCardRequest();

  ngOnInit(): void {
    this.initializeFormData();
    this.getDataFromState();
  }

  constructor(
    private router: Router,
    private fb: FormBuilder,
    private cardService: CardService
  ) {}

  //initialize form data
  private initializeFormData(): void {
    this.formData = this.fb.group({
      userName: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      phoneNumber: ['', [Validators.required, Validators.pattern(/^\d{10}$/)]],
      address: ['', Validators.required],
      income: ['', Validators.required],
      dateOfBirth: ['', Validators.required],
      idProofNumber: ['', [Validators.required]],
    });
  }

  save() {
    console.log(this.formData);
    console.log(this.formData.value);
    if (this.formData.valid) {
      this.cardData.userName = this.formData.value.userName;
      this.cardData.email = this.formData.value.email;
      this.cardData.phoneNumber = this.formData.value.phoneNumber;
      this.cardData.address = this.formData.value.address;
      this.cardData.income = this.formData.value.income;
      this.cardData.dateOfBirth = this.formData.value.dateOfBirth;
      this.cardData.idProofNumber = this.formData.value.idProofNumber;

      const saveOrUpdate$ = this.isAdding
        ? this.cardService.sendCardRequest(this.cardData)
        : this.cardService.updateCardRequest(this.cardData, '');
      saveOrUpdate$.subscribe({
        next: (response) => {
          console.log(response);
          this.handleSuccessResponse(response.message);
        },
        error: (error) => {
          console.log(error.message);
          this.handleErrorResponse(error.message);
        },
      });
    }
  }

  private getDataFromState() {
    const { creditCardRequest, isAdding } = history.state;
    this.isAdding = isAdding;
    this.creditCardRequest = creditCardRequest || this.creditCardRequest;
    if (!this.isAdding) {
      this.patchFormDataWithCreitCardData();
    }
  }
  private patchFormDataWithCreitCardData() {
    this.formData.patchValue(this.creditCardRequest);
  }

  handleSuccessResponse(response: any): void {
    Swal.fire({
      title: 'Success',
      text: response,
      icon: 'success',
      timer: 5000,
      timerProgressBar: true,
      showConfirmButton: false,
      allowOutsideClick: true,
    }).then(() => {
      this.gotoCreditCard();
    });
    console.log(response);
  }

  handleErrorResponse(error: any): void {
    console.log(error);
  }

  clearForm(): void {
    this.formData.reset();
  }
 

  gotoCreditCard() {
    this.router.navigate(['layout']);
  }
  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }
}
