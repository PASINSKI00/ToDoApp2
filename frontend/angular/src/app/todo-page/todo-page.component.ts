import { Component, OnInit } from '@angular/core';
import { faCaretUp, faCheck, faPencilAlt } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-todo-page',
  templateUrl: './todo-page.component.html',
  styleUrls: ['./todo-page.component.css']
})
export class TodoPageComponent implements OnInit {
  faCheck = faCheck;
  faPencil = faPencilAlt;
  faCaretUp = faCaretUp;

  constructor() { }

  ngOnInit(): void {
  }
}
