import { environment } from '../environments/environment';

export const ROLE_API = 'role';
export const CARD_API = 'cardapproval';
export const USER_API = 'user';

//card request ais
export const SAVE_CREDIT_CARD_REQUEST =
  environment.BaseUrl + CARD_API + '/save';

  //card request ais
export const UPDATE_CREDIT_CARD_REQUEST =
environment.BaseUrl + CARD_API + '/update';


  //card request ais
  export const GET_CREDIT_CARD_REQUEST_BY_ID =
  environment.BaseUrl + CARD_API ;


// user apis
export const LOGIN = environment.BaseUrl + USER_API + '/login';

export const FETCH_CREDIT_CARD_REQUEST_BASED_ON_LOGGED_IN =
  environment.BaseUrl + CARD_API + '/get/card/requests';

export const FETCH_CREDIT_CARD_REQUESTS =
  environment.BaseUrl + CARD_API + '/getall';
