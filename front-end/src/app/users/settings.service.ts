import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from './user';

@Injectable({
  providedIn: 'root'
})
export class SettingsService {

  constructor(private http: HttpClient) { }

  readonly baseUrl = 'http://localhost:9000/v1/remove-account';
  readonly updateUrl = 'http://localhost:9000/v1/update';
  readonly userUrl = 'http://localhost:9000/v1/get-user';

  removeUser(){
    return this.http.delete(this.baseUrl,
      {headers:new HttpHeaders(
        {'Authorization': 'Basic ' + btoa(sessionStorage.getItem("credentials"))}
        )
      })
  }

getUser(): Observable<User>{
  return this.http.get<User>(
    this.userUrl,
    {headers:new HttpHeaders(
      {'Authorization': 'Basic ' + btoa(sessionStorage.getItem("credentials"))}
      )
    }
    );
}

  updateProfile(data){

    return this.http.put(this.updateUrl, data,{headers : new  HttpHeaders(
      {'Authorization': 'Basic ' +  btoa(sessionStorage.getItem("credentials"))}
      )});
  }
}
