import { CommonModule } from '@angular/common';
import { Component , OnInit} from '@angular/core';
import { Router, RouterModule, RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterModule, CommonModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit {

  title = 'digital-banking-app';
  currRouter : string = 'home';

  constructor(private router: Router) { }
  ngOnInit() {
    if(this.router.url === '/') {
      this.router.navigateByUrl('/home');
    }
  }

  goToHome(){
    this.currRouter = 'home';
    this.router.navigateByUrl('/home');
  }

  goToProducts() {
    this.currRouter = 'products';
    this.router.navigateByUrl('/products');
  }
  goToNews() {
    this.currRouter = 'news';
    this.router.navigateByUrl('/news');
  }

}
