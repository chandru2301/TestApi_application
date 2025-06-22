import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

export interface ApiRequest {
  url: string;
  method: string;
  headers: Record<string, string>;
  body: string;
}

export interface ApiResponse {
  statusCode: number;
  headers: Record<string, string>;
  body: string;
  responseTimeMs: number;
}

@Injectable({
  providedIn: 'root'
})
export class ApiService {
  // Use relative URL for proxy support
  private apiUrl = 'http://localhost:8081/api/test/execute';

  constructor(private http: HttpClient) { }

  executeRequest(request: ApiRequest): Observable<ApiResponse> {
    return this.http.post<ApiResponse>(this.apiUrl, request)
      .pipe(
        catchError(this.handleError)
      );
  }

  private handleError(error: HttpErrorResponse) {
    let errorMessage = '';
    
    if (error.error instanceof ErrorEvent) {
      // Client-side error
      errorMessage = `Error: ${error.error.message}`;
    } else {
      // Server-side error
      errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;
    }
    
    return throwError(() => new Error(errorMessage));
  }
}
