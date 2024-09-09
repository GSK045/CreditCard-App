import { Component, EventEmitter, Inject, OnInit, Output } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { CardService } from 'src/app/services/card.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-appprovedialog',
  templateUrl: './approvedialog.component.html',
  styleUrls: ['./approvedialog.component.css'],
})
export class ApproveDialogComponent implements OnInit {
  displayedData: string = '';
  reason: any = '';
  @Output() onClose: EventEmitter<boolean> = new EventEmitter<boolean>();

  ngOnInit(): void {}

  public dialogRef: MatDialogRef<ApproveDialogComponent> | undefined;

  constructor(
    dialogRef: MatDialogRef<ApproveDialogComponent>,
    private router: Router,
    private cardservice: CardService,
    @Inject(MAT_DIALOG_DATA) public data: { creditcardRequest: any }
  ) {
    this.dialogRef = dialogRef;
  }

  onCancelDialog() {
    console.log('close');
    this.onClose.emit(false);
    this.dialogRef?.close('cancel');
  }

  onCloseDialog() {
    this.onClose.emit(false);
    this.dialogRef?.close();
  }

  onApprove(status: string) {
    console.log(status, this.reason);

    const updatedRequest = {
      ...this.data.creditcardRequest,
      reason: this.reason,
    };

    this.cardservice.updateCardRequest(updatedRequest, status).subscribe({
      next: (response) => {
        console.log(response);
        this.handleSuccessResponse(response.message);
      },
    });
    this.onClose.emit(false);
    this.dialogRef?.close(this.reason);
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

  gotoCreditCard() {
    this.router.navigate(['layout/creditcard']);
  }
}
