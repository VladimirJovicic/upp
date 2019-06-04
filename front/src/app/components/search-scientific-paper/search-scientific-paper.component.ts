import { Component, OnInit } from '@angular/core';
import { SearchService } from './search.service';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-search-scientific-paper',
  templateUrl: './search-scientific-paper.component.html',
  styleUrls: ['./search-scientific-paper.component.css']
})
export class SearchScientificPaperComponent implements OnInit {

  scientificPapers : any;
  magazine : String;
  moreLikeThisData : any;
  geoPointData : any;

  constructor(private searchService:SearchService) { }

  ngOnInit() {
  }

  onSearchSubmit(form: NgForm) {

    let json = "{";
    let searchField = '';
    let searchValue = '';
    let optionalArray = [];
    let phraseArray = [];
    let count = 0;

    if(form.value.magazine !== '') {
      json += "\"magazine\":" + "\"" + form.value.magazine + "\",";
      searchField = 'magazine';
      searchValue = form.value.magazine;
      count++;

      if(form.value.magazineOptional)
        optionalArray.push('magazine');
      
      if(form.value.magazinePhrase)
        phraseArray.push('magazine');
      
    }

    if(form.value.title !== '') {
      json += "\"title\":" + "\"" + form.value.title + "\",";
      searchField = 'title';
      searchValue = form.value.title;
      count++;

      if(form.value.titleOptional)
        optionalArray.push('title');

      if(form.value.titlePhrase)
        phraseArray.push('title');
    }

    if(form.value.author !== '') {
      json += "\"author\":" + "\"" + form.value.author + "\",";
      searchField = 'author';
      searchValue = form.value.author;
      count++;

      if(form.value.authorOptional)
        optionalArray.push('author');

      if(form.value.authorPhrase)
        phraseArray.push('author');
    }

    if(form.value.keywords !== '') {
      json += "\"keywords\":" + "\"" + form.value.keywords + "\",";
      searchField = 'keywords';
      searchValue = form.value.keywords;
      count++;

      if(form.value.keywordsOptional)
        optionalArray.push('keywords');

      if(form.value.keywordsPhrase)
        phraseArray.push('keywords');
    }

    if(form.value.content !== '') {
      json += "\"pdfText\":" + "\"" + form.value.content + "\",";
      searchField = 'pdfText';
      searchValue = form.value.content;
      count++;

      if(form.value.contentOptional)
        optionalArray.push('pdfText');

      if(form.value.contentPhrase)
        phraseArray.push('pdfText');
    }

    if(form.value.scientificArea !== '') {
      json += "\"scientificArea\":" + "\"" + form.value.scientificArea + "\",";
      searchField = 'scientificArea';
      searchValue = form.value.scientificArea;
      count++;

      if(form.value.scientificAreaOptional)
        optionalArray.push('scientificArea');

      if(form.value.scientificAreaPhrase)
        phraseArray.push('scientificArea');
    }

    json = json.slice(0, json.length-1);

      if(optionalArray.length === 0 && phraseArray.length === 0) {

        console.log('0 bool & 0 phrase');

        json += "}";
      } 
      else if(optionalArray.length > 0 && phraseArray.length === 0) {
        console.log('Multiple bool');

        json += ",\"optional\":[";

        for(let optional of optionalArray) {
          json += "\"" + optional + "\"" + ",";
        }

        json = json.slice(0, json.length-1);
        json += "]}";

        
      }
      else if(optionalArray.length === 0 && phraseArray.length > 0) {
        console.log('Multiple phrase');

        json += ",\"phrase\":[";

        for(let phrase of phraseArray) {
          json += "\"" + phrase + "\"" + ",";
        }

        json = json.slice(0, json.length-1);
        json += "]}";
      } 
      else {
        console.log('Multiple bool & phrase');

        json += ",\"optional\":[";

        for(let optional of optionalArray) {
          json += "\"" + optional + "\"" + ",";
        }

        json = json.slice(0, json.length-1);
        json += "],";
        json += "\"phrase\":[";

        for(let phrase of phraseArray) {
          json += "\"" + phrase + "\"" + ",";
        }

        json = json.slice(0, json.length-1);
        json += "]}";

        }

        

        if(json === "}") {
          json = "{}";
        }

        console.log(json);

        this.searchService.searchByMultipleOptionalFields(json)
        .subscribe(data => {
          console.log(data);
          this.scientificPapers = data;
        });
      

    }

    download(title : string) {
      this.searchService.downloadPdf(title);
    }

    buy(title: string) {
      alert('Raise all payment servers to buy magazine: ' + title + ' !');
    }

    moreLikeThis(id : string) {
      this.searchService.searchByMoreLikeThis(id).subscribe(data => {
        console.log('MoreLikeThis results: ');
        console.log(data);
        this.moreLikeThisData = data;
      });
    }
    
    geoSearch(location : any, id : string) {
      this.searchService.searchByGeoPoint(location['lat'], location['lon']).subscribe(data => {
        console.log('GeoSearch results: ');
        this.geoPointData = data;

        // this.geoPointData.splice(this.geoPointData.findIndex(function(i){
        //   return i.id === id;
        // }), 1);
        console.log(this.geoPointData);

      });
    }
}
