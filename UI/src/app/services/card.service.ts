import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, Subject, tap } from 'rxjs';
import {
  FETCH_CREDIT_CARD_REQUEST_BASED_ON_LOGGED_IN,
  FETCH_CREDIT_CARD_REQUESTS,
  GET_CREDIT_CARD_REQUEST_BY_ID,
  SAVE_CREDIT_CARD_REQUEST,
  UPDATE_CREDIT_CARD_REQUEST,
} from '../apis/apis';
import { CreditCardRequest, Page } from '../Models/creditcardrequest';

@Injectable({
  providedIn: 'root',
})
export class CardService {
  constructor(private http: HttpClient) {}

  private _refreshRequired = new Subject<void>();
  public refreshRequired = this._refreshRequired.asObservable();

  //adding reports
  sendCardRequest(cardRequest: any): Observable<any> {
    console.log(cardRequest);
    return this.http.post<any>(`${SAVE_CREDIT_CARD_REQUEST}`, cardRequest);
  }

  //get credit crad request bsed on logged in person

  getCardRequest(email: string): Observable<CreditCardRequest[]> {
    console.log(email);
    return this.http.get<any[]>(
      `${FETCH_CREDIT_CARD_REQUEST_BASED_ON_LOGGED_IN}?email=${email}`
    );
  }

  getAllCreditCardRequests(page: any, size: any, status: string) {
    return this.http.get<Page<CreditCardRequest[]>>(
      `${FETCH_CREDIT_CARD_REQUESTS}/${page}/${size}/${status}`
    );
  }

  getCreditCardRequestById(id: number) {
    console.log(id);
    return this.http.get<CreditCardRequest>(
      `${GET_CREDIT_CARD_REQUEST_BY_ID}/${id}`
    );
  }

  // updateCardRequest(caredRequest: any, statusType: string) {
  //   return this.http.put<any>(
  //     `${UPDATE_CREDIT_CARD_REQUEST}?statusType=${statusType}`,
  //     caredRequest
  //   );
  // }
  // Example in CardService
  updateCardRequest(caredRequest: any, statusType: string): Observable<any> {
    return this.http
      .put<any>(
        `${UPDATE_CREDIT_CARD_REQUEST}?statusType=${statusType}`,
        caredRequest
      )
      .pipe(
        tap(() => {
          console.log('Data updated, emitting refresh signal');
          this._refreshRequired.next(); // Notify subscribers about the data update
        })
      );
  }
}
