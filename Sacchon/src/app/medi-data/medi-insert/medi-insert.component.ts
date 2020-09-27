import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MediDataService } from '../medi-data.service';

@Component({
  selector: 'app-medi-insert',
  templateUrl: './medi-insert.component.html',
  styleUrls: ['./medi-insert.component.scss']
})
export class MediInsertComponent implements OnInit {
  form: FormGroup;

  constructor(private mediDataService: MediDataService) { }

  ngOnInit(): void {
    this.form = new FormGroup({
      carb: new FormControl(null, [Validators.required]),
      glucose: new FormControl(null, Validators.required),
      measuredDate: new FormControl(null, Validators.required)
    });
  }

  formSumbit(){
    this.mediDataService.addMedi(this.form).subscribe(data => {
      alert(JSON.stringify(data));
      this.ngOnInit();
    })
    
  }
}
