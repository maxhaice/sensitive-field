import { Component, } from '@angular/core';

@Component({
  selector: 'test',
  template: '<div></div>',
  styleUrls: ['test.component.css']
})
export class Test {
    groups = [
        {
          "name": "pencils",
          "items": "red pencil"
        },
        {
          "name": "rubbers",
          "items": "big rubber"
        },
      ];
    
      addItem(index) {
        var currentElement = this.groups[index];
        this.groups.splice(index, 0, currentElement);
      }
}
