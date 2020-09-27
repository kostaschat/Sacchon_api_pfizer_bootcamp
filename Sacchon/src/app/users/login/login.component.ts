import { HttpClient, HttpHeaders} from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  res: any;
  form :FormGroup;
  
  constructor(private router: Router, private http: HttpClient) { }
 

  ngOnInit(): void {
    this.form = new FormGroup({
      username: new FormControl(null, Validators.required),
      password: new FormControl(null, Validators.required)   
    });
  } 

  readonly url = 'http://localhost:9000/v1/login';
  
  loginSumbit():Observable<any>{
    const data ={
      "username": this.form.get('username').value,
      "password":this.form.get('password').value
    }
  
    this.http.post(this.url, data,{ responseType:'text'}).subscribe(
        (response) => {
          this.res = response;
          console.log(this.res)

          if (this.res != null){
            sessionStorage.setItem("credentials", data.username + ":" + data.password)
            sessionStorage.setItem("role", this.res)
            sessionStorage.setItem("username", data.username)
            this.router.navigate(['dashboard'])
           }
           else {
            alert("Wrong login or password");
          }
        }
    )
    return this.http.get<any>(this.url+'/auth',{ params:data});
  }
}