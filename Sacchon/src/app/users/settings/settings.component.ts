import { Component, OnInit } from '@angular/core';
import { SettingsService } from '../settings.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-settings',
  templateUrl: './settings.component.html',
  styleUrls: ['./settings.component.scss']
})
export class SettingsComponent implements OnInit {

  constructor(private settingsService: SettingsService,  private router: Router) { }

  ngOnInit(): void {
    if(sessionStorage.getItem("credentials") == null) {
      this.router.navigate(['login'])
    }

  }

  callRemoveService(){
    this.settingsService.removeUser().subscribe();

    //delete sessions
    sessionStorage.clear();
    this.router.navigate(['dashboard'])
  }

}
