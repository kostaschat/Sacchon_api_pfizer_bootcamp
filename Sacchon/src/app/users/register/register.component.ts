import { HttpClient, HttpHeaders} from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';


@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {

  form :FormGroup;
  constructor(private http: HttpClient, private router: Router) { }
  
  
  ngOnInit(): void {
  
        this.form = new FormGroup({
          email: new FormControl(null, Validators.required),
          username: new FormControl(null, Validators.required),
          password: new FormControl(null, Validators.required),
          firstName: new FormControl(null, Validators.required),
          lastName: new FormControl(null, Validators.required),
          address : new FormControl(null, Validators.required),
          city : new FormControl(null, Validators.required),
          zipCode : new FormControl(null, Validators.required),
          phoneNumber : new FormControl(null, Validators.required),
          dob : new FormControl(null, Validators.required)
      });


}

formRegisterSumbit(){
  
  const data ={
    "username": this.form.get('username').value,
    "password":this.form.get('password').value,
    "email":this.form.get('email').value,
    "firstName": this.form.get('firstName').value,
    "lastName": this.form.get('lastName').value,
    "address" : this.form.get('address').value,
    "city" : this.form.get('city').value,
    "zipCode" : this.form.get('zipCode').value,
    "phoneNumber" : this.form.get('phoneNumber').value,
    "dob" : this.form.get('dob').value,
    "role": "patient"
  }

  this.http.post('http://localhost:9000/v1/register', data).subscribe(
      (response) => console.log(response),
      (error) => console.log(error),
      this.router.navigate['dashboard']
  ) 
}
}