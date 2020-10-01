import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Session } from 'protractor';

@Component({
  selector: 'app-logout',
  templateUrl: './logout.component.html',
  styleUrls: ['./logout.component.scss']
})
export class LogoutComponent implements OnInit {

  constructor(private router: Router) { }

  ngOnInit(): void {
    sessionStorage.clear();
    this.router.navigate(['dashboard'])
  }

}
