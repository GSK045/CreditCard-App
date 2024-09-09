import { Component, EventEmitter, Inject, OnInit, Output } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { CardService } from 'src/app/services/card.service';

@Component({
  selector: 'app-appprovedialog',
  templateUrl: './viewapproval.component.html',
  styleUrls: ['./viewapproval.component.css'],
})
export class ViewApproveDialogComponent implements OnInit {
  displayedData: string = '';
  reason: any = '';
  approvedDate: any;
  status:string='';
  @Output() onClose: EventEmitter<boolean> = new EventEmitter<boolean>();

  ngOnInit(): void {}

  public dialogRef: MatDialogRef<ViewApproveDialogComponent> | undefined;

  constructor(
    dialogRef: MatDialogRef<ViewApproveDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { creditcardRequest: any }
  ) {
    this.dialogRef = dialogRef;
    this.reason = this.data.creditcardRequest.reason;
    console.log(this.data.creditcardRequest);
    this.approvedDate = this.data.creditcardRequest.approvalDate;
    this.status=this.data.creditcardRequest.cardApprovalStatus;
    console.log(this.approvedDate);
  }
  onCancelDialog() {
    console.log('close');
    this.onClose.emit(false);
    this.dialogRef?.close('cancel');
  }
}
