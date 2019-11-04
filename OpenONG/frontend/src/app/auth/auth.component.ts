import { Component, OnInit, Input } from '@angular/core';
import { MediaChange, ObservableMedia } from '@angular/flex-layout';
import { LoginService } from '../login.service';




@Component({
  selector: 'app-auth',
  templateUrl: './auth.component.html',
  styleUrls: ['./auth.component.scss']

})

export class AuthComponent implements OnInit {
  //TODO: verificar pq nao funcionou a autenticacao
  mostrarTelas: boolean = false;
  @Input() isVisible: boolean = true;
  visibility = 'shown';
 
  sideNavOpened: boolean = true;
  matDrawerOpened: boolean = false;
  matDrawerShow: boolean = true;
  sideNavMode: string = 'side';

  ngOnChanges() {
    this.visibility = this.isVisible ? 'shown' : 'hidden';
  }

  constructor(private media: ObservableMedia, private loginService: LoginService) { }

  ngOnInit() {

    this.loginService.mostrarTelasEmitter.subscribe(
      mostrar => {
        debugger;
        this.mostrarTelas = mostrar
        console.log(this.mostrarTelas)
       }
      );

    this.media.subscribe((mediaChange: MediaChange) => {
      this.toggleView();
  });
  }
  getRouteAnimation(outlet) {

    return outlet.activatedRouteData.animation;
    //return outlet.isActivated ? outlet.activatedRoute : ''
  }

  toggleView() {
    if (this.media.isActive('gt-md')) {
      this.sideNavMode = 'side';
      this.sideNavOpened = true;
      this.matDrawerOpened = false;
      this.matDrawerShow = true;
    } else if (this.media.isActive('gt-xs')) {
      this.sideNavMode = 'side';
      this.sideNavOpened = false;
      this.matDrawerOpened = true;
      this.matDrawerShow = true;
    } else if (this.media.isActive('lt-sm')) {
      this.sideNavMode = 'over';
      this.sideNavOpened = false;
      this.matDrawerOpened = false;
      this.matDrawerShow = false;
    }
  }


}
