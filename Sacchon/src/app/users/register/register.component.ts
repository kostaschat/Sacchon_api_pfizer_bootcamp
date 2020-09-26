import { HttpClient, HttpHeaders} from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';


@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  form :FormGroup;
  constructor(private http: HttpClient) { }
  
  
  ngOnInit(): void {
  
        this.form = new FormGroup({
          email: new FormControl(null, Validators.required),
          username: new FormControl(null, Validators.required),
          password: new FormControl(null, Validators.required),
          firstName: new FormControl(null, Validators.required),
          lastName: new FormControl(null, Validators.required)
      });


}

formRegisterSumbit(){
  
  const data ={
    "username": this.form.get('username').value,
    "password":this.form.get('password').value,
    "email":this.form.get('email').value,
    "firstName": this.form.get('firstName').value,
    "lastName": this.form.get('lastName').value,
    "role": "patient"
  }

  this.http.post('http://localhost:9000/v1/register', data).subscribe(
      (response) => console.log(response),
      (error) => console.log(error)
  ) 
}
}