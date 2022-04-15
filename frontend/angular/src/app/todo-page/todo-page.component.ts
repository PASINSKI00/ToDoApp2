import { Component, OnInit } from '@angular/core';
import { faCaretUp, faCheck, faPencilAlt } from '@fortawesome/free-solid-svg-icons';
import { Category } from '../category';
import { CategoryService } from '../category.service';
import { TaskService } from '../task.service';
import { Task } from '../task';

@Component({
  selector: 'app-todo-page',
  templateUrl: './todo-page.component.html',
  styleUrls: ['./todo-page.component.css']
})
export class TodoPageComponent implements OnInit {
  faCheck = faCheck;
  faPencil = faPencilAlt;
  faCaretUp = faCaretUp;
  categories: Array<Category> = [];
  tasks: Array<Task> = [];
  finishedTasks: Array<Task> = [];
  unfinishedTasks: Array<Task> = [];
  activeCategoryId: number = 0;

  constructor(
    private categoryService: CategoryService,
    private taskService: TaskService
  ) { }

  ngOnInit(): void {
    this.getCategories();
    this.displayPlannedCategory();
  }

  displayCategory(id: number) {
    this.activeCategoryId = id;
    this.taskService.getTasksByCategory(id).subscribe(
      data => {
        this.tasks = data;
        this.sortTasks();
        console.log(this.tasks);
      },
      error => {
        console.log(error);
      }
    );
    console.log(id);
  }

  getCategories(){
    this.categoryService.getCategories().subscribe(
      data => {
        this.categories = data;
      },
      error => {
        console.log(error);
      }
      );
    }
    
    editTask(id: number) {
      console.log(id);
    }

    displayPlannedCategory() {
      this.activeCategoryId = 0;
      this.taskService.getPlannedTasks().subscribe(
        data => {
          this.unfinishedTasks = data;
          this.finishedTasks = [];
          console.log(this.tasks);
        },
        error => {
          console.log(error);
        });
      }

    private sortTasks() {
      this.unfinishedTasks = this.tasks.filter(task => !task.finished);
      this.finishedTasks = this.tasks.filter(task => task.finished);
    }
}
