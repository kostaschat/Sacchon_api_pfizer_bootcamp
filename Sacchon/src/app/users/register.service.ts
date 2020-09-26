import { Injectable } from '@angular/core';
import { HttpClient,HttpParams,HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Register } from './register';

@Injectable({
  providedIn: 'root'
})
export class RegisterService {

  constructor(private http: HttpClient) { }

  getProducts(): Observable<Register[]> {

    // let headers = new HttpHeaders();
    // headers = headers.append("Authorization", "Basic " + btoa('anestis'+':'+'anestis'));
    // headers = headers.append("Content-Type", "application/x-www-form-urlencoded");


    //  let headers = new HttpHeaders().set('anestis','anestis');
    // let headers = new HttpHeaders({'Authorization':'Basic'+ btoa('anestis'+':'+'anestis')});
    return this.http.get<Register[]>('http://localhost:9000/v1/medidata');
  }
}
