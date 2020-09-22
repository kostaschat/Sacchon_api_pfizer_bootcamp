import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-dashboard',
  template: `
    <div class="card">
      <div class="card-body">
        <h5 class="card-title">dashboard page</h5>
        <p class="card-text">Lorem ipsum</p>
      </div>
    </div>
  `,
  styles: []
})
export class DashboardComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }

}