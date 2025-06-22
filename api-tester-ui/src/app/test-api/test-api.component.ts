import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ApiService, ApiRequest, ApiResponse } from '../services/api.service';
import { FormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatIconModule } from '@angular/material/icon';
import { MatTabsModule } from '@angular/material/tabs';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatDividerModule } from '@angular/material/divider';
import { MatCheckboxModule } from '@angular/material/checkbox';

@Component({
  selector: 'app-test-api',
  standalone: true,
  imports: [
    CommonModule, 
    FormsModule, 
    MatCardModule,
    MatButtonModule,
    MatInputModule,
    MatSelectModule,
    MatIconModule,
    MatTabsModule,
    MatProgressSpinnerModule,
    MatDividerModule,
    MatCheckboxModule
  ],
  templateUrl: './test-api.component.html',
  styleUrls: ['./test-api.component.scss'],
  encapsulation: ViewEncapsulation.None,
  styles: [`
    .api-tester-container {
      display: flex;
      flex-direction: column;
      gap: 16px;
      padding: 16px;
      max-width: 1200px;
      margin: 0 auto;
    }
    
    .request-card, .response-card {
      width: 100%;
      box-shadow: 0 4px 6px rgba(0,0,0,0.1);
      border-radius: 8px;
      overflow: hidden;
      margin-bottom: 20px;
    }
    
    .common-urls-container {
      margin-bottom: 16px;
    }
    
    .url-method-container {
      display: flex;
      gap: 16px;
      align-items: center;
      margin-bottom: 16px;
    }
    
    .method-selector {
      width: 120px;
      min-width: 120px;
    }
    
    .url-input {
      flex: 1;
    }
    
    .headers-container {
      display: flex;
      flex-direction: column;
      gap: 8px;
      padding: 16px 0;
    }
    
    .header-option {
      margin-bottom: 16px;
    }
    
    .header-item {
      display: flex;
      gap: 16px;
      align-items: center;
    }
    
    .actions {
      display: flex;
      justify-content: flex-end;
      margin-top: 16px;
    }
  `]
})
export class TestApiComponent implements OnInit {
  headerItems: {key: string, value: string}[] = [];
  
  request: ApiRequest = {
    method: 'GET',
    url: '',
    headers: {},
    body: ''
  };
  
  response?: ApiResponse;
  isLoading = false;
  error?: string;
  
  addJsonContentType = true;
  
  commonUrls = [
    { name: 'OpenAI Email API', url: 'http://localhost:8087/api/openai/email', method: 'POST' }
  ];
  
  constructor(private apiService: ApiService) {}
  
  ngOnInit(): void {
    // Initialize with common headers
    this.headerItems = [
      { key: '', value: '' }
    ];
  }
  
  selectCommonUrl(url: string, method: string): void {
    this.request.url = url;
    this.request.method = method;
    
    // Set default body if it's the OpenAI endpoint
    if (url.includes('/api/openai/email')) {
      this.request.body = JSON.stringify({
        "prompt": "Write a short email introducing myself for a job application."
      }, null, 2);
    }
  }
  
  addHeader(): void {
    this.headerItems.push({ key: '', value: '' });
  }
  
  removeHeader(index: number): void {
    this.headerItems.splice(index, 1);
  }
  
  updateHeaders(): void {
    this.request.headers = {};
    this.headerItems.forEach(item => {
      if (item.key.trim()) {
        this.request.headers[item.key] = item.value;
      }
    });
    
    // Add Content-Type if not present and option is checked
    if (this.addJsonContentType && !this.request.headers['Content-Type']) {
      this.request.headers['Content-Type'] = 'application/json';
    }
  }
  
  formatJson(jsonString: string): string {
    try {
      const obj = JSON.parse(jsonString);
      return JSON.stringify(obj, null, 2);
    } catch (e) {
      return jsonString;
    }
  }
  
  send(): void {
    this.updateHeaders();
    this.isLoading = true;
    this.error = undefined;
    
    this.apiService.executeRequest(this.request).subscribe({
      next: (response) => {
        this.response = response;
        if (response.body) {
          try {
            // Try to format JSON response
            this.response.body = this.formatJson(response.body);
          } catch (e) {
            // If not JSON, keep as is
          }
        }
        this.isLoading = false;
      },
      error: (err) => {
        this.error = err.message || 'An error occurred';
        this.isLoading = false;
      }
    });
  }
}
