import { Component, OnInit } from '@angular/core';
import { faCaretUp, faCheck, faPencilAlt } from '@fortawesome/free-solid-svg-icons';
import { Category } from '../category';
import { CategoryService } from '../category.service';
import { TaskService } from '../task.service';
import { Task } from '../task';
import { FlipProp } from '@fortawesome/fontawesome-svg-core';
import { FormBuilder } from '@angular/forms';
import { formatDate } from '@angular/common';

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
  showFinished: boolean = true;
  flipCaret: FlipProp = "vertical";
  taskModifierVisible: boolean = false;

  constructor(
    private categoryService: CategoryService,
    private taskService: TaskService,
    private formBuilder: FormBuilder
  ) { }

  taskForm = this.formBuilder.group({
    id: '',
    name: '',
    description: [''],
    dueDate: ['']
  });

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

    addCategory(name: string) {
      console.log(name);
      this.categoryService.addCategory(name).subscribe(
        data => {
          this.categories.push(data);
          console.log(this.categories);
        },
        error => {
          console.log(error);
        });
    }

    addTask(name:string){
      console.log(name);
      let task: Task = {} as Task;
      task.name = name;
      task.categoryId = this.activeCategoryId;

      this.taskService.addTask(task).subscribe(
        data => {
          this.tasks.push(data);
          this.sortTasks();
          console.log(this.tasks);
          this.displayCategory(this.activeCategoryId);
        },
        error => {
          console.log(error);
        });
    }

    changeShowFinished() {
      this.showFinished = !this.showFinished;
      this.flipCaret = this.showFinished ? "vertical" : "horizontal";
    }

    finishTask(task: Task){
      task.finished = !task.finished;
      this.taskService.updateTask(task).subscribe(
        data => {
          console.log(data);
          this.sortTasks();
        },
        error => {
          console.log(error);
          task.finished = !task.finished;
          this.sortTasks();
        });
    }

    showTaskModifier(task: Task) {
      console.log(task);
      this.taskModifierVisible = true;

      let desc: string = '';
      let date: Date = new Date('yyyy-MM-dd');

      if(task.description) 
        desc = task.description;
      

      if(task.dueDate) 
        date = task.dueDate;
      
      this.taskForm.setValue({
        id: task.id,
        name: task.name,
        description: desc,
        dueDate: date
      });
    }

    updateTask(){
      let task: Task = {} as Task;
      task.id = this.taskForm.value.id;
      task.name = this.taskForm.value.name;
      task.description = this.taskForm.value.description;
      task.dueDate = this.taskForm.value.dueDate;

      this.taskService.updateTask(task).subscribe(
        data => {
          console.log(data);
          this.tasks.filter(t => t.id === task.id)[0] = task;
          this.sortTasks();
          this.taskModifierVisible = false;
          if(this.activeCategoryId === 0) 
            this.displayPlannedCategory();
          else
            this.displayCategory(this.activeCategoryId);
          
        },
        error => {
          console.log(error);
        });
    }
}
