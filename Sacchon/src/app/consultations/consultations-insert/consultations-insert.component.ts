import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import {FormsModule,ReactiveFormsModule} from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ConsultationsService } from '../consultations.service';

@Component({
  selector: 'app-consultations-insert',
  templateUrl: './consultations-insert.component.html',
  styleUrls: ['./consultations-insert.component.scss']
})
export class ConsultationsInsertComponent implements OnInit {

  form: FormGroup;
  id: any;
  
  constructor(private consultationsService: ConsultationsService,
     private route: ActivatedRoute, private router:Router) { }

  ngOnInit(): void {
    
    this.form = new FormGroup({
      medicationName: new FormControl(null, [Validators.required]),
      dosage: new FormControl(null, Validators.required),
      advice: new FormControl(null, Validators.required)
    });

    this.id = this.route.snapshot.queryParamMap.get("id");
  }

  formSubmit(){
    this.consultationsService.addConsultation(this.form, this.id).subscribe(data => {
      alert("You successfully submitted your consultation.");this.router.navigate(['doctor/mypatients'])
    })
  }

}
