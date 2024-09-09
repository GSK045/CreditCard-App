export interface ICreditCardRequest {
  id: number;
  userName: string;
  email: string;
  phoneNumber: string;
  address: string;
  dateOfBirth: string;
  income: number;
  idProofNumber: number;
  cardApprovalStatus: string;
  applicationDate: any;
  approvalDate: any;
  reason: string;
}

export class CreditCardRequest implements ICreditCardRequest {
  id: number = 0;
  userName: string = '';
  email: string = '';
  phoneNumber: string = '';
  address: string = '';
  dateOfBirth: string = '';
  income: number = 0;
  idProofNumber: number = 0;
  cardApprovalStatus: string = '';
  applicationDate: any;
  approvalDate: any;
  reason: string = '';
}

export class Page<T> {
  pageNo: number = 0;
  pageSize: number = 0;
  last: boolean = false;
  first: boolean = false;
  totalPages: number = 0;
  records: any;
  totalRecords: number = 0;
}
