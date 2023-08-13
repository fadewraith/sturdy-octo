import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { ArticleInterface } from './article.interface';
import { ArticlesService } from './articles.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent implements OnInit {
  searchValue = '';
  articles: ArticleInterface[] = [];
  searchForm = this.fb.nonNullable.group({
    searchValue: '',
  });

  constructor(
    private articlesService: ArticlesService,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.fetchData();
    this.searchForm.get('searchValue')?.valueChanges.subscribe((val) => {
      this.searchValue = val;
      // console.warn(val);
      this.fetchData();
    });
  }

  fetchData(): void {
    this.articlesService.getArticles(this.searchValue).subscribe((articles) => {
      this.articles = articles;
    });
  }

  onSearchSubmit(): void {
    // this.searchForm.get('searchValue')?.valueChanges.subscribe((val) => {
    //   this.searchValue = val ?? '';
    //   console.warn(val);
    // });
    // this.searchValue = this.searchForm.value.searchValue ?? '';
    this.fetchData();
  }
}
