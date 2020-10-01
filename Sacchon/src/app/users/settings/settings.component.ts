import { Component, OnInit } from '@angular/core';
import { SettingsService } from '../settings.service';
import { Router } from '@angular/router';
import { FormControl, FormGroup, ValidationErrors, ValidatorFn, Validators } from '@angular/forms';
import { User } from '../user';


export function AtLeastOneFieldValidator(group: FormGroup): {[key: string]: any} {
  let isAtLeastOne = false;
  if (group && group.controls) {
    for (const control in group.controls) {
      if (group.controls.hasOwnProperty(control) && group.controls[control].valid && group.controls[control].value) {
        isAtLeastOne = true;
        break;
      }
    }
  }
  return isAtLeastOne ? null : { 'required': true };
}

@Component({
  selector: 'app-settings',
  templateUrl: './settings.component.html',
  styleUrls: ['./settings.component.scss']
})


export class SettingsComponent implements OnInit {

  user: User;
  myform: FormGroup;
  atleastonefieldvalidator = AtLeastOneFieldValidator;

  constructor(private settingsService: SettingsService,  private router: Router) { }

  ngOnInit(): void {
    
    if(sessionStorage.getItem("credentials") == null) {
      this.router.navigate(['login'])
    }
    
    this.settingsService.getUser().subscribe(p => {this.user = p ,
      this.fillData()} );

      
    this.myform = new FormGroup({
    
      firstName: new FormControl(),
      lastName: new FormControl(),
      password: new FormControl(), 
      address: new FormControl(),
      email: new FormControl('',[
        Validators.pattern("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$")])
       
    },  AtLeastOneFieldValidator);
  
  }

  fillData(){
    this.myform.patchValue(this.user)
 }


updateProfile(){
 const data ={
    "firstName": this.myform.get('firstName').value,
    "lastName":this.myform.get('lastName').value,
    "password": this.myform.get('password').value,
    "address": this.myform.get("address").value,
    "email": this.myform.get("email").value
    }
    console.log(data)
    this.settingsService.updateProfile(data).subscribe(data => { alert("Your data has been saved"); this.router.navigate(['dashboard'])});
  }

  callRemoveService(){
    this.settingsService.removeUser().subscribe();

    //delete sessions
    sessionStorage.clear();
    this.router.navigate(['dashboard'])
  }

}
