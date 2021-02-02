import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AppService {

  API_PREFIX = "http://localhost:9898/api"
  CATEGORIES_API = `${this.API_PREFIX}/categories`
  CONVERSION_API = (category, from, to, value) => `${this.API_PREFIX}/convert/${category}/${from}/${to}/${value}`;
  constructor(private http: HttpClient) { }

  getCategories() {
    return this.http.get(this.CATEGORIES_API);
  }

  convertValue(category: string, from: string, to: string, value: number) {
    return this.http.get(this.CONVERSION_API(category, from, to, value))
  }


}
