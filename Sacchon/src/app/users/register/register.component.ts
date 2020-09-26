import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  formRegister :FormGroup

  private readonly url ='http://localhost:9000/v1/register';

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    this.formRegister = new FormGroup({
      firstName: new FormControl(null, [Validators.required]),
      lastName: new FormControl(null, Validators.required),
      email: new FormControl(null, Validators.required),
      username: new FormControl(null, Validators.required),
      password: new FormControl(null, Validators.required)
  });
}
formRegisterSumbit(){
  console.log(this.formRegister.value)
    this.http.post(this.url,this.formRegister.value)

}

}
