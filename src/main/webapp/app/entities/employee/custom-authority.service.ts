import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {Observable} from 'rxjs';

import {SERVER_API_URL} from 'app/app.constants';
import {createRequestOption} from 'app/shared/util/request-util';
import {ICustomAuthority} from "app/shared/model/custom-authority.model";

type EntityResponseType = HttpResponse<ICustomAuthority>;
type EntityArrayResponseType = HttpResponse<ICustomAuthority[]>;

@Injectable({ providedIn: 'root' })
export class CustomAuthorityService {
  public resourceUrl = SERVER_API_URL + 'api/customAuthorities';

  constructor(protected http: HttpClient) {}

  create(company: ICustomAuthority): Observable<EntityResponseType> {
    return this.http.post<ICustomAuthority>(this.resourceUrl, company, { observe: 'response' });
  }

  update(company: ICustomAuthority): Observable<EntityResponseType> {
    return this.http.put<ICustomAuthority>(this.resourceUrl, company, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICustomAuthority>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICustomAuthority[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
