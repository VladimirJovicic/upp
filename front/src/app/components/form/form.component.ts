import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { TasksService } from '../tasks/tasks.service';

@Component({
  selector: 'app-form',
  templateUrl: './form.component.html',
  styleUrls: ['./form.component.css']
})
export class FormComponent implements OnInit {

  form : any;
  taskId: any;
  reviewerForm: boolean = false;
  public reviewerIds;
  taskName: string;
  paper: any = {};
  badFormatingMessage: string;
  reviews: any = [];

  constructor(private taskService:TasksService,
              private router: Router) { }

  ngOnInit() {
    this.taskName = localStorage.getItem('taskName');
    if(this.taskName == 'check_scientific_paper') {
      this.getScientificPaper();
    }

    if(this.taskName == 'submit_paper_again') {
      this.getBadFormattingMessage();
    }

    if(this.taskName == 'author_making_decision' || this.taskName == 'pdf_correction') {
      this.getReviews();
    }
    
    var retrievedObject = localStorage.getItem('form');
    this.form = JSON.parse(retrievedObject);
    if(this.form[0].id === 'reviewerId') {
      this.reviewerForm = true;
    }
    this.taskId = localStorage.getItem('taskId');
  }

  getScientificPaper() {
    this.taskService.getScientificPaper(localStorage.getItem('taskId')).subscribe(
      data=>{
        this.paper = data;
      }
    )
  }

  getBadFormattingMessage() {
    this.taskService.getBadFormattingMessage(localStorage.getItem('taskId')).subscribe(
      (data:any)=>{
        this.badFormatingMessage = data.message;
        console.log(data)
      }
    )
  }

  getReviews() {
    this.taskService.getReviews (localStorage.getItem('taskId')).subscribe(
      (data:any)=>{
        this.reviews = data;
      }
    )
  }

  submitForm(formValue, f) {
    console.log(formValue);
    console.log(f);
    this.taskService.executeTask(formValue, this.taskId)
    .subscribe( data => {
      console.log(data);
      this.router.navigate(['/tasks']);
    }
    )
  }

  submitReviewers() {
    console.log('Reviewer ids: ');
    console.log(this.reviewerIds);
    console.log('Reviewer ids after split: ');
    console.log(this.reviewerIds.split(','));
    console.log('Reviewer ids after json stringify: ');
    console.log(JSON.stringify(this.reviewerIds.split(',')))

    let body = { reviewers: this.reviewerIds.split(',')}
    this.taskService.executeTaskReviewers(body, this.taskId)
      .subscribe( (res: any) => {
        console.log('Succesfully executed executeTaskReviewers.');
        console.log(res);
        this.router.navigate(['/tasks']);
      });
  }

}
