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
  hoursOfFinishRev: Number = 1;
  reviewerDecision: string = "Prihvatiti";

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

    if(this.taskName == 'scientific_paper_submission' || this.taskName == 'submit_paper_again') {
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
    this.taskService.getReviewers(localStorage.getItem('taskId')).subscribe(
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
    
    if(this.taskName == "reviewing_process") {
      var odluka = formValue["review"] +" -> Predlog : "+this.reviewerDecision;
      formValue["review"] = odluka;
    }

    console.log(formValue)
    
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
    if(this.selectedReviewers.length < 2){
      alert("You must select at least 2 reivewers!")
    }else if (this.hoursOfFinishRev < 1) {
      alert("Number of hours must be greater than 0");
      this.hoursOfFinishRev = 1;
    }else {
    let body = { reviewers: this.selectedReviewers,
                 hoursOfFinishRev:this.hoursOfFinishRev }
    this.taskService.executeTask(body, this.taskId)
      .subscribe( (res: any) => {
        console.log('Succesfully executed executeTaskReviewers.');
        console.log(res);
        this.router.navigate(['/tasks']);
      });
    }
  }


}
