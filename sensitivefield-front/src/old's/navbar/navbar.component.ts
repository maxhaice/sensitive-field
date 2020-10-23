import { Component, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'navbar',
  templateUrl: 'navbar.component.html',
  styleUrls: ['navbar.component.css']
})
export class NavBar {
    @Output() block = new EventEmitter<void>();
    setBlock(){
      this.block.emit();
      console.log('omg!');
    }
}
