import { Component } from '@angular/core';

@Component({
  /* selector: 'app-root',
 templateUrl: './app.component.html',
 styleUrls: ['./app.component.scss']*/
   selector: 'body', template: '<router-outlet></router-outlet>',
 })
export class AppComponent {
  title = 'MyTeamApp';
}
