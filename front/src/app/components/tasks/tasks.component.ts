import { Component, OnInit } from '@angular/core';
import { TasksService } from './tasks.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-tasks',
  templateUrl: './tasks.component.html',
  styleUrls: ['./tasks.component.css']
})
export class TasksComponent implements OnInit {

  tasks : any;
  form : any;
  taskID : any;

  constructor(private taskService:TasksService,
              private router: Router) { }

  ngOnInit() {
    this.taskService.getAll()
    .subscribe( data => {
      this.tasks = data;
      console.log(this.tasks);
    },
      error => console.log(error));
  }

  executeTask(taskId : string) {
    this.taskID = taskId;
    this.taskService.getFormFromTask(taskId)
    .subscribe( data => {
      this.form = data['formFields'];

      let properties = new Array();
      for (var property in this.form) {
        properties.push({fieldId : property, fieldValue : this.form[property]});
      }
      localStorage.setItem('taskName',data.taskName )
      localStorage.setItem('form', JSON.stringify(this.form));
      localStorage.setItem('taskId', this.taskID);
      console.log(this.form);
      this.router.navigate(['/form']);
    },
      error => console.log(error));
  }

}
