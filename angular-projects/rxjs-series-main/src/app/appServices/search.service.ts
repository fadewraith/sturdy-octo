import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { Search } from "../appInterface/search.interface";

@Injectable({
    providedIn: 'root'
})

export class SearchService {

    url = 'https://my-json-server.typicode.com/Uxtrendz/apis/videoList';

    constructor(private http: HttpClient) { }

    getSearches(searchTerm: any): Observable<Search> {
        return this.http.get<Search>(this.url+'?q='+searchTerm);
    }

}