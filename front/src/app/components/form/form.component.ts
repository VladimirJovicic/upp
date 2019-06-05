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
  smallOrBigChangesFeedback: any = {};
  pdfText: string;
  enumValues: any = [];
  reviewers: any = [];
  selectedReviewers: Array<{}> = [];

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
      this.getBigOrSmallChangeFeedback();
    }

    if(this.taskName == 'final_decision' || this.taskName == 'reviewing_process' || this.taskName == 'author_making_decision') {
      this.getPdfText();
    }

    if(this.taskName == 'scientific_paper_submission') {
      this.getScientificAreas();
    }

    if(this.taskName == 'choose_reviewers') {
      this.getReviewers();
    }
    
    var retrievedObject = localStorage.getItem('form');
    this.form = JSON.parse(retrievedObject);
    if(this.form[0].id === 'reviewerId') {
      this.reviewerForm = true;
    }

    this.taskId = localStorage.getItem('taskId');
  }

  getScientificAreas() {
    this.taskService.getScientificAreas().subscribe(
      (data:any)=> {
        this.enumValues = data;
      }
    )
  }

  getReviewers() {
    this.taskService.getReviewers().subscribe(
      (data:any)=> {
        this.reviewers = data;
      }
    )
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
      }
    )
  }

  getPdfText() {
    this.taskService.getPdfText(localStorage.getItem('taskId')).subscribe(
      (data:any)=>{
        this.pdfText = data.message;
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

  getBigOrSmallChangeFeedback(){
    this.taskService.getBigOrSmallChangeFeedback(localStorage.getItem('taskId')).subscribe(
      (data:any)=>{
        this.smallOrBigChangesFeedback = data.message;
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

  addOrRemove(reviwer:any, event){
    console.log(event.target.checked);
    if(event.target.checked == true) {
      this.selectedReviewers.push(reviwer.id+"");
    }else { 
      for(var i = 0; i < this.selectedReviewers.length; i++) {
        if(this.selectedReviewers[i]+"" == reviwer.id+"") {
          this.selectedReviewers.splice(i,1); 
        }
      }
    }
    console.log(this.selectedReviewers);
  }

  submitReviewers() {
    let body = { reviewers: this.selectedReviewers}
    this.taskService.executeTaskReviewers(body, this.taskId)
      .subscribe( (res: any) => {
        console.log('Succesfully executed executeTaskReviewers.');
        console.log(res);
        this.router.navigate(['/tasks']);
      });
  }

}
