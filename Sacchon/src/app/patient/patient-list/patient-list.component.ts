import { Component, OnInit } from '@angular/core';


@Component({
  selector: 'app-product-list',
  template: `
    <div class="card">
      <div class="card-body">
        <h5 class="card-title">Products Page</h5>
        <div class="row">
          <div class="col-2">
            Title
          </div>
          <div class="col-2">
            SKU
          </div>
        </div>
        <div class="row">
          <div class="col-2">
            Jacket
          </div>
          <div class="col-2">
            #jac123
          </div>
        </div>
      </div>
    </div>
  `,
  styles: []
})
export class PatientListComponent implements OnInit {

  constructor() { 
      
  }

  ngOnInit(): void {

  }

}
